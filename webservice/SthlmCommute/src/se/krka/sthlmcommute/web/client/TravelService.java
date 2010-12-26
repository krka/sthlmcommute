package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import se.krka.sthlmcommute.web.shared.ScheduleEntryTO;

import java.util.List;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("travel")
public interface TravelService extends RemoteService {
  String optimize(List<ScheduleEntryTO> entries, boolean extend, String locale, String priceCategory);
}
