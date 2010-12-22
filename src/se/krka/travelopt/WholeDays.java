package se.krka.travelopt;

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
