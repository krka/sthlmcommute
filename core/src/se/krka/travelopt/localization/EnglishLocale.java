package se.krka.travelopt.localization;

import se.krka.travelopt.*;

import java.util.Date;

public class EnglishLocale implements TravelOptLocale {
    public static final EnglishLocale INSTANCE = new EnglishLocale();
    private EnglishLocale() {
    }

    public String tooLongPeriodError() {
        return "Travel plan can not be longer than two years.";
    }

    public String wholeDays(String name, int numDays, Money price) {
        return numDays + " days for " + price;
    }

    public String simpleTicketDesc(int numTickets, Money price) {
        return numTickets + " tickets for " + price;
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

    public String travelPlanDate(Date date, int numTickets) {
        return formatDate(date) + " needs " + numTickets + " tickets";
    }

    @Override
    public String mustSelectPeriod() {
        return "You must select a non-empty time period.";
    }

    public String formatDate(Date date) {
        return weekDay(date).substring(0, 3) + " " + Util.format(date);
    }

    private String weekDay(Date date) {
        return weekDayName(Util.getDayOfWeek(date));
    }

}
