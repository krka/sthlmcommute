package se.krka.travelopt;

import org.joda.time.DateTime;
import se.krka.travelopt.localization.TravelOptLocale;

public class Ticket {
    private final TravelOptLocale locale;

    private final TicketType ticketType;
    private final DateTime startDate;
    private final Money cost;

	public Ticket(TravelOptLocale locale, Money cost, TicketType ticketType, DateTime startDate) {
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

	public DateTime getStartDate() {
		return startDate;
	}

	public DateTime getEndDate() {
		return startDate.plus(ticketType.numberOfDays() - 1);
	}

	@Override
	public String toString() {
        return locale.ticket(this);
	}

    public Object toString(TravelOptLocale locale) {
        return locale.ticket(this);
    }
}
