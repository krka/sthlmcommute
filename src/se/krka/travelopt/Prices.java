package se.krka.travelopt;

/**
 * Created by IntelliJ IDEA.
 * User: krka
 * Date: 2010-dec-17
 * Time: 18:02:14
 * To change this template use File | Settings | File Templates.
 */
public class Prices {
	public static final PriceStructure SL_FULL_PRICE = createSLFullPrice();
	public static final PriceStructure SL_REDUCED_PRICE = createSLReducedPrice();

	private static PriceStructure createSLFullPrice() {
		PriceStructure.Builder builder = new PriceStructure.Builder();

		builder.addTicketType(new WholeDays("30-dagars kort", 690, 30));
		builder.addTicketType(new WholeDays("7-dagars kort", 260, 7));
		builder.addTicketType(new SimpleTicket("Rabatthäfte", 180, 16));
		return new PriceStructure(builder);
	}

	private static PriceStructure createSLReducedPrice() {
		PriceStructure.Builder builder = new PriceStructure.Builder();

		builder.addTicketType(new WholeDays("30-dagars kort", 420, 30));
		builder.addTicketType(new WholeDays("7-dagars kort", 200, 7));
		builder.addTicketType(new SimpleTicket("Rabatthäfte", 110, 16));
		return new PriceStructure(builder);
	}
}
