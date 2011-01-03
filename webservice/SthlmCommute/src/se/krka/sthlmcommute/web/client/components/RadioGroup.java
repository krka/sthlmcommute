package se.krka.sthlmcommute.web.client.components;

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
    private final MyClickHandler listener = new MyClickHandler();

    public RadioGroup(String identifier) {
        this.identifier = identifier;
        root = new VerticalPanel();
        initWidget(root);
    }

    public void addClickHandler(ClickHandler handler) {
        listener.add(handler);
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

    public void setSelected(String formValue) {
        for (Widget widget : root) {
            if (widget instanceof Button) {
                Button button = (Button) widget;
                if (button.getGroup() == this) {
                    if (button.getFormValue().equals(formValue)) {
                        button.setValue(true);
                        listener.onClick(new ClickEvent() {

                        });
                    }
                }
            }
        }
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
        private final List<ClickHandler> handlers = new ArrayList<ClickHandler>();

        @Override
        public void onClick(ClickEvent clickEvent) {
            for (ClickHandler handler : handlers) {
                handler.onClick(clickEvent);
            }
        }

        public void add(ClickHandler handler) {
            handlers.add(handler);
        }
    }
}
