package se.krka.sthlmcommute.web.client;

/**
 * Interface to represent the constants contained in resource bundle:
 * 	'/home/krka/Dokument/unversioned/travelopt/webservice/SthlmCommute/src/se/krka/sthlmcommute/web/client/ClientConstants.properties'.
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
   * Translated "Betalar du helt eller reducerat pris?<br><br>Besök SL:s hemsida om du är osäker på om du har rätt till reducerat pris.".
   * 
   * @return translated "Betalar du helt eller reducerat pris?<br><br>Besök SL:s hemsida om du är osäker på om du har rätt till reducerat pris."
   */
  @DefaultStringValue("Betalar du helt eller reducerat pris?<br><br>Besök SL:s hemsida om du är osäker på om du har rätt till reducerat pris.")
  @Key("choosePriceCategoryHelp")
  String choosePriceCategoryHelp();

  /**
   * Translated "Skapa en planering".
   * 
   * @return translated "Skapa en planering"
   */
  @DefaultStringValue("Skapa en planering")
  @Key("creatingSchedule")
  String creatingSchedule();

  /**
   * Translated "Skapa en planering över dina planerade resor.<br><br>En planering består av en eller flera poster och varje post består av ett datumspann och antal kuponger som behöver användas per dag.<br><br>Börja med att skapa den första posten.".
   * 
   * @return translated "Skapa en planering över dina planerade resor.<br><br>En planering består av en eller flera poster och varje post består av ett datumspann och antal kuponger som behöver användas per dag.<br><br>Börja med att skapa den första posten."
   */
  @DefaultStringValue("Skapa en planering över dina planerade resor.<br><br>En planering består av en eller flera poster och varje post består av ett datumspann och antal kuponger som behöver användas per dag.<br><br>Börja med att skapa den första posten.")
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
   * Translated "Målsättningar".
   * 
   * @return translated "Målsättningar"
   */
  @DefaultStringValue("Målsättningar")
  @Key("optimizeOptions")
  String optimizeOptions();

  /**
   * Translated "Det finns två sätt att räkna ut den billigaste reskostnaden.<br><br>Antingen så tar man enbart hänsyn till just de dagar man fyllt in i planeringen, vilket är det vanligaste fallet om man vet när man kommer ta ett längre reseuppehåll, till exempel under semesterledigheten.<br><br>Det andra sättet är att även ta hänsyn till att man kommer vilja fortsätta resa även efter planeringen tagit slut. Ett typiskt exempel är att man vet att man kommer fortsätta resa till jobbet måndag till fredag, men inte exakt när nästa semester är.<br>Med detta alternativ valt kommer programmet att ta hänsyn både till dagarna i planeringen samt kommande dagar enligt den givna kuponginställningen.".
   * 
   * @return translated "Det finns två sätt att räkna ut den billigaste reskostnaden.<br><br>Antingen så tar man enbart hänsyn till just de dagar man fyllt in i planeringen, vilket är det vanligaste fallet om man vet när man kommer ta ett längre reseuppehåll, till exempel under semesterledigheten.<br><br>Det andra sättet är att även ta hänsyn till att man kommer vilja fortsätta resa även efter planeringen tagit slut. Ett typiskt exempel är att man vet att man kommer fortsätta resa till jobbet måndag till fredag, men inte exakt när nästa semester är.<br>Med detta alternativ valt kommer programmet att ta hänsyn både till dagarna i planeringen samt kommande dagar enligt den givna kuponginställningen."
   */
  @DefaultStringValue("Det finns två sätt att räkna ut den billigaste reskostnaden.<br><br>Antingen så tar man enbart hänsyn till just de dagar man fyllt in i planeringen, vilket är det vanligaste fallet om man vet när man kommer ta ett längre reseuppehåll, till exempel under semesterledigheten.<br><br>Det andra sättet är att även ta hänsyn till att man kommer vilja fortsätta resa även efter planeringen tagit slut. Ett typiskt exempel är att man vet att man kommer fortsätta resa till jobbet måndag till fredag, men inte exakt när nästa semester är.<br>Med detta alternativ valt kommer programmet att ta hänsyn både till dagarna i planeringen samt kommande dagar enligt den givna kuponginställningen.")
  @Key("optimizeOptionsHelp")
  String optimizeOptionsHelp();

  /**
   * Translated "Reducerat pris".
   * 
   * @return translated "Reducerat pris"
   */
  @DefaultStringValue("Reducerat pris")
  @Key("reducedprice")
  String reducedprice();

  /**
   * Translated "Resultatvyn".
   * 
   * @return translated "Resultatvyn"
   */
  @DefaultStringValue("Resultatvyn")
  @Key("resultView")
  String resultView();

  /**
   * Translated "Resultatvyn är ett förslag på biljetter du behöver köpa för att kunna resa enligt planeringen. Den är vald så att det ska bli så billigt som möjligt att resa.<br><br>Du kan utläsa vilket datum biljetten ska börja gälla och vilken dag den tar slut.<br>Du ser även hur mycket biljetten kostar.<br>Om du behöver köpa fler än en biljett så visas även en summering av kostnaden.".
   * 
   * @return translated "Resultatvyn är ett förslag på biljetter du behöver köpa för att kunna resa enligt planeringen. Den är vald så att det ska bli så billigt som möjligt att resa.<br><br>Du kan utläsa vilket datum biljetten ska börja gälla och vilken dag den tar slut.<br>Du ser även hur mycket biljetten kostar.<br>Om du behöver köpa fler än en biljett så visas även en summering av kostnaden."
   */
  @DefaultStringValue("Resultatvyn är ett förslag på biljetter du behöver köpa för att kunna resa enligt planeringen. Den är vald så att det ska bli så billigt som möjligt att resa.<br><br>Du kan utläsa vilket datum biljetten ska börja gälla och vilken dag den tar slut.<br>Du ser även hur mycket biljetten kostar.<br>Om du behöver köpa fler än en biljett så visas även en summering av kostnaden.")
  @Key("resultViewHelp")
  String resultViewHelp();

  /**
   * Translated "Välja kuponger".
   * 
   * @return translated "Välja kuponger"
   */
  @DefaultStringValue("Välja kuponger")
  @Key("selectingCoupons")
  String selectingCoupons();

  /**
   * Translated "Fyll i hur många kuponger det skulle ha gått åt att resa under en hel dag. Antal kuponger du ska fylla i beror både på antal resor som göra och hur många zoner som passeras.<br><br>Du kan fylla i antalet kuponger per dag för hela veckan och sedan göra finjusteringar för enskilda veckodagar.<br><br>Se SL:s hemsida för mer information om zonsystemet och hur många kuponger olika resor kostar.<br><br><b>Exempel:</b> Jag bor i zon B och jobbar i zon A. Då krävs <b>3</b> kuponger per resa. Jag gör <b>2</b> resor per dag och behöver alltså <b>6</b> kuponger per dag. Detta gäller dock bara måndag till fredag så jag väljer <b>0</b> för lördag och söndag.".
   * 
   * @return translated "Fyll i hur många kuponger det skulle ha gått åt att resa under en hel dag. Antal kuponger du ska fylla i beror både på antal resor som göra och hur många zoner som passeras.<br><br>Du kan fylla i antalet kuponger per dag för hela veckan och sedan göra finjusteringar för enskilda veckodagar.<br><br>Se SL:s hemsida för mer information om zonsystemet och hur många kuponger olika resor kostar.<br><br><b>Exempel:</b> Jag bor i zon B och jobbar i zon A. Då krävs <b>3</b> kuponger per resa. Jag gör <b>2</b> resor per dag och behöver alltså <b>6</b> kuponger per dag. Detta gäller dock bara måndag till fredag så jag väljer <b>0</b> för lördag och söndag."
   */
  @DefaultStringValue("Fyll i hur många kuponger det skulle ha gått åt att resa under en hel dag. Antal kuponger du ska fylla i beror både på antal resor som göra och hur många zoner som passeras.<br><br>Du kan fylla i antalet kuponger per dag för hela veckan och sedan göra finjusteringar för enskilda veckodagar.<br><br>Se SL:s hemsida för mer information om zonsystemet och hur många kuponger olika resor kostar.<br><br><b>Exempel:</b> Jag bor i zon B och jobbar i zon A. Då krävs <b>3</b> kuponger per resa. Jag gör <b>2</b> resor per dag och behöver alltså <b>6</b> kuponger per dag. Detta gäller dock bara måndag till fredag så jag väljer <b>0</b> för lördag och söndag.")
  @Key("selectingCouponsHelp")
  String selectingCouponsHelp();

  /**
   * Translated "Välj datumspann".
   * 
   * @return translated "Välj datumspann"
   */
  @DefaultStringValue("Välj datumspann")
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
