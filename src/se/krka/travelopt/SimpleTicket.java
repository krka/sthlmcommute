package se.krka.travelopt;

public class SimpleTicket implements TicketType {
	private final String name;
	private final Money price;
	private final int numTickets;

    public SimpleTicket(String name, Money price, int numTickets) {
		this.name = name;
		this.price = price;
		this.numTickets = numTickets;
    }

	public int numberOfDays() {
		return 1;
	}

	public Money cost(int numTickets) {
        return price.multiply(numTickets).divideBy(this.numTickets);
	}

	@Override
	public String toString() {
		return name + " (" + numTickets + " tickets for " + price + ")";
	}
}
