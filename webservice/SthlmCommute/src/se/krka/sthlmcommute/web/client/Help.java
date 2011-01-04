package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
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
    private final HelpElement selectCoupons;
    private final HelpElement shownResult;

    public Help(ClientConstants clientConstants,
                final PriceCategories priceCategories,
                TravelSchedule travelSchedule,
                TravelOptRunner travelOptRunner,
                OptimizeOptions optimizeOptions) {
        helpSection = new HelpSection();

        priceCategory = helpSection.createAndAdd(
                clientConstants.choosePriceCategory(),
                new HTML(clientConstants.choosePriceCategoryHelp()),
                priceCategories);

        newEntry = helpSection.createAndAdd(
                clientConstants.creatingSchedule(),
                new HTML(clientConstants.creatingScheduleHelp()),
                travelSchedule);

        selectDates = helpSection.createAndAdd(
                clientConstants.selectingDateSpan(),
                new HTML(clientConstants.selectingDateSpanHelp()));
        travelSchedule.getAsyncList().runWhenReady(new AsyncWidgetUsage<TravelScheduleList>() {
            @Override
            public void run(TravelScheduleList widget) {
                selectDates.setHighlight(widget.getTravelScheduleEditor().getRangeEditor());
            }
        });

        selectCoupons = helpSection.createAndAdd(
                clientConstants.selectingCoupons(),
                new HTML(clientConstants.selectingCouponsHelp()));

        shownResult = helpSection.createAndAdd(
				clientConstants.resultView(),
				new HTML(clientConstants.resultViewHelp()),
				travelOptRunner.getResultPanel());

        helpSection.createAndAdd(
                clientConstants.optimizeOptions(),
                new HTML(clientConstants.optimizeOptionsHelp()),
                optimizeOptions
                );
        travelOptRunner.addResultListener(new TravelResultListener() {
            @Override
            public void newResult(TravelResult result) {
                gotResult();
            }
        });

        priceCategories.getRadioGroup().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (priceCategories.getSelected() != null) {
                    selectedPriceCategory();
                }
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

                selectCoupons.setHighlight(widget.getTravelScheduleEditor().getCouponEditor());
                widget.addScheduleEntryUpdateListener(new ScheduleEntryUpdateListener() {
                    @Override
                    public void updated(ScheduleEntry entry) {
                        if (entry.getInterval().getDays() > 0) {
                            selectedDates();
                        }
                        if (entry.getWeekdays().countCoupons() > 0) {
                            selectedCoupons();
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

            selectCoupons.open();
        }
    }


    public void selectedCoupons() {
        if (selectCoupons.tryConsume()) {
            selectCoupons.close();

            shownResult.open();
        }
    }

    public void gotResult() {
        if (shownResult.tryConsume()) {
            shownResult.open();
        }
    }
}
