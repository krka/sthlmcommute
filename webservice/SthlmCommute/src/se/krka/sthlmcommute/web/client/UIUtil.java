package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.*;

public class UIUtil {

    public static <T extends HasWidgets> T wrapIn(Widget widget, T panel) {
        panel.add(widget);
        return panel;
    }

    public static ScrollPanel wrapScroll(Widget widget, String width, String height) {
        ScrollPanel panel = new ScrollPanel(widget);
        panel.setWidth(width);
        panel.setHeight(height);
        return panel;
    }

    public static CaptionPanel wrapCaption(String caption, Widget widget) {
        return wrapIn(widget, new CaptionPanel(caption));
    }

    public static DisclosurePanel wrapDisclosure(String header, Widget widget) {
        return wrapIn(widget, new DisclosurePanel(header));
    }

    public static Widget wrapDecorated(Widget widget) {
        return wrapIn(widget, new DecoratorPanel());
    }

    public static Widget wrapFlow(Widget... widgets) {
        return wrapMultiple(new FlowPanel(), widgets);
    }

    public static Widget wrapHorizontal(Widget... widgets) {
        return wrapMultiple(new HorizontalPanel(), widgets);
    }

    public static Widget wrapVertical(Widget... widgets) {
        return wrapMultiple(new VerticalPanel(), widgets);
    }

    private static <T extends HasWidgets> T  wrapMultiple(T container, Widget... widgets) {
        for (Widget widget : widgets) {
            container.add(widget);
        }
        return container;
    }
}
