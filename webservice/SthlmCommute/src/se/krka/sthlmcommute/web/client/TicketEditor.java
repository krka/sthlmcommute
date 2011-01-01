package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.*;

public class TicketEditor extends Composite {

    private final HelpInfo ticketHelpInfo;


    private final TicketListBox ticket;
    private final WeekdayEditor weekdays;
    private final DelayedWork worker;

    public TicketEditor(final UpdateListener listener, DelayedWork worker) {
        this.worker = worker;
        ticket = new TicketListBox(false);
        ticketHelpInfo = new HelpInfo("Determine the number of tickets you need to use per day.");

        weekdays = new WeekdayEditor(listener);

        Panel root = new VerticalPanel();
        root.add(ticketHelpInfo);
        root.add(new Label("Number of tickets per day:"));
        root.add(ticket);
        root.add(weekdays);
        initWidget(root);

        setVisible(false);

        ticket.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                ticketHelpInfo.setVisible(false);
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
}
