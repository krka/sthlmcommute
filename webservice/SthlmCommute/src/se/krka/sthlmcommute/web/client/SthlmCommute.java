package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import se.krka.sthlmcommute.web.shared.ScheduleEntryTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SthlmCommute implements EntryPoint {

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final TravelServiceAsync travelService = GWT.create(TravelService.class);

    private Label errorLabel;

    private final List<ScheduleEntry> entries = new ArrayList<ScheduleEntry>();

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        final DecoratorPanel tabPanel = new DecoratorPanel();
        RangeEditor rangeForm = createRangeForm();
        rangeForm.getIntervalPicker().install();
        tabPanel.add(rangeForm);

        final CheckBox extend = new CheckBox("Optimize for long term travel");
        RootPanel.get("extendContainer").add(extend);

        RootPanel.get("addContentContainer").add(tabPanel);
        errorLabel = new Label();

        final Label result = new Label();
        RootPanel.get("result").add(result);

        RootPanel.get("errorLabelContainer").add(errorLabel);

        Button execute = new Button("Execute");
        RootPanel.get("execute").add(execute);
        execute.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                ArrayList<ScheduleEntryTO> scheduleEntryTOs = new ArrayList<ScheduleEntryTO>();
                Collections.sort(entries);
                for (ScheduleEntry entry : entries) {
                    scheduleEntryTOs.add(new ScheduleEntryTO(entry.getInterval().getFrom(), entry.getInterval().getTo(), entry.getWeekdays().toString()));
                }
                result.setText("Waiting for reply...");
                travelService.optimize(scheduleEntryTOs, getBoolValue(extend.getValue()), new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        result.setText("Error: " + throwable.getMessage());
                    }

                    @Override
                    public void onSuccess(String s) {
                        result.setText(s);
                    }
                });
            }
        });
    }

    private boolean getBoolValue(Boolean value) {
        return value != null && value.booleanValue();
    }

    private RangeEditor createRangeForm() {
        final RangeEditor rangeEditor = new RangeEditor();

        final Grid buttons = new Grid(1, 2);
        Button reset = new Button("Reset");
        buttons.setWidget(0, 0, reset);
        Button add = new Button("Add");
        buttons.setWidget(0, 1, add);

        rangeEditor.add(buttons);

        reset.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Date date = Util.newDate();

                rangeEditor.getFrom().setValue(date, true);
                rangeEditor.getFrom().setCurrentMonth(date);
                rangeEditor.getTo().setValue(date, true);
                rangeEditor.getTo().setCurrentMonth(date);
                rangeEditor.getWeekdays().getTicket().setSelectedIndex(0);

                for (int i = 0; i < 7; i++) {
                    rangeEditor.getWeekdays().setWeekDay(i, "-1");
                }
            }
        });

        add.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                errorLabel.setText("");
                if (rangeEditor.getFrom().getValue() == null || rangeEditor.getFrom().getValue() == null) {
                    errorLabel.setText("You need to select a date range.");
                    return;
                }
                Weekdays days = rangeEditor.getWeekdays().getWeekdays();
                if (days.countTickets() == 0) {
                    errorLabel.setText("You must require at least one ticket");
                    return;
                }
                ScheduleEntry entry = new ScheduleEntry(rangeEditor.getFrom().getValue(), rangeEditor.getTo().getValue(), days, entries);
                entries.add(entry);
                RootPanel.get("addEntriesContainer").add(entry);

            }
        });

        reset.click();

        return rangeEditor;
    }

}
