package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.*;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.Date;

public class TravelScheduleEditor extends Composite {
    private final RangeEditor rangeEditor;

    private final TicketEditor ticketEditor;

    private ScheduleEntry active;
    private TravelScheduleList travelScheduleList;
    private final DelayedWork worker;

    public TravelScheduleEditor(DelayedWork worker, TravelOptLocale locale) {
        this.worker = worker;
        VerticalPanel root = new VerticalPanel();
        rangeEditor = new RangeEditor();

        //helpInfo = new HelpInfo("Choose the time period for when you need to travel.");
        ticketEditor = new TicketEditor(new UpdateListener() {
            @Override
            public void updated() {
                active.setWeekdays(ticketEditor.getWeekdays());
                travelScheduleList.update();
            }
        }, this.worker, locale);

        root.add(rangeEditor);
        root.add(ticketEditor);
        initWidget(root);
        setVisible(false);

        rangeEditor.getIntervalPicker().addListener(new DateIntervalUpdateListener() {
            @Override
            public void intervalChanged(DateIntervalPicker picker, Date fromValue, Date toValue) {
                if (fromValue != null && toValue != null) {
                    active.getInterval().set(fromValue, toValue);
                    travelScheduleList.update();
                    TravelScheduleEditor.this.worker.requestWork();
                }
            }
        });
    }

    public void setTravelScheduleList(TravelScheduleList travelScheduleList) {
        this.travelScheduleList = travelScheduleList;
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

        rangeEditor.getIntervalPicker().applyHighlight();

        int defaultValue = entry.getWeekdays().getDefaultValue();
        ticketEditor.getTicket().setSelectedTicket(defaultValue);
        ticketEditor.getWeekdayEditor().setWeekDays(entry.getWeekdays().getRawTickets());
        setVisible(true);
    }

    public RangeEditor getRangeEditor() {
        return rangeEditor;
    }
}
