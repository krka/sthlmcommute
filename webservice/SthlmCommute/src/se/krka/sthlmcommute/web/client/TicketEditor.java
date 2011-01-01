package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.*;
import se.krka.travelopt.localization.TravelOptLocale;

public class TicketEditor extends Composite {

    private final TicketListBox ticket;
    private final WeekdayEditor weekdays;
    private final DelayedWork worker;

    public TicketEditor(final UpdateListener listener, DelayedWork worker, TravelOptLocale locale) {
        this.worker = worker;
        ticket = new TicketListBox(false);
        //ticketHelpInfo = new HelpInfo("Determine the number of tickets you need to use per day.");

        weekdays = new WeekdayEditor(listener, worker, locale);

        Panel root = new VerticalPanel();
        root.add(new Label("Number of tickets per day:"));
        root.add(ticket);
        root.add(weekdays);
        initWidget(root);

        ticket.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                listener.updated();
                TicketEditor.this.worker.requestWork();
            }
        });
    }

    public Weekdays getWeekdays() {
        return new Weekdays(ticket.getSelectedTicket(), weekdays.getTickets());
    }

    public WeekdayEditor getWeekdayEditor() {
        return weekdays;
    }

    public TicketListBox getTicket() {
        return ticket;
    }

    public void hideHelp() {
        weekdays.hideHelp();
    }
}
