package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.krka.sthlmcommute.web.client.components.RadioGroup;
import se.krka.sthlmcommute.web.client.util.DelayedWork;
import se.krka.sthlmcommute.web.client.util.UIUtil;
import se.krka.travelopt.localization.TravelOptLocale;

public class OptimizeOptions extends Composite {
    private final RadioGroup radioGroup;
    private final TicketEditor ticketEditor;

    public OptimizeOptions(final DelayedWork delayedWork, TravelOptLocale locale) {
        radioGroup = new RadioGroup("extendGroup");

        ticketEditor = new TicketEditor(new UpdateListener() {
            @Override
            public void updated() {
                delayedWork.requestWork();
            }
        }, locale);

        Panel root = new VerticalPanel();
        root.setWidth("60em");

        ClickHandler handler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                ticketEditor.setVisible(isEnabled());
                delayedWork.requestWork();
            }
        };

        radioGroup.addClickHandler(handler);
        radioGroup.addRadioButton("only", "Minimize cost for the selected period.");
        radioGroup.addRadioButton("extend", "Minimize cost for the selected period and extend indefinitely.");
        radioGroup.setSelected("only");

        VerticalPanel hidden = new VerticalPanel();
        hidden.setWidth("4em");

        root.add(UIUtil.wrapCaption("Optimization settings", UIUtil.wrapFlow(radioGroup, UIUtil.wrapHorizontal(hidden, ticketEditor))));
        initWidget(root);

        ticketEditor.setVisible(false);

    }

    public int[] getTickets() {
        return ticketEditor.getWeekdays().getTickets();
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public TicketEditor getTicketEditor() {
        return ticketEditor;
    }

    public boolean isEnabled() {
        return radioGroup.getSelected().getFormValue().equals("extend");
    }
}
