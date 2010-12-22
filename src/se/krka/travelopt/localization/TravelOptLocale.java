package se.krka.travelopt.localization;

import org.joda.time.DateTime;
import se.krka.travelopt.Money;
import se.krka.travelopt.Ticket;
import se.krka.travelopt.TravelResult;
import se.krka.travelopt.WeekDays;

import java.util.Locale;

public interface TravelOptLocale {
    String tooLongPeriodError();

    String wholeDays(String name, int numDays, Money price);

    String simpleTicket(String name, int numTickets, Money price);

    String travelResult(TravelResult travelResult);

    String ticket(Ticket ticket);

    String priceStructure(String body);

    String tooManyColonsInTerm(String term);

    String tooManyDashesInTerm(String term);

    String ambiguousWeekDay(String input, WeekDays.WeekDayEnum match1, WeekDays.WeekDayEnum match2);

    String weekDayName(WeekDays.WeekDayEnum value);

    Locale locale();

    String invalidWeekDay(String input);

    String travelPlanDate(DateTime date, int numTickets);
}
