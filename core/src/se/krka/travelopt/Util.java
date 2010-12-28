package se.krka.travelopt;

import org.gwttime.time.DateTime;

public class Util {
    public static DateTime date(String s) {
        String[] split = s.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        return new DateTime(year, month, day, 0, 0);
    }

    public static String pad(char pad, int width, String s) {
        int toAdd = s.length() - width;
        for (int i = 0; i < toAdd; i++) {
            s += pad;
        }
        return s;
    }

    public static int numDays(DateTime from, DateTime to) {
        long millis = to.getMillis() - from.getMillis();
        return (int) (1 + (millis + 86400000 - 1)/ 86400000);
    }
}
