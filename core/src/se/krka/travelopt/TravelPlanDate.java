package se.krka.travelopt;

import org.joda.time.DateTime;
import se.krka.travelopt.localization.TravelOptLocale;

public class TravelPlanDate implements Comparable<TravelPlanDate> {
    private final TravelOptLocale locale;

	private final DateTime date;
	private final int numTickets;

	public TravelPlanDate(DateTime date, int numTickets, TravelOptLocale locale) {
		this.date = date;
		this.numTickets = numTickets;
        this.locale = locale;
    }

	public int compareTo(TravelPlanDate travelPlanDate) {
		return date.compareTo(travelPlanDate.date);
	}

	public DateTime getDate() {
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
