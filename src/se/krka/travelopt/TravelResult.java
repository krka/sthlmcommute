package se.krka.travelopt;

import se.krka.travelopt.localization.TravelOptLocale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TravelResult {
    private final TravelOptLocale locale;
    private final List<Ticket> tickets;

    public TravelResult(TravelOptLocale locale, List<Ticket> tickets) {
        this.locale = locale;
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
        return locale.travelResult(this);
	}

	public String ticketsToString() {
		StringBuilder builder = new StringBuilder();
		for (Ticket ticket : tickets) {
			builder.append(ticket).append("\n");
		}
		return builder.toString();
	}
}
