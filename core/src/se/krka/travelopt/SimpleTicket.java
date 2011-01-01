package se.krka.travelopt;

import se.krka.travelopt.localization.TravelOptLocale;

public class SimpleTicket implements TicketType {
    private final TravelOptLocale locale;

    private final String name;
	private final Money price;
	private final int numTickets;

    public SimpleTicket(TravelOptLocale locale, String name, Money price, int numTickets) {
        this.locale = locale;
        this.name = name;
		this.price = price;
		this.numTickets = numTickets;
    }

	public int numberOfDays() {
		return 1;
	}

	public Money cost(int numTickets) {
        return price.multiply(numTickets).divideBy(this.numTickets);
	}

    @Override
    public int getCount(int numTickets) {
        return numTickets;
    }

    @Override
	public String toString() {
        return locale.simpleTicket(name, numTickets, price);
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleTicket that = (SimpleTicket) o;

        if (numTickets != that.numTickets) return false;
        if (!locale.equals(that.locale)) return false;
        if (!name.equals(that.name)) return false;
        if (!price.equals(that.price)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = locale.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + numTickets;
        return result;
    }
}
