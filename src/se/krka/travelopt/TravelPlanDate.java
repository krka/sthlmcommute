package se.krka.travelopt;

import org.joda.time.DateTime;

public class TravelPlanDate implements Comparable<TravelPlanDate> {
	private final DateTime date;
	private final int numTickets;

	public TravelPlanDate(DateTime date, int numTickets) {
		this.date = date;
		this.numTickets = numTickets;
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
		return "TravelPlanDate{" +
				"date=" + date +
				", numTickets=" + numTickets +
				'}';
	}
}
