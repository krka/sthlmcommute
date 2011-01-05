package se.krka.travelopt;

import se.krka.travelopt.localization.EnglishLocale;
import se.krka.travelopt.localization.TravelOptLocale;

public class Ticket {
    private final TravelOptLocale locale;

    private final TicketType ticketType;
    private final int startDate;
    private final int endDate;
    private final Money cost;
    private final int numberOfTickets;

    public Ticket(TravelOptLocale locale, Money cost, TicketType ticketType, int startDate, int endDate) {
        this(locale, cost, ticketType, startDate, endDate, 1);
    }

    public Ticket(TravelOptLocale locale, Money cost, TicketType ticketType, int startDate, int endDate, int numberOfTickets) {
        this.locale = locale;
        this.cost = cost;
		this.ticketType = ticketType;
		this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfTickets = numberOfTickets;
    }

    public Money getCost() {
		return cost;
	}

	public TicketType getTicketType() {
		return ticketType;
	}

	public int getStartDate() {
		return startDate;
	}

	public int getEndDate() {
        return endDate;
    }

    @Override
	public String toString() {
        String res;
        if (getEndDate() == getStartDate()) {
            res = EnglishLocale.INSTANCE.formatDay(getStartDate());
        } else {
            res = EnglishLocale.INSTANCE.formatDay(getStartDate()) + " to " + EnglishLocale.INSTANCE.formatDay(getEndDate());
        }
        return res + " " + getTicketType() + ", " + getCost();
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (endDate != ticket.endDate) return false;
        if (numberOfTickets != ticket.numberOfTickets) return false;
        if (startDate != ticket.startDate) return false;
        if (!cost.equals(ticket.cost)) return false;
        if (!locale.equals(ticket.locale)) return false;
        if (ticketType != null ? !ticketType.equals(ticket.ticketType) : ticket.ticketType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = locale.hashCode();
        result = 31 * result + (ticketType != null ? ticketType.hashCode() : 0);
        result = 31 * result + startDate;
        result = 31 * result + endDate;
        result = 31 * result + cost.hashCode();
        result = 31 * result + numberOfTickets;
        return result;
    }
}
