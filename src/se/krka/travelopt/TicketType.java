package se.krka.travelopt;

/**
 * Created by IntelliJ IDEA.
 * User: krka
 * Date: 2010-dec-19
 * Time: 13:54:22
 * To change this template use File | Settings | File Templates.
 */
public interface TicketType {
	int numberOfDays();
	int cost(int numTickets);
}
