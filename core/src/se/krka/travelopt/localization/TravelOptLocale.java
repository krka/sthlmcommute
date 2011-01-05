package se.krka.travelopt.localization;

import se.krka.travelopt.Money;
import se.krka.travelopt.Ticket;
import se.krka.travelopt.TravelResult;
import se.krka.travelopt.WeekDays;

public interface TravelOptLocale {

    String wholeDays(String name, int numDays, Money price);

    String couponTicketDesc(int numTickets, Money price);

    String weekDayName(int day);

    String formatDay(int dayOrdinal);

    int firstDayOfWeek();
}
