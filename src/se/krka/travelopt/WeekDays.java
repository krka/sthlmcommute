package se.krka.travelopt;

import java.util.EnumMap;
import java.util.Map;

public class WeekDays {
	private final Map<WeekDayEnum, Integer> map = new EnumMap<WeekDayEnum, Integer>(WeekDayEnum.class);
	public static final WeekDays ALL = new WeekDays("mon-sun");

	public WeekDays(String s) {
		String[] terms = s.split(",");
		for (String term : terms) {
			processTerm(term);
		}
	}

	public int getNumTickets(int dayOfWeek, int defaultNumTickets) {
		Integer integer = map.get(WeekDayEnum.VALUES[dayOfWeek - 1]);
		if (integer == null) {
			return 0;
		}
		if (integer == -1) {
			return defaultNumTickets;
		}
		return integer;
	}

	private void processTerm(String term) {
		String[] s = term.split(":");
		if (s.length == 1) {
			processRange(s[0], -1);
		} else if (s.length == 2) {
			int numTickets = Integer.parseInt(s[1].trim());
			processRange(s[0], numTickets);
		} else {
			throw new IllegalArgumentException("Too many : in " + term);
		}
	}

	private void processRange(String term, int numTickets) {
        String[] s = term.split("-");
        if (s.length == 1) {
            WeekDayEnum weekDayEnum = WeekDayEnum.parse(s[0]);
            processWeekDay(weekDayEnum, numTickets);
        } else if (s.length == 2) {
            WeekDayEnum from = WeekDayEnum.parse(s[0]);
            WeekDayEnum to = WeekDayEnum.parse(s[1]);
            processWeekDay(from, numTickets);
            while (from != to) {
                from = from.succ();
                processWeekDay(from, numTickets);
            }
        } else {
            throw new IllegalArgumentException("Too many - in " + term);
        }
    }

	private void processWeekDay(WeekDayEnum weekDay, int numTickets) {
		map.put(weekDay, numTickets);
	}

	private enum WeekDayEnum {
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY,
		SUNDAY;

		private final static WeekDayEnum[] VALUES = values();

		public static WeekDayEnum parse(String s) {
            s = s.trim();
            String upper = s.toUpperCase();
			WeekDayEnum match = null;
			for (WeekDayEnum value : VALUES) {
				if (value.name().startsWith(upper)) {
					if (match == null) {
						match = value;
					} else {
						throw new IllegalArgumentException("weekday " + s + " is ambiguous, could mean either " + match + " or " + value);
					}
				}
			}
			if (match == null) {
				throw new IllegalArgumentException(s + " is not a valid weekday");
			}
			return match;
		}

		public WeekDayEnum succ() {
			return VALUES[(ordinal() + 1) % 7];
		}
	}
}
