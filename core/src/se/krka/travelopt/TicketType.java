package se.krka.travelopt;

import se.krka.travelopt.localization.TravelOptLocale;

public interface TicketType {
	int numberOfDays();
	Money cost(int numCoupons);
    int getCount(int numCoupons);
    String name();
    String description(TravelOptLocale locale);
}
