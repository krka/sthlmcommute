package se.krka.travelopt;

import se.krka.travelopt.localization.TravelOptLocale;

public class WeekDays {
    private final int[] tickets = new int[7];

    public static WeekDays ALL = new WeekDays();

    private WeekDays() {
        for (int i = 0; i < 7; i++) {
            tickets[i] = -1;
        }
    }

    public WeekDays(int[] tickets) {
        System.arraycopy(tickets, 0, this.tickets, 0, 7);
    }

    public WeekDays(TravelOptLocale locale, String s) {
        String[] terms = s.split(",");
		for (String term : terms) {
			processTerm(term, locale);
		}
	}

	public int getNumTickets(int defaultNumTickets, int dayOrdinal) {
        int integer = tickets[Util.getDayOfWeek(dayOrdinal)];
		if (integer == -1) {
			return defaultNumTickets;
		}
		return integer;
	}

	private void processTerm(String term, TravelOptLocale locale) {
		String[] s = term.split(":");
		if (s.length == 1) {
			processRange(s[0], -1, locale);
		} else if (s.length == 2) {
			int numTickets = Integer.parseInt(s[1].trim());
			processRange(s[0], numTickets, locale);
		} else {
			throw new IllegalArgumentException(locale.tooManyColonsInTerm(term));
		}
	}

	private void processRange(String term, int numTickets, TravelOptLocale locale) {
        String[] s = term.split("-");
        if (s.length == 1) {
            WeekDayEnum weekDayEnum = WeekDayEnum.parse(locale, s[0]);
            processWeekDay(weekDayEnum, numTickets);
        } else if (s.length == 2) {
            WeekDayEnum from = WeekDayEnum.parse(locale, s[0]);
            WeekDayEnum to = WeekDayEnum.parse(locale, s[1]);
            processWeekDay(from, numTickets);
            while (from != to) {
                from = from.succ();
                processWeekDay(from, numTickets);
            }
        } else {
            throw new IllegalArgumentException(locale.tooManyDashesInTerm(term));
        }
    }

	private void processWeekDay(WeekDayEnum weekDay, int numTickets) {
        tickets[weekDay.ordinal()] = numTickets;
	}

	public enum WeekDayEnum {
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY,
		SUNDAY;

		private final static WeekDayEnum[] VALUES = values();

		public static WeekDayEnum parse(TravelOptLocale locale, String s) {
            s = s.trim();
            String upper = s.toUpperCase();
			WeekDayEnum match = null;
			for (WeekDayEnum value : VALUES) {
                String weekDayName = locale.weekDayName(value.ordinal()).toUpperCase();
				if (weekDayName.startsWith(upper)) {
					if (match == null) {
						match = value;
					} else {
						throw new IllegalArgumentException(locale.ambiguousWeekDay(s, match, value));
					}
				}
			}
			if (match == null) {
				throw new IllegalArgumentException(locale.invalidWeekDay(s));
			}
			return match;
		}

		public WeekDayEnum succ() {
			return VALUES[(ordinal() + 1) % 7];
		}
	}
}
