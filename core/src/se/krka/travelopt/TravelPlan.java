package se.krka.travelopt;

import org.gwttime.time.DateTime;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class TravelPlan {
    private final TravelOptLocale locale;

	private final SortedSet<TravelPlanDate> dates = new TreeSet<TravelPlanDate>();
	private final SortedSet<TravelPlanDate> immutableDates = Collections.unmodifiableSortedSet(dates);

    private final DateTime extensionStart;

	public TravelPlan(TravelOptLocale locale, Collection<TravelPlanDate> dates, DateTime extensionStart) {
        this.locale = locale;
        this.extensionStart = extensionStart;
		this.dates.addAll(dates);
	}

	public static TravelPlan.Builder builder(TravelOptLocale locale) {
		return new Builder(locale);
	}

	public SortedSet<TravelPlanDate> getDates() {
		return immutableDates;
	}

	public DateTime getExtensionStart() {
		return extensionStart;
	}

    public TravelOptLocale getLocale() {
        return locale;
    }

    public int getNumDays() {
        return Util.numDays(dates.first().getDate(), dates.last().getDate());
    }

    public static class Builder {
        private final TravelOptLocale locale;

        private final SortedSet<TravelPlanDate> dates = new TreeSet<TravelPlanDate>();
        private int numTickets;

        public Builder(TravelOptLocale locale) {
            this.locale = locale;
        }

        public Builder addDay(DateTime date) {
			addDay(date, numTickets);
			return this;
		}

		public TravelPlan build() {
            if (dates.isEmpty()) {
                return new TravelPlan(locale, dates, null);
            }
            return new TravelPlan(locale, dates, dates.last().getDate().plusDays(1));
        }

		public Builder setTicketsPerDay(int numTickets) {
			this.numTickets = numTickets;
			return this;
		}

		public Builder addPeriod(DateTime from, DateTime to, String days) {
            WeekDays weekDays = new WeekDays(locale, days);
            return addPeriod(from, to, weekDays);
		}

        private Builder addPeriod(DateTime from, DateTime to, WeekDays weekDays) {
            if (Util.numDays(from, to) > 2*365) {
                throw new IllegalArgumentException(locale.tooLongPeriodError());
            }
            addDay(from, weekDays);
            while (from.isBefore(to)) {
                from = from.plusDays(1);
                addDay(from, weekDays);
            }
            return this;
        }

        public Builder addPeriod(DateTime from, DateTime to) {
            return addPeriod(from, to, WeekDays.ALL);
        }

		public TravelPlan buildExtended(String days) {
            WeekDays weekDays = new WeekDays(locale, days);
			DateTime lastDate = dates.last().getDate();
			DateTime extensionStart= lastDate.plusDays(1);

			// Hardcoded number of days = 30, the best ticket type for SL
			for (int i = 0; i < 30; i++) {
				lastDate = lastDate.plusDays(1);
				addDay(lastDate, weekDays);
			}
			return new TravelPlan(locale, dates, extensionStart);
		}

		private void addDay(DateTime dateTime, WeekDays weekDays) {
			int numTickets = weekDays.getNumTickets(this.numTickets, dateTime);
			addDay(dateTime, numTickets);
		}

		private void addDay(DateTime dateTime, int numTickets) {
			if (numTickets > 0) {
				dates.add(new TravelPlanDate(dateTime, numTickets, locale));
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
