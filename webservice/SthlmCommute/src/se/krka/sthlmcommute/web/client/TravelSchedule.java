package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SelectionChangeEvent;
import se.krka.sthlmcommute.web.client.async.AsyncWidget;
import se.krka.sthlmcommute.web.client.async.AsyncWidgetLoader;
import se.krka.sthlmcommute.web.client.async.AsyncWidgetUsage;
import se.krka.sthlmcommute.web.client.persistors.EntryPersistor;
import se.krka.travelopt.localization.TravelOptLocale;

public class TravelSchedule extends Composite {

    private final AsyncWidget<TravelScheduleList> asyncTravelScheduleList;
    private final TravelScheduleEditor travelScheduleEditor;

    private final DelayedWork worker;
    private final Button newButton;

    public TravelSchedule(ClientConstants clientConstants, final TravelOptLocale locale, DelayedWork worker) {
        this.worker = worker;
        travelScheduleEditor = new TravelScheduleEditor(worker, locale);
        asyncTravelScheduleList = new AsyncWidget<TravelScheduleList>(new AsyncWidgetLoader<TravelScheduleList>() {
            @Override
            public TravelScheduleList load() {
                TravelScheduleList travelScheduleList1 = new TravelScheduleList(locale, travelScheduleEditor);
                travelScheduleEditor.setTravelScheduleList(travelScheduleList1);
                return travelScheduleList1;
            }
        });;

        newButton = new Button("New entry");

        HorizontalPanel panel = new HorizontalPanel();
        panel.add(createList());
        panel.add(travelScheduleEditor);

        CaptionPanel root = new CaptionPanel("Travel schedule");
        root.add(panel);
        initWidget(root);
    }

    private Widget createList() {
        VerticalPanel panel = new VerticalPanel();

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
                asyncTravelScheduleList.runWhenReady(new AsyncWidgetUsage<TravelScheduleList>() {
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
                    asyncTravelScheduleList.runWhenReady(new AsyncWidgetUsage<TravelScheduleList>() {
                        @Override
                        public void run(TravelScheduleList widget) {
                            widget.removeSelectedEntry();
                            travelScheduleEditor.setActive(null);
                            deleteButton.setEnabled(false);
                            worker.requestWork();
                        }
                    });

                }
            }
        });

        panel.add(UIUtil.wrapFlow(newButton, deleteButton));
        panel.add(asyncTravelScheduleList);
        return panel;
    }

    public RangeEditor getRangeEditor() {
        return travelScheduleEditor.getRangeEditor();
    }

    public void addListener(final HelpSection helpSection) {
        asyncTravelScheduleList.runWhenReady(new AsyncWidgetUsage<TravelScheduleList>() {
            @Override
            public void run(TravelScheduleList widget) {
                widget.addListener(helpSection);
            }
        });
    }

    public void addPersistance(final ClientPersistance persistance) {
        asyncTravelScheduleList.runWhenReady(new AsyncWidgetUsage<TravelScheduleList>() {
            @Override
            public void run(TravelScheduleList widget) {
                persistance.add(new EntryPersistor(widget));
            }
        });
    }

    public AsyncWidget<TravelScheduleList> getAsyncWidget() {
        return asyncTravelScheduleList;
    }
}
