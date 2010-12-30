package se.krka.travelopt;

import se.krka.travelopt.localization.TravelOptLocale;

import java.util.*;

public class TravelPlan {
    private final TravelOptLocale locale;

	private final SortedSet<TravelPlanDate> dates = new TreeSet<TravelPlanDate>();
	private final SortedSet<TravelPlanDate> immutableDates = Collections.unmodifiableSortedSet(dates);

    private final Date extensionStart;

	public TravelPlan(TravelOptLocale locale, Collection<TravelPlanDate> dates, Date extensionStart) {
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

	public Date getExtensionStart() {
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

        public Builder addDay(Date date) {
			addDay(date, numTickets);
			return this;
		}

		public TravelPlan build() {
            if (dates.isEmpty()) {
                return new TravelPlan(locale, dates, null);
            }
            Date ext = Util.plusDays(dates.last().getDate(), 1);
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
            if (Util.numDays(from, to) > 2*365) {
                throw new IllegalArgumentException(locale.tooLongPeriodError());
            }
            addDay(from, weekDays);
            while (from.before(to)) {
                from = Util.plusDays(from, 1);
                addDay(from, weekDays);
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
            Date lastDate = dates.last().getDate();
            Date extensionStart = Util.plusDays(lastDate, 1);

            // Hardcoded number of days = 30, the best ticket type for SL
            for (int i = 0; i < 30; i++) {
                lastDate = Util.plusDays(lastDate, 1);
                addDay(lastDate, weekDays);
            }
            return new TravelPlan(locale, dates, extensionStart);
        }

        private void addDay(Date Date, WeekDays weekDays) {
			int numTickets = weekDays.getNumTickets(this.numTickets, Date);
			addDay(Date, numTickets);
		}

		private void addDay(Date date, int numTickets) {
			if (numTickets > 0) {
				dates.add(new TravelPlanDate(date, numTickets, locale));
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
