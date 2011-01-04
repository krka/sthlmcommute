package se.krka.sthlmcommute.web.client;

import com.google.gwt.view.client.ListDataProvider;
import se.krka.sthlmcommute.web.client.components.dateinterval.DateInterval;
import se.krka.travelopt.Util;

import java.util.Date;
import java.util.List;

public class CollisionDetector implements Runnable {
    private final List<ScheduleEntry> list;
    private final ListDataProvider<ScheduleEntry> listDataProvider;

    public CollisionDetector(List<ScheduleEntry> list, ListDataProvider<ScheduleEntry> listDataProvider) {
        this.list = list;
        this.listDataProvider = listDataProvider;
    }

    @Override
    public void run() {
        int n = list.size();
        for (ScheduleEntry scheduleEntry : list) {
            scheduleEntry.setOverlapping(false);
        }

        for (int i = 0; i < n - 1; i++) {
            ScheduleEntry x = list.get(i);
            if (x.valid()) {
                for (int j = i + 1; j < n; j++) {
                    ScheduleEntry y = list.get(j);
                    if (y.valid()) {
                        if (overlaps(x.getInterval(), y.getInterval())) {
                            x.setOverlapping(true);
                            y.setOverlapping(true);
                            listDataProvider.refresh();
                            return;
                        }
                    }
                }
            }
        }
    }

    private boolean overlaps(DateInterval x, DateInterval y) {
        return inside(x.getFrom(), y) || inside(x.getTo(), y) ||
                inside(y.getFrom(), x) || inside(y.getTo(), x);
    }

    private boolean inside(Date x, DateInterval interval) {
        if (Util.before(x, interval.getFrom())) {
            return false;
        }
        if (Util.before(interval.getTo(), x)) {
            return false;
        }
        return true;
    }
}
