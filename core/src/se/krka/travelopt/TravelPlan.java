package se.krka.travelopt;

import se.krka.travelopt.localization.TravelOptLocale;

import java.util.*;

public class TravelPlan {
    private final TravelOptLocale locale;

	private final TreeSet<TravelPlanDate> dates = new TreeSet<TravelPlanDate>();
	private final Collection<TravelPlanDate> immutableDates = Collections.unmodifiableSortedSet(dates);

    private final int extensionStart;

	public TravelPlan(TravelOptLocale locale, Collection<TravelPlanDate> dates, int extensionStart) {
        this.locale = locale;
        this.extensionStart = extensionStart;
		this.dates.addAll(dates);
	}

	public static TravelPlan.Builder builder(TravelOptLocale locale) {
		return new Builder(locale);
	}

	public Collection<TravelPlanDate> getDates() {
		return immutableDates;
	}

	public int getExtensionStart() {
		return extensionStart;
	}

    public TravelOptLocale getLocale() {
        return locale;
    }

    public int getNumDays() {
        return dates.last().getDayOrdinal() - dates.first().getDayOrdinal();
    }

    public static class Builder {
        private final TravelOptLocale locale;

        private final SortedSet<TravelPlanDate> dates = new TreeSet<TravelPlanDate>();
        private int numTickets;

        public Builder(TravelOptLocale locale) {
            this.locale = locale;
        }

        public Builder addDay(Date date) {
			addDay(Util.toDayOrdinal(date), numTickets);
			return this;
		}

		public TravelPlan build() {
            if (dates.isEmpty()) {
                return new TravelPlan(locale, dates, 0);
            }
            int ext = dates.last().getDayOrdinal() + 1;
            return new TravelPlan(locale, dates, ext);
        }

		public Builder setTicketsPerDay(int numTickets) {
			this.numTickets = numTickets;
			return this;
		}

        public Builder addPeriod(Date from, Date to, String days) {
            WeekDays weekDays = new WeekDays(locale, days);
            return addPeriod(from, to, weekDays);
        }

		public Builder addPeriod(Date from, Date to, int[] tickets) {
            WeekDays weekDays = new WeekDays(tickets);
            return addPeriod(from, to, weekDays);
		}

        private Builder addPeriod(Date from, Date to, WeekDays weekDays) {
            int fromOrdinal = Util.toDayOrdinal(from);
            int toOrdinal = Util.toDayOrdinal(to);
            if (toOrdinal - fromOrdinal > 2*365) {
                throw new IllegalArgumentException(locale.tooLongPeriodError());
            }
            for (int i = fromOrdinal; i <= toOrdinal; i++) {
                addDay(i, weekDays);
            }
            return this;
        }

        public Builder addPeriod(Date from, Date to) {
            return addPeriod(from, to, WeekDays.ALL);
        }

        public TravelPlan buildExtended(int[] tickets) {
            return buildExtended(new WeekDays(tickets));
        }

		public TravelPlan buildExtended(String days) {
            return buildExtended(new WeekDays(locale, days));
		}

        private TravelPlan buildExtended(WeekDays weekDays) {
            int lastDate = dates.last().getDayOrdinal();
            int extensionStart = lastDate + 1;

            // Hardcoded number of days = 30, the best ticket type for SL
            for (int i = 0; i < 30; i++) {
                addDay(extensionStart + i, weekDays);
            }
            return new TravelPlan(locale, dates, extensionStart);
        }

        private void addDay(int dayOrdinal, WeekDays weekDays) {
            addDay(dayOrdinal, weekDays.getNumTickets(numTickets, dayOrdinal));
		}

		private void addDay(int dayOrdinal, int numTickets) {
			if (numTickets > 0) {
				dates.add(new TravelPlanDate(dayOrdinal, numTickets, locale));
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
