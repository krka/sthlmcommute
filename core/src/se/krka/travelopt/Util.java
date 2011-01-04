package se.krka.travelopt;

import java.util.Date;

public class Util {
    private static final long DAY_IN_MILLIS = 86400000;
    private static final int WEEKDAY_OFFSET = 3;

    public static Date parseDate(String s) {
        String[] split = s.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        int day = Integer.parseInt(split[2]);
        return new Date(year - 1900, month - 1, day);
    }

    public static int parseDay(String s) {
        return toDayOrdinal(parseDate(s));
    }

    public static String format(Date date) {
        if (date == null) {
            return "";
        }
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();
        return pad('0', WEEKDAY_OFFSET, "" + year) + "-" +
                pad('0', 2, "" + month) + "-" +
                pad('0', 2, "" + day);

    }

    public static String formatDay(int dayOrdinal) {
        return format(fromDayOrdinal(dayOrdinal));
    }

    public static String pad(char pad, int width, String s) {
        int toAdd = width - s.length();
        for (int i = 0; i < toAdd; i++) {
            s = pad + s;
        }
        return s;
    }

    private static final long KNOWN_OFFSET = parseDate("2000-01-01").getTime() % DAY_IN_MILLIS;

    public static int dayDifference(Date x, Date y) {
        return toDayOrdinal(x) - toDayOrdinal(y);
    }

    public static int toDayOrdinal(Date date) {
        return WEEKDAY_OFFSET + (int) ((date.getTime() - KNOWN_OFFSET) / DAY_IN_MILLIS);
    }

    public static Date fromDayOrdinal(int ordinal) {
        return new Date((ordinal - WEEKDAY_OFFSET) * DAY_IN_MILLIS + KNOWN_OFFSET);
    }

    public static boolean before(Date x, Date y) {
        return dayDifference(y, x) > 0;
    }

    public static Date plusDays(Date date, int days) {
        return new Date(date.getTime() + DAY_IN_MILLIS * days);
    }

    /**
     *
     * @return 0 for monday, 6 for sunday
     */
    public static int getDayOfWeek(int dayOrdinal) {
        return dayOrdinal % 7;
    }

}
