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
        return "Travel plan suggestion: " + getTotalCost() + "\n" + ticketsToString();
    }

	public String ticketsToString() {
		StringBuilder builder = new StringBuilder();
		for (Ticket ticket : tickets) {
			builder.append(ticket.toString()).append("\n");
		}
		return builder.toString();
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TravelResult that = (TravelResult) o;

        if (!locale.equals(that.locale)) return false;
        if (!tickets.equals(that.tickets)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = locale.hashCode();
        result = 31 * result + tickets.hashCode();
        return result;
    }
}
