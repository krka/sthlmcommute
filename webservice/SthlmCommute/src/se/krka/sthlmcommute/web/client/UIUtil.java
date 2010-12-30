package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.*;

public class UIUtil {
    public static Widget wrapScroll(Widget widget) {
        ScrollPanel panel = new ScrollPanel(widget);
        panel.setAlwaysShowScrollBars(true);
        panel.setSize("25em", "20em");
        return panel;
    }

    public static Widget wrapCaption(String caption, Widget widget) {
        CaptionPanel panel = new CaptionPanel(caption);
        panel.add(widget);
        return panel;
    }

    public static Widget wrapDecorated(Widget widget) {
        Panel panel = new DecoratorPanel();
        panel.add(widget);
        return panel;
    }

    public static Widget wrapFlow(Widget... widgets) {
        Panel p = new FlowPanel();
        for (Widget widget : widgets) {
            p.add(widget);
        }
        return p;
    }
}
