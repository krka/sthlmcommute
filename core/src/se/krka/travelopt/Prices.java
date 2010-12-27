package se.krka.travelopt;

import se.krka.travelopt.localization.SwedishLocale;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.Locale;

public class Prices {
    public static PriceStructure createSLFullPrice(TravelOptLocale locale) {
        PriceStructure.Builder builder = PriceStructure.builder(locale);

        builder.addWholeDays("Årskort", Money.parse("7280 SEK"), 365);
        builder.addWholeDays("90-dagars kort", Money.parse("2010 SEK"), 90);
        builder.addWholeDays("30-dagars kort", Money.parse("690 SEK"), 30);
        builder.addWholeDays("7-dagars kort", Money.parse("260 SEK"), 7);
        builder.addWholeDays("72-timmars kort", Money.parse("200 SEK"), 3);
        builder.addWholeDays("24-timmars kort", Money.parse("100 SEK"), 1);
        builder.addSimpleTicket("Rabatthäfte", Money.parse("180 SEK"), 16);

        return builder.build();
    }

    public static PriceStructure createSLReducedPrice(TravelOptLocale locale) {
        PriceStructure.Builder builder = PriceStructure.builder(locale);

        builder.addWholeDays("Årskort", Money.parse("4370 SEK"), 365);
        builder.addWholeDays("90-dagars kort", Money.parse("1200 SEK"), 90);
        builder.addWholeDays("30-dagars kort", Money.parse("420 SEK"), 30);
        builder.addWholeDays("7-dagars kort", Money.parse("200 SEK"), 7);
        builder.addWholeDays("72-timmars kort", Money.parse("120 SEK"), 3);
        builder.addWholeDays("24-timmars kort", Money.parse("60 SEK"), 1);
        builder.addSimpleTicket("Rabatthäfte", Money.parse("110 SEK"), 16);

        return builder.build();
    }

    public static PriceStructure getPriceCategory(String priceCategory, TravelOptLocale locale) {
        if (priceCategory.equals("reduced")) {
            return createSLReducedPrice(locale);
        }
        return createSLFullPrice(locale);
    }
}
