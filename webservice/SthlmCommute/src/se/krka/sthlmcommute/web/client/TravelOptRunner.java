package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import se.krka.travelopt.Money;
import se.krka.travelopt.Prices;
import se.krka.travelopt.Ticket;
import se.krka.travelopt.TravelOpt;
import se.krka.travelopt.TravelPlan;
import se.krka.travelopt.TravelResult;
import se.krka.travelopt.Util;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TravelOptRunner implements Runnable {
    private final TravelOptLocale locale;
    private final Label errorLabel;
    private final CellTable<Ticket> ticketCellTable;

    private List<ScheduleEntry> entries;
    private PriceCategories priceCategories;
    private final Label totalCostLabel;


    public TravelOptRunner(final TravelOptLocale locale) {
        this.locale = locale;
        errorLabel = new Label("error");
        totalCostLabel = new Label();

        ticketCellTable = new CellTable<Ticket>(10000);
        ticketCellTable.setWidth("60em");

        ticketCellTable.addColumn(new TextColumn<Ticket>() {
            @Override
            public String getValue(Ticket ticket) {
                return locale.formatDate(ticket.getStartDate());
            }
        }, "From");
        ticketCellTable.addColumn(new TextColumn<Ticket>() {
            @Override
            public String getValue(Ticket ticket) {
                return locale.formatDate(ticket.getEndDate());
            }
        }, "To");
        ticketCellTable.addColumn(new TextColumn<Ticket>() {
            @Override
            public String getValue(Ticket ticket) {
                return String.valueOf(ticket.getNumberOfTickets());
            }
        }, "Number of tickets");

        ticketCellTable.addColumn(new TextColumn<Ticket>() {
            @Override
            public String getValue(Ticket ticket) {
                return ticket.getTicketType().toString();
            }
        }, "Type");
        ticketCellTable.addColumn(new TextColumn<Ticket>() {
            @Override
            public String getValue(Ticket ticket) {
                return ticket.getCost().toString();
            }
        }, "Cost");

        errorLabel.setVisible(false);
        ticketCellTable.setVisible(false);

        RootPanel.get("result").add(errorLabel);
        RootPanel.get("result").add(totalCostLabel);
        RootPanel.get("result").add(ticketCellTable);
    }

    public void setup(List<ScheduleEntry> entries, PriceCategories priceCategories) {
        this.entries = entries;
        this.priceCategories = priceCategories;
    }

    @Override
    public void run() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
        totalCostLabel.setText("");
        totalCostLabel.setVisible(false);
        ticketCellTable.setRowData(Collections.EMPTY_LIST);
        ticketCellTable.setVisible(false);

        String s = optimize(entries, false, priceCategories.getSelected());
        if (s != null) {
            errorLabel.setText(s);
            errorLabel.setVisible(true);
        } else {
            ticketCellTable.setVisible(true);
        }
    }

    public String optimize(List<ScheduleEntry> entries, boolean extend, String priceCategory) throws IllegalArgumentException {
        try {
            TravelPlan.Builder builder = TravelPlan.builder(locale);
            int[] lastWeekdays = null;
            for (ScheduleEntry entry : entries) {
                lastWeekdays = entry.getWeekdays().getTickets();
                builder.addPeriod(entry.getInterval().getFrom(), entry.getInterval().getTo(), lastWeekdays);
            }
            TravelPlan travelPlan;
            if (extend && lastWeekdays != null) {
                travelPlan = builder.buildExtended(lastWeekdays);
            } else {
                travelPlan = builder.build();
            }
            if (travelPlan.getDates().isEmpty()) {
                return locale.mustSelectPeriod();
            }
            ;

            TravelOpt travelOpt = new TravelOpt(Prices.getPriceCategory(priceCategory, locale));
            TravelResult result = travelOpt.findOptimum(travelPlan);
            renderResult(result);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private void renderResult(TravelResult result) {
        List<Ticket> tickets = summarize(result.getTickets());
        ticketCellTable.setRowData(tickets);
        totalCostLabel.setText("Total cost: " + result.getTotalCost());
        totalCostLabel.setVisible(true);
    }

    private List<Ticket> summarize(List<Ticket> tickets) {
        ArrayList<Ticket> res = new ArrayList<Ticket>();

        Ticket prev = null;
        for (Ticket ticket : tickets) {
            if (prev != null) {
                boolean sameType = ticket.getTicketType().equals(prev.getTicketType());
                boolean adjacent = Util.numDays(prev.getEndDate(), ticket.getStartDate()) == 2;
                if (sameType && adjacent) {
                    Money newCost = prev.getCost().add(ticket.getCost());
                    int numberOfTickets = prev.getNumberOfTickets() + ticket.getNumberOfTickets();
                    prev = new Ticket(locale, newCost, prev.getTicketType(), prev.getStartDate(), ticket.getEndDate(), numberOfTickets);
                } else {
                    res.add(prev);
                    prev = ticket;
                }
            } else {
                prev = ticket;
            }
        }
        if (prev != null) {
            res.add(prev);
        }
        return res;
    }
}
