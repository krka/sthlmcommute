package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.datepicker.client.DatePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateIntervalPicker {
    private final DatePicker from;
    private final DatePicker to;

    private Date highlightStart;
    private Date highlightEnd;

    private final List<DateIntervalUpdateListener> listeners = new ArrayList<DateIntervalUpdateListener>();

    public DateIntervalPicker(DatePicker from, DatePicker to) {
        this.from = from;
        this.to = to;

        install();
    }

    public void install() {
        ValueChangeHandler<Date> valueChangeHandler = new ValueChangeHandler<Date>() {
            @Override
            public void onValueChange(ValueChangeEvent<Date> dateValueChangeEvent) {
                Date date = dateValueChangeEvent.getValue();

                if (date == null) {
                    removeHighlight();
                    return;
                }

                Object source = dateValueChangeEvent.getSource();
                Date fromValue = from.getValue();
                Date toValue = to.getValue();
                if (source == from) {
                    if (toValue == null || date.getTime() > toValue.getTime()) {
                        to.setValue(date, false);
                        to.setCurrentMonth(date);
                    }
                } else if (source == to) {
                    if (fromValue == null || date.getTime() < fromValue.getTime()) {
                        from.setValue(date, false);
                        from.setCurrentMonth(date);
                    }
                } else {
                    return;
                }

                applyHighlight();
                reportIntervalChange(fromValue, toValue);
            }
        };
        ValueChangeHandler<Date> handler = valueChangeHandler;
        from.addValueChangeHandler(handler);
        to.addValueChangeHandler(handler);
    }

    private boolean eq(Date x, Date y) {
        if (x == null) {
            return y == null;
        }
        return x.equals(y);
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
        if (from != null && to != null && !to.before(from)) {
            highlightStart = from;
            highlightEnd = to;

            long time = highlightStart.getTime();
            Date date = new Date(time);
            while (date.before(highlightEnd)) {
                this.from.addStyleToDates("interval", date);
                this.to.addStyleToDates("interval", date);
                time += 86400000;
                date.setTime(time);
            }
            this.from.addStyleToDates("interval", date);
            this.to.addStyleToDates("interval", date);
        }
    }

    private void removeHighlight() {
        if (!isHighlighted()) {
            return;
        }

        long time = highlightStart.getTime();
        Date date = new Date(time);
        while (date.before(highlightEnd)) {
            from.removeStyleFromDates("interval", date);
            to.removeStyleFromDates("interval", date);
            time += 86400000;
            date.setTime(time);
        }
        from.removeStyleFromDates("interval", date);
        to.removeStyleFromDates("interval", date);

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

