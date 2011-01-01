package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.krka.travelopt.localization.TravelOptLocale;

public class OptimizeOptions extends Composite {
    private final RadioButton only;
    private final RadioButton extend;
    private final TicketEditor ticketEditor;

    public OptimizeOptions(final DelayedWork delayedWork, TravelOptLocale locale) {
        ticketEditor = new TicketEditor(new UpdateListener() {
            @Override
            public void updated() {
                delayedWork.requestWork();
            }
        }, delayedWork, locale);
        ticketEditor.hideHelp();

        Panel root = new VerticalPanel();
        root.setWidth("60em");

        ClickHandler handler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                ticketEditor.setVisible(extend.getValue());
                delayedWork.requestWork();
            }
        };

        only = newButton("Minimize cost for the selected period.", handler);
        extend = newButton("Minimize cost for the selected period and extend indefinitely.", handler);

        VerticalPanel extendPanel = new VerticalPanel();
        extendPanel.add(extend);
        VerticalPanel hidden = new VerticalPanel();
        hidden.setWidth("4em");
        extendPanel.add(UIUtil.wrapHorizontal(hidden, ticketEditor));

        only.setValue(true);

        root.add(UIUtil.wrapCaption("Optimization settings", UIUtil.wrapFlow(only, extendPanel)));
        initWidget(root);
    }

    private RadioButton newButton(String label, ClickHandler handler) {
        RadioButton button = new RadioButton("extendGroup", label);
        button.addClickHandler(handler);
        return button;
    }

    public boolean isEnabled() {
        return extend.getValue();
    }

    public int[] getTickets() {
        return ticketEditor.getWeekdays().getTickets();
    }
}
