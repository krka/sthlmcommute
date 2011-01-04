package se.krka.sthlmcommute.web.client;

import se.krka.sthlmcommute.web.client.components.dateinterval.DateInterval;
import se.krka.travelopt.Util;

import java.util.Date;

public class ScheduleEntry implements Comparable<ScheduleEntry> {
    private final DateInterval interval = new DateInterval(null, null);
    private Weekdays weekdays;

    public ScheduleEntry(Weekdays weekdays) {
        this.weekdays = weekdays;

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
        return interval.getFrom() != null && interval.getTo() != null && weekdays.countCoupons() > 0;
    }

    public String serialize() {
        Date from = interval.getFrom();
        Date to = interval.getTo();
        int defaultValue = weekdays.getDefaultValue();
        int[] coupons = weekdays.getRawCoupons();

        StringBuilder sb = new StringBuilder();
        sb.append(serialize(from));
        sb.append(":");
        sb.append(serialize(to));
        sb.append(":");
        sb.append(defaultValue);
        for (int i = 0; i < 7; i++) {
            sb.append(":");
            sb.append(coupons[i]);
        }
        return sb.toString();
    }

    public void deserialize(String s, TravelScheduleList travelScheduleList) {
        String[] split = s.split(":");
        if (split.length != 10) {
            return;
        }

        Date from = deserializeDate(split[0]);
        Date to = deserializeDate(split[1]);

        int defaultValue = Integer.parseInt(split[2]);
        int[] coupons = new int[7];
        for (int i = 0; i < 7; i++) {
            coupons[i] = Integer.parseInt(split[3 + i]);
        }
        travelScheduleList.updateScheduleEntryInterval(this, from, to);
        travelScheduleList.updateCoupons(this, new Weekdays(defaultValue, coupons));
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
