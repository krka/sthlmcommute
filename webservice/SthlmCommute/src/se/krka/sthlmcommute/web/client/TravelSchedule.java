package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SelectionChangeEvent;
import se.krka.sthlmcommute.web.client.async.AsyncWidget;
import se.krka.sthlmcommute.web.client.async.AsyncWidgetLoader;
import se.krka.sthlmcommute.web.client.async.AsyncWidgetUsage;
import se.krka.sthlmcommute.web.client.components.dateinterval.DateIntervalPicker;
import se.krka.sthlmcommute.web.client.components.dateinterval.DateIntervalUpdateListener;
import se.krka.sthlmcommute.web.client.persistance.ClientPersistance;
import se.krka.sthlmcommute.web.client.persistance.EntryPersistor;
import se.krka.travelopt.localization.TravelOptLocale;

public class TravelSchedule extends Composite {

    private final AsyncWidget<TravelScheduleList> asyncTravelScheduleList;

    private final DelayedWork worker;
    private final Button newButton;

    public TravelSchedule(ClientConstants clientConstants, final TravelOptLocale locale, final DelayedWork worker) {
        this.worker = worker;
        asyncTravelScheduleList = new AsyncWidget<TravelScheduleList>(new AsyncWidgetLoader<TravelScheduleList>() {
            @Override
            public TravelScheduleList load() {
                TravelScheduleList travelScheduleList1 = new TravelScheduleList(locale, worker);
                return travelScheduleList1;
            }
        });;

        newButton = new Button("New entry");

        initWidget(UIUtil.wrapCaption("Travel schedule", createView()));
    }

    private HorizontalPanel createView() {
        final HorizontalPanel horiz = new HorizontalPanel();
        VerticalPanel vert = new VerticalPanel();

        final Button deleteButton = new Button("Delete");
        deleteButton.setEnabled(false);


        asyncTravelScheduleList.runWhenReady(new AsyncWidgetUsage<TravelScheduleList>() {
            @Override
            public void run(final TravelScheduleList widget) {
                widget.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
                    @Override
                    public void onSelectionChange(SelectionChangeEvent selectionChangeEvent) {
                        ScheduleEntry obj = widget.getSelectedObject();
                        deleteButton.setEnabled(obj != null);
                    }
                });
            }
        });

        newButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                newButton.setEnabled(false);
                asyncTravelScheduleList.runASAP(new AsyncWidgetUsage<TravelScheduleList>() {
                    @Override
                    public void run(final TravelScheduleList widget) {
                        widget.createNew();
                        newButton.setEnabled(true);
                    }
                });
            }
        });

        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                if (asyncTravelScheduleList.isReady()) {
                    asyncTravelScheduleList.runASAP(new AsyncWidgetUsage<TravelScheduleList>() {
                        @Override
                        public void run(TravelScheduleList widget) {
                            widget.removeSelectedEntry();
                            widget.getTravelScheduleEditor().setActive(null);
                            deleteButton.setEnabled(false);
                            worker.requestWork();
                        }
                    });

                }
            }
        });

        vert.add(UIUtil.wrapFlow(newButton, deleteButton));
        vert.add(asyncTravelScheduleList);
        horiz.add(vert);
        asyncTravelScheduleList.runWhenReady(new AsyncWidgetUsage<TravelScheduleList>() {
            @Override
            public void run(TravelScheduleList widget) {
                horiz.add(widget.getTravelScheduleEditor());
            }
        });
        return horiz;
    }

    public void addPersistance(final ClientPersistance persistance, final Help help) {
        asyncTravelScheduleList.runASAP(new AsyncWidgetUsage<TravelScheduleList>() {
            @Override
            public void run(TravelScheduleList widget) {
                persistance.add(new EntryPersistor(widget, help, worker));
            }
        });
    }

    public AsyncWidget<TravelScheduleList> getAsyncList() {
        return asyncTravelScheduleList;
    }

    public void addListener(final DateIntervalUpdateListener listener) {
        asyncTravelScheduleList.runWhenReady(new AsyncWidgetUsage<TravelScheduleList>() {
            @Override
            public void run(TravelScheduleList widget) {
                widget.getTravelScheduleEditor().getRangeEditor().addListener(listener);
            }
        });
    }

}
