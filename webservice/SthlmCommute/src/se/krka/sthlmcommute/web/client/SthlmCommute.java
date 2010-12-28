package se.krka.sthlmcommute.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import se.krka.sthlmcommute.web.shared.ScheduleEntryTO;
import se.krka.travelopt.*;
import se.krka.travelopt.localization.Locales;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SthlmCommute implements EntryPoint {

    private Label errorLabel;

    private final List<ScheduleEntry> entries = new ArrayList<ScheduleEntry>();
    private Label result;
    private CheckBox extend;
    private final String localeName = getLocaleName();

    private static String getLocaleName() {
        String s = LocaleInfo.getCurrentLocale().getLocaleName();
        return s;
    }

    private ClientConstants clientConstants;
    private ListBox priceCategories;

    public static String optimize(List<ScheduleEntryTO> entries, boolean extend, String locale, String priceCategory) throws IllegalArgumentException {
        try {
            TravelOptLocale travelOptLocale = Locales.getLocale(locale);
            TravelPlan.Builder builder = TravelPlan.builder(travelOptLocale);
            String lastWeekdays = null;
            for (ScheduleEntryTO entry : entries) {

                lastWeekdays = entry.getWeekDays();
                builder.addPeriod(entry.getFrom(), entry.getTo(), lastWeekdays);
            }
            TravelPlan travelPlan;
            if (extend && lastWeekdays != null) {
                travelPlan = builder.buildExtended(lastWeekdays);
            } else {
                travelPlan = builder.build();
            }
            if (travelPlan.getDates().isEmpty()) {
                return travelOptLocale.mustSelectPeriod();
            }
            ;

            TravelOpt travelOpt = new TravelOpt(Prices.getPriceCategory(priceCategory, travelOptLocale));
            TravelResult result = travelOpt.findOptimum(travelPlan);
            return result.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        clientConstants = GWT.create(ClientConstants.class);

        addLocaleLinks();

        addPriceCategories();

        addPriceList();



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

    private void addPriceList() {
        RootPanel panel = RootPanel.get("priceList");
        for (Widget widget : panel) {
            widget.removeFromParent();
        }

        TravelOptLocale locale = Locales.getLocale(localeName);
        int index = priceCategories.getSelectedIndex();
        String value = priceCategories.getValue(index);
        PriceStructure priceCategory = Prices.getPriceCategory(value, locale);

        int i = 0;
        List<TicketType> ticketTypes = priceCategory.getTicketTypes();
        Grid grid = new Grid(ticketTypes.size(), 1);
        for (TicketType ticket: ticketTypes) {
            grid.setWidget(i, 0, new Label(ticket.toString()));
            i++;
        }
        panel.add(grid);
    }

    private void addPriceCategories() {
        priceCategories = new ListBox();
        priceCategories.addItem(clientConstants.fullPrice(), "full");
        priceCategories.addItem(clientConstants.reducedPrice(), "reduced");
        priceCategories.setSelectedIndex(0);

        priceCategories.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                updateTravelSuggestion();
                addPriceList();
            }
        });
        RootPanel.get("priceCategories").add(priceCategories);
    }

    void updateTravelSuggestion() {
        ArrayList<ScheduleEntryTO> scheduleEntryTOs = new ArrayList<ScheduleEntryTO>();
        Collections.sort(entries);
        for (ScheduleEntry entry : entries) {
            scheduleEntryTOs.add(new ScheduleEntryTO(entry.getInterval().getFrom(), entry.getInterval().getTo(), entry.getWeekdays().toString()));
        }
        result.setText("Waiting for reply...");
        String s = optimize(scheduleEntryTOs, getBoolValue(extend.getValue()), localeName, priceCategories.getValue(priceCategories.getSelectedIndex()));
        SthlmCommute.this.result.setText(s);
    }

    private final String[] locales = new String[]{"sv", "en"};

    private void addLocaleLinks() {
        String href = Window.Location.getHref().replaceAll("locale=[a-zA-Z_]+", "");
        href = href.replaceAll("#.*", "");

        String append;
        if (href.endsWith("?") || href.endsWith("&")) {
            append = "locale=";
        } else {
            append = href.contains("?") ? "&locale=" : "?locale=";
        }

        String content = "";
        for (String code : locales) {
            String name = LocaleInfo.getLocaleNativeDisplayName(code);

            if (name == null) {
                name = "svenska";
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
