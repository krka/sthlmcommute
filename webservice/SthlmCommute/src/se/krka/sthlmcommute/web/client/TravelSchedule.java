package se.krka.sthlmcommute.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SelectionChangeEvent;
import se.krka.travelopt.localization.TravelOptLocale;

public class TravelSchedule extends Composite {

    private final HelpInfo helpInfo;
    private final ClientConstants clientConstants;
    private final TravelOptLocale locale;
    private final TravelScheduleList travelScheduleList;
    private final TravelScheduleEditor travelScheduleEditor;

    private DelayedWork worker;

    public TravelSchedule(ClientConstants clientConstants, TravelOptLocale locale) {
        this.clientConstants = clientConstants;
        this.locale = locale;
        helpInfo = new HelpInfo("Begin by creating an entry in the travel schedule.");
        travelScheduleEditor = new TravelScheduleEditor();
        travelScheduleList = new TravelScheduleList(clientConstants, locale, travelScheduleEditor);
        travelScheduleEditor.setTravelScheduleList(travelScheduleList);

        HorizontalPanel panel = new HorizontalPanel();
        panel.add(createList());
        panel.add(travelScheduleEditor);

        CaptionPanel root = new CaptionPanel("Travel schedule");
        root.add(panel);
        initWidget(root);
    }

    private Widget createList() {
        VerticalPanel panel = new VerticalPanel();

        panel.add(helpInfo);

        Button newButton = new Button("New entry");
        final Button deleteButton = new Button("Delete");
        deleteButton.setEnabled(false);


        newButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                helpInfo.setVisible(false);
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

    public void setWorker(DelayedWork worker) {
        this.worker = worker;
        this.travelScheduleEditor.setWorker(worker);
    }
}
