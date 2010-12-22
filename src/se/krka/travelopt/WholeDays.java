package se.krka.travelopt;

/**
 * Created by IntelliJ IDEA.
 * User: krka
 * Date: 2010-dec-17
 * Time: 18:13:29
 * To change this template use File | Settings | File Templates.
 */
public class WholeDays implements TicketType {
	private final String name;
	private final Money price;
	private final int numDays;

	public WholeDays(String name, Money price, int numDays) {
		this.name = name;
		this.price = price;
		this.numDays = numDays;
	}

	public int numberOfDays() {
		return numDays;
	}

	public Money cost(int numTickets) {
		return price;
	}

	@Override
	public String toString() {
		return name + " (" + numDays + " days for " + price + ")";
	}
}
