package se.krka.sthlmcommute.web.client.persistance;

public interface ClientPersistor {
    void onExit();

    void onLoad();
}
