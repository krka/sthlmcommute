package se.krka.travelopt.localization;

import se.krka.travelopt.*;

import java.util.Date;

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

    public String weekDayName(int value) {
        switch (value) {
            case 0: return "Måndag";
            case 1: return "Tisdag";
            case 2: return "Onsdag";
            case 3: return "Torsdag";
            case 4: return "Fredag";
            case 5: return "Lördag";
            case 6: return "Söndag";
            default: throw new IllegalStateException("Can not occur.");
        }
    }

    public String invalidWeekDay(String input) {
        return input + " är inte en giltig veckodag.";
    }

    public String travelPlanDate(Date date, int numTickets) {
        return formatDate(date) + " behöver " + numTickets + " biljetter";
    }

    @Override
    public String mustSelectPeriod() {
        return "Du måste välja en icketom tidsperiod";
    }

    private String formatDate(Date date) {
        return weekDay(date).substring(0, 3) + " " + Util.format(date);
    }

    private String weekDay(Date date) {
        return weekDayName(WeekDays.WeekDayEnum.get(date));
    }

}
