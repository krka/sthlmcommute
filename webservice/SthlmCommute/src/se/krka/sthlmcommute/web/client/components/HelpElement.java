package se.krka.sthlmcommute.web.client.components;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.*;
import se.krka.sthlmcommute.web.client.TravelScheduleEditor;

public class HelpElement implements OpenHandler<DisclosurePanel>, CloseHandler<DisclosurePanel> {
    private final DisclosurePanel widget;
    private final HelpSection helpSection;
    private Widget highlight;

    private boolean consumed;

    public HelpElement(HelpSection helpSection, String header, Widget content) {
        this(helpSection, header, content, new Hidden());
    }

    public HelpElement(HelpSection helpSection, String header, Widget content, Widget highlight) {
        this.helpSection = helpSection;
        this.highlight = highlight;
        widget = new DisclosurePanel(header);
        widget.setAnimationEnabled(true);
        widget.add(content.asWidget());
        widget.addOpenHandler(this);
        widget.addCloseHandler(this);
        content.addStyleName("highlighted");
    }

    public Widget getWidget() {
        return widget;
    }

    public void open() {
        widget.setOpen(true);
    }

    public void close() {
        widget.setOpen(false);
    }

    @Override
    public void onOpen(OpenEvent<DisclosurePanel> disclosurePanelOpenEvent) {
        helpSection.closeAllExcept(this);
        highlight.addStyleName("highlighted");
    }

    @Override
    public void onClose(CloseEvent<DisclosurePanel> disclosurePanelCloseEvent) {
        highlight.removeStyleName("highlighted");
    }

    public boolean tryConsume() {
        if (consumed) {
            return false;
        }
        consumed = true;
        return true;
    }

    public void setHighlight(Widget newHighlight) {
        if (widget.isOpen()) {
            newHighlight.addStyleName("highlighted");
            highlight.removeStyleName("highlighted");
        }
        this.highlight = newHighlight;
    }
}
