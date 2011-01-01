package se.krka.sthlmcommute.web.client;

public class Weekdays {

    private final int[] tickets;
    private final int defaultValue;

    public Weekdays(int defaultValue, int[] tickets) {
        this.defaultValue = defaultValue;
        this.tickets = new int[7];
        System.arraycopy(tickets, 0, this.tickets, 0, 7);
    }

    public int calcValue(int day) {
        int ticket = tickets[day];
        if (ticket == -1) {
            return defaultValue;
        }
        return ticket;
    }

    public int getValue(int day) {
        return tickets[day];
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public int countTickets() {
        int sum = 0;
        for (int i = 0; i < 7; i++) {
            sum += calcValue(i);
        }
        return sum;
    }

    public int[] getTickets() {
        int[] res = new int[7];
        for (int i = 0; i < 7; i++) {
            res[i] = calcValue(i);
        }
        return res;
    }

    public int[] getRawTickets() {
        int[] res = new int[7];
        for (int i = 0; i < 7; i++) {
            res[i] = getValue(i);
        }
        return res;
    }
}
