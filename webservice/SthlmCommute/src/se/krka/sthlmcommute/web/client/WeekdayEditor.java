package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.*;
import se.krka.sthlmcommute.web.client.components.TicketListBox;
import se.krka.sthlmcommute.web.client.util.DelayedWork;
import se.krka.travelopt.localization.TravelOptLocale;

public class WeekdayEditor extends Composite {
    private final Grid weekDayForm;
    private final UpdateListener listener;
    private final TravelOptLocale locale;

    public WeekdayEditor(UpdateListener listener, TravelOptLocale locale) {
        this.listener = listener;
        this.locale = locale;
        weekDayForm = createWeekDayForm();

        Panel root = new VerticalPanel();
        root.add(new Label("Exceptions:"));
        root.add(weekDayForm);
        initWidget(root);
    }

    private Grid createWeekDayForm() {
        Grid grid = new Grid(2, 7);
        for (int day = 0; day < 7; day++) {
            grid.setWidget(0, day, new Label(locale.weekDayName(day).substring(0, 1)));
            final TicketListBox listBox = new TicketListBox(true);
            listBox.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent changeEvent) {
                    listener.updated();
                }
            });

            grid.setWidget(1, day, listBox);
        }
        return grid;

    }

    public int getWeekday(int i) {
        TicketListBox listBox = (TicketListBox) weekDayForm.getWidget(1, i);
        return listBox.getSelectedTicket();
    }

    public void setWeekDay(int index, int value) {
        TicketListBox listBox = (TicketListBox) weekDayForm.getWidget(1, index);
        listBox.setSelectedTicket(value);
    }

    public int[] getTickets() {
        int[] res = new int[7];
        for (int i = 0; i < 7; i++) {
            res[i] = getWeekday(i);
        }
        return res;
    }

    public void setWeekDays(int[] tickets) {
        for (int i = 0; i < 7; i++) {
            setWeekDay(i, tickets[i]);
        }
    }
}
