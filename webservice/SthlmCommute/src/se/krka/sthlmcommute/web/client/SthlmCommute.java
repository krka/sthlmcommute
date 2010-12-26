package se.krka.sthlmcommute.web.client;

import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import se.krka.sthlmcommute.web.shared.ScheduleEntryTO;

import java.util.*;

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
    private Label result;
    private CheckBox extend;
    private final String localeName = LocaleInfo.getCurrentLocale().getLocaleName();
    private ClientConstants clientConstants;

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        addLocaleLinks();

        clientConstants = GWT.create(ClientConstants.class);

        final DecoratorPanel tabPanel = new DecoratorPanel();
        RangeEditor rangeForm = createRangeForm();
        rangeForm.getIntervalPicker().install();
        tabPanel.add(rangeForm);

        extend = new CheckBox("Optimize for long term travel");
        RootPanel.get("extendContainer").add(extend);

        RootPanel.get("addContentContainer").add(tabPanel);
        errorLabel = new Label();

        result = new Label();
        RootPanel.get("result").add(result);

        RootPanel.get("errorLabelContainer").add(errorLabel);
    }

    void updateTravelSuggestion() {
        ArrayList<ScheduleEntryTO> scheduleEntryTOs = new ArrayList<ScheduleEntryTO>();
        Collections.sort(entries);
        for (ScheduleEntry entry : entries) {
            scheduleEntryTOs.add(new ScheduleEntryTO(entry.getInterval().getFrom(), entry.getInterval().getTo(), entry.getWeekdays().toString()));
        }
        result.setText("Waiting for reply...");
        travelService.optimize(scheduleEntryTOs, getBoolValue(extend.getValue()), localeName, "full", new AsyncCallback<String>() {
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

    private void addLocaleLinks() {

        String[] codes = LocaleInfo.getAvailableLocaleNames();

        String href = Window.Location.getHref().replaceAll("locale=[a-zA-Z_]+", "");
        href = href.replaceAll("#.*", "");

        String append;
        if (href.endsWith("?") || href.endsWith("&")) {
            append = "locale=";
        } else {
            append = href.contains("?") ? "&locale=" : "?locale=";
        }

        String content = "";
        for (String code : codes) {
            String name = LocaleInfo.getLocaleNativeDisplayName(code);

            if (name == null) {
                continue;
            }

            if (content.length() > 0) {
                content += " ";
            }

            String linkName = "[" + name + "]";
            if (!localeName.equals(code)) {
                String link = href + append + code;
                content += "<a href=\"" + link + "\">" + linkName + "</a>";
            } else {
                content += linkName;
            }
        }
        RootPanel.get("locales").add(new HTML(content));
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
                Weekdays days = rangeEditor.getWeekdays().getWeekdays(clientConstants);
                if (days.countTickets() == 0) {
                    errorLabel.setText("You must require at least one ticket");
                    return;
                }
                ScheduleEntry entry = new ScheduleEntry(rangeEditor.getFrom().getValue(), rangeEditor.getTo().getValue(), days, entries, SthlmCommute.this);
                entries.add(entry);
                RootPanel.get("addEntriesContainer").add(entry);
                updateTravelSuggestion();
            }
        });

        reset.click();

        return rangeEditor;
    }

}
