package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import java.util.Date;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SthlmCommute implements EntryPoint {

    /**
     * Create a remote service proxy to talk to the server-side Greeting service.
     */
    private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

    private Label errorLabel;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        final DecoratorPanel tabPanel = new DecoratorPanel();
        tabPanel.add(createRangeForm());

        RootPanel.get("addContentContainer").add(tabPanel);
        errorLabel = new Label();

        RootPanel.get("errorLabelContainer").add(errorLabel);
    }

    private VerticalPanel createRangeForm() {
        final Grid dates = new Grid(2, 2);
        dates.setWidget(0, 0, new Label("From:"));
        dates.setWidget(0, 1, new Label("To:"));
        final DatePicker from = new DatePicker();
        dates.setWidget(1, 0, from);
        final DatePicker to = new DatePicker();
        dates.setWidget(1, 1, to);

        final Grid buttons = new Grid(1, 2);
        Button reset = new Button("Reset");
        buttons.setWidget(0, 0, reset);
        Button add = new Button("Add");
        buttons.setWidget(0, 1, add);

        final VerticalPanel form = new VerticalPanel();
        form.add(dates);
        final Label dateSelectionLabel = new Label("");
        form.add(dateSelectionLabel);

        Grid defaultTickets = new Grid(1, 2);
        defaultTickets.setWidget(0, 0, new Label("Required number of tickets per day:"));
        final ListBox ticketListBox = createTicketListBox();
        defaultTickets.setWidget(0, 1, ticketListBox);
        form.add(defaultTickets);

        final Grid weekDayForm = createWeekDayForm();
        form.add(weekDayForm);
        form.add(buttons);

        Date date = Util.newDate();
        from.setValue(date, true);
        from.setCurrentMonth(date);
        to.setValue(date, true);
        to.setCurrentMonth(date);

        reset.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Date date = Util.newDate();

                from.setValue(date, true);
                from.setCurrentMonth(date);
                to.setValue(date, true);
                to.setCurrentMonth(date);
                ticketListBox.setSelectedIndex(0);
                for (int i = 0; i < 7; i++) {
                    ListBox widget = (ListBox) weekDayForm.getWidget(1, i);
                    widget.setSelectedIndex(0);
                }
            }
        });

        add.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                errorLabel.setText("");
                if (from.getValue() == null || to.getValue() == null) {
                    errorLabel.setText("You need to select a date range.");
                    return;
                }
                int defaultValue = Integer.parseInt(ticketListBox.getValue(ticketListBox.getSelectedIndex()));
                if (defaultValue == -1) {
                    errorLabel.setText("You need to select number of tickets.");
                    return;
                }

                int[] weekdays = new int[7];
                for (int i = 0; i < 7; i++) {
                    ListBox widget = (ListBox) weekDayForm.getWidget(1, i);
                    int value = Integer.parseInt(widget.getValue(widget.getSelectedIndex()));
                    if (value == -1) {
                        value = defaultValue;
                    }
                    weekdays[i] = value;
                }

                ScheduleEntry entry = new ScheduleEntry(from.getValue(), to.getValue(), new Weekdays(weekdays));
                RootPanel.get("addEntriesContainer").add(entry);

            }
        });

        reset.click();

        DateIntervalPicker intervalPicker = new DateIntervalPicker(from, to, dateSelectionLabel);
        intervalPicker.install();

        return form;
    }

    private static final String[] weekdays = new String[]{"mo", "tu", "we", "th", "fr", "sa", "su"};
    private Grid createWeekDayForm() {
        Grid grid = new Grid(2, 7);
        for (int day = 0; day < 7; day++) {
            grid.setWidget(0, day, new Label(weekdays[day]));
            ListBox listBox = createTicketListBox();
            listBox.setSelectedIndex(0);
            grid.setWidget(1, day, listBox);
        }
        return grid;

    }

    private ListBox createTicketListBox() {
        ListBox listBox = new ListBox();
        listBox.addItem("--", "-1");
        listBox.addItem("No", "0");
        for (int i = 1; i < 10; i++) {
            listBox.addItem(i + "", "" + i);
        }
        return listBox;
    }
}
