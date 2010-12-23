package se.krka.sthlmcommute.web.server;

import org.joda.time.DateTime;
import se.krka.sthlmcommute.web.client.TravelService;
import se.krka.sthlmcommute.web.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import se.krka.sthlmcommute.web.shared.ScheduleEntryTO;
import se.krka.travelopt.Prices;
import se.krka.travelopt.TravelOpt;
import se.krka.travelopt.TravelPlan;
import se.krka.travelopt.TravelResult;
import se.krka.travelopt.localization.EnglishLocale;

import java.util.List;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class TravelServiceImpl extends RemoteServiceServlet implements
        TravelService {

  public String optimize(List<ScheduleEntryTO> entries, boolean extend) throws IllegalArgumentException {

      TravelPlan.Builder builder = TravelPlan.builder(new EnglishLocale());
      String lastWeekdays = null;
      for (ScheduleEntryTO entry : entries) {

          lastWeekdays = entry.getWeekDays();
          builder.addPeriod(new org.joda.time.DateTime(entry.getFrom()), new DateTime(entry.getTo()), lastWeekdays);
      }
      TravelPlan travelPlan;
      if (extend && lastWeekdays != null) {
          travelPlan = builder.buildExtended(lastWeekdays);
      } else {
          travelPlan = builder.build();
      }
      TravelOpt travelOpt = new TravelOpt(Prices.SL_FULL_PRICE);
      TravelResult result = travelOpt.findOptimum(travelPlan);
      return result.toString();
  }
}
