package se.krka.sthlmcommute.web.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/home/krka/priv/sthlmcommute/webservice/SthlmCommute/src/se/krka/sthlmcommute/web/client/ClientMessages.properties'.
 */
public interface ClientMessages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "{0} kuponger/vecka".
   * 
   * @return translated "{0} kuponger/vecka"
   */
  @DefaultMessage("{0} kuponger/vecka")
  @Key("couponsPerWeek")
  String couponsPerWeek(String arg0);

  /**
   * Translated "Skicka ett email till {0} om du har några frågor, synpunkter eller idéer.".
   * 
   * @return translated "Skicka ett email till {0} om du har några frågor, synpunkter eller idéer."
   */
  @DefaultMessage("Skicka ett email till {0} om du har några frågor, synpunkter eller idéer.")
  @Key("sendEmailTo")
  String sendEmailTo(String arg0);

  /**
   * Translated "{0} dagar".
   * 
   * @return translated "{0} dagar"
   */
  @DefaultMessage("{0} dagar")
  @Key("xDays")
  String xDays(String arg0);
}
