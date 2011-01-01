package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

import java.util.Date;
import java.util.List;

public class ScheduleEntry implements Comparable<ScheduleEntry> {
    private final DateInterval interval = new DateInterval(null, null);
    private Weekdays weekdays;

    public ScheduleEntry(Weekdays weekdays) {
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

    public boolean valid() {
        return interval.getFrom() != null && interval.getTo() != null && weekdays.countTickets() > 0;
    }
}
