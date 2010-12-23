package se.krka.travelopt;

import org.joda.time.DateTime;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.EnumMap;
import java.util.Map;

public class WeekDays {
    private final Map<WeekDayEnum, Integer> map = new EnumMap<WeekDayEnum, Integer>(WeekDayEnum.class);

    public static WeekDays ALL = new WeekDays();

    private WeekDays() {
        for (WeekDayEnum weekDayEnum : WeekDayEnum.values()) {
            map.put(weekDayEnum, -1);
        }
    }

    public WeekDays(TravelOptLocale locale, String s) {
        String[] terms = s.split(",");
		for (String term : terms) {
			processTerm(term, locale);
		}
	}

	public int getNumTickets(int defaultNumTickets, DateTime date) {
        Integer integer = map.get(WeekDayEnum.get(date));
		if (integer == null) {
			return 0;
		}
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
		map.put(weekDay, numTickets);
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
            String upper = s.toUpperCase(locale.locale());
			WeekDayEnum match = null;
			for (WeekDayEnum value : VALUES) {
                String weekDayName = locale.weekDayName(value).toUpperCase(locale.locale());
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

        public static WeekDayEnum get(DateTime date) {
            return VALUES[date.getDayOfWeek() - 1];
        }

	}
}