package se.krka.travelopt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: krka
 * Date: 2010-dec-17
 * Time: 18:02:54
 * To change this template use File | Settings | File Templates.
 */
public class PriceStructure {
	private final List<TicketType> ticketTypes = new ArrayList<TicketType>();

	public PriceStructure(Builder builder) {
		ticketTypes.addAll(builder.ticketTypes);
	}

	public List<TicketType> getTicketTypes() {
		return ticketTypes;
	}

	public static class Builder {
		private final List<TicketType> ticketTypes = new ArrayList<TicketType>();

		public Builder addTicketType(TicketType ticketType) {
			ticketTypes.add(ticketType);
			return this;
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (TicketType ticketType : ticketTypes) {
			builder.append(ticketType).append("\n");
		}
		return "PriceStructure:\n" + builder.toString();
	}
}
