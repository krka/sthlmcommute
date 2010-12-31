package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.*;

import java.util.Date;

public class TravelScheduleEditor extends Composite {
    private final RangeEditor rangeEditor;

    private final HelpInfo helpInfo;
    private final TicketEditor ticketEditor;

    private boolean isReady;
    private ScheduleEntry active;
    private TravelScheduleList travelScheduleList;
    private DelayedWork worker;

    public TravelScheduleEditor() {
        VerticalPanel root = new VerticalPanel();
        rangeEditor = new RangeEditor();

        helpInfo = new HelpInfo("Choose the time period for when you need to travel.");
        ticketEditor = new TicketEditor(new UpdateListener() {
            @Override
            public void updated() {
                active.setWeekdays(ticketEditor.getWeekdays());
                travelScheduleList.update();
            }
        });

        root.add(helpInfo);
        root.add(rangeEditor.getDates());
        root.add(ticketEditor);
        initWidget(root);
        setVisible(false);

        ticketEditor.setVisible(false);
        /*
        ticket.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                if (ticket.getSelectedIndex() > 0) {
                    ticketHelpInfo.setVisible(false);
                    weekDayForm.setVisible(true);
                    weekdayHelpInfo.setVisible(true);
                    weekdayLabel.setVisible(true);
                } else {
                    ticketHelpInfo.setVisible(true);
                    weekDayForm.setVisible(false);
                    weekdayHelpInfo.setVisible(false);
                    weekdayLabel.setVisible(false);
                }
            }
        });
        */

        rangeEditor.getIntervalPicker().addListener(new DateIntervalUpdateListener() {
            @Override
            public void intervalChanged(DateIntervalPicker picker, Date fromValue, Date toValue) {
                boolean ready = fromValue != null && toValue != null;
                if (ready) {
                    active.getInterval().set(fromValue, toValue);
                    travelScheduleList.update();
                    worker.requestWork();
                }
                if (ready && !isReady) {
                    isReady = true;

                    ticketEditor.setVisible(true);
                    helpInfo.setVisible(false);
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

    public void setWorker(DelayedWork worker) {
        this.worker = worker;
        ticketEditor.setWorker(worker);
    }
}
