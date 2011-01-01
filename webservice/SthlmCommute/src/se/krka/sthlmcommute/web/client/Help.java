package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.*;

public class Help extends Composite {
    private final CaptionPanel root = new CaptionPanel("Help");

    public Help() {
        initWidget(root);
        clear();
    }

    private void clear() {
        root.setVisible(false);
        for (Widget widget : root) {
            widget.removeFromParent();
        }
    }

    public void choosePriceCategory() {
        clear();
        root.setVisible(true);
        root.add(new Label("Are you paying full price or do you get a discount for being young or being a senior citizen?"));
    }

    public void createNewEntry() {
        clear();
        root.setVisible(true);
        root.add(new Label("Begin by creating an entry in the travel schedule"));
    }
}
