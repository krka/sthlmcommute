package se.krka.sthlmcommute.web.client.persistance;

import com.google.gwt.user.client.Cookies;
import se.krka.sthlmcommute.web.client.*;

import java.util.List;

public class EntryPersistor implements ClientPersistor {
    private final TravelScheduleList travelSchedule;
    private final Help help;
    private final DelayedWork worker;

    public EntryPersistor(TravelScheduleList travelSchedule, Help help, DelayedWork worker) {
        this.travelSchedule = travelSchedule;
        this.help = help;
        this.worker = worker;
    }

    @Override
    public void onExit() {
        StringBuilder sb = new StringBuilder();
        List<ScheduleEntry> list = travelSchedule.getList();
        for (ScheduleEntry scheduleEntry : list) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(scheduleEntry.serialize());
        }
        Cookies.setCookie("entries", sb.toString());
    }

    @Override
    public void onLoad() {
        String s = Cookies.getCookie("entries");
        if (s != null && !s.equals("")) {
            String[] split = s.split(",");
            for (String s2 : split) {
                ScheduleEntry scheduleEntry = travelSchedule.createNew();
                scheduleEntry.deserialize(s2);
                travelSchedule.update();
                worker.requestWork();

                if (scheduleEntry.getInterval().getFrom() != null && scheduleEntry.getInterval().getTo() != null) {
                    help.selectedDates();
                }
            }
        }
    }
}
