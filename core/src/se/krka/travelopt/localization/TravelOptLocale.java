package se.krka.travelopt.localization;

import se.krka.travelopt.*;

public abstract class TravelOptLocale {

    public abstract String wholeDays(String name, int numDays, Money price);

    public abstract String couponTicketDesc(int numTickets, Money price);

    public abstract String weekDayName(int day);

    public String formatDay(int dayOrdinal) {
        return weekDay(dayOrdinal).substring(0, 3) + " " + Util.formatDay(dayOrdinal);
    }

    private String weekDay(int dayOrdinal) {
        return weekDayName(Util.getDayOfWeek(dayOrdinal));
    }

    public abstract int firstDayOfWeek();
}
