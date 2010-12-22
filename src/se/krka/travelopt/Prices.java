package se.krka.travelopt;

public class Prices {
	public static final PriceStructure SL_FULL_PRICE = createSLFullPrice();
	public static final PriceStructure SL_REDUCED_PRICE = createSLReducedPrice();

	private static PriceStructure createSLFullPrice() {
		PriceStructure.Builder builder = new PriceStructure.Builder();

		builder.addTicketType(new WholeDays("30-dagars kort", Money.parse("690 SEK"), 30));
		builder.addTicketType(new WholeDays("7-dagars kort", Money.parse("260 SEK"), 7));
		builder.addTicketType(new SimpleTicket("Rabatthäfte", Money.parse("180 SEK"), 16));
		return new PriceStructure(builder);
	}

	private static PriceStructure createSLReducedPrice() {
		PriceStructure.Builder builder = new PriceStructure.Builder();

		builder.addTicketType(new WholeDays("30-dagars kort", Money.parse("420 SEK"), 30));
		builder.addTicketType(new WholeDays("7-dagars kort", Money.parse("200 SEK"), 7));
		builder.addTicketType(new SimpleTicket("Rabatthäfte", Money.parse("110 SEK"), 16));
		return new PriceStructure(builder);
	}
}
