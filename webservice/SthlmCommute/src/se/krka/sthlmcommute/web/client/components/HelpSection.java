package se.krka.sthlmcommute.web.client.components;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import se.krka.sthlmcommute.web.client.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HelpSection extends Composite {

    private final List<HelpElement> elements = new ArrayList<HelpElement>();
    private final VerticalPanel root;

    public HelpSection() {
        root = new VerticalPanel();
        initWidget(root);
    }

    public HelpElement createAndAdd(String header, Widget content, Widget highlight) {
        HelpElement element = new HelpElement(this, header, content, highlight);
        add(element);
        return element;
    }

    private void add(HelpElement element) {
        elements.add(element);
        root.add(element.getWidget());
    }

    public void closeAllExcept(HelpElement except) {
        for (HelpElement element : elements) {
            if (except != element) {
                element.close();
            }
        }
    }
}
