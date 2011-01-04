package se.krka.sthlmcommute.web.client;

public class Weekdays {

    private final int[] coupons;
    private final int defaultValue;

    public Weekdays(int defaultValue, int[] coupons) {
        this.defaultValue = defaultValue;
        this.coupons = new int[7];
        System.arraycopy(coupons, 0, this.coupons, 0, 7);
    }

    public int calcValue(int day) {
        int coupon = coupons[day];
        if (coupon == -1) {
            return defaultValue;
        }
        return coupon;
    }

    public int getValue(int day) {
        return coupons[day];
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public int countCoupons() {
        int sum = 0;
        for (int i = 0; i < 7; i++) {
            sum += calcValue(i);
        }
        return sum;
    }

    public int[] getCoupons() {
        int[] res = new int[7];
        for (int i = 0; i < 7; i++) {
            res[i] = calcValue(i);
        }
        return res;
    }

    public int[] getRawCoupons() {
        int[] res = new int[7];
        for (int i = 0; i < 7; i++) {
            res[i] = getValue(i);
        }
        return res;
    }
}
