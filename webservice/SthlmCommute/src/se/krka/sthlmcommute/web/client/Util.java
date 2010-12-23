package se.krka.sthlmcommute.web.client;

import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;

public class Util {
    private static final DateTimeFormat DATE_TIME_FORMAT = DateTimeFormat.getFormat("yyyy-MM-dd");
    public static String format(Date value) {
        return DATE_TIME_FORMAT.format(value);
    }

    public static Date newDate() {
        Date date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return date;
    }
}
