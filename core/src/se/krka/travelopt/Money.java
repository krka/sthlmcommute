package se.krka.travelopt;

public class Money {
    public static final Money ZERO = new Money("", 0L);

    private final String currency;
    private final long cents;

    public Money(String currency, long cents) {
        this.currency = currency;
        this.cents = cents;
    }

    @Override
    public String toString() {
        if (isZero()) {
            return "0";
        }
        long big = cents / 100;
        long small = cents - big * 100;
        return big + (small != 0 ?  "." + small : "") + " " + currency;
    }

    public boolean isZero() {
        return cents == 0;
    }

    public Money add(Money other) {
        if (this.isZero()) {
            return other;
        }
        if (other.isZero()) {
            return this;
        }
        assertSameCurrency(other);
        return new Money(this.currency, cents + other.cents);
    }

    public void assertSameCurrency(Money other) {
        if (!isSameCurrency(other)) {
            throw new IllegalArgumentException("Mismatching currencies, " + this.currency + " and " + other.currency);
        }
    }

    public boolean isSameCurrency(Money other) {
        if (this.isZero() || other.isZero()) {
            return true;
        }
        if (this.currency == null) {
            if (other.currency != null) {
                return false;
            }
        } else {
            if (!this.currency.equals(other.currency)) {
                return false;
            }
        }
        return true;
    }

    public Money subtract(Money other) {
        return add(other.negate());
    }

    public Money negate() {
        return new Money(currency, -cents);
    }

    public int compareTo(Money other) {
        assertSameCurrency(other);
        return Long.signum(cents - other.cents);
    }

    public String getCurrency() {
        return currency;
    }

    public long getValue() {
        return cents;
    }

    @Override
    public int hashCode() {
        return (int) cents;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Money) {
            if (!isSameCurrency((Money) o)) {
                return false;
            }
            return compareTo((Money) o) == 0;
        }
        return false;
    }

    public Money multiply(long x) {
        return new Money(currency, cents * x);
    }

    public Money divideBy(long x) {
        return new Money(currency, cents / x);
    }

    public static Money parse(String s) {
        String[] split = s.trim().split(" ");
        if (split.length == 2) {
            try {
                long value = parseMoney(split[0].trim());
                return new Money(split[1], value);
            } catch (NumberFormatException e) {
                long value = parseMoney(split[1].trim());
                return new Money(split[0], value);
            }
        }
        throw new IllegalArgumentException("Too many whitespaces in " + s);
    }

    private static long parseMoney(String s) {
        int i = s.indexOf('.');
        if (i < 0) {
            return 100L * Long.parseLong(s);
        }

        int decimals = s.length() - i - 1;
        s = s.replace(".", "");
        return 100L * Long.parseLong(s) / getDiv(decimals);
    }

    private static long getDiv(int decimals) {
        long div = 1;
        while (decimals > 0) {
            div *= 10;
            decimals--;
        }
        return div;
    }


}
