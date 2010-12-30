package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;

import java.util.Date;

public class RangeEditor {
    private final DateIntervalPicker intervalPicker;
    private final Grid dates;

    public RangeEditor() {
        final DatePicker from = new DatePicker();
        final DatePicker to = new DatePicker();
        intervalPicker = new DateIntervalPicker(from, to);

        dates = new Grid(2, 2);
        dates.setWidget(0, 0, new Label("From:"));
        dates.setWidget(0, 1, new Label("To:"));
        dates.setWidget(1, 0, from);
        dates.setWidget(1, 1, to);


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

    public Grid getDates() {
        return dates;
    }

}
