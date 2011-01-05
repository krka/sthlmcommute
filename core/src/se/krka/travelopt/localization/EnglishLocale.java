package se.krka.travelopt.localization;

import se.krka.travelopt.*;

public class EnglishLocale implements TravelOptLocale {
    public static final EnglishLocale INSTANCE = new EnglishLocale();
    private EnglishLocale() {
    }

    public String wholeDays(String name, int numDays, Money price) {
        return numDays + " days for " + price;
    }

    public String couponTicketDesc(int numTickets, Money price) {
        return numTickets + " coupons for " + price;
    }

    public String weekDayName(int day) {
        switch (day) {
            case 0: return "Monday";
            case 1: return "Tuesday";
            case 2: return "Wednesday";
            case 3: return "Thursday";
            case 4: return "Friday";
            case 5: return "Saturday";
            case 6: return "Sunday";
            default: throw new IllegalStateException();
        }
    }

    @Override
    public String formatDay(int dayOrdinal) {
        return weekDay(dayOrdinal).substring(0, 3) + " " + Util.formatDay(dayOrdinal);
    }

    @Override
    public int firstDayOfWeek() {
        return 6;
    }

    private String weekDay(int dayOrdinal) {
        return weekDayName(Util.getDayOfWeek(dayOrdinal));
    }
}
