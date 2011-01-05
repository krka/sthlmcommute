package se.krka.travelopt;

import se.krka.travelopt.localization.TravelOptLocale;

import java.util.ArrayList;
import java.util.List;

public class PriceStructure {
    private final TravelOptLocale locale;

    private final List<TicketType> ticketTypes = new ArrayList<TicketType>();

    public PriceStructure(TravelOptLocale locale, Builder builder) {
        this.locale = locale;
        ticketTypes.addAll(builder.ticketTypes);
	}

    static Builder builder(TravelOptLocale locale) {
        return new Builder(locale);
    }

	public List<TicketType> getTicketTypes() {
		return ticketTypes;
	}

	public static class Builder {
        private final TravelOptLocale locale;
        private final List<TicketType> ticketTypes = new ArrayList<TicketType>();

        public Builder(TravelOptLocale locale) {
            this.locale = locale;
        }

        public Builder addTicketType(TicketType ticketType) {
			ticketTypes.add(ticketType);
			return this;
		}

        public Builder addWholeDays(String name, Money price, int days) {
            addTicketType(new WholeDays(locale, name, price, days));
            return this;
        }

        public Builder addCouponTicket(String name, Money price, int numTickets) {
            addTicketType(new CouponTicket(locale, name, price, numTickets));
            return this;
        }

        public PriceStructure build() {
            return new PriceStructure(locale, this);
        }
    }

	public String devToString() {
		StringBuilder builder = new StringBuilder();
		for (TicketType ticketType : ticketTypes) {
			builder.append(ticketType.name() + " " + ticketType.description()).append("\n");
		}
        return "Price list:\n" + builder.toString();
    }
}
