package se.krka.sthlmcommute.web.client;

import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.safehtml.shared.OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.Date;
import java.util.List;

public class TravelScheduleList extends Composite {
    private final List<ScheduleEntry> list;
    private final CellTable<ScheduleEntry> scheduleEntryCellTable;
    private final SingleSelectionModel<ScheduleEntry> selectionModel;
    private final TravelScheduleEditor travelScheduleEditor;
    private final ListDataProvider<ScheduleEntry> listDataProvider;

    public TravelScheduleList(ClientConstants clientConstants, final TravelOptLocale locale, TravelScheduleEditor travelScheduleEditor) {
        this.travelScheduleEditor = travelScheduleEditor;
        scheduleEntryCellTable = new CellTable<ScheduleEntry>(10);
        selectionModel = new SingleSelectionModel<ScheduleEntry>();
        scheduleEntryCellTable.setSelectionModel(selectionModel);

        Header header = new TextHeader(clientConstants.when());

        TextColumn<ScheduleEntry> from = new TextColumn<ScheduleEntry>() {
            @Override
            public String getValue(ScheduleEntry scheduleEntry) {
                Date date = scheduleEntry.getInterval().getFrom();
                if (date == null) {
                    return "";
                }
                return locale.formatDate(date);
            }
        };
        scheduleEntryCellTable.addColumn(from, header);

        TextColumn<ScheduleEntry> days = new TextColumn<ScheduleEntry>() {
            @Override
            public String getValue(ScheduleEntry scheduleEntry) {
                return scheduleEntry.getInterval().getDays() + "";
            }
        };
        scheduleEntryCellTable.addColumn(days, clientConstants.days());

        for (int i = 0; i < 7; i++) {
            final int finalI = i;
            TextColumn<ScheduleEntry> weekdayColumn = new TextColumn<ScheduleEntry>() {
                @Override
                public String getValue(ScheduleEntry scheduleEntry) {
                    int tickets = scheduleEntry.getWeekdays().calcValue(finalI);
                    switch (tickets) {
                        case 0: return "";
                        case 9: return "9+";
                        default: return "" + tickets;
                    }
                }
            };
            weekdayColumn.setHorizontalAlignment(HasHorizontalAlignment.HorizontalAlignmentConstant.endOf(HasDirection.Direction.DEFAULT));
            SafeHtml html = new OnlyToBeUsedInGeneratedCodeStringBlessedAsSafeHtml(locale.weekDayName(i).substring(0, 1));
            scheduleEntryCellTable.addColumn(weekdayColumn, new SafeHtmlHeader(html));
        }
        scheduleEntryCellTable.setWidth("1px");
        listDataProvider = new ListDataProvider<ScheduleEntry>();
        list = this.listDataProvider.getList();
        listDataProvider.addDataDisplay(scheduleEntryCellTable);

        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent selectionChangeEvent) {
                TravelScheduleList.this.travelScheduleEditor.setActive(selectionModel.getSelectedObject());
            }
        });

        Panel root = new VerticalPanel();
        root.add(scheduleEntryCellTable);
        initWidget(root);
        setVisible(false);
    }

    public void createNew() {
        setVisible(true);
        ScheduleEntry entry = new ScheduleEntry(null, null, new Weekdays(0, createDefaultWeekdays()));
        list.add(entry);
        selectionModel.setSelected(entry, true);
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
    }

}
