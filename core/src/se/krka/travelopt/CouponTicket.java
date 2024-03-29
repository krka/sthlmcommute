package se.krka.travelopt;

import se.krka.travelopt.localization.EnglishLocale;
import se.krka.travelopt.localization.TravelOptLocale;

public class CouponTicket implements TicketType {

    private final String name;
	private final Money price;
	private final int numCoupons;

    public CouponTicket(String name, Money price, int numCoupons) {
        this.name = name;
		this.price = price;
		this.numCoupons = numCoupons;
    }

	public int numberOfDays() {
		return 1;
	}

	public Money cost(int numCoupons) {
        return price.multiply(numCoupons).divideBy(this.numCoupons);
	}

    @Override
    public int getCount(int numCoupons) {
        return numCoupons;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description(TravelOptLocale locale) {
        return locale.couponTicketDesc(numCoupons, price);
    }

    @Override
	public String toString() {
        return name + " (" + description(EnglishLocale.INSTANCE) + ")";
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CouponTicket that = (CouponTicket) o;

        if (numCoupons != that.numCoupons) return false;
        if (!name.equals(that.name)) return false;
        if (!price.equals(that.price)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + numCoupons;
        return result;
    }
}
