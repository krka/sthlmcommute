package se.krka.sthlmcommute.web.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/home/krka/dev/sthlmcommute/webservice/SthlmCommute/src/se/krka/sthlmcommute/web/client/ClientMessages.properties'.
 */
public interface ClientMessages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Welcome.  The current time is {0}.".
   * 
   * @return translated "Welcome.  The current time is {0}."
   */
  @DefaultMessage("Welcome.  The current time is {0}.")
  @Key("welcome")
  String welcome(String arg0);
}
