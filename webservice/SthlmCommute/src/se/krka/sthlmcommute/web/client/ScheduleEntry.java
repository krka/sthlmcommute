package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

import java.util.Date;
import java.util.List;

public class ScheduleEntry extends Composite implements Comparable<ScheduleEntry> {
    private final DateInterval interval;
    private final Weekdays weekdays;

    public ScheduleEntry(Date from, Date to, Weekdays weekdays, final List<ScheduleEntry> list, final SthlmCommute sthlmCommute) {
        interval = new DateInterval(from, to);
        this.weekdays = weekdays;

        final Button remove = new Button("Remove");

        final HorizontalPanel shortView = new HorizontalPanel();

        shortView.add(remove);
        shortView.add(new Label(this.toString()));

        remove.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                list.remove(ScheduleEntry.this);
                ScheduleEntry.this.removeFromParent();
                sthlmCommute.updateTravelSuggestion();
            }
        });



        final HorizontalPanel mainPanel = new HorizontalPanel();
        mainPanel.add(shortView);

        DecoratorPanel root = new DecoratorPanel();
        root.add(mainPanel);
        initWidget(root);

    }

    @Override
    public String toString() {
        return interval.toString() + " " + weekdays.toString();
    }

    public DateInterval getInterval() {
        return interval;
    }

    public Weekdays getWeekdays() {
        return weekdays;
    }

    @Override
    public int compareTo(ScheduleEntry scheduleEntry) {
        return this.interval.compareTo(scheduleEntry.interval);
    }
}
