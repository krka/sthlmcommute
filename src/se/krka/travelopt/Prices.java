package se.krka.travelopt;

public class Prices {
    public static final PriceStructure SL_FULL_PRICE = createSLFullPrice();
    public static final PriceStructure SL_REDUCED_PRICE = createSLReducedPrice();

    private static PriceStructure createSLFullPrice() {
        PriceStructure.Builder builder = new PriceStructure.Builder();

        builder.addTicketType(new WholeDays("Årskort", Money.parse("7280 SEK"), 365));
        builder.addTicketType(new WholeDays("90-dagars kort", Money.parse("2010 SEK"), 90));
        builder.addTicketType(new WholeDays("30-dagars kort", Money.parse("690 SEK"), 30));
        builder.addTicketType(new WholeDays("7-dagars kort", Money.parse("260 SEK"), 7));
        builder.addTicketType(new WholeDays("72-timmars kort", Money.parse("200 SEK"), 3));
        builder.addTicketType(new WholeDays("24-timmars kort", Money.parse("100 SEK"), 1));
        builder.addTicketType(new SimpleTicket("Rabatthäfte", Money.parse("180 SEK"), 16));
        return new PriceStructure(builder);
    }

    private static PriceStructure createSLReducedPrice() {
        PriceStructure.Builder builder = new PriceStructure.Builder();

        builder.addTicketType(new WholeDays("Årskort", Money.parse("4370 SEK"), 365));
        builder.addTicketType(new WholeDays("90-dagars kort", Money.parse("1200 SEK"), 90));
        builder.addTicketType(new WholeDays("30-dagars kort", Money.parse("420 SEK"), 30));
        builder.addTicketType(new WholeDays("7-dagars kort", Money.parse("200 SEK"), 7));
        builder.addTicketType(new WholeDays("72-timmars kort", Money.parse("120 SEK"), 3));
        builder.addTicketType(new WholeDays("24-timmars kort", Money.parse("60 SEK"), 1));
        builder.addTicketType(new SimpleTicket("Rabatthäfte", Money.parse("110 SEK"), 16));
        return new PriceStructure(builder);
    }
}
