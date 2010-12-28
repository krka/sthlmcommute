package se.krka.travelopt.localization;

import org.gwttime.time.DateTime;
import se.krka.travelopt.*;

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
        return formatTicketDate(ticket) + " " + ticket.getTicketType() + ", " + ticket.getCost();
    }

    private String formatTicketDate(Ticket ticket) {
        if (ticket.getEndDate().equals(ticket.getStartDate())) {
            return formatDate(ticket.getStartDate());
        }
        return formatDate(ticket.getStartDate()) + " to " + formatDate(ticket.getEndDate());
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

    public String weekDayName(int value) {
        switch (value) {
            case 0: return "Monday";
            case 1: return "Tuesday";
            case 2: return "Wednesday";
            case 3: return "Thursday";
            case 4: return "Friday";
            case 5: return "Saturday";
            case 6: return "Sunday";
            default: throw new IllegalStateException("Can not occur.");
        }
    }

    public String invalidWeekDay(String input) {
        return input + " is not a valid weekday";
    }

    public String travelPlanDate(DateTime date, int numTickets) {
        return formatDate(date) + " needs " + numTickets + " tickets";
    }

    @Override
    public String mustSelectPeriod() {
        return "You must select a non-empty time period.";
    }

    private String formatDate(DateTime date) {
        int year = date.getYear();
        int month = date.getMonthOfYear();
        int day = date.getDayOfMonth();
        String dateString = Util.pad('0', 4, "" + year) + "-" +
                Util.pad('0', 2, "" + month) + "-" +
                Util.pad('0', 2, "" + day);
        return weekDay(date).substring(0, 3) + " " + dateString;
    }

    private String weekDay(DateTime date) {
        return weekDayName(WeekDays.WeekDayEnum.get(date));
    }

}
