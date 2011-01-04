package se.krka.sthlmcommute.web.client.persistance;

import java.util.ArrayList;
import java.util.List;

public class ClientPersistance {
    private final List<ClientPersistor> persistorList = new ArrayList<ClientPersistor>();

    public ClientPersistance() {
    }

    public void onExit() {
        for (ClientPersistor clientPersistor : persistorList) {
            clientPersistor.onExit();
        }
    }

    public void add(ClientPersistor persistor) {
        persistorList.add(persistor);
        persistor.onLoad();
    }
}
