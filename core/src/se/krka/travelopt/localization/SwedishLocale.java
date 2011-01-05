package se.krka.travelopt.localization;

import se.krka.travelopt.*;

public class SwedishLocale extends TravelOptLocale {
    public static final SwedishLocale INSTANCE = new SwedishLocale();
    private SwedishLocale() {
    }

	public String wholeDays(String name, int numDays, Money price) {
        return numDays + " dagar för " + price;
    }

    public String couponTicketDesc(int numTickets, Money price) {
        return numTickets + " biljetter för " + price;
    }


    public String weekDayName(int day) {
        switch (day) {
            case 0: return "Måndag";
            case 1: return "Tisdag";
            case 2: return "Onsdag";
            case 3: return "Torsdag";
            case 4: return "Fredag";
            case 5: return "Lördag";
            case 6: return "Söndag";
            default: throw new IllegalStateException();
        }
    }

    @Override
    public int firstDayOfWeek() {
        return 0;
    }
}
