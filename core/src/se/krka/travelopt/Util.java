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
        if (date == null) {
            return "";
        }
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();
        return pad('0', 4, "" + year) + "-" +
                pad('0', 2, "" + month) + "-" +
                pad('0', 2, "" + day);

    }

    public static String pad(char pad, int width, String s) {
        int toAdd = width - s.length();
        for (int i = 0; i < toAdd; i++) {
            s = pad + s;
        }
        return s;
    }

    public static long numDays(Date from, Date to) {
        return dayDifference(to, from) + 1;
    }

    private static final long KNOWN_OFFSET = parse("2000-01-01").getTime() % DAY_IN_MILLIS;

    public static long dayDifference(Date x, Date y) {
        long x1 = (x.getTime() - KNOWN_OFFSET) / DAY_IN_MILLIS;
        long y1 = (y.getTime() - KNOWN_OFFSET) / DAY_IN_MILLIS;
        return x1 - y1;
    }

    public static boolean before(Date x, Date y) {
        return dayDifference(y, x) > 0;
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
