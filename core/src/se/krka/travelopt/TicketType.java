package se.krka.travelopt;

public interface TicketType {
	int numberOfDays();
	Money cost(int numTickets);
}