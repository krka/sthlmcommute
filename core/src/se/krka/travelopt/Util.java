package se.krka.travelopt;

import java.util.Date;

public class Util {
    public static final long DAY_IN_MILLIS = 86400000;

    public static Date parse(String s) {
        String[] split = s.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        return new Date(year - 1900, month - 1, day);
    }

    public static String format(Date date) {
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();
        return pad('0', 4, "" + year) + "-" +
                pad('0', 2, "" + month) + "-" +
                pad('0', 2, "" + day);

    }

    public static String pad(char pad, int width, String s) {
        int toAdd = s.length() - width;
        for (int i = 0; i < toAdd; i++) {
            s += pad;
        }
        return s;
    }

    public static int numDays(Date from, Date to) {
        long millis = to.getTime() - from.getTime();
        return (int) (1 + divideCeil(millis, DAY_IN_MILLIS));
    }

    private static long divideCeil(long x, long y) {
        return (x + y - 1)/ y;
    }

    public static Date plusDays(Date date, int days) {
        return new Date(date.getTime() + DAY_IN_MILLIS * days);
    }

    /**
     *
     * @return 0 for monday, 6 for sunday
     */
    public static int getDayOfWeek(Date date) {
        return (date.getDay() + 7 - 1) % 7;
    }
}
