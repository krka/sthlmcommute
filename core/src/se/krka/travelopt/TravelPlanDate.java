package se.krka.travelopt;

import se.krka.travelopt.localization.EnglishLocale;
import se.krka.travelopt.localization.TravelOptLocale;

public class TravelPlanDate implements Comparable<TravelPlanDate> {
    private final TravelOptLocale locale;
	private final int dayOrdinal;
	private final int numCoupons;

	public TravelPlanDate(int dayOrdinal, int numCoupons, TravelOptLocale locale) {
        this.dayOrdinal = dayOrdinal;
		this.numCoupons = numCoupons;
        this.locale = locale;
    }

	public int compareTo(TravelPlanDate travelPlanDate) {
        return dayOrdinal - travelPlanDate.dayOrdinal;
	}

	public int getNumCoupons() {
		return numCoupons;
	}

	@Override
	public String toString() {
        return EnglishLocale.INSTANCE.formatDay(dayOrdinal) + " needs " + numCoupons + " tickets";
    }

    public int getDayOrdinal() {
        return dayOrdinal;
    }
}
