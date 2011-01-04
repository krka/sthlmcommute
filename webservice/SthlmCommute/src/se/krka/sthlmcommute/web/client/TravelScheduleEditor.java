package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.krka.sthlmcommute.web.client.components.dateinterval.DateIntervalPicker;
import se.krka.sthlmcommute.web.client.components.dateinterval.DateIntervalUpdateListener;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.Date;

public class TravelScheduleEditor extends Composite {
    private final DateIntervalPicker rangeEditor;
    private final TicketEditor ticketEditor;

    private ScheduleEntry active;

    public TravelScheduleEditor(TravelOptLocale locale, final TravelScheduleList travelScheduleList) {
        VerticalPanel root = new VerticalPanel();
        rangeEditor = new DateIntervalPicker();

        //helpInfo = new HelpInfo("Choose the time period for when you need to travel.");
        ticketEditor = new TicketEditor(new UpdateListener() {
            @Override
            public void updated() {
                travelScheduleList.updateTickets(active, ticketEditor.getWeekdays());
            }
        }, locale);

        root.add(rangeEditor);
        root.add(ticketEditor);
        initWidget(root);
        setVisible(false);

        rangeEditor.addListener(new DateIntervalUpdateListener() {
            @Override
            public void intervalChanged(DateIntervalPicker picker, Date fromValue, Date toValue) {
                if (fromValue != null && toValue != null) {
                    travelScheduleList.updateScheduleEntryInterval(active, fromValue, toValue);
                }
            }
        });
    }

    public void setActive(ScheduleEntry entry) {
        active = entry;
        if (entry == null) {
            setVisible(false);
            return;
        }
        Date from = entry.getInterval().getFrom();
        rangeEditor.getFrom().setValue(from);
        if (from != null) {
            rangeEditor.getFrom().setCurrentMonth(from);
        }
        Date to = entry.getInterval().getTo();
        rangeEditor.getTo().setValue(to);
        if (to != null) {
            rangeEditor.getTo().setCurrentMonth(to);
        }

        rangeEditor.applyHighlight();

        int defaultValue = entry.getWeekdays().getDefaultValue();
        ticketEditor.getTicket().setSelectedTicket(defaultValue);
        ticketEditor.getWeekdayEditor().setWeekDays(entry.getWeekdays().getRawTickets());
        setVisible(true);
    }

    public DateIntervalPicker getRangeEditor() {
        return rangeEditor;
    }

    public TicketEditor getTicketEditor() {
        return ticketEditor;
    }
}
