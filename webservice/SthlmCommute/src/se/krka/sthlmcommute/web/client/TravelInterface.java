package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.*;
import se.krka.sthlmcommute.web.client.async.AsyncWidgetUsage;
import se.krka.sthlmcommute.web.client.persistance.ClientPersistance;
import se.krka.sthlmcommute.web.client.persistance.EntryPersistor;
import se.krka.sthlmcommute.web.client.persistance.OptimizePersistor;
import se.krka.sthlmcommute.web.client.persistance.PriceCategoryClientPersistor;
import se.krka.sthlmcommute.web.client.util.DelayedWork;
import se.krka.travelopt.localization.TravelOptLocale;

public class TravelInterface {
    private final PriceCategories priceCategories;
    private final TravelSchedule travelSchedule;
    private final OptimizeOptions optimizeOptions;
    private final Help help;

    public TravelInterface(TravelOptLocale locale, ClientConstants clientConstants, final ClientPersistance persistance) {
        final TravelOptRunner travelOptRunner = new TravelOptRunner(locale);
        final DelayedWork worker = new DelayedWork(travelOptRunner);

        priceCategories = new PriceCategories(clientConstants, worker, locale);
        travelSchedule = new TravelSchedule(clientConstants, locale, worker);

        optimizeOptions = new OptimizeOptions(worker, locale);
        travelSchedule.getAsyncList().runASAP(new AsyncWidgetUsage<TravelScheduleList>() {
            @Override
            public void run(TravelScheduleList widget) {
                travelOptRunner.setup(widget.getList(), priceCategories, optimizeOptions);
            }
        });

        help = new Help(clientConstants, priceCategories, travelSchedule, travelOptRunner);

        persistance.add(new PriceCategoryClientPersistor(priceCategories));
        persistance.add(new OptimizePersistor(optimizeOptions));
        travelSchedule.getAsyncList().runASAP(new AsyncWidgetUsage<TravelScheduleList>() {
            @Override
            public void run(TravelScheduleList widget) {
                persistance.add(new EntryPersistor(widget));
            }
        });
    }

    public void addComponents() {
        RootPanel.get("helpsection").add(help.getHelpSection());
        RootPanel.get("choosePriceCategories").add(priceCategories);
        RootPanel.get("travelSchedule").add(travelSchedule);
        RootPanel.get("extendContainer").add(optimizeOptions);
    }
}
