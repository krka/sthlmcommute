package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
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
    private OptimizeOptions optimizeOptions;


    public TravelOptRunner(final TravelOptLocale locale) {
        this.locale = locale;
        errorLabel = new Label("error");

        ticketCellTable = new CellTable<Ticket>(10000);
        ticketCellTable.setWidth("60em");

        ticketCellTable.addColumn(new TextColumn<Ticket>() {
            @Override
            public String getValue(Ticket ticket) {
                if (ticket.getStartDate() == null) {
                    return "";
                }
                return locale.formatDate(ticket.getStartDate());
            }
        }, "From");
        ticketCellTable.addColumn(new TextColumn<Ticket>() {
            @Override
            public String getValue(Ticket ticket) {
                if (ticket.getEndDate() == null) {
                    return "";
                }
                return locale.formatDate(ticket.getEndDate());
            }
        }, "To");
        ticketCellTable.addColumn(new TextColumn<Ticket>() {
            @Override
            public String getValue(Ticket ticket) {
                if (ticket.getStartDate() == null) {
                    return "";
                }
                return String.valueOf(ticket.getNumberOfTickets());
            }
        }, "Number of tickets");

        ticketCellTable.addColumn(new TextColumn<Ticket>() {
            @Override
            public String getValue(Ticket ticket) {
                if (ticket.getTicketType() == null) {
                    return "Total";
                }
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
        RootPanel.get("result").add(ticketCellTable);
    }

    public void setup(List<ScheduleEntry> entries, PriceCategories priceCategories, OptimizeOptions optimizeOptions) {
        this.entries = entries;
        this.priceCategories = priceCategories;
        this.optimizeOptions = optimizeOptions;
    }

    @Override
    public void run() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
        ticketCellTable.setRowData(Collections.EMPTY_LIST);
        ticketCellTable.setVisible(false);

        String s = optimize(entries, optimizeOptions, priceCategories.getSelected());
        if (s != null) {
            errorLabel.setText(s);
            errorLabel.setVisible(true);
        } else {
            ticketCellTable.setVisible(true);
        }
    }

    public String optimize(List<ScheduleEntry> entries, OptimizeOptions optimizeOptions, String priceCategory) throws IllegalArgumentException {
        try {
            TravelPlan.Builder builder = TravelPlan.builder(locale);
            for (ScheduleEntry entry : entries) {
                builder.addPeriod(entry.getInterval().getFrom(), entry.getInterval().getTo(), entry.getWeekdays().getTickets());
            }
            TravelPlan travelPlan;
            if (optimizeOptions.isEnabled()) {
                travelPlan = builder.buildExtended(optimizeOptions.getTickets());
            } else {
                travelPlan = builder.build();
            }
            if (travelPlan.getDates().isEmpty()) {
                return locale.mustSelectPeriod();
            }

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
        tickets.add(0, new Ticket(locale, result.getTotalCost(), null, null, null));
        ticketCellTable.setRowData(tickets);
    }

    private List<Ticket> summarize(List<Ticket> tickets) {
        ArrayList<Ticket> res = new ArrayList<Ticket>();

        Ticket prev = null;
        for (Ticket ticket : tickets) {
            if (prev != null) {
                boolean sameType = ticket.getTicketType().equals(prev.getTicketType());
                boolean adjacent = Util.dayDifference(ticket.getEndDate(), prev.getStartDate()) == 1;
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
