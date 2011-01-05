package se.krka.travelopt.localization;

import se.krka.travelopt.*;

public class EnglishLocale extends TravelOptLocale {
    public static final EnglishLocale INSTANCE = new EnglishLocale();
    private EnglishLocale() {
    }

    public String wholeDays(String name, int numDays, Money price) {
        return numDays + " days for " + price;
    }

    public String couponTicketDesc(int numTickets, Money price) {
        return numTickets + " coupons for " + price;
    }

    public String weekDayName(int dayOfWeek) {
        switch (dayOfWeek) {
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
    public int firstDayOfWeek() {
        return 6;
    }

}
