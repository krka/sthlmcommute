package se.krka.travelopt.localization;

import org.joda.time.DateTime;
import se.krka.travelopt.Money;
import se.krka.travelopt.Ticket;
import se.krka.travelopt.TravelResult;
import se.krka.travelopt.WeekDays;

import java.util.Locale;

public class EnglishLocale implements TravelOptLocale {
    public String tooLongPeriodError() {
        return "Travel plan can not be longer than two years.";
    }

    public String wholeDays(String name, int numDays, Money price) {
        return name + " (" + numDays + " days for " + price + ")";
    }

    public String simpleTicket(String name, int numTickets, Money price) {
        return name + " (" + numTickets + " tickets for " + price + ")";
    }

    public String travelResult(TravelResult travelResult) {
        return "Travel plan suggestion: " + travelResult.getTotalCost() + "\n" + travelResult.ticketsToString();
    }

    public String ticket(Ticket ticket) {
        return formatDate(ticket.getStartDate()) + " " + ticket.getTicketType() + ", " + ticket.getCost();
    }

    public String priceStructure(String body) {
        return "Price list:\n" + body;
    }

    public String tooManyColonsInTerm(String term) {
        return "Too many colons in " + term;
    }

    public String tooManyDashesInTerm(String term) {
        return "Too many dashes in " + term;
    }

    public String ambiguousWeekDay(String input, WeekDays.WeekDayEnum match1, WeekDays.WeekDayEnum match2) {
        return "weekday " + input + " is ambiguous, could mean either " + match1 + " or " + match2;
    }

    public String weekDayName(WeekDays.WeekDayEnum value) {
        switch (value) {
            case MONDAY: return "Monday";
            case TUESDAY: return "Tuesday";
            case WEDNESDAY: return "Wednesday";
            case THURSDAY: return "Thursday";
            case FRIDAY: return "Friday";
            case SATURDAY: return "Saturday";
            case SUNDAY: return "Sunday";
            default: throw new IllegalStateException("Can not occur.");
        }
    }

    public Locale locale() {
        return new Locale("en", "GB");
    }

    public String invalidWeekDay(String input) {
        return input + " is not a valid weekday";
    }

    public String travelPlanDate(DateTime date, int numTickets) {
        return formatDate(date) + " needs " + numTickets + " tickets";
    }

    private String formatDate(DateTime date) {
        return weekDay(date).substring(0, 3) + " " + date.toString("YYYY-MM-dd");
    }

    private String weekDay(DateTime date) {
        return weekDayName(WeekDays.WeekDayEnum.get(date));
    }

}
