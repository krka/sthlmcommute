package se.krka.travelopt;

import se.krka.travelopt.localization.TravelOptLocale;

import java.util.Date;

public class TravelPlanDate implements Comparable<TravelPlanDate> {
    private final TravelOptLocale locale;

	private final Date date;
	private final int numTickets;

	public TravelPlanDate(Date date, int numTickets, TravelOptLocale locale) {
		this.date = date;
		this.numTickets = numTickets;
        this.locale = locale;
    }

	public int compareTo(TravelPlanDate travelPlanDate) {
		return date.compareTo(travelPlanDate.date);
	}

	public Date getDate() {
		return date;
	}

	public int getNumTickets() {
		return numTickets;
	}

	@Override
	public String toString() {
        return locale.travelPlanDate(date, numTickets);
	}
}
