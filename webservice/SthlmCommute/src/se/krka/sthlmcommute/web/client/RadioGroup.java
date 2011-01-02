package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.List;

public class RadioGroup extends Composite {
    private final VerticalPanel root;
    private final String identifier;
    private final ClickHandler listener = new MyClickHandler();

    private final List<ClickHandler> handlers = new ArrayList<ClickHandler>();

    public RadioGroup(String identifier) {
        this.identifier = identifier;
        root = new VerticalPanel();
        initWidget(root);
    }

    public void addClickHandler(ClickHandler handler) {
        handlers.add(handler);
    }

    public void addRadioButton(String value, String header) {
        Button button = new Button(value, header);
        button.addClickHandler(listener);
        root.add(button);
    }

    public Button getSelected() {
        for (Widget widget : root) {
            if (widget instanceof Button) {
                Button button = (Button) widget;
                if (button.getGroup() == this) {
                    if (button.getValue()) {
                        return button;
                    }
                }
            }
        }
        return null;
    }

    public class Button extends RadioButton {
        public Button(String value, String header) {
            super(identifier, header);
            setFormValue(value);
        }

        private RadioGroup getGroup() {
            return RadioGroup.this;
        }
    }

    private class MyClickHandler implements ClickHandler {
        @Override
        public void onClick(ClickEvent clickEvent) {
            for (ClickHandler handler : handlers) {
                handler.onClick(clickEvent);
            }
        }
    }
}
