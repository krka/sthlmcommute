package se.krka.travelopt;

import se.krka.travelopt.localization.TravelOptLocale;

public class TravelPlanDate implements Comparable<TravelPlanDate> {
    private final TravelOptLocale locale;
	private final int dayOrdinal;
	private final int numTickets;

	public TravelPlanDate(int dayOrdinal, int numTickets, TravelOptLocale locale) {
        this.dayOrdinal = dayOrdinal;
		this.numTickets = numTickets;
        this.locale = locale;
    }

	public int compareTo(TravelPlanDate travelPlanDate) {
        return dayOrdinal - travelPlanDate.dayOrdinal;
	}

	public int getNumTickets() {
		return numTickets;
	}

	@Override
	public String toString() {
        return locale.travelPlanDate(dayOrdinal, numTickets);
	}

    public int getDayOrdinal() {
        return dayOrdinal;
    }
}
