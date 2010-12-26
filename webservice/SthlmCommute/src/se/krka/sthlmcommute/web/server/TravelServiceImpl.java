package se.krka.sthlmcommute.web.server;

import com.google.gwt.core.client.GWT;
import org.joda.time.DateTime;
import se.krka.sthlmcommute.web.client.TravelService;
import se.krka.sthlmcommute.web.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import se.krka.sthlmcommute.web.shared.ScheduleEntryTO;
import se.krka.travelopt.*;
import se.krka.travelopt.localization.EnglishLocale;
import se.krka.travelopt.localization.SwedishLocale;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.List;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class TravelServiceImpl extends RemoteServiceServlet implements
        TravelService {

  public String optimize(List<ScheduleEntryTO> entries, boolean extend, String locale, String priceCategory) throws IllegalArgumentException {
      try {
          TravelOptLocale travelOptLocale = getLocale(locale);
          TravelPlan.Builder builder = TravelPlan.builder(travelOptLocale);
          String lastWeekdays = null;
          for (ScheduleEntryTO entry : entries) {

              lastWeekdays = entry.getWeekDays();
              builder.addPeriod(new DateTime(entry.getFrom()), new DateTime(entry.getTo()), lastWeekdays);
          }
          TravelPlan travelPlan;
          if (extend && lastWeekdays != null) {
              travelPlan = builder.buildExtended(lastWeekdays);
          } else {
              travelPlan = builder.build();
          }
          if (travelPlan.getPeriod() == null) {
              return travelOptLocale.mustSelectPeriod();
          }
          ;

          TravelOpt travelOpt = new TravelOpt(getPriceCategory(priceCategory));
          TravelResult result = travelOpt.findOptimum(travelPlan);
          return result.toString();
      } catch (Exception e) {
          return e.getMessage();
      }
  }

    private PriceStructure getPriceCategory(String priceCategory) {
        if (priceCategory.equals("reduced")) {
            return Prices.SL_REDUCED_PRICE;
        }
        return Prices.SL_FULL_PRICE;
    }

    private TravelOptLocale getLocale(String locale) {
        if (locale.equals("sv")) {
            return new SwedishLocale();
        }
        return new EnglishLocale();
    }
}
