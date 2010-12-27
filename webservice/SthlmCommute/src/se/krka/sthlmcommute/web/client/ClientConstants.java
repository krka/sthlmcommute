package se.krka.sthlmcommute.web.client;

/**
 * Interface to represent the constants contained in resource bundle:
 * '/home/krka/dev/sthlmcommute/webservice/SthlmCommute/src/se/krka/sthlmcommute/web/client/ClientConstants.properties'.
 */
public interface ClientConstants extends com.google.gwt.i18n.client.Constants {

    @Key("days")
    String getDays();

    @Key("fullprice")
    String fullPrice();

    @Key("reducedprice")
    String reducedPrice();
}
