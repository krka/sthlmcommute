package se.krka.travelopt;

import se.krka.travelopt.localization.EnglishLocale;
import se.krka.travelopt.localization.SwedishLocale;

public class Prices {
    public static final PriceStructure SL_FULL_PRICE = createSLFullPrice();
    public static final PriceStructure SL_REDUCED_PRICE = createSLReducedPrice();
    

    private static PriceStructure createSLFullPrice() {
        PriceStructure.Builder builder = PriceStructure.builder(new SwedishLocale());

        builder.addWholeDays("Årskort", Money.parse("7280 SEK"), 365);
        builder.addWholeDays("90-dagars kort", Money.parse("2010 SEK"), 90);
        builder.addWholeDays("30-dagars kort", Money.parse("690 SEK"), 30);
        builder.addWholeDays("7-dagars kort", Money.parse("260 SEK"), 7);
        builder.addWholeDays("72-timmars kort", Money.parse("200 SEK"), 3);
        builder.addWholeDays("24-timmars kort", Money.parse("100 SEK"), 1);
        builder.addSimpleTicket("Rabatthäfte", Money.parse("180 SEK"), 16);

        return builder.build();
    }

    private static PriceStructure createSLReducedPrice() {
        PriceStructure.Builder builder = PriceStructure.builder(new SwedishLocale());

        builder.addWholeDays("Årskort", Money.parse("4370 SEK"), 365);
        builder.addWholeDays("90-dagars kort", Money.parse("1200 SEK"), 90);
        builder.addWholeDays("30-dagars kort", Money.parse("420 SEK"), 30);
        builder.addWholeDays("7-dagars kort", Money.parse("200 SEK"), 7);
        builder.addWholeDays("72-timmars kort", Money.parse("120 SEK"), 3);
        builder.addWholeDays("24-timmars kort", Money.parse("60 SEK"), 1);
        builder.addSimpleTicket("Rabatthäfte", Money.parse("110 SEK"), 16);

        return builder.build();
    }
}
