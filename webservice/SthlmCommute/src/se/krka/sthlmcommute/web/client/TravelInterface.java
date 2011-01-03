package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.*;
import se.krka.sthlmcommute.web.client.persistors.EntryPersistor;
import se.krka.sthlmcommute.web.client.persistors.OptimizePersistor;
import se.krka.sthlmcommute.web.client.persistors.PriceCategoryClientPersistor;
import se.krka.travelopt.localization.TravelOptLocale;

public class TravelInterface {
    private final Help help;
    private final PriceCategories priceCategories;
    private final TravelSchedule travelSchedule;
    private final OptimizeOptions optimizeOptions;
    private final HelpSection helpSection;

    public TravelInterface(TravelOptLocale locale, ClientConstants clientConstants, ClientPersistance persistance) {
        help = new Help();

        TravelOptRunner travelOptRunner = new TravelOptRunner(locale);
        DelayedWork worker = new DelayedWork(travelOptRunner);

        priceCategories = new PriceCategories(this, clientConstants, worker, locale);
        travelSchedule = new TravelSchedule(clientConstants, locale, worker);

        optimizeOptions = new OptimizeOptions(worker, locale);
        travelOptRunner.setup(travelSchedule.getList().getList(), priceCategories, optimizeOptions);

        helpSection = new HelpSection(priceCategories, travelSchedule, travelSchedule.getRangeEditor());

        persistance.add(new PriceCategoryClientPersistor(priceCategories));
        persistance.add(new OptimizePersistor(optimizeOptions));
        persistance.add(new EntryPersistor(travelSchedule));
    }

    public void setup() {
        RootPanel.get("helpsection").add(helpSection);
        RootPanel.get("priceCategories").add(priceCategories);
        RootPanel.get("travelSchedule").add(travelSchedule);
        RootPanel.get("extendContainer").add(optimizeOptions);

        start();
    }

    private void start() {
        help.choosePriceCategory();
    }

    public void doneWithPriceCategory() {
        help.createNewEntry();
    }
}
