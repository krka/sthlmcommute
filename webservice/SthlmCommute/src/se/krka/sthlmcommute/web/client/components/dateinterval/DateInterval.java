package se.krka.sthlmcommute.web.client.components.dateinterval;

import se.krka.travelopt.Util;

import java.util.Date;

public class DateInterval implements Comparable<DateInterval> {
    private Date from;
    private Date to;

    public DateInterval(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public void set(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public int getDays() {
        if (to == null || from == null) {
            return 0;
        }
        return Util.toDayOrdinal(to) - Util.toDayOrdinal(from) + 1;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public int compareTo(DateInterval other) {
        int v = to.compareTo(other.to);
        if (v != 0) {
            return v;
        }
        return from.compareTo(other.from);
    }
}
