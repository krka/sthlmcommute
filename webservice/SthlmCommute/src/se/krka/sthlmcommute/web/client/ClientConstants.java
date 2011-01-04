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
   * Translated "Skapa ett schema".
   * 
   * @return translated "Skapa ett schema"
   */
  @DefaultStringValue("Skapa ett schema")
  @Key("creatingSchedule")
  String creatingSchedule();

  /**
   * Translated "Skapa ett schema över dina planerade resor. Ett schema består av en eller flera poster. Varje post består av ett datumspann och antal kuponger som behöver användas per dag. Börja med att skapa den första posten.".
   * 
   * @return translated "Skapa ett schema över dina planerade resor. Ett schema består av en eller flera poster. Varje post består av ett datumspann och antal kuponger som behöver användas per dag. Börja med att skapa den första posten."
   */
  @DefaultStringValue("Skapa ett schema över dina planerade resor. Ett schema består av en eller flera poster. Varje post består av ett datumspann och antal kuponger som behöver användas per dag. Börja med att skapa den första posten.")
  @Key("creatingScheduleHelp")
  String creatingScheduleHelp();

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
   * Translated "Välja kuponger".
   * 
   * @return translated "Välja kuponger"
   */
  @DefaultStringValue("Välja kuponger")
  @Key("selectingCoupons")
  String selectingCoupons();

  /**
   * Translated "Fyll i hur många kuponger det skulle ha gått åt att resa under en hel dag. Antal kuponger du ska fylla i beror både på antal resor som göra och hur många zoner som passeras.<br><br>Du kan fylla i antalet kuponger per dag för hela veckan och sedan göra finjusteringar för enskilda veckodagar.<br><br>Se SL:s hemsida för mer information om zonsystemet och hur många kuponger olika resor kostar.<br><br>Exempel: Jag bor i zon B och jobbar i zon A. Då krävs tre kuponger per resa. Jag gör två resor per dag och behöver alltså sex kuponger per dag. Detta gäller dock bara måndag till fredag så jag väljer 0 för lördag och söndag.".
   * 
   * @return translated "Fyll i hur många kuponger det skulle ha gått åt att resa under en hel dag. Antal kuponger du ska fylla i beror både på antal resor som göra och hur många zoner som passeras.<br><br>Du kan fylla i antalet kuponger per dag för hela veckan och sedan göra finjusteringar för enskilda veckodagar.<br><br>Se SL:s hemsida för mer information om zonsystemet och hur många kuponger olika resor kostar.<br><br>Exempel: Jag bor i zon B och jobbar i zon A. Då krävs tre kuponger per resa. Jag gör två resor per dag och behöver alltså sex kuponger per dag. Detta gäller dock bara måndag till fredag så jag väljer 0 för lördag och söndag."
   */
  @DefaultStringValue("Fyll i hur många kuponger det skulle ha gått åt att resa under en hel dag. Antal kuponger du ska fylla i beror både på antal resor som göra och hur många zoner som passeras.<br><br>Du kan fylla i antalet kuponger per dag för hela veckan och sedan göra finjusteringar för enskilda veckodagar.<br><br>Se SL:s hemsida för mer information om zonsystemet och hur många kuponger olika resor kostar.<br><br>Exempel: Jag bor i zon B och jobbar i zon A. Då krävs tre kuponger per resa. Jag gör två resor per dag och behöver alltså sex kuponger per dag. Detta gäller dock bara måndag till fredag så jag väljer 0 för lördag och söndag.")
  @Key("selectingCouponsHelp")
  String selectingCouponsHelp();

  /**
   * Translated "Välja datumspann".
   * 
   * @return translated "Välja datumspann"
   */
  @DefaultStringValue("Välja datumspann")
  @Key("selectingDateSpan")
  String selectingDateSpan();

  /**
   * Translated "Du måste välja ett startdatum från den vänstra panelen och ett slutdatum från den högra. Det går att välja samma datum för både start och slut för en endags-post.".
   * 
   * @return translated "Du måste välja ett startdatum från den vänstra panelen och ett slutdatum från den högra. Det går att välja samma datum för både start och slut för en endags-post."
   */
  @DefaultStringValue("Du måste välja ett startdatum från den vänstra panelen och ett slutdatum från den högra. Det går att välja samma datum för både start och slut för en endags-post.")
  @Key("selectingDateSpanHelp")
  String selectingDateSpanHelp();

  /**
   * Translated "När".
   * 
   * @return translated "När"
   */
  @DefaultStringValue("När")
  @Key("when")
  String when();
}
