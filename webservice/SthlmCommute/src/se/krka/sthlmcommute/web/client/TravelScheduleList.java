package se.krka.sthlmcommute.web.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import se.krka.travelopt.Util;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.Date;
import java.util.List;

public class TravelScheduleList extends Composite {
    private final List<ScheduleEntry> list;

    private final SingleSelectionModel<ScheduleEntry> selectionModel;
    private final TravelScheduleEditor travelScheduleEditor;
    private final ListDataProvider<ScheduleEntry> listDataProvider;

    private final DelayedWork collisionDetector;
    private final CellList<ScheduleEntry> scheduleEntryCellList;

    public TravelScheduleList(final TravelOptLocale locale, TravelScheduleEditor travelScheduleEditor) {
        this.travelScheduleEditor = travelScheduleEditor;

        Cell<ScheduleEntry> cell = new AbstractCell<ScheduleEntry>() {
            @Override
            public void render(Context context, ScheduleEntry scheduleEntry, SafeHtmlBuilder safeHtmlBuilder) {
                Date from = scheduleEntry.getInterval().getFrom();
                if (from == null || scheduleEntry.getInterval().getTo() == null) {
                    setError(safeHtmlBuilder, "Incomplete time period");
                    return;
                }
                if (scheduleEntry.getWeekdays().countTickets() == 0) {
                    setError(safeHtmlBuilder, "Incomplete tickets");
                    return;
                }
                if (scheduleEntry.isOverlapping()) {
                    setError(safeHtmlBuilder, "Overlapping period");
                    return;
                }
                safeHtmlBuilder.appendHtmlConstant("<span style='float:left;min-width:8em'>" + locale.formatDay(Util.toDayOrdinal(from)) + "</span>");
                safeHtmlBuilder.appendEscaped(" ");
                safeHtmlBuilder.appendHtmlConstant("<span style='float:left;min-width:5em;text-align:right;padding-right:2em'>" + scheduleEntry.getInterval().getDays() + " days</span>");

                int ticketsPerWeek = scheduleEntry.getWeekdays().countTickets();
                safeHtmlBuilder.appendHtmlConstant("<span>" + ticketsPerWeek + " ticket/week</span>");
            }
        };
        scheduleEntryCellList = new CellList<ScheduleEntry>(cell);

        selectionModel = new SingleSelectionModel<ScheduleEntry>();
        scheduleEntryCellList.setSelectionModel(selectionModel);

        scheduleEntryCellList.setWidth("22em");
        listDataProvider = new ListDataProvider<ScheduleEntry>();
        list = this.listDataProvider.getList();
        listDataProvider.addDataDisplay(scheduleEntryCellList);

        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent selectionChangeEvent) {
                TravelScheduleList.this.travelScheduleEditor.setActive(selectionModel.getSelectedObject());
            }
        });

        Panel root = new VerticalPanel();
        root.add(scheduleEntryCellList);

        collisionDetector = new DelayedWork(new CollisionDetector(list, listDataProvider), 500);
        initWidget(UIUtil.wrapCaption("Entries:", root));
    }

    private void setError(SafeHtmlBuilder safeHtmlBuilder, String reason) {
        safeHtmlBuilder.appendHtmlConstant("<div style='text-align:center;width:10em;margin-left:auto;margin-right:auto'>" + reason + "</div>");
    }

    public ScheduleEntry createNew() {
        ScheduleEntry entry = new ScheduleEntry(new Weekdays(0, createDefaultWeekdays()));
        list.add(entry);
        selectionModel.setSelected(entry, true);
        return entry;
    }

    public CellList<ScheduleEntry> getCellList() {
        return scheduleEntryCellList;
    }

    private int[] createDefaultWeekdays() {
        int[] res = new int[7];
        for (int i = 0; i < 7; i++) {
            res[i] = -1;
        }
        return res;
    }

    public void update() {
        collisionDetector.requestWork();
        listDataProvider.refresh();
    }

    public List<ScheduleEntry> getList() {
        return list;
    }

    public void removeSelectedEntry() {
        list.remove(selectionModel.getSelectedObject());
    }

    public ScheduleEntry getSelectedObject() {
        return selectionModel.getSelectedObject();
    }

    public void addSelectionChangeHandler(SelectionChangeEvent.Handler handler) {
        selectionModel.addSelectionChangeHandler(handler);
    }
}
