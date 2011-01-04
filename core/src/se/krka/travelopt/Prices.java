package se.krka.travelopt;

import se.krka.travelopt.localization.TravelOptLocale;

public class Prices {
    public static final String FULL = "full";
    public static final String REDUCED = "reduced";

    private static PriceStructure createSLFullPrice(TravelOptLocale locale) {
        PriceStructure.Builder builder = PriceStructure.builder(locale);

        builder.addWholeDays("Årsbiljett", Money.parse("7280 SEK"), 365);
        builder.addWholeDays("90-dagarsbiljett", Money.parse("2010 SEK"), 90);
        builder.addWholeDays("30-dagarsbiljett", Money.parse("690 SEK"), 30);
        builder.addWholeDays("7-dagarsbiljett", Money.parse("260 SEK"), 7);
        builder.addWholeDays("72-timmarsbiljett", Money.parse("200 SEK"), 3);
        builder.addWholeDays("24-timmarsbiljett", Money.parse("100 SEK"), 1);
        builder.addCouponTicket("Förköpsremsa", Money.parse("180 SEK"), 16);

        return builder.build();
    }

    private static PriceStructure createSLReducedPrice(TravelOptLocale locale) {
        PriceStructure.Builder builder = PriceStructure.builder(locale);

        builder.addWholeDays("Årsbiljett", Money.parse("4370 SEK"), 365);
        builder.addWholeDays("90-dagarsbiljett", Money.parse("1200 SEK"), 90);
        builder.addWholeDays("30-dagarsbiljett", Money.parse("420 SEK"), 30);
        builder.addWholeDays("7-dagarsbiljett", Money.parse("200 SEK"), 7);
        builder.addWholeDays("72-timmarsbiljett", Money.parse("120 SEK"), 3);
        builder.addWholeDays("24-timmarsbiljett", Money.parse("60 SEK"), 1);
        builder.addCouponTicket("Förköpsremsa", Money.parse("110 SEK"), 16);

        return builder.build();
    }

    public static PriceStructure getPriceCategory(String priceCategory, TravelOptLocale locale) {
        if (priceCategory.equals(REDUCED)) {
            return createSLReducedPrice(locale);
        }
        return createSLFullPrice(locale);
    }
}
