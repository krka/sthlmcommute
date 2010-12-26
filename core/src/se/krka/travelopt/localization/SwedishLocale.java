package se.krka.travelopt.localization;

import org.joda.time.DateTime;
import se.krka.travelopt.Money;
import se.krka.travelopt.Ticket;
import se.krka.travelopt.TravelResult;
import se.krka.travelopt.WeekDays;

import java.util.Locale;

public class SwedishLocale implements TravelOptLocale {
    public String tooLongPeriodError() {
        return "Reseplanering kan inte göras för mer än två år.";
    }

    public String wholeDays(String name, int numDays, Money price) {
        return name + " (" + numDays + " dagar för " + price + ")";
    }

    public String simpleTicket(String name, int numTickets, Money price) {
        return name + " (" + numTickets + " biljetter för " + price + ")";
    }

    public String travelResult(TravelResult travelResult) {
        return "Planeringsförslag: " + travelResult.getTotalCost() + "\n" + travelResult.ticketsToString();
    }

    public String ticket(Ticket ticket) {
        return formatTicketDate(ticket) + " " + ticket.getTicketType() + ", " + ticket.getCost();
    }

    private String formatTicketDate(Ticket ticket) {
        if (ticket.getEndDate().equals(ticket.getStartDate())) {
            return formatDate(ticket.getStartDate());
        }
        return formatDate(ticket.getStartDate()) + " till " + formatDate(ticket.getEndDate());
    }


    public String priceStructure(String body) {
        return "Biljettprislista:\n" + body;
    }

    public String tooManyColonsInTerm(String term) {
        return "För många kolon i " + term;
    }

    public String tooManyDashesInTerm(String term) {
        return "Får många bindestreck i " + term;
    }

    public String ambiguousWeekDay(String input, WeekDays.WeekDayEnum match1, WeekDays.WeekDayEnum match2) {
        return "Veckodagen " + input + " är tvetydig, kan betyda både " + match1 + " och " + match2;
    }

    public String weekDayName(WeekDays.WeekDayEnum value) {
        switch (value) {
            case MONDAY: return "Måndag";
            case TUESDAY: return "Tisdag";
            case WEDNESDAY: return "Onsdag";
            case THURSDAY: return "Torsdag";
            case FRIDAY: return "Fredag";
            case SATURDAY: return "Lördag";
            case SUNDAY: return "Söndag";
            default: throw new IllegalStateException("Can not occur.");
        }
    }

    public Locale locale() {
        return new Locale("sv", "SE");
    }

    public String invalidWeekDay(String input) {
        return input + " är inte en giltig veckodag.";
    }

    public String travelPlanDate(DateTime date, int numTickets) {
        return formatDate(date) + " behöver " + numTickets + " biljetter";
    }

    @Override
    public String mustSelectPeriod() {
        return "Du måste välja en icketom tidsperiod";
    }

    private String formatDate(DateTime date) {
        return weekDay(date).substring(0, 3) + " " + date.toString("YYYY-MM-dd");
    }

    private String weekDay(DateTime date) {
        return weekDayName(WeekDays.WeekDayEnum.get(date));
    }

}
