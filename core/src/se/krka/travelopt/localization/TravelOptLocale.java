package se.krka.travelopt.localization;

import se.krka.travelopt.Money;
import se.krka.travelopt.Ticket;
import se.krka.travelopt.TravelResult;
import se.krka.travelopt.WeekDays;

public interface TravelOptLocale {
    String tooLongPeriodError();

    String wholeDays(String name, int numDays, Money price);

    String simpleTicketDesc(int numTickets, Money price);

    String travelResult(TravelResult travelResult);

    String ticket(Ticket ticket);

    String priceStructure(String body);

    String tooManyColonsInTerm(String term);

    String tooManyDashesInTerm(String term);

    String ambiguousWeekDay(String input, WeekDays.WeekDayEnum match1, WeekDays.WeekDayEnum match2);

    String weekDayName(int value);

    String invalidWeekDay(String input);

    String travelPlanDate(int dayOrdinal, int numTickets);

    String mustSelectPeriod();

    String formatDay(int dayOrdinal);

    int firstDayOfWeek();
}
