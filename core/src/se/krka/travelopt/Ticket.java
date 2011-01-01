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
}
