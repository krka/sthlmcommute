package se.krka.travelopt;

import org.joda.time.*;
import org.joda.time.Period;

import java.util.*;

public class TravelPlan {
	private final SortedSet<TravelPlanDate> dates = new TreeSet<TravelPlanDate>();
	private final SortedSet<TravelPlanDate> immutableDates = Collections.unmodifiableSortedSet(dates);

	private final DateTime extensionStart;
	private final Period period;

	public TravelPlan(Collection<TravelPlanDate> dates, DateTime extensionStart) {
		this.extensionStart = extensionStart;
		this.dates.addAll(dates);
        if (dates.isEmpty()) {
            period = null;
        } else {
            period = new Period(this.dates.first().getDate(), this.dates.last().getDate());
        }
	}

	public static TravelPlan.Builder builder() {
		return new Builder();
	}

	public SortedSet<TravelPlanDate> getDates() {
		return immutableDates;
	}

	public DateTime getExtensionStart() {
		return extensionStart;
	}

	public Period getPeriod() {
		return period;
	}

	public static class Builder {
		private final SortedSet<TravelPlanDate> dates = new TreeSet<TravelPlanDate>();
		private int numTickets;

		public Builder addDay(DateTime date) {
			addDay(date, numTickets);
			return this;
		}

		public TravelPlan build() {
            if (dates.isEmpty()) {
                return new TravelPlan(dates, null);
            }
            return new TravelPlan(dates, dates.last().getDate().plusDays(1));
        }

		public Builder setTicketsPerDay(int numTickets) {
			this.numTickets = numTickets;
			return this;
		}

		public Builder addPeriod(DateTime from, DateTime to, WeekDays days) {
			Period period = new Period(from, to);
			if (period.getYears() > 2) {
				throw new IllegalArgumentException("date range can not be longer than two years");
			}
			addDay(from, days);
			while (from.isBefore(to)) {
				from = from.plusDays(1);
				addDay(from, days);
			}
			return this;
		}

		public TravelPlan buildExtended(WeekDays days) {
			DateTime lastDate = dates.last().getDate();
			DateTime extensionStart= lastDate.plusDays(1);

			// Hardcoded number of days = 30, the best ticket type for SL
			for (int i = 0; i < 30; i++) {
				lastDate = lastDate.plusDays(1);
				addDay(lastDate, days);
			}
			return new TravelPlan(dates, extensionStart);
		}

		private void addDay(DateTime dateTime, WeekDays weekDays) {
			int dayOfWeek = dateTime.getDayOfWeek();
			int numTickets = weekDays.getNumTickets(dayOfWeek, this.numTickets);
			addDay(dateTime, numTickets);
		}

		private void addDay(DateTime dateTime, int numTickets) {
			if (numTickets > 0) {
				dates.add(new TravelPlanDate(dateTime, numTickets));
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
