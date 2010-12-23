package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;
import se.krka.sthlmcommute.web.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

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

        Date date = new Date();
        from.setValue(date, true);
        from.setCurrentMonth(date);
        to.setValue(date, true);
        to.setCurrentMonth(date);


        from.addValueChangeHandler(new ValueChangeHandler<Date>() {
            @Override
            public void onValueChange(ValueChangeEvent<Date> dateValueChangeEvent) {
                Date date = dateValueChangeEvent.getValue();
                if (to.getValue() == null || date.after(to.getValue())) {
                    to.setValue(date);
                    to.setCurrentMonth(date);
                }
                updateDateSelection(dateSelectionLabel, from, to);
            }
        });
        to.addValueChangeHandler(new ValueChangeHandler<Date>() {
            @Override
            public void onValueChange(ValueChangeEvent<Date> dateValueChangeEvent) {
                Date date = dateValueChangeEvent.getValue();
                if (from.getValue() == null || date.before(from.getValue())) {
                    from.setValue(date);
                    from.setCurrentMonth(date);
                }
                updateDateSelection(dateSelectionLabel, from, to);
            }
        });

        reset.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                Date date = new Date();

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
        updateDateSelection(dateSelectionLabel, from, to);

        return form;
    }

    private void updateDateSelection(Label dateSelectionLabel, DatePicker from, DatePicker to) {
        if (to.getValue() == null || from.getValue() == null) {
            dateSelectionLabel.setText("No dates have been selected.");
        } else {
            DateInterval interval = new DateInterval(from.getValue(), to.getValue());
            dateSelectionLabel.setText(interval.toString());
        }
    }

    private String pad(char c, int width, int value) {
        String s = String.valueOf(value);
        int toAdd = s.length() - width;
        for (int i = 0; i < toAdd; i++) {
            s = s + c;
        }
        return s;
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
