package se.krka.sthlmcommute.web.client;

/**
 * Interface to represent the constants contained in resource bundle:
 * 	'/home/krka/priv/sthlmcommute/webservice/SthlmCommute/src/se/krka/sthlmcommute/web/client/ClientConstants.properties'.
 */
public interface ClientConstants extends com.google.gwt.i18n.client.Constants {
  
  /**
   * Translated "Välj pristyp".
   * 
   * @return translated "Välj pristyp"
   */
  @DefaultStringValue("Välj pristyp")
  @Key("choosePriceCategory")
  String choosePriceCategory();

  /**
   * Translated "Betalar du helt eller reducerat pris? Besök SL:s hemsida om du är osäker på om du har rätt till reducerat pris.".
   * 
   * @return translated "Betalar du helt eller reducerat pris? Besök SL:s hemsida om du är osäker på om du har rätt till reducerat pris."
   */
  @DefaultStringValue("Betalar du helt eller reducerat pris? Besök SL:s hemsida om du är osäker på om du har rätt till reducerat pris.")
  @Key("choosePriceCategoryHelp")
  String choosePriceCategoryHelp();

  /**
   * Translated "Dagar".
   * 
   * @return translated "Dagar"
   */
  @DefaultStringValue("Dagar")
  @Key("days")
  String days();

  /**
   * Translated "Helt pris".
   * 
   * @return translated "Helt pris"
   */
  @DefaultStringValue("Helt pris")
  @Key("fullprice")
  String fullprice();

  /**
   * Translated "Reducerat pris".
   * 
   * @return translated "Reducerat pris"
   */
  @DefaultStringValue("Reducerat pris")
  @Key("reducedprice")
  String reducedprice();

  /**
   * Translated "När".
   * 
   * @return translated "När"
   */
  @DefaultStringValue("När")
  @Key("when")
  String when();
}
