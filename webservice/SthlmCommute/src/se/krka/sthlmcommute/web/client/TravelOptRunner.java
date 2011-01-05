package se.krka.sthlmcommute.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.NoSelectionModel;
import se.krka.sthlmcommute.web.client.async.AsyncWidget;
import se.krka.sthlmcommute.web.client.async.AsyncWidgetLoader;
import se.krka.sthlmcommute.web.client.async.AsyncWidgetUsage;
import se.krka.sthlmcommute.web.client.async.GenericFailureAsyncCallback;
import se.krka.sthlmcommute.web.client.util.UIUtil;
import se.krka.travelopt.*;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.ArrayList;
import java.util.List;

public class TravelOptRunner implements Runnable {
    private final ClientConstants clientConstants;
    private final Label errorLabel;
    private final AsyncWidget<CellTable<Ticket>> asyncTable;

    private List<ScheduleEntry> entries;
    private PriceCategories priceCategories;
    private OptimizeOptions optimizeOptions;
    private final List<Ticket> tickets = new ArrayList<Ticket>();

    private final List<TravelResultListener> listeners = new ArrayList<TravelResultListener>();
    private final CaptionPanel resultPanel;
    private final ScrollPanel scrollPanel;

    public TravelOptRunner(final TravelOptLocale locale, final ClientConstants clientConstants) {
        this.clientConstants = clientConstants;
        errorLabel = new Label();

        asyncTable = new AsyncWidget<CellTable<Ticket>>(new AsyncWidgetLoader<CellTable<Ticket>>() {
            @Override
            public CellTable<Ticket> load() {
                CellTable<Ticket> ticketCellTable = new CellTable<Ticket>(5);
                ticketCellTable.setSelectionModel(new NoSelectionModel<Object>());

                ticketCellTable.setWidth("55em");

                ticketCellTable.addColumn(new TextColumn<Ticket>() {
                    @Override
                    public String getValue(Ticket ticket) {
                        if (ticket.getStartDate() == 0) {
                            return "";
                        }
                        return locale.formatDay(ticket.getStartDate());
                    }
                }, clientConstants.from());
                ticketCellTable.addColumn(new TextColumn<Ticket>() {
                    @Override
                    public String getValue(Ticket ticket) {
                        if (ticket.getEndDate() == 0) {
                            return "";
                        }
                        return locale.formatDay(ticket.getEndDate());
                    }
                }, clientConstants.to());
                ticketCellTable.addColumn(new TextColumn<Ticket>() {
                    @Override
                    public String getValue(Ticket ticket) {
                        if (ticket.getStartDate() == 0) {
                            return "";
                        }
                        return String.valueOf(ticket.getNumberOfTickets());
                    }
                }, clientConstants.numberOfTickets());

                ticketCellTable.addColumn(new TextColumn<Ticket>() {
                    @Override
                    public String getValue(Ticket ticket) {
                        if (ticket.getTicketType() == null) {
                            return clientConstants.total();
                        }
                        return ticket.getTicketType().name();
                    }
                }, clientConstants.tickettype());
                ticketCellTable.addColumn(new TextColumn<Ticket>() {
                    @Override
                    public String getValue(Ticket ticket) {
                        return ticket.getCost().toString();
                    }
                }, clientConstants.price());
                return ticketCellTable;
            }
        });
        VerticalPanel panel = new VerticalPanel();
        panel.add(errorLabel);
        scrollPanel = UIUtil.wrapScroll(asyncTable, "57em", "10em");
        panel.add(scrollPanel);
        resultPanel = UIUtil.wrapCaption(clientConstants.result(), panel);
        RootPanel.get("result").add(resultPanel);
        clear();
    }

    public CaptionPanel getResultPanel() {
        return resultPanel;
    }

    public void setup(List<ScheduleEntry> entries, PriceCategories priceCategories, OptimizeOptions optimizeOptions) {
        this.entries = entries;
        this.priceCategories = priceCategories;
        this.optimizeOptions = optimizeOptions;

        run();
    }

    @Override
    public void run() {
        clear();
        GWT.runAsync(new GenericFailureAsyncCallback(){
            @Override
            public void onSuccess() {
                clear();
                String s = optimize(entries, optimizeOptions, priceCategories.getSelected());
                if (s != null) {
                    errorLabel.setText(s);
                    errorLabel.setVisible(true);
                } else {
                    scrollPanel.setVisible(true);
                }
           }
        });
    }

    private void clear() {
        scrollPanel.setVisible(false);
        errorLabel.setVisible(false);
    }

    public String optimize(List<ScheduleEntry> entries, OptimizeOptions optimizeOptions, String priceCategory) throws IllegalArgumentException {
        try {
            TravelPlan.Builder builder = TravelPlan.builder();
            if (entries.isEmpty()) {
                return clientConstants.noEntries();
            }
            for (ScheduleEntry entry : entries) {
                if (!entry.valid()) {
                    return clientConstants.invalidEntries();
                }
                builder.addPeriod(entry.getInterval().getFrom(), entry.getInterval().getTo(), entry.getWeekdays().getCoupons());
            }
            TravelPlan travelPlan;
            if (optimizeOptions.isEnabled()) {
                travelPlan = builder.buildExtended(optimizeOptions.getCouponEditor().getWeekdays().getCoupons());
            } else {
                travelPlan = builder.build();
            }
            if (travelPlan.getDates().isEmpty()) {
                return clientConstants.emptySchedule();
            }

            TravelOpt travelOpt = new TravelOpt(Prices.getPriceCategory(priceCategory));
            TravelResult result = travelOpt.findOptimum(travelPlan);
            renderResult(result);
            notifyListeners(result);
            return null;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private void notifyListeners(TravelResult result) {
        for (TravelResultListener listener : listeners) {
            listener.newResult(result);
        }
    }

    private void renderResult(TravelResult result) {
        summarize(result.getTickets());
        addTotal(tickets, result.getTotalCost());
        asyncTable.runASAP(new AsyncWidgetUsage<CellTable<Ticket>>() {
            @Override
            public void run(CellTable<Ticket> widget) {
                widget.setRowData(tickets);
            }
        });
    }

    private void addTotal(List<Ticket> tickets, Money totalCost) {
        if (tickets.size() != 1) {
            tickets.add(0, new Ticket(totalCost, null, 0, 0));
        }
    }

    private void summarize(List<Ticket> result) {
        tickets.clear();

        Ticket prev = null;
        for (Ticket ticket : result) {
            if (prev != null) {
                boolean sameType = ticket.getTicketType().equals(prev.getTicketType());
                boolean adjacent = ticket.getStartDate() == prev.getEndDate() + 1;
                if (sameType && adjacent) {
                    Money newCost = prev.getCost().add(ticket.getCost());
                    int numberOfTickets = prev.getNumberOfTickets() + ticket.getNumberOfTickets();
                    prev = new Ticket(newCost, prev.getTicketType(), prev.getStartDate(), ticket.getEndDate(), numberOfTickets);
                } else {
                    tickets.add(prev);
                    prev = ticket;
                }
            } else {
                prev = ticket;
            }
        }
        if (prev != null) {
            tickets.add(prev);
        }
    }

    public void addResultListener(TravelResultListener listener) {
        listeners.add(listener);
    }
}
