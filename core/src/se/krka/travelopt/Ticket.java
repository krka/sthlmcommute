package se.krka.travelopt;

import se.krka.travelopt.localization.TravelOptLocale;

import java.util.Date;

public class Ticket {
    private final TravelOptLocale locale;

    private final TicketType ticketType;
    private final Date startDate;
    private final Date endDate;
    private final Money cost;
    private final int numberOfTickets;

    public Ticket(TravelOptLocale locale, Money cost, TicketType ticketType, Date startDate, Date endDate) {
        this(locale, cost, ticketType, startDate, endDate, 1);
    }

    public Ticket(TravelOptLocale locale, Money cost, TicketType ticketType, Date startDate, Date endDate, int numberOfTickets) {
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

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
        return endDate;
    }

    @Override
	public String toString() {
        return locale.ticket(this);
	}

    public Object toString(TravelOptLocale locale) {
        return locale.ticket(this);
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (numberOfTickets != ticket.numberOfTickets) return false;
        if (!cost.equals(ticket.cost)) return false;
        if (endDate != null ? !endDate.equals(ticket.endDate) : ticket.endDate != null) return false;
        if (locale != null ? !locale.equals(ticket.locale) : ticket.locale != null) return false;
        if (startDate != null ? !startDate.equals(ticket.startDate) : ticket.startDate != null) return false;
        if (ticketType != null ? !ticketType.equals(ticket.ticketType) : ticket.ticketType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = locale != null ? locale.hashCode() : 0;
        result = 31 * result + (ticketType != null ? ticketType.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + cost.hashCode();
        result = 31 * result + numberOfTickets;
        return result;
    }
}
