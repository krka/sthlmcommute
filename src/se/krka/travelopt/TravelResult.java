package se.krka.travelopt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: krka
 * Date: 2010-dec-17
 * Time: 17:59:17
 * To change this template use File | Settings | File Templates.
 */
public class TravelResult {
	private final List<Ticket> tickets;

	public TravelResult(List<Ticket> tickets) {
		this.tickets = Collections.unmodifiableList(new ArrayList<Ticket>(tickets));
	}

	public int getTotalCost() {
		int sum = 0;
		for (Ticket ticket : tickets) {
			sum += ticket.getCost();
		}
		return sum;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	@Override
	public String toString() {
		return "TravelResult: " + getTotalCost() + " SEK\n" + ticketsToString();
	}

	private String ticketsToString() {
		StringBuilder builder = new StringBuilder();
		for (Ticket ticket : tickets) {
			builder.append(ticket).append("\n");
		}
		return builder.toString();
	}
}
