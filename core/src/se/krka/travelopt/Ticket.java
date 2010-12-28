package se.krka.travelopt;

import se.krka.travelopt.localization.TravelOptLocale;

import java.util.Date;

public class Ticket {
    private final TravelOptLocale locale;

    private final TicketType ticketType;
    private final Date startDate;
    private final Money cost;

	public Ticket(TravelOptLocale locale, Money cost, TicketType ticketType, Date startDate) {
        this.locale = locale;
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

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
        return Util.plusDays(startDate, ticketType.numberOfDays() - 1);
    }

    @Override
	public String toString() {
        return locale.ticket(this);
	}

    public Object toString(TravelOptLocale locale) {
        return locale.ticket(this);
    }
}
