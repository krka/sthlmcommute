package se.krka.travelopt;

import se.krka.travelopt.localization.EnglishLocale;

public class WeekDays {
    private final int[] coupons = new int[7];

    public static WeekDays ALL = new WeekDays();

    private WeekDays() {
        for (int i = 0; i < 7; i++) {
            coupons[i] = -1;
        }
    }

    public WeekDays(int[] coupons) {
        System.arraycopy(coupons, 0, this.coupons, 0, 7);
    }

    public WeekDays(String s) {
        String[] terms = s.split(",");
		for (String term : terms) {
			processTerm(term);
		}
	}

	public int getNumCoupons(int defaultNumCoupons, int dayOrdinal) {
        int dayOfWeek = Util.getDayOfWeek(dayOrdinal);
        int integer = coupons[dayOfWeek];
		if (integer == -1) {
			return defaultNumCoupons;
		}
		return integer;
	}

	private void processTerm(String term) {
		String[] s = term.split(":");
		if (s.length == 1) {
			processRange(s[0], -1);
		} else if (s.length == 2) {
			int numCoupons = Integer.parseInt(s[1].trim());
			processRange(s[0], numCoupons);
		} else {
            throw new IllegalArgumentException("Too many colons in " + term);
		}
	}

	private void processRange(String term, int numCoupons) {
        String[] s = term.split("-");
        if (s.length == 1) {
            WeekDayEnum weekDayEnum = WeekDayEnum.parse(s[0]);
            processWeekDay(weekDayEnum, numCoupons);
        } else if (s.length == 2) {
            WeekDayEnum from = WeekDayEnum.parse(s[0]);
            WeekDayEnum to = WeekDayEnum.parse(s[1]);
            processWeekDay(from, numCoupons);
            while (from != to) {
                from = from.succ();
                processWeekDay(from, numCoupons);
            }
        } else {
            throw new IllegalArgumentException("Too many dashes in " + term);
        }
    }

	private void processWeekDay(WeekDayEnum weekDay, int numCoupons) {
        coupons[weekDay.ordinal()] = numCoupons;
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

		public static WeekDayEnum parse(String s) {
            s = s.trim();
            String upper = s.toUpperCase();
			WeekDayEnum match = null;
			for (WeekDayEnum value : VALUES) {
                String weekDayName = EnglishLocale.INSTANCE.weekDayName(value.ordinal()).toUpperCase();
				if (weekDayName.startsWith(upper)) {
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
