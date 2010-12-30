package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import se.krka.travelopt.localization.TravelOptLocale;

public class TravelSchedule extends Composite {

    private final HelpInfo helpInfo;
    private final ClientConstants clientConstants;
    private final TravelOptLocale locale;
    private final TravelScheduleList travelScheduleList;
    private final TravelScheduleEditor travelScheduleEditor;

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
        newButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                helpInfo.setVisible(false);
                travelScheduleList.createNew();
            }
        });
        panel.add(newButton);
        panel.add(travelScheduleList);
        return panel;
    }
}
