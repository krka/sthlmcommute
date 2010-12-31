package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.TextArea;
import se.krka.travelopt.Prices;
import se.krka.travelopt.TravelOpt;
import se.krka.travelopt.TravelPlan;
import se.krka.travelopt.TravelResult;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.List;

public class TravelOptRunner implements Runnable {
    private final TravelOptLocale locale;
    private final List<ScheduleEntry> entries;
    private final PriceCategories priceCategories;
    private final TextArea result;

    public TravelOptRunner(TravelOptLocale locale, List<ScheduleEntry> entries, PriceCategories priceCategories, TextArea result) {
        this.locale = locale;
        this.entries = entries;
        this.priceCategories = priceCategories;
        this.result = result;
    }

    @Override
    public void run() {
        String s = optimize(entries, false, priceCategories.getSelected());
        result.setText(s);
    }

    public String optimize(List<ScheduleEntry> entries, boolean extend, String priceCategory) throws IllegalArgumentException {
        try {
            TravelPlan.Builder builder = TravelPlan.builder(locale);
            int[] lastWeekdays = null;
            for (ScheduleEntry entry : entries) {
                lastWeekdays = entry.getWeekdays().getTickets();
                builder.addPeriod(entry.getInterval().getFrom(), entry.getInterval().getTo(), lastWeekdays);
            }
            TravelPlan travelPlan;
            if (extend && lastWeekdays != null) {
                travelPlan = builder.buildExtended(lastWeekdays);
            } else {
                travelPlan = builder.build();
            }
            if (travelPlan.getDates().isEmpty()) {
                return locale.mustSelectPeriod();
            }
            ;

            TravelOpt travelOpt = new TravelOpt(Prices.getPriceCategory(priceCategory, locale));
            TravelResult result = travelOpt.findOptimum(travelPlan);
            return result.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }


}
