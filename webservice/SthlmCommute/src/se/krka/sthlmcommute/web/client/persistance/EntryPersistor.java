package se.krka.sthlmcommute.web.client.persistance;

import com.google.gwt.user.client.Cookies;
import se.krka.sthlmcommute.web.client.Help;
import se.krka.sthlmcommute.web.client.ScheduleEntry;
import se.krka.sthlmcommute.web.client.TravelScheduleList;
import se.krka.sthlmcommute.web.client.util.DelayedWork;

import java.util.List;

public class EntryPersistor implements ClientPersistor {
    public static final String ENTRIES = "entries";
    private final TravelScheduleList travelSchedule;

    public EntryPersistor(TravelScheduleList travelSchedule) {
        this.travelSchedule = travelSchedule;
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
        Cookies.setCookie(ENTRIES, sb.toString());
    }

    @Override
    public void onLoad() {
        String s = Cookies.getCookie(ENTRIES);
        if (s != null && !s.equals("")) {
            String[] split = s.split(",");
            for (String s2 : split) {
                ScheduleEntry scheduleEntry = travelSchedule.createNew();
                scheduleEntry.deserialize(s2, travelSchedule);
            }
        }
    }
}
