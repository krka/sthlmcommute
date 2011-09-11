package se.krka.travelopt;

import se.krka.travelopt.localization.TravelOptLocale;

public class Prices {
    public static final String FULL = "full";
    public static final String REDUCED = "reduced";
    public static final PriceStructure SL_REDUCED_PRICE = createSLReducedPrice();
    public static final PriceStructure SL_FULL_PRICE = createSLFullPrice();

    private static PriceStructure createSLFullPrice() {
        PriceStructure.Builder builder = PriceStructure.builder();

        builder.addWholeDays("Årsbiljett", Money.parse("8300 SEK"), 365);
        builder.addWholeDays("90-dagarsbiljett", Money.parse("2300 SEK"), 90);
        builder.addWholeDays("30-dagarsbiljett", Money.parse("790 SEK"), 30);
        builder.addWholeDays("7-dagarsbiljett", Money.parse("300 SEK"), 7);
        builder.addWholeDays("72-timmarsbiljett", Money.parse("230 SEK"), 3);
        builder.addWholeDays("24-timmarsbiljett", Money.parse("115 SEK"), 1);
        builder.addCouponTicket("Förköpsremsa", Money.parse("200 SEK"), 16);

        return builder.build();
    }

    private static PriceStructure createSLReducedPrice() {
        PriceStructure.Builder builder = PriceStructure.builder();

        builder.addWholeDays("Årsbiljett", Money.parse("4990 SEK"), 365);
        builder.addWholeDays("90-dagarsbiljett", Money.parse("1400 SEK"), 90);
        builder.addWholeDays("30-dagarsbiljett", Money.parse("490 SEK"), 30);
        builder.addWholeDays("7-dagarsbiljett", Money.parse("180 SEK"), 7);
        builder.addWholeDays("72-timmarsbiljett", Money.parse("140 SEK"), 3);
        builder.addWholeDays("24-timmarsbiljett", Money.parse("70 SEK"), 1);
        builder.addCouponTicket("Förköpsremsa", Money.parse("120 SEK"), 16);

        return builder.build();
    }

    public static PriceStructure getPriceCategory(String priceCategory) {
        if (priceCategory.equals(REDUCED)) {
            return SL_REDUCED_PRICE;
        } else if (priceCategory.equals(FULL)) {
            return SL_FULL_PRICE;
        }
        throw new IllegalArgumentException();
    }
}
