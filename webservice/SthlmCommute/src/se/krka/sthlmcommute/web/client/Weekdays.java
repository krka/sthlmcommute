package se.krka.sthlmcommute.web.client;

public class Weekdays {
    private static final String[] days = new String[]{
            "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
    };

    private final int[] tickets;
    private final String desc;
    private final int defaultValue;

    public Weekdays(int defaultValue, int[] tickets) {
        this.defaultValue = defaultValue;
        this.tickets = new int[7];
        String desc = "";

        int prevValue = -1;
        int prevStart = 0;
        for (int i = 0; i < 7; i++) {
            this.tickets[i] = tickets[i];
            int actual = actualValue(tickets[i]);
            if (actual != prevValue) {
                if (prevValue != -1) {
                    desc = addToDesc(desc, prevValue, prevStart, i - 1);
                }
                prevStart = i;
                prevValue = actual;
            }
        }
        desc = addToDesc(desc, prevValue, prevStart, 6);
        this.desc = desc;
    }

    private int actualValue(int value) {
        if (value == -1) {
            return defaultValue;
        }
        return value;
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

    private String addToDesc(String desc, int value, int from, int to) {
        if (value <= 0) {
            return desc;
        }

        if (!desc.equals("")) {
            desc += ", ";
        }
        desc += days[from];
        if (from < to) {
            desc += "-" + days[to];
        }
        desc += ": " + value;
        return desc;
    }

    @Override
    public String toString() {
        return desc;
    }

    public int countTickets() {
        int sum = 0;
        for (int i = 0; i < 7; i++) {
            sum += calcValue(i);
        }
        return sum;
    }
}
