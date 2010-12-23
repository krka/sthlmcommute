package se.krka.travelopt;

import java.math.BigDecimal;

public class Money {
    public static final Money ZERO = new Money("", new BigDecimal("0"));

    private final String currency;
    private final BigDecimal value;

    public Money(String currency, BigDecimal value) {
        this.currency = currency;
        this.value = value;
    }

    public Money(String currency, long value) {
        this(currency, BigDecimal.valueOf(value));
    }

    @Override
    public String toString() {
        if (isZero()) {
            return "0";
        }
        return value + " " + currency;
    }

    public boolean isZero() {
        return this.value.equals(BigDecimal.ZERO);
    }

    public Money add(Money other) {
        if (this.isZero()) {
            return other;
        }
        if (other.isZero()) {
            return this;
        }
        assertSameCurrency(other);
        return new Money(this.currency, this.value.add(other.value));
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
        return new Money(currency, value.negate());
    }

    public int compareTo(Money other) {
        assertSameCurrency(other);
        return value.compareTo(other.value);
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
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
        return new Money(currency, this.value.multiply(BigDecimal.valueOf(x)));
    }

    public Money divideBy(long x) {
        return new Money(currency, this.value.divide(BigDecimal.valueOf(x)));
    }

    public static Money parse(String s) {
        String[] split = s.trim().split(" ");
        if (split.length == 2) {
            try {
                BigDecimal value = new BigDecimal(split[0].trim());
                return new Money(split[1], value);
            } catch (NumberFormatException e) {
                BigDecimal value = new BigDecimal(split[1].trim());
                return new Money(split[0], value);
            }
        }
        throw new IllegalArgumentException("Too many whitespaces in " + s);
    }
}
