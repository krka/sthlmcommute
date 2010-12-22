package se.krka.travelopt;

/**
 * Created by IntelliJ IDEA.
 * User: krka
 * Date: 2010-dec-17
 * Time: 18:15:33
 * To change this template use File | Settings | File Templates.
 */
public class SimpleTicket implements TicketType {
	private final String name;
	private final int price;
	private final int numTickets;

	public SimpleTicket(String name, int price, int numTickets) {
		this.name = name;
		this.price = price;
		this.numTickets = numTickets;
	}

	public int numberOfDays() {
		return 1;
	}

	public int cost(int numTickets) {
		return price * numTickets / this.numTickets;
	}

	@Override
	public String toString() {
		return name + " (" + price +
				"/" + numTickets + ")";
	}
}
