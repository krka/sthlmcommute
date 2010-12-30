package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.*;

public class HelpInfo extends Composite {
    public HelpInfo(String text) {
        Panel root = new DecoratorPanel();
        root.add(new Label(text));
        initWidget(root);
    }
}
