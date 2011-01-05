package se.krka.travelopt;

import se.krka.travelopt.localization.TravelOptLocale;

public class Prices {
    public static final String FULL = "full";
    public static final String REDUCED = "reduced";
    public static final PriceStructure SL_REDUCED_PRICE = createSLReducedPrice();
    public static final PriceStructure SL_FULL_PRICE = createSLFullPrice();

    private static PriceStructure createSLFullPrice() {
        PriceStructure.Builder builder = PriceStructure.builder();

        builder.addWholeDays("Årsbiljett", Money.parse("7280 SEK"), 365);
        builder.addWholeDays("90-dagarsbiljett", Money.parse("2010 SEK"), 90);
        builder.addWholeDays("30-dagarsbiljett", Money.parse("690 SEK"), 30);
        builder.addWholeDays("7-dagarsbiljett", Money.parse("260 SEK"), 7);
        builder.addWholeDays("72-timmarsbiljett", Money.parse("200 SEK"), 3);
        builder.addWholeDays("24-timmarsbiljett", Money.parse("100 SEK"), 1);
        builder.addCouponTicket("Förköpsremsa", Money.parse("180 SEK"), 16);

        return builder.build();
    }

    private static PriceStructure createSLReducedPrice() {
        PriceStructure.Builder builder = PriceStructure.builder();

        builder.addWholeDays("Årsbiljett", Money.parse("4370 SEK"), 365);
        builder.addWholeDays("90-dagarsbiljett", Money.parse("1200 SEK"), 90);
        builder.addWholeDays("30-dagarsbiljett", Money.parse("420 SEK"), 30);
        builder.addWholeDays("7-dagarsbiljett", Money.parse("200 SEK"), 7);
        builder.addWholeDays("72-timmarsbiljett", Money.parse("120 SEK"), 3);
        builder.addWholeDays("24-timmarsbiljett", Money.parse("60 SEK"), 1);
        builder.addCouponTicket("Förköpsremsa", Money.parse("110 SEK"), 16);

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
