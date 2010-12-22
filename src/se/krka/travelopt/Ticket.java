package se.krka.travelopt;

import org.joda.time.DateTime;

/**
 * Created by IntelliJ IDEA.
 * User: krka
 * Date: 2010-dec-20
 * Time: 17:28:57
 * To change this template use File | Settings | File Templates.
 */
public class Ticket {
	private final TicketType ticketType;
	private final DateTime startDate;
	private final Money cost;

	public Ticket(Money cost, TicketType ticketType, DateTime startDate) {
		this.cost = cost;
		this.ticketType = ticketType;
		this.startDate = startDate;
	}

	public Money getCost() {
		return cost;
	}

	public TicketType getTicketType() {
		return ticketType;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	@Override
	public String toString() {
		return startDate.toString("YYYY-MM-dd") + " " + ticketType + ", " + cost;
	}
}
