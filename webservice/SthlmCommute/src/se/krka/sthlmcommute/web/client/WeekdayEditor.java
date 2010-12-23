package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;

public class WeekdayEditor extends Composite {
    private final ListBox ticketListBox;
    private final Grid weekDayForm;

    public WeekdayEditor() {
        VerticalPanel root = new VerticalPanel();

        Grid defaultTickets = new Grid(1, 2);
        defaultTickets.setWidget(0, 0, new Label("Required number of tickets per day:"));
        ticketListBox = createTicketListBox(false);
        defaultTickets.setWidget(0, 1, ticketListBox);

        root.add(defaultTickets);
        weekDayForm = createWeekDayForm();
        root.add(weekDayForm);

        initWidget(root);
    }

    public static ListBox createTicketListBox(boolean includeDefault) {
        ListBox listBox = new ListBox();
        if (includeDefault) {
            listBox.addItem("--", "-1");
        }
        for (int i = 0; i < 10; i++) {
            listBox.addItem(i + "", "" + i);
        }
        return listBox;
    }


    private static final String[] weekdays = new String[]{"mo", "tu", "we", "th", "fr", "sa", "su"};

    public static Grid createWeekDayForm() {
        Grid grid = new Grid(2, 7);
        for (int day = 0; day < 7; day++) {
            grid.setWidget(0, day, new Label(weekdays[day]));
            ListBox listBox = createTicketListBox(true);
            listBox.setSelectedIndex(0);
            grid.setWidget(1, day, listBox);
        }
        return grid;

    }

    public ListBox getTicket() {
        return ticketListBox;
    }

    public String  getWeekday(int i) {
        ListBox listBox = (ListBox) weekDayForm.getWidget(1, i);
        int selectedIndex = listBox.getSelectedIndex();
        return listBox.getValue(selectedIndex);
    }

    public void setWeekDay(int index, String value) {
        ListBox listBox = (ListBox) weekDayForm.getWidget(1, index);
        int n = listBox.getItemCount();
        for (int i = 0; i < n; i++) {
            String s = listBox.getValue(i);
            if (s.equals(value)) {
                listBox.setSelectedIndex(i);
                return;
            }
        }
    }

    public String getSelectedTicket() {
        int index = ticketListBox.getSelectedIndex();
        return ticketListBox.getValue(index);
    }

    public void setSelectedTicket(String value) {
        int n = ticketListBox.getItemCount();
        for (int i = 0; i < n; i++) {
            String s = ticketListBox.getValue(i);
            if (s.equals(value)) {
                ticketListBox.setSelectedIndex(i);
                return;
            }
        }
    }

    Weekdays getWeekdays() {
        int defaultValue = Integer.parseInt(getSelectedTicket());

        int[] days = new int[7];
        for (int i = 0; i < 7; i++) {
            int value = Integer.parseInt(getWeekday(i));
            days[i] = value;
        }

        return new Weekdays(defaultValue, days);
    }
}
