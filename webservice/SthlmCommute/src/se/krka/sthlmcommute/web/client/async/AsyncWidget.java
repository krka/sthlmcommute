package se.krka.sthlmcommute.web.client.async;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AsyncWidget<T extends Widget> extends Composite implements HasWidgets {
    private final VerticalPanel root;
    private final AsyncWidgetLoader<T> loader;
    private final List<AsyncWidgetUsage<T>> pending = new ArrayList<AsyncWidgetUsage<T>>();
    private int state;
    private T widget;

    public AsyncWidget(AsyncWidgetLoader<T> loader) {
        this.loader = loader;
        root = new VerticalPanel();
        initWidget(root);
    }

    public void startPrefetch() {
        runASAP(EmptyWidgetUsage.<T>getInstance());
    }

    public void runWhenReady(final AsyncWidgetUsage<T> usage) {
        switch (state) {
            case 0:
            case 1:
                pending.add(usage);
                break;
            case 2:
                usage.run(widget);
                break;
        }
    }

    public void runASAP(final AsyncWidgetUsage<T> usage) {
        switch (state) {
            case 0:
                state = 1;
                GWT.runAsync(new GenericFailureAsyncCallback() {
                    @Override
                    public void onSuccess() {
                        widget = loader.load();
                        root.add(widget);
                        usage.run(widget);
                        state = 2;
                        for (AsyncWidgetUsage<T> p : pending) {
                            p.run(widget);
                        }
                        pending.clear();
                    }
                });
                break;
            case 1:
                pending.add(usage);
                break;
            case 2:
                usage.run(widget);
                break;
        }

    }

    public boolean isReady() {
        return state == 2;
    }

    @Override
    public void add(Widget widget) {
        root.add(widget);
    }

    @Override
    public void clear() {
        root.clear();
    }

    @Override
    public Iterator<Widget> iterator() {
        return root.iterator();
    }

    @Override
    public boolean remove(Widget widget) {
        return root.remove(widget);
    }
}
