package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.view.client.RowCountChangeEvent;
import se.krka.sthlmcommute.web.client.async.AsyncWidgetUsage;
import se.krka.sthlmcommute.web.client.components.HelpElement;
import se.krka.sthlmcommute.web.client.components.HelpSection;

import java.util.Date;

public class Help {
    private final HelpElement priceCategory;
    private final HelpElement newEntry;
    private final HelpElement selectDates;
    private final HelpSection helpSection;

    public Help(PriceCategories priceCategories, TravelSchedule travelSchedule, RangeEditor rangeEditor) {
        helpSection = new HelpSection();

        priceCategory = helpSection.createAndAdd(
                "Choosing a price category",
                new Label("Are you paying full price or do you get a discount for being under 20 years old or being a senior citizen? " +
                        "Pick the appropriate option."),
                priceCategories);

        newEntry = helpSection.createAndAdd(
                "Creating a schedule",
                new Label("Start by creating a new entry for your travel schedule."), travelSchedule);

        selectDates = helpSection.createAndAdd("Selecting an interval", new Label("Select a time interval for your new entry"), rangeEditor);


        priceCategories.getRadioGroup().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                selectedPriceCategory();
            }
        });
        travelSchedule.getAsyncWidget().runWhenReady(new AsyncWidgetUsage<TravelScheduleList>() {
            @Override
            public void run(TravelScheduleList widget) {
                widget.getCellList().addRowCountChangeHandler(new RowCountChangeEvent.Handler() {
                    @Override
                    public void onRowCountChange(RowCountChangeEvent rowCountChangeEvent) {
                        createdAnEntry();
                    }
                });
            }
        });
        rangeEditor.getIntervalPicker().addListener(new DateIntervalUpdateListener() {
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

            // next .open();
        }
    }

}
