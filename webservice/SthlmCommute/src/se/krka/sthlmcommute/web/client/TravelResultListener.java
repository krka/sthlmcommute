package se.krka.sthlmcommute.web.client;

import se.krka.travelopt.TravelResult;

public interface TravelResultListener {
    void newResult(TravelResult result);
}
