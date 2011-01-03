package se.krka.sthlmcommute.web.client.async;

import com.google.gwt.user.client.ui.Widget;

class EmptyWidgetUsage<T extends Widget> implements AsyncWidgetUsage<T> {
    private static final EmptyWidgetUsage<?> INSTANCE = new EmptyWidgetUsage();

    @Override
    public void run(T widget) {
    }

    public static <T extends Widget> EmptyWidgetUsage<T> getInstance() {
        return (EmptyWidgetUsage<T>) INSTANCE;
    }
}
