package se.krka.sthlmcommute.web.client.persistors;

import com.google.gwt.user.client.Cookies;
import se.krka.sthlmcommute.web.client.ClientPersistor;
import se.krka.sthlmcommute.web.client.ScheduleEntry;
import se.krka.sthlmcommute.web.client.TravelSchedule;
import se.krka.sthlmcommute.web.client.TravelScheduleList;

import java.util.List;

public class EntryPersistor implements ClientPersistor {
    private final TravelSchedule travelSchedule;

    public EntryPersistor(TravelSchedule travelSchedule) {
        this.travelSchedule = travelSchedule;
    }

    @Override
    public void onExit() {
        StringBuilder sb = new StringBuilder();
        List<ScheduleEntry> list = travelSchedule.getList().getList();
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
                ScheduleEntry scheduleEntry = travelSchedule.getList().createNew();
                scheduleEntry.deserialize(s2);
            }
        }
    }
}
