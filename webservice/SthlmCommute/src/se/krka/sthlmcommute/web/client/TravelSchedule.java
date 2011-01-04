package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import se.krka.sthlmcommute.web.client.async.AsyncWidget;
import se.krka.sthlmcommute.web.client.async.AsyncWidgetLoader;
import se.krka.sthlmcommute.web.client.async.AsyncWidgetUsage;
import se.krka.sthlmcommute.web.client.components.dateinterval.DateIntervalUpdateListener;
import se.krka.sthlmcommute.web.client.util.DelayedWork;
import se.krka.sthlmcommute.web.client.util.UIUtil;
import se.krka.travelopt.localization.TravelOptLocale;

public class TravelSchedule extends Composite {

    private final AsyncWidget<TravelScheduleList> asyncTravelScheduleList;

    private final DelayedWork worker;
    private final Button newButton;
    private final ClientConstants clientConstants;

    public TravelSchedule(final ClientConstants clientConstants, final TravelOptLocale locale, final DelayedWork worker, final ClientMessages clientMessages) {
        this.worker = worker;
        this.clientConstants = clientConstants;
        asyncTravelScheduleList = new AsyncWidget<TravelScheduleList>(new AsyncWidgetLoader<TravelScheduleList>() {
            @Override
            public TravelScheduleList load() {
                return new TravelScheduleList(locale, worker, clientConstants, clientMessages);
            }
        });

        newButton = new Button(clientConstants.newEntry());

        initWidget(UIUtil.wrapCaption(clientConstants.schedule(), createView()));
    }

    private HorizontalPanel createView() {
        final HorizontalPanel horiz = new HorizontalPanel();
        VerticalPanel vert = new VerticalPanel();

        final Button deleteButton = new Button(clientConstants.delete());
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
