package se.krka.sthlmcommute.web.client;

import java.util.Date;

public class DateInterval {
    private final Date from;
    private final Date to;

    public DateInterval(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public int getDays() {
        long num = to.getTime() - from.getTime();
        return (int) (1 + (num + 86400000 - 1)/ 86400000);
    }

    @Override
    public String toString() {
        String s = Util.format(from) + " to " + Util.format(to);
        int days = getDays();
        if (days == 1) {
            s += " (1 day)";
        } else {
            s += " (" + days + " days)";
        }
        return s;
    }
}
