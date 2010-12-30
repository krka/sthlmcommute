package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

import java.util.Date;
import java.util.List;

public class ScheduleEntry implements Comparable<ScheduleEntry> {
    private final DateInterval interval;
    private Weekdays weekdays;

    public ScheduleEntry(Date from, Date to, Weekdays weekdays) {
        interval = new DateInterval(from, to);
        this.weekdays = weekdays;

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

    public void setWeekdays(Weekdays weekdays) {
        this.weekdays = weekdays;
    }
}
