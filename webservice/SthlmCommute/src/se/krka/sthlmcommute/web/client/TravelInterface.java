package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.*;
import se.krka.travelopt.localization.TravelOptLocale;

public class TravelInterface {
    private final Help help;
    private final PriceCategories priceCategories;
    private final TravelSchedule travelSchedule;
    private final OptimizeOptions optimizeOptions;

    public TravelInterface(TravelOptLocale locale, ClientConstants clientConstants) {
        help = new Help();

        TravelOptRunner travelOptRunner = new TravelOptRunner(locale);
        DelayedWork worker = new DelayedWork(travelOptRunner);

        priceCategories = new PriceCategories(this, clientConstants, worker);
        travelSchedule = new TravelSchedule(clientConstants, locale, worker);

        optimizeOptions = new OptimizeOptions(worker, locale);
        travelOptRunner.setup(travelSchedule.getList().getList(), priceCategories, optimizeOptions);
    }

    public void setup() {
        RootPanel.get("priceCategories").add(priceCategories);
        RootPanel.get("travelSchedule").add(travelSchedule);
        RootPanel.get("extendContainer").add(optimizeOptions);

        start();
    }

    private void start() {
        help.choosePriceCategory();
        travelSchedule.setVisible(false);
        //optimizeOptions.setVisible(false);
    }

    public void doneWithPriceCategory() {
        help.createNewEntry();
        travelSchedule.setVisible(true);
    }
}
