package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HelpSection extends Composite implements DateIntervalUpdateListener {

    private final List<HelpElement> elements = new ArrayList<HelpElement>();
    private final HelpElement priceCategory;
    private final HelpElement newEntry;
    private final HelpElement selectDates;

    public HelpSection(PriceCategories priceCategories,
                       TravelSchedule travelSchedule,
                       RangeEditor rangeEditor) {
        priceCategory = createAndAdd(
                "Choosing a price category",
                new Label("Are you paying full price or do you get a discount for being under 20 years old or being a senior citizen? " +
                        "Pick the appropriate option."),
                priceCategories);

        newEntry = createAndAdd(
                "Creating a schedule",
                new Label("Start by creating a new entry for your travel schedule."), travelSchedule);

        selectDates = createAndAdd("Selecting an interval", new Label("Select a time interval for your new entry"), rangeEditor);


        VerticalPanel root = new VerticalPanel();
        for (HelpElement element : elements) {
            root.add(element.getWidget());
        }
        initWidget(root);

        priceCategories.addListener(this);
        travelSchedule.addListener(this);
        rangeEditor.getIntervalPicker().addListener(this);

        priceCategory.open();
    }

    private HelpElement createAndAdd(String header, Widget content, Widget highlight) {
        HelpElement element = new HelpElement(this, header, content, highlight);
        elements.add(element);
        return element;
    }

    public void closeAllExcept(HelpElement except) {
        for (HelpElement element : elements) {
            if (except != element) {
                element.close();
            }
        }
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

    @Override
    public void intervalChanged(DateIntervalPicker picker, Date fromValue, Date toValue) {
        if (fromValue != null && toValue != null) {
            if (selectDates.tryConsume()) {
                selectDates.close();

                // next .open();
            }
        }
    }
}
