package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.*;

public class UIUtil {
    public static Widget wrapScroll(Widget widget, String width, String height) {
        ScrollPanel panel = new ScrollPanel(widget);
        panel.setWidth(width);
        panel.setHeight(height);
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

    public static Widget wrapHorizontal(Widget... widgets) {
        Panel p = new HorizontalPanel();
        for (Widget widget : widgets) {
            p.add(widget);
        }
        return p;
    }
}
