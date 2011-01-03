package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SelectionChangeEvent;
import se.krka.travelopt.localization.TravelOptLocale;

public class TravelSchedule extends Composite {

    private final TravelScheduleList travelScheduleList;
    private final TravelScheduleEditor travelScheduleEditor;

    private final DelayedWork worker;
    private final Button newButton;

    public TravelSchedule(ClientConstants clientConstants, TravelOptLocale locale, DelayedWork worker) {
        this.worker = worker;
        travelScheduleEditor = new TravelScheduleEditor(worker, locale);
        travelScheduleList = new TravelScheduleList(locale, travelScheduleEditor);
        travelScheduleEditor.setTravelScheduleList(travelScheduleList);

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


        newButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                travelScheduleList.createNew();
            }
        });
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                travelScheduleList.getList().remove(travelScheduleList.getSelectionModel().getSelectedObject());
                travelScheduleEditor.setActive(null);
                deleteButton.setEnabled(false);
                worker.requestWork();
            }
        });

        travelScheduleList.getSelectionModel().addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent selectionChangeEvent) {
                ScheduleEntry obj = travelScheduleList.getSelectionModel().getSelectedObject();
                deleteButton.setEnabled(obj != null);
            }
        });
        panel.add(UIUtil.wrapFlow(newButton, deleteButton));
        panel.add(travelScheduleList);
        return panel;
    }

    public TravelScheduleList getList() {
        return travelScheduleList;
    }

    public RangeEditor getRangeEditor() {
        return travelScheduleEditor.getRangeEditor();
    }
}
