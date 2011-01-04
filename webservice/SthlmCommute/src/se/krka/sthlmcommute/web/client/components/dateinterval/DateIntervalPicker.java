package se.krka.sthlmcommute.web.client.components.dateinterval;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.datepicker.client.DatePicker;
import se.krka.travelopt.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateIntervalPicker extends Composite {
    private final DatePicker from;
    private final DatePicker to;

    private Date highlightStart;
    private Date highlightEnd;

    private final List<DateIntervalUpdateListener> listeners = new ArrayList<DateIntervalUpdateListener>();

    public DateIntervalPicker() {
        from = new DatePicker();
        to = new DatePicker();

        Grid root = new Grid(2, 2);
        root.setWidget(0, 0, new Label("From:"));
        root.setWidget(0, 1, new Label("To:"));
        root.setWidget(1, 0, from);
        root.setWidget(1, 1, to);
        initWidget(root);
        install();
    }

    public void install() {
        ValueChangeHandler<Date> handler = new ValueChangeHandler<Date>() {
            @Override
            public void onValueChange(ValueChangeEvent<Date> dateValueChangeEvent) {
                Date date = dateValueChangeEvent.getValue();

                if (date == null) {
                    removeHighlight();
                    return;
                }

                boolean fire = true;
                Object source = dateValueChangeEvent.getSource();
                Date fromValue = from.getValue();
                Date toValue = to.getValue();
                if (source == from) {
                    if (toValue != null && date.getTime() > toValue.getTime()) {
                        to.setValue(date, true);
                        to.setCurrentMonth(date);
                        fire = false;
                    }
                } else if (source == to) {
                    if (fromValue != null && date.getTime() < fromValue.getTime()) {
                        from.setValue(date, true);
                        from.setCurrentMonth(date);
                        fire = false;
                    }
                } else {
                    return;
                }

                if (fire) {
                    applyHighlight();
                    reportIntervalChange(fromValue, toValue);
                }
            }
        };
        from.addValueChangeHandler(handler);
        to.addValueChangeHandler(handler);
    }

    private boolean isHighlighted() {
        return highlightStart != null;
    }

    public void applyHighlight() {
        if (isHighlighted()) {
            removeHighlight();
        }
        Date from = this.from.getValue();
        Date to = this.to.getValue();

        if (from == null || to == null) {
            return;
        }

        int fromOrdinal = Util.toDayOrdinal(from);
        int toOrdinal = Util.toDayOrdinal(to);

        if (fromOrdinal <= toOrdinal) {
            highlightStart = from;
            highlightEnd = to;

            for (int i = fromOrdinal; i <= toOrdinal; i++) {
                Date date = Util.fromDayOrdinal(i);
                this.from.addStyleToDates("interval", date);
                this.to.addStyleToDates("interval", date);
            }
        }
    }

    private void removeHighlight() {
        if (!isHighlighted()) {
            return;
        }

        int fromOrdinal = Util.toDayOrdinal(highlightStart);
        int toOrdinal = Util.toDayOrdinal(highlightEnd);

        for (int i = fromOrdinal; i <= toOrdinal; i++) {
            Date date = Util.fromDayOrdinal(i);
            this.from.removeStyleFromDates("interval", date);
            this.to.removeStyleFromDates("interval", date);
        }

        highlightStart = null;
        highlightEnd = null;
    }

    public void addListener(DateIntervalUpdateListener listener) {
        listeners.add(listener);
    }

    private void reportIntervalChange(Date fromValue, Date toValue) {
        for (DateIntervalUpdateListener listener : listeners) {
            listener.intervalChanged(this, fromValue, toValue);
        }
    }

    public DatePicker getFrom() {
        return from;
    }

    public DatePicker getTo() {
        return to;
    }
}

