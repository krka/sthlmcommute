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
import se.krka.sthlmcommute.web.client.util.DelayedWork;
import se.krka.sthlmcommute.web.client.util.UIUtil;
import se.krka.travelopt.Util;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TravelScheduleList extends Composite {
    private final List<ScheduleEntry> list;

    private final SingleSelectionModel<ScheduleEntry> selectionModel;
    private final TravelScheduleEditor travelScheduleEditor;
    private final ListDataProvider<ScheduleEntry> listDataProvider;

    private final CellList<ScheduleEntry> scheduleEntryCellList;

    private final List<ScheduleEntryUpdateListener> listeners = new ArrayList<ScheduleEntryUpdateListener>();
    private final DelayedWork worker;

    public TravelScheduleList(final TravelOptLocale locale, DelayedWork worker, final ClientConstants clientConstants, final ClientMessages clientMessages) {
        this.worker = worker;
        travelScheduleEditor = new TravelScheduleEditor(locale, this, clientConstants);

        Cell<ScheduleEntry> cell = new AbstractCell<ScheduleEntry>() {
            @Override
            public void render(Context context, ScheduleEntry scheduleEntry, SafeHtmlBuilder safeHtmlBuilder) {
                Date from = scheduleEntry.getInterval().getFrom();
                if (from == null || scheduleEntry.getInterval().getTo() == null) {
                    setError(safeHtmlBuilder, clientConstants.incompleteDateSpan());
                    return;
                }
                if (scheduleEntry.getWeekdays().countCoupons() == 0) {
                    setError(safeHtmlBuilder, clientConstants.incompleteCoupons());
                    return;
                }
                safeHtmlBuilder.appendHtmlConstant("<span style='float:left;min-width:8em'>" + locale.formatDay(Util.toDayOrdinal(from)) + "</span>");
                safeHtmlBuilder.appendEscaped(" ");
                safeHtmlBuilder.appendHtmlConstant("<span style='float:left;min-width:5em;text-align:right;padding-right:2em'>" + clientMessages.xDays(String.valueOf(scheduleEntry.getInterval().getDays())) + "</span>");

                int couponsPerWeek = scheduleEntry.getWeekdays().countCoupons();
                safeHtmlBuilder.appendHtmlConstant("<span>" + clientMessages.couponsPerWeek(String.valueOf(couponsPerWeek)) + "</span>");
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

        initWidget(UIUtil.wrapCaption(clientConstants.entries(), root));
    }

    public void updateCoupons(ScheduleEntry active, Weekdays weekdays) {
        active.setWeekdays(weekdays);
        update();
        notifyUpdatedScheduleEntry(active);
    }


    public void updateScheduleEntryInterval(ScheduleEntry entry, Date from, Date to) {
        entry.getInterval().set(from, to);
        update();
        notifyUpdatedScheduleEntry(entry);

    }
    private void notifyUpdatedScheduleEntry(ScheduleEntry entry) {
        for (ScheduleEntryUpdateListener listener : listeners) {
            listener.updated(entry);
        }
    }

    public void addScheduleEntryUpdateListener(ScheduleEntryUpdateListener listener) {
        listeners.add(listener);
    }

    private void setError(SafeHtmlBuilder safeHtmlBuilder, String reason) {
        safeHtmlBuilder.appendHtmlConstant("<div style='text-align:center;width:10em;margin-left:auto;margin-right:auto'>" + reason + "</div>");
    }

    public ScheduleEntry createNew() {
        ScheduleEntry entry = new ScheduleEntry(new Weekdays(0, createDefaultWeekdays()));
        list.add(entry);
        selectionModel.setSelected(entry, true);
        notifyUpdatedScheduleEntry(entry);
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
        listDataProvider.refresh();
        worker.requestWork();
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

    public TravelScheduleEditor getTravelScheduleEditor() {
        return travelScheduleEditor;
    }
}
