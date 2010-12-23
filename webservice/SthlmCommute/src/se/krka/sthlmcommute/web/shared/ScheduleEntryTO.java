package se.krka.sthlmcommute.web.shared;

import java.io.Serializable;
import java.util.Date;

public class ScheduleEntryTO implements Serializable {
    private Date from;
    private Date to;
    private String weekDays;

    private ScheduleEntryTO() {
    }

    public ScheduleEntryTO(Date from, Date to, String weekDays) {
        this.from = from;
        this.to = to;
        this.weekDays = weekDays;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public String getWeekDays() {
        return weekDays;
    }
}
