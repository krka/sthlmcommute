package se.krka.sthlmcommute.web.client;

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
        long num = to.getTime() - from.getTime();
        return (int) (1 + (num + 86400000 - 1) / 86400000);
    }

    @Override
    public String toString() {
        return calcDesc();
    }

    private String calcDesc() {
        String from = Util.format(this.from);
        String to = Util.format(this.to);
        if (from.equals(to) || to.equals("")) {
            return from;
        }
        String s = from + " to " + to;
        int days = getDays();
        if (days == 1) {
            s += " (1 day)";
        } else {
            s += " (" + days + " days)";
        }
        return s;
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
