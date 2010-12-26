package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import se.krka.sthlmcommute.web.shared.ScheduleEntryTO;

import java.util.List;

/**
 * The async counterpart of <code>TravelService</code>.
 */
public interface TravelServiceAsync {
    void optimize(List<ScheduleEntryTO> entries, boolean extend, String locale, String priceCategory, AsyncCallback<String> callback);
}
