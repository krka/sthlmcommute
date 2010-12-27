package se.krka.travelopt;

import org.gwttime.time.DateTime;
import org.gwttime.time.format.DateTimeFormat;
import org.gwttime.time.format.DateTimeFormatter;

public class Util {

    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd");

    public static DateTime date(String s) {
        return formatter.parseDateTime(s);

    }

}
