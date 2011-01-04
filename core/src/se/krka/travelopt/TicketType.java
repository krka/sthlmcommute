package se.krka.travelopt;

public interface TicketType {
	int numberOfDays();
	Money cost(int numCoupons);
    int getCount(int numCoupons);
    String name();
    String description();
}
