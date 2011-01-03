package se.krka.sthlmcommute.web.client.async;

import com.google.gwt.user.client.ui.Widget;

public interface AsyncWidgetLoader<T extends Widget> {
    T load();
}
