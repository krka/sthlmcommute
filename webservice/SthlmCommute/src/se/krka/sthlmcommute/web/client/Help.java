package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.RowCountChangeEvent;
import se.krka.sthlmcommute.web.client.async.AsyncWidgetUsage;
import se.krka.sthlmcommute.web.client.components.HelpElement;
import se.krka.sthlmcommute.web.client.components.HelpSection;
import se.krka.sthlmcommute.web.client.components.dateinterval.DateIntervalPicker;
import se.krka.sthlmcommute.web.client.components.dateinterval.DateIntervalUpdateListener;
import se.krka.travelopt.TravelResult;

import java.util.Date;

public class Help {
    private final HelpSection helpSection;

    private final HelpElement priceCategory;
    private final HelpElement newEntry;
    private final HelpElement selectDates;
    private final HelpElement selectTickets;
    private final HelpElement shownResult;

    public Help(ClientConstants clientConstants, PriceCategories priceCategories, TravelSchedule travelSchedule, TravelOptRunner travelOptRunner) {
        helpSection = new HelpSection();

        priceCategory = helpSection.createAndAdd(
                clientConstants.choosePriceCategory(),
                new Label(clientConstants.choosePriceCategoryHelp()),
                priceCategories);

        newEntry = helpSection.createAndAdd(
                "Creating a schedule",
                new Label("Start by creating a new entry for your travel schedule."), travelSchedule);

        selectDates = helpSection.createAndAdd("Selecting an interval", new Label("Select a time interval for your new entry"));
        travelSchedule.getAsyncList().runWhenReady(new AsyncWidgetUsage<TravelScheduleList>() {
            @Override
            public void run(TravelScheduleList widget) {
                selectDates.setHighlight(widget.getTravelScheduleEditor().getRangeEditor());
            }
        });

        selectTickets = helpSection.createAndAdd("Selecting tickets", new Label("foo"));

        shownResult = helpSection.createAndAdd("The result", new Label("bar"), travelOptRunner.getResultPanel());
        travelOptRunner.addResultListener(new TravelResultListener() {
            @Override
            public void newResult(TravelResult result) {
                gotResult();
            }
        });

        priceCategories.getRadioGroup().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                selectedPriceCategory();
            }
        });

        travelSchedule.getAsyncList().runWhenReady(new AsyncWidgetUsage<TravelScheduleList>() {
            @Override
            public void run(TravelScheduleList widget) {
                widget.getCellList().addRowCountChangeHandler(new RowCountChangeEvent.Handler() {
                    @Override
                    public void onRowCountChange(RowCountChangeEvent rowCountChangeEvent) {
                        createdAnEntry();
                    }
                });

                selectTickets.setHighlight(widget.getTravelScheduleEditor().getTicketEditor());
                widget.addScheduleEntryUpdateListener(new ScheduleEntryUpdateListener() {
                    @Override
                    public void updated(ScheduleEntry entry) {
                        if (entry.getInterval().getDays() > 0) {
                            selectedDates();
                        }
                        if (entry.getWeekdays().countTickets() > 0) {
                            selectedTickets();
                        }
                    }
                });


            }
        });
        travelSchedule.addListener(new DateIntervalUpdateListener() {
            @Override
            public void intervalChanged(DateIntervalPicker picker, Date fromValue, Date toValue) {
                if (fromValue != null && toValue != null) {
                    selectedDates();
                }
            }
        });

        priceCategory.open();
    }

    public HelpSection getHelpSection() {
        return helpSection;
    }

    public void selectedPriceCategory() {
        if (priceCategory.tryConsume()) {
            priceCategory.close();
            newEntry.open();
        }
    }

    public void createdAnEntry() {
        if (newEntry.tryConsume()) {
            newEntry.close();

            selectDates.open();
        }
    }

    public void selectedDates() {
        if (selectDates.tryConsume()) {
            selectDates.close();

            selectTickets.open();
        }
    }


    public void selectedTickets() {
        if (selectTickets.tryConsume()) {
            selectTickets.close();

            shownResult.open();
        }
    }

    public void gotResult() {
        if (shownResult.tryConsume()) {
            shownResult.open();
        }
    }
}
