package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.datepicker.client.DatePicker;

import java.util.Date;

public class DateIntervalPicker implements ValueChangeHandler<Date> {
    private final DatePicker from;
    private final DatePicker to;
    private final Label dateSelectionLabel;

    private Date fromValue;
    private Date toValue;

    public DateIntervalPicker(DatePicker from, DatePicker to, Label dateSelectionLabel) {
        this.from = from;
        this.to = to;
        this.dateSelectionLabel = dateSelectionLabel;

        install();
    }

    public void install() {
        fromValue = from.getValue();
        toValue = to.getValue();

        applyHighlight();

        from.addValueChangeHandler(this);
        to.addValueChangeHandler(this);
    }

    private void applyHighlight() {
        long time = fromValue.getTime();
        Date date = new Date(time);
        while (date.before(toValue)) {
            from.addStyleToDates("interval", date);
            to.addStyleToDates("interval", date);
            time += 86400000;
            date.setTime(time);
        }
        from.addStyleToDates("interval", date);
        to.addStyleToDates("interval", date);
        updateDateSelection();
    }

    private void removeHighlight() {
        long time = fromValue.getTime();
        Date date = new Date(time);
        while (date.before(toValue)) {
            from.removeStyleFromDates("interval", date);
            to.removeStyleFromDates("interval", date);
            time += 86400000;
            date.setTime(time);
        }
        from.removeStyleFromDates("interval", date);
        to.removeStyleFromDates("interval", date);
    }

    @Override
    public void onValueChange(ValueChangeEvent<Date> dateValueChangeEvent) {
        Date date = dateValueChangeEvent.getValue();

        Object source = dateValueChangeEvent.getSource();
        if (source == from) {
            if (date.getTime() > to.getValue().getTime()) {
                to.setValue(date, false);
                to.setCurrentMonth(date);
            }
        } else if (source == to) {
            if (date.getTime() < from.getValue().getTime()) {
                from.setValue(date, false);
                from.setCurrentMonth(date);
            }
        } else {
            return;
        }

        removeHighlight();
        fromValue = from.getValue();
        toValue = to.getValue();
        applyHighlight();
    }

    private void updateDateSelection() {
        if (to.getValue() == null || from.getValue() == null) {
            dateSelectionLabel.setText("No dates have been selected.");
        } else {
            DateInterval interval = new DateInterval(from.getValue(), to.getValue());
            dateSelectionLabel.setText(interval.toString());
        }
    }

    public DatePicker getFrom() {
        return from;
    }

    public DatePicker getTo() {
        return to;
    }
}

