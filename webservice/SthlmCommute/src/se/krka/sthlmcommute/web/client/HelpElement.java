package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Widget;

public class HelpElement implements OpenHandler<DisclosurePanel>, CloseHandler<DisclosurePanel> {
    private final DisclosurePanel widget;
    private final HelpSection helpSection;
    private final Widget highlight;

    private boolean consumed;

    public HelpElement(HelpSection helpSection, String header, Widget content, Widget highlight) {
        this.helpSection = helpSection;
        this.highlight = highlight;
        widget = new DisclosurePanel(header);
        widget.setAnimationEnabled(true);
        widget.add(content);
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
        if (highlight != null) {
            highlight.addStyleName("highlighted");
        }
    }

    @Override
    public void onClose(CloseEvent<DisclosurePanel> disclosurePanelCloseEvent) {
        if (highlight != null) {
            highlight.removeStyleName("highlighted");
        }
    }

    public boolean isClosed() {
        return !widget.isOpen();
    }

    public boolean tryConsume() {
        if (consumed) {
            return false;
        }
        consumed = true;
        return true;
    }
}
