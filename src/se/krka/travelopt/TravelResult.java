package se.krka.travelopt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class TravelResult {
	private final List<Ticket> tickets;

	public TravelResult(List<Ticket> tickets) {
		this.tickets = Collections.unmodifiableList(new ArrayList<Ticket>(tickets));
	}

	public Money getTotalCost() {
		Money sum = Money.ZERO;
		for (Ticket ticket : tickets) {
            sum = sum.add(ticket.getCost());
		}
		return sum;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	@Override
	public String toString() {
		return "TravelResult: " + getTotalCost() + "\n" + ticketsToString();
	}

	private String ticketsToString() {
		StringBuilder builder = new StringBuilder();
		for (Ticket ticket : tickets) {
			builder.append(ticket).append("\n");
		}
		return builder.toString();
	}
}
