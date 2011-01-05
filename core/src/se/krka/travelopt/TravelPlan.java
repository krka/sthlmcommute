package se.krka.travelopt;

import java.util.*;

public class TravelPlan {

    private final TreeSet<TravelPlanDate> dates = new TreeSet<TravelPlanDate>();
	private final Collection<TravelPlanDate> immutableDates = Collections.unmodifiableSortedSet(dates);

    private final int extensionStart;

	public TravelPlan(Collection<TravelPlanDate> dates, int extensionStart) {
        this.extensionStart = extensionStart;
		this.dates.addAll(dates);
	}

	public static TravelPlan.Builder builder() {
		return new Builder();
	}

	public Collection<TravelPlanDate> getDates() {
		return immutableDates;
	}

	public int getExtensionStart() {
		return extensionStart;
	}

    public int getNumDays() {
        if (dates.isEmpty()) {
            return 0;
        }
        return dates.last().getDayOrdinal() - dates.first().getDayOrdinal();
    }

    public static class Builder {

        private final SortedSet<TravelPlanDate> dates = new TreeSet<TravelPlanDate>();
        private int numCoupons;
        private int lastDay;

        public Builder() {
        }

        public Builder addDay(Date date) {
			addDay(Util.toDayOrdinal(date), numCoupons);
			return this;
		}

		public TravelPlan build() {
            if (dates.isEmpty()) {
                return new TravelPlan(dates, 0);
            }
            int ext = lastDay + 1;
            return new TravelPlan(dates, ext);
        }

		public Builder setCouponsPerDay(int numCoupons) {
			this.numCoupons = numCoupons;
			return this;
		}

        public Builder addPeriod(Date from, Date to, String days) {
            WeekDays weekDays = new WeekDays(days);
            return addPeriod(from, to, weekDays);
        }

		public Builder addPeriod(Date from, Date to, int[] coupons) {
            WeekDays weekDays = new WeekDays(coupons);
            return addPeriod(from, to, weekDays);
		}

        private Builder addPeriod(Date from, Date to, WeekDays weekDays) {
            int fromOrdinal = Util.toDayOrdinal(from);
            int toOrdinal = Util.toDayOrdinal(to);
            if (toOrdinal - fromOrdinal > 1000) {
                throw new IllegalArgumentException();
            }
            for (int i = fromOrdinal; i <= toOrdinal; i++) {
                addDay(i, weekDays);
            }
            return this;
        }

        public Builder addPeriod(Date from, Date to) {
            return addPeriod(from, to, WeekDays.ALL);
        }

        public TravelPlan buildExtended(int[] coupons) {
            return buildExtended(new WeekDays(coupons));
        }

		public TravelPlan buildExtended(String days) {
            return buildExtended(new WeekDays(days));
		}

        private TravelPlan buildExtended(WeekDays weekDays) {
            if (lastDay == 0) {
                return new TravelPlan(dates, 0);
            }
            int extensionStart = lastDay + 1;

            // Hardcoded number of days = 30, the best ticket type for SL
            for (int i = 0; i < 30; i++) {
                addDay(extensionStart + i, weekDays);
            }
            return new TravelPlan(dates, extensionStart);
        }

        private void addDay(int dayOrdinal, WeekDays weekDays) {
            addDay(dayOrdinal, weekDays.getNumCoupons(numCoupons, dayOrdinal));
		}

		private void addDay(int dayOrdinal, int numCoupons) {
            if (dayOrdinal > lastDay) {
                lastDay = dayOrdinal;
            }
            if (numCoupons > 0) {
                dates.add(new TravelPlanDate(dayOrdinal, numCoupons));
            }
        }

    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (TravelPlanDate date : dates) {
			builder.append(date).append("\n");
		}
		return builder.toString();
	}
}
