package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.*;

import java.util.Date;

public class ScheduleEntry extends Composite {
    private final DateInterval interval;
    private final Weekdays weekdays;

    public ScheduleEntry(Date from, Date to, Weekdays weekdays) {
        interval = new DateInterval(from, to);
        this.weekdays = weekdays;

        HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.add(new Label(this.toString()));
        horizontalPanel.add(new Button("Remove"));

        DecoratorPanel root = new DecoratorPanel();
        root.add(horizontalPanel);
        initWidget(root);
    }

    @Override
    public String toString() {
        return interval.toString() + " " + weekdays.toString();
    }
}
