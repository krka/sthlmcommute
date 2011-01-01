package se.krka.sthlmcommute.web.client;

import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.Collections;
import java.util.List;

public class TravelInterface {
    private final TravelOptLocale locale;
    private final ClientConstants clientConstants;

    private final Help help;
    private final PriceCategories priceCategories;
    private final TravelSchedule travelSchedule;

    private List<ScheduleEntry> entries;

    public TravelInterface(TravelOptLocale locale, ClientConstants clientConstants) {
        this.locale = locale;
        this.clientConstants = clientConstants;
        help = new Help(clientConstants);

        TravelOptRunner travelOptRunner = new TravelOptRunner(locale);
        DelayedWork worker = new DelayedWork(travelOptRunner);

        priceCategories = new PriceCategories(this, clientConstants, worker);
        travelSchedule = new TravelSchedule(clientConstants, locale, worker);

        travelOptRunner.setup(travelSchedule.getList().getList(), priceCategories);
    }

    private boolean getBoolValue(Boolean value) {
        return value != null && value.booleanValue();
    }

    private RangeEditor createRangeForm() {
        final RangeEditor rangeEditor = new RangeEditor();

        /*
        add.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                errorLabel.setText("");
                if (rangeEditor.getFrom().getValue() == null || rangeEditor.getFrom().getValue() == null) {
                    errorLabel.setText("You need to select a date range.");
                    return;
                }
                Weekdays days = rangeEditor.getWeekdays().getWeekdays();
                if (days.countTickets() == 0) {
                    errorLabel.setText("You must require at least one ticket");
                    return;
                }
                ScheduleEntry entry = new ScheduleEntry(
                        rangeEditor.getFrom().getValue(),
                        rangeEditor.getTo().getValue(),
                        days,
                        entries,
                        TravelInterface.this);
                lastEntry = entry;
                entries.add(entry);
                //RootPanel.get("addEntriesContainer").add(entry);
                updateTravelSuggestion();
            }
        });

        reset.click();
        */

        return rangeEditor;
    }


    private void addPeriodList() {
        CellTable<ScheduleEntry> table = new CellTable<ScheduleEntry>(10);
        table.setSelectionModel(new MultiSelectionModel<Object>());

        TextColumn<ScheduleEntry> days = new TextColumn<ScheduleEntry>() {
            @Override
            public String getValue(ScheduleEntry scheduleEntry) {
                return scheduleEntry.getInterval().getDays() + "";
            }
        };
        table.addColumn(days, clientConstants.days());

        Header header = new TextHeader(clientConstants.when());

        TextColumn<ScheduleEntry> from = new TextColumn<ScheduleEntry>() {
            @Override
            public String getValue(ScheduleEntry scheduleEntry) {
                return locale.formatDate(scheduleEntry.getInterval().getFrom());
            }
        };

        table.addColumn(from, header);
        TextColumn<ScheduleEntry> to = new TextColumn<ScheduleEntry>() {
            @Override
            public String getValue(ScheduleEntry scheduleEntry) {
                DateInterval interval = scheduleEntry.getInterval();
                if (interval.getFrom().equals(interval.getTo())) {
                    return "";
                }
                return locale.formatDate(interval.getTo());
            }
        };
        table.addColumn(to, header);


        for (int i = 0; i < 7; i++) {
            final int finalI = i;
            TextColumn<ScheduleEntry> weekdayColumn = new TextColumn<ScheduleEntry>() {
                @Override
                public String getValue(ScheduleEntry scheduleEntry) {
                    int tickets = scheduleEntry.getWeekdays().calcValue(finalI);
                    switch (tickets) {
                        case 0: return "";
                        case 9: return "9+";
                        default: return "" + tickets;
                    }
                }
            };
            weekdayColumn.setHorizontalAlignment(HasHorizontalAlignment.HorizontalAlignmentConstant.endOf(HasDirection.Direction.DEFAULT));
            table.addColumn(weekdayColumn, locale.weekDayName(i).substring(0, 1));
        }
        table.setWidth("1px");
        ListDataProvider<ScheduleEntry> listDataProvider = new ListDataProvider<ScheduleEntry>();
        entries = listDataProvider.getList();
        listDataProvider.addDataDisplay(table);

        RootPanel.get("addEntriesContainer").add(table);
    }

    /*
    private void addPriceList() {
        RootPanel panel = RootPanel.get("priceList");
        for (Widget widget : panel) {
            widget.removeFromParent();
        }

        int index = priceCategories.getSelectedIndex();
        String value = priceCategories.getValue(index);
        PriceStructure priceCategory = Prices.getPriceCategory(value, locale);

        int i = 0;
        List<TicketType> ticketTypes = priceCategory.getTicketTypes();
        Grid grid = new Grid(ticketTypes.size(), 1);
        for (TicketType ticket: ticketTypes) {
            grid.setWidget(i, 0, new Label(ticket.toString()));
            i++;
        }
        panel.add(grid);
    }

    private void addPriceCategories() {
        priceCategories = new ListBox();
        priceCategories.addItem(clientConstants.fullPrice(), "full");
        priceCategories.addItem(clientConstants.reducedPrice(), "reduced");
        priceCategories.setSelectedIndex(0);

        priceCategories.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                updateTravelSuggestion();
                addPriceList();
            }
        });
        RootPanel.get("priceCategories").add(priceCategories);
    }
*/
    void updateTravelSuggestion() {
        Collections.sort(entries);
        //result.setText("Waiting for reply...");
        //String s = optimize(entries, getBoolValue(extend.getValue()), priceCategories.getValue(priceCategories.getSelectedIndex()));
        //SthlmCommute.this.result.setText(s);
    }


    public void setup() {
        //RootPanel.get("help").add(help);
        RootPanel.get("priceCategories").add(priceCategories);
        RootPanel.get("travelSchedule").add(travelSchedule);

        start();

        /*
        addPriceCategories();

        addPriceList();

        addPeriodList();


        RangeEditor rangeForm = createRangeForm();
        rangeForm.getIntervalPicker().install();

        RootPanel.get("datepicker").add(rangeForm.getDates());
        RootPanel.get("datepicker").add(rangeForm.getLabel());

        RootPanel.get("defaulttickets").add(rangeForm.getWeekdays().getTicket());
        RootPanel.get("ticketexceptions").add(rangeForm.getWeekdays().getWeekDayForm());

        extend = new CheckBox("Optimize for long term travel");
        RootPanel.get("extendContainer").add(extend);

        errorLabel = new Label();

        result = new Label();
        RootPanel.get("result").add(result);

        RootPanel.get("errorLabelContainer").add(errorLabel);
        */
    }

    private void start() {
        help.choosePriceCategory();
        travelSchedule.setVisible(false);
    }

    public void doneWithPriceCategory() {
        help.createNewEntry();
        travelSchedule.setVisible(true);
    }
}
