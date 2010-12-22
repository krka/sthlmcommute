package se.krka.travelopt;

import se.krka.travelopt.localization.TravelOptLocale;

public class WholeDays implements TicketType {
    private final TravelOptLocale locale;

    private final String name;
	private final Money price;
	private final int numDays;

	public WholeDays(TravelOptLocale locale, String name, Money price, int numDays) {
        this.locale = locale;
        this.name = name;
		this.price = price;
		this.numDays = numDays;
	}

	public int numberOfDays() {
		return numDays;
	}

	public Money cost(int numTickets) {
		return price;
	}

	@Override
	public String toString() {
        return locale.wholeDays(name, numDays, price);
	}
}
