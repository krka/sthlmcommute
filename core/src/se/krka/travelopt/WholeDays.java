package se.krka.travelopt;

import se.krka.travelopt.localization.EnglishLocale;
import se.krka.travelopt.localization.TravelOptLocale;

public class WholeDays implements TicketType {

    private final String name;
	private final Money price;
	private final int numDays;

	public WholeDays(String name, Money price, int numDays) {
        this.name = name;
		this.price = price;
		this.numDays = numDays;
	}

	public int numberOfDays() {
		return numDays;
	}

	public Money cost(int numCoupons) {
		return price;
	}

    @Override
    public int getCount(int numCoupons) {
        return 1;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description(TravelOptLocale locale) {
        return locale.wholeDays(name, numDays, price);
    }

    @Override
	public String toString() {
        return name + " (" + description(EnglishLocale.INSTANCE) + ")";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WholeDays wholeDays = (WholeDays) o;

        if (numDays != wholeDays.numDays) return false;
        if (!name.equals(wholeDays.name)) return false;
        if (!price.equals(wholeDays.price)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + numDays;
        return result;
    }
}
