package se.krka.travelopt;

import se.krka.travelopt.localization.EnglishLocale;

import java.util.ArrayList;
import java.util.List;

public class PriceStructure {

    private final List<TicketType> ticketTypes = new ArrayList<TicketType>();

    public PriceStructure(Builder builder) {
        ticketTypes.addAll(builder.ticketTypes);
	}

    static Builder builder() {
        return new Builder();
    }

	public List<TicketType> getTicketTypes() {
		return ticketTypes;
	}

	public static class Builder {
        private final List<TicketType> ticketTypes = new ArrayList<TicketType>();

        public Builder() {
        }

        public Builder addTicketType(TicketType ticketType) {
			ticketTypes.add(ticketType);
			return this;
		}

        public Builder addWholeDays(String name, Money price, int days) {
            addTicketType(new WholeDays(name, price, days));
            return this;
        }

        public Builder addCouponTicket(String name, Money price, int numTickets) {
            addTicketType(new CouponTicket(name, price, numTickets));
            return this;
        }

        public PriceStructure build() {
            return new PriceStructure(this);
        }
    }

	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (TicketType ticketType : ticketTypes) {
			builder.append(ticketType.name() + " " + ticketType.description(EnglishLocale.INSTANCE)).append("\n");
		}
        return "Price list:\n" + builder.toString();
    }
}
