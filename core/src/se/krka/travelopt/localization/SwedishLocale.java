package se.krka.travelopt.localization;

import se.krka.travelopt.*;

public class SwedishLocale implements TravelOptLocale {
    public static final SwedishLocale INSTANCE = new SwedishLocale();
    private SwedishLocale() {
    }

    public String tooLongPeriodError() {
        return "Reseplanering kan inte göras för mer än två år.";
    }

    public String wholeDays(String name, int numDays, Money price) {
        return numDays + " dagar för " + price;
    }

    public String couponTicketDesc(int numTickets, Money price) {
        return numTickets + " biljetter för " + price;
    }

    public String travelResult(TravelResult travelResult) {
        return "Planeringsförslag: " + travelResult.getTotalCost() + "\n" + travelResult.ticketsToString();
    }

    public String ticket(Ticket ticket) {
        return formatTicketDate(ticket) + " " + ticket.getTicketType() + ", " + ticket.getCost();
    }

    private String formatTicketDate(Ticket ticket) {
        if (ticket.getEndDate() == ticket.getStartDate()) {
            return formatDay(ticket.getStartDate());
        }
        return formatDay(ticket.getStartDate()) + " till " + formatDay(ticket.getEndDate());
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

    public String travelPlanDate(int dayOrdinal, int numTickets) {
        return formatDay(dayOrdinal) + " behöver " + numTickets + " biljetter";
    }

    @Override
    public String mustSelectPeriod() {
        return "Du måste välja en icketom tidsperiod";
    }

    @Override
    public String formatDay(int dayOrdinal) {
        return weekDay(dayOrdinal).substring(0, 3) + " " + Util.formatDay(dayOrdinal);
    }

    @Override
    public int firstDayOfWeek() {
        return 0;
    }

    private String weekDay(int dayOrdinal) {
        return weekDayName(Util.getDayOfWeek(dayOrdinal));
    }

}
