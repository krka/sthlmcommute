package se.krka.sthlmcommute.web.client;

public class Weekdays {
    private static final String[] days = new String[]{
            "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
    };

    private final int[] tickets;
    private final String desc;

    public Weekdays(int[] tickets) {
        this.tickets = new int[7];
        String desc = "";

        int prevValue = -1;
        int prevStart = -1;
        for (int i = 0; i < 7; i++) {
            this.tickets[i] = tickets[i];
            if (tickets[i] != prevValue) {
                desc = addToDesc(desc, prevValue, prevStart, i - 1);
                prevStart = i;
                prevValue = tickets[i];
            }
        }
        desc = addToDesc(desc, prevValue, prevStart, 6);
        this.desc = desc;
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
}
