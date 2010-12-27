package se.krka.sthlmcommute.web.server;

import org.joda.time.DateTime;
import se.krka.sthlmcommute.web.client.TravelService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import se.krka.sthlmcommute.web.shared.ScheduleEntryTO;
import se.krka.travelopt.*;
import se.krka.travelopt.localization.Locales;
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
          TravelOptLocale travelOptLocale = Locales.getLocale(locale);
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

          TravelOpt travelOpt = new TravelOpt(Prices.getPriceCategory(priceCategory, travelOptLocale));
          TravelResult result = travelOpt.findOptimum(travelPlan);
          return result.toString();
      } catch (Exception e) {
          return e.getMessage();
      }
  }

}
