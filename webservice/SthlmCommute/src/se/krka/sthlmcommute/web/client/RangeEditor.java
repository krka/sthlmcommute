package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;

import java.util.Date;

public class RangeEditor extends Composite {
    private final VerticalPanel form = new VerticalPanel();
    private final DateIntervalPicker intervalPicker;
    private final WeekdayEditor weekdays;

    public RangeEditor() {
        final Grid dates = new Grid(2, 2);
        dates.setWidget(0, 0, new Label("From:"));
        dates.setWidget(0, 1, new Label("To:"));
        final DatePicker from = new DatePicker();
        dates.setWidget(1, 0, from);
        final DatePicker to = new DatePicker();
        dates.setWidget(1, 1, to);

        form.add(dates);
        final Label dateSelectionLabel = new Label("");
        form.add(dateSelectionLabel);

        weekdays = new WeekdayEditor();
        form.add(weekdays);

        Date date = Util.newDate();
        from.setValue(date, true);
        from.setCurrentMonth(date);
        to.setValue(date, true);
        to.setCurrentMonth(date);

        intervalPicker = new DateIntervalPicker(from, to, dateSelectionLabel);

        initWidget(form);
    }

    public void add(Widget widget) {
        form.add(widget);
    }

    public void insert(Widget widget, int index) {
        form.insert(widget, index);
    }


    public DatePicker getFrom() {
        return intervalPicker.getFrom();
    }

    public DatePicker getTo() {
        return intervalPicker.getTo();
    }


    public DateIntervalPicker getIntervalPicker() {
        return intervalPicker;
    }

    public WeekdayEditor getWeekdays() {
        return weekdays;
    }
}
