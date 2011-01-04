package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.*;
import se.krka.sthlmcommute.web.client.async.AsyncWidgetUsage;
import se.krka.sthlmcommute.web.client.persistors.OptimizePersistor;
import se.krka.sthlmcommute.web.client.persistors.PriceCategoryClientPersistor;
import se.krka.travelopt.localization.TravelOptLocale;

public class TravelInterface {
    private final PriceCategories priceCategories;
    private final TravelSchedule travelSchedule;
    private final OptimizeOptions optimizeOptions;
    private final Help help;

    public TravelInterface(TravelOptLocale locale, ClientConstants clientConstants, ClientPersistance persistance) {
        final TravelOptRunner travelOptRunner = new TravelOptRunner(locale);
        DelayedWork worker = new DelayedWork(travelOptRunner);

        priceCategories = new PriceCategories(clientConstants, worker, locale);
        travelSchedule = new TravelSchedule(clientConstants, locale, worker);

        optimizeOptions = new OptimizeOptions(worker, locale);
        travelSchedule.getAsyncWidget().runASAP(new AsyncWidgetUsage<TravelScheduleList>() {
            @Override
            public void run(TravelScheduleList widget) {
                travelOptRunner.setup(widget.getList(), priceCategories, optimizeOptions);
            }
        });

        help = new Help(priceCategories, travelSchedule, travelSchedule.getRangeEditor());

        persistance.add(new PriceCategoryClientPersistor(priceCategories));
        persistance.add(new OptimizePersistor(optimizeOptions));
        travelSchedule.addPersistance(persistance, help);
    }

    public void addComponents() {
        RootPanel.get("helpsection").add(help.getHelpSection());
        RootPanel.get("priceCategories").add(priceCategories);
        RootPanel.get("travelSchedule").add(travelSchedule);
        RootPanel.get("extendContainer").add(optimizeOptions);
    }
}
