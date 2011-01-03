package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import se.krka.travelopt.Util;

import java.util.Date;
import java.util.List;

public class ScheduleEntry implements Comparable<ScheduleEntry> {
    private final DateInterval interval = new DateInterval(null, null);
    private Weekdays weekdays;
    private boolean overlapping;

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

    public void setOverlapping(boolean overlapping) {
        this.overlapping = overlapping;
    }

    public boolean isOverlapping() {
        return overlapping;
    }

    public String serialize() {
        Date from = interval.getFrom();
        Date to = interval.getTo();
        int defaultValue = weekdays.getDefaultValue();
        int[] tickets = weekdays.getRawTickets();

        StringBuilder sb = new StringBuilder();
        sb.append(serialize(from));
        sb.append(":");
        sb.append(serialize(to));
        sb.append(":");
        sb.append(defaultValue);
        for (int i = 0; i < 7; i++) {
            sb.append(":");
            sb.append(tickets[i]);
        }
        return sb.toString();
    }

    public void deserialize(String s) {
        String[] split = s.split(":");
        if (split.length != 10) {
            return;
        }

        Date from = deserializeDate(split[0]);
        Date to = deserializeDate(split[1]);

        int defaultValue = Integer.parseInt(split[2]);
        int[] tickets = new int[7];
        for (int i = 0; i < 7; i++) {
            tickets[i] = Integer.parseInt(split[3 + i]);
        }
        weekdays = new Weekdays(defaultValue, tickets);
        interval.set(from, to);
    }

    private static Date deserializeDate(String s) {
        if (s.equals("")) {
            return null;
        }
        return Util.parseDate(s);
    }

    private String serialize(Date date) {
        if (date == null) {
            return "";
        }
        return Util.format(date);
    }
}
