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
   * Translated "Betalar du helt eller reducerat pris?<br><br>Besök SL:s hemsida om du är osäker på om du har rätt till reducerat pris.".
   * 
   * @return translated "Betalar du helt eller reducerat pris?<br><br>Besök SL:s hemsida om du är osäker på om du har rätt till reducerat pris."
   */
  @DefaultStringValue("Betalar du helt eller reducerat pris?<br><br>Besök SL:s hemsida om du är osäker på om du har rätt till reducerat pris.")
  @Key("choosePriceCategoryHelp")
  String choosePriceCategoryHelp();

  /**
   * Translated "Kontakta utvecklaren".
   * 
   * @return translated "Kontakta utvecklaren"
   */
  @DefaultStringValue("Kontakta utvecklaren")
  @Key("contact")
  String contact();

  /**
   * Translated "Kuponger".
   * 
   * @return translated "Kuponger"
   */
  @DefaultStringValue("Kuponger")
  @Key("coupons")
  String coupons();

  /**
   * Translated "Antal kuponger per dag".
   * 
   * @return translated "Antal kuponger per dag"
   */
  @DefaultStringValue("Antal kuponger per dag")
  @Key("couponsPerDay")
  String couponsPerDay();

  /**
   * Translated "Skapa ett schema".
   * 
   * @return translated "Skapa ett schema"
   */
  @DefaultStringValue("Skapa ett schema")
  @Key("creatingSchedule")
  String creatingSchedule();

  /**
   * Translated "Skapa ett schema över dina planerade resor.<br><br>Ett schema består av en eller flera poster och varje post består av ett datumspann och antal kuponger som behöver användas per dag.<br><br>Börja med att skapa den första posten.".
   * 
   * @return translated "Skapa ett schema över dina planerade resor.<br><br>Ett schema består av en eller flera poster och varje post består av ett datumspann och antal kuponger som behöver användas per dag.<br><br>Börja med att skapa den första posten."
   */
  @DefaultStringValue("Skapa ett schema över dina planerade resor.<br><br>Ett schema består av en eller flera poster och varje post består av ett datumspann och antal kuponger som behöver användas per dag.<br><br>Börja med att skapa den första posten.")
  @Key("creatingScheduleHelp")
  String creatingScheduleHelp();

  /**
   * Translated "Ta bort".
   * 
   * @return translated "Ta bort"
   */
  @DefaultStringValue("Ta bort")
  @Key("delete")
  String delete();

  /**
   * Translated "Beskrivning".
   * 
   * @return translated "Beskrivning"
   */
  @DefaultStringValue("Beskrivning")
  @Key("description")
  String description();

  /**
   * Translated "Schemat innehöll inga dagar som kräver kuponger".
   * 
   * @return translated "Schemat innehöll inga dagar som kräver kuponger"
   */
  @DefaultStringValue("Schemat innehöll inga dagar som kräver kuponger")
  @Key("emptySchedule")
  String emptySchedule();

  /**
   * Translated "Poster".
   * 
   * @return translated "Poster"
   */
  @DefaultStringValue("Poster")
  @Key("entries")
  String entries();

  /**
   * Translated "Undantag".
   * 
   * @return translated "Undantag"
   */
  @DefaultStringValue("Undantag")
  @Key("exceptions")
  String exceptions();

  /**
   * Translated "Från".
   * 
   * @return translated "Från"
   */
  @DefaultStringValue("Från")
  @Key("from")
  String from();

  /**
   * Translated "Helt pris".
   * 
   * @return translated "Helt pris"
   */
  @DefaultStringValue("Helt pris")
  @Key("fullprice")
  String fullprice();

  /**
   * Translated "Hjälp".
   * 
   * @return translated "Hjälp"
   */
  @DefaultStringValue("Hjälp")
  @Key("help")
  String help();

  /**
   * Translated "Kuponger saknas".
   * 
   * @return translated "Kuponger saknas"
   */
  @DefaultStringValue("Kuponger saknas")
  @Key("incompleteCoupons")
  String incompleteCoupons();

  /**
   * Translated "Datum saknas".
   * 
   * @return translated "Datum saknas"
   */
  @DefaultStringValue("Datum saknas")
  @Key("incompleteDateSpan")
  String incompleteDateSpan();

  /**
   * Translated "STHLM commute är programmet för dig som regelbundet reser med SL och vill hitta den billigaste kombinationen av biljetter.".
   * 
   * @return translated "STHLM commute är programmet för dig som regelbundet reser med SL och vill hitta den billigaste kombinationen av biljetter."
   */
  @DefaultStringValue("STHLM commute är programmet för dig som regelbundet reser med SL och vill hitta den billigaste kombinationen av biljetter.")
  @Key("introText")
  String introText();

  /**
   * Translated "Det finns fortfarande ofullständiga poster.".
   * 
   * @return translated "Det finns fortfarande ofullständiga poster."
   */
  @DefaultStringValue("Det finns fortfarande ofullständiga poster.")
  @Key("invalidEntries")
  String invalidEntries();

  /**
   * Translated "Namn".
   * 
   * @return translated "Namn"
   */
  @DefaultStringValue("Namn")
  @Key("name")
  String name();

  /**
   * Translated "Ny post".
   * 
   * @return translated "Ny post"
   */
  @DefaultStringValue("Ny post")
  @Key("newEntry")
  String newEntry();

  /**
   * Translated "Det finns inga poster än.".
   * 
   * @return translated "Det finns inga poster än."
   */
  @DefaultStringValue("Det finns inga poster än.")
  @Key("noEntries")
  String noEntries();

  /**
   * Translated "Antal".
   * 
   * @return translated "Antal"
   */
  @DefaultStringValue("Antal")
  @Key("numberOfTickets")
  String numberOfTickets();

  /**
   * Translated "Optimering".
   * 
   * @return translated "Optimering"
   */
  @DefaultStringValue("Optimering")
  @Key("optimizeOptions")
  String optimizeOptions();

  /**
   * Translated "Optimera för schemalagda dagar samt närmast följande dagar".
   * 
   * @return translated "Optimera för schemalagda dagar samt närmast följande dagar"
   */
  @DefaultStringValue("Optimera för schemalagda dagar samt närmast följande dagar")
  @Key("optimizeOptions.option.extended")
  String optimizeOptions_option_extended();

  /**
   * Translated "Optimera för schemalagda dagar".
   * 
   * @return translated "Optimera för schemalagda dagar"
   */
  @DefaultStringValue("Optimera för schemalagda dagar")
  @Key("optimizeOptions.option.onlySchedule")
  String optimizeOptions_option_onlySchedule();

  /**
   * Translated "Det finns två sätt att räkna ut den billigaste reskostnaden.<br><br>Antingen så tar man enbart hänsyn till just de dagar man fyllt in i schemat, vilket är det vanligaste fallet om man vet när man kommer ta ett längre reseuppehåll, till exempel under semesterledigheten.<br><br>Det andra sättet är att även ta hänsyn till att man kommer vilja fortsätta resa även efter schemats slut. Ett typiskt exempel är att man vet att man kommer fortsätta resa till jobbet måndag till fredag, men inte exakt när nästa semester är.<br>Med detta alternativ valt kommer programmet att ta hänsyn både till dagarna i schemat samt kommande dagar enligt den givna kuponginställningen.".
   * 
   * @return translated "Det finns två sätt att räkna ut den billigaste reskostnaden.<br><br>Antingen så tar man enbart hänsyn till just de dagar man fyllt in i schemat, vilket är det vanligaste fallet om man vet när man kommer ta ett längre reseuppehåll, till exempel under semesterledigheten.<br><br>Det andra sättet är att även ta hänsyn till att man kommer vilja fortsätta resa även efter schemats slut. Ett typiskt exempel är att man vet att man kommer fortsätta resa till jobbet måndag till fredag, men inte exakt när nästa semester är.<br>Med detta alternativ valt kommer programmet att ta hänsyn både till dagarna i schemat samt kommande dagar enligt den givna kuponginställningen."
   */
  @DefaultStringValue("Det finns två sätt att räkna ut den billigaste reskostnaden.<br><br>Antingen så tar man enbart hänsyn till just de dagar man fyllt in i schemat, vilket är det vanligaste fallet om man vet när man kommer ta ett längre reseuppehåll, till exempel under semesterledigheten.<br><br>Det andra sättet är att även ta hänsyn till att man kommer vilja fortsätta resa även efter schemats slut. Ett typiskt exempel är att man vet att man kommer fortsätta resa till jobbet måndag till fredag, men inte exakt när nästa semester är.<br>Med detta alternativ valt kommer programmet att ta hänsyn både till dagarna i schemat samt kommande dagar enligt den givna kuponginställningen.")
  @Key("optimizeOptionsHelp")
  String optimizeOptionsHelp();

  /**
   * Translated "Optimeringsinställningar".
   * 
   * @return translated "Optimeringsinställningar"
   */
  @DefaultStringValue("Optimeringsinställningar")
  @Key("optimizeSettings")
  String optimizeSettings();

  /**
   * Translated "Pris".
   * 
   * @return translated "Pris"
   */
  @DefaultStringValue("Pris")
  @Key("price")
  String price();

  /**
   * Translated "Reducerat pris".
   * 
   * @return translated "Reducerat pris"
   */
  @DefaultStringValue("Reducerat pris")
  @Key("reducedprice")
  String reducedprice();

  /**
   * Translated "Resultat".
   * 
   * @return translated "Resultat"
   */
  @DefaultStringValue("Resultat")
  @Key("result")
  String result();

  /**
   * Translated "Resultatvyn".
   * 
   * @return translated "Resultatvyn"
   */
  @DefaultStringValue("Resultatvyn")
  @Key("resultView")
  String resultView();

  /**
   * Translated "Resultatvyn är ett förslag på biljetter du behöver köpa för att kunna resa enligt schemat. Den är vald så att det ska bli så billigt som möjligt att resa.<br><br>Du kan utläsa vilket datum biljetten ska börja gälla och vilken dag den tar slut.<br>Du ser även hur mycket biljetten kostar.<br>Om du behöver köpa fler än en biljett så visas även en summering av kostnaden.".
   * 
   * @return translated "Resultatvyn är ett förslag på biljetter du behöver köpa för att kunna resa enligt schemat. Den är vald så att det ska bli så billigt som möjligt att resa.<br><br>Du kan utläsa vilket datum biljetten ska börja gälla och vilken dag den tar slut.<br>Du ser även hur mycket biljetten kostar.<br>Om du behöver köpa fler än en biljett så visas även en summering av kostnaden."
   */
  @DefaultStringValue("Resultatvyn är ett förslag på biljetter du behöver köpa för att kunna resa enligt schemat. Den är vald så att det ska bli så billigt som möjligt att resa.<br><br>Du kan utläsa vilket datum biljetten ska börja gälla och vilken dag den tar slut.<br>Du ser även hur mycket biljetten kostar.<br>Om du behöver köpa fler än en biljett så visas även en summering av kostnaden.")
  @Key("resultViewHelp")
  String resultViewHelp();

  /**
   * Translated "Schema".
   * 
   * @return translated "Schema"
   */
  @DefaultStringValue("Schema")
  @Key("schedule")
  String schedule();

  /**
   * Translated "Välja kuponger".
   * 
   * @return translated "Välja kuponger"
   */
  @DefaultStringValue("Välja kuponger")
  @Key("selectingCoupons")
  String selectingCoupons();

  /**
   * Translated "Fyll i hur många kuponger som behövs för att resa under en hel dag. Antal kuponger du ska fylla i beror både på antal resor som göra och hur många zoner som passeras.<br><br>Du kan fylla i antalet kuponger per dag för hela veckan och sedan göra finjusteringar för enskilda veckodagar.<br><br>Se SL:s hemsida för mer information om zonsystemet och hur många kuponger olika resor kostar.<br><br><b>Exempel:</b> Du bor i zon B och jobbar i zon A. Då krävs <b>3</b> kuponger per resa. Du gör <b>2</b> resor per dag och behöver alltså <b>6</b> kuponger per dag. Detta gäller dock bara måndag till fredag så du väljer <b>0</b> för lördag och söndag.".
   * 
   * @return translated "Fyll i hur många kuponger som behövs för att resa under en hel dag. Antal kuponger du ska fylla i beror både på antal resor som göra och hur många zoner som passeras.<br><br>Du kan fylla i antalet kuponger per dag för hela veckan och sedan göra finjusteringar för enskilda veckodagar.<br><br>Se SL:s hemsida för mer information om zonsystemet och hur många kuponger olika resor kostar.<br><br><b>Exempel:</b> Du bor i zon B och jobbar i zon A. Då krävs <b>3</b> kuponger per resa. Du gör <b>2</b> resor per dag och behöver alltså <b>6</b> kuponger per dag. Detta gäller dock bara måndag till fredag så du väljer <b>0</b> för lördag och söndag."
   */
  @DefaultStringValue("Fyll i hur många kuponger som behövs för att resa under en hel dag. Antal kuponger du ska fylla i beror både på antal resor som göra och hur många zoner som passeras.<br><br>Du kan fylla i antalet kuponger per dag för hela veckan och sedan göra finjusteringar för enskilda veckodagar.<br><br>Se SL:s hemsida för mer information om zonsystemet och hur många kuponger olika resor kostar.<br><br><b>Exempel:</b> Du bor i zon B och jobbar i zon A. Då krävs <b>3</b> kuponger per resa. Du gör <b>2</b> resor per dag och behöver alltså <b>6</b> kuponger per dag. Detta gäller dock bara måndag till fredag så du väljer <b>0</b> för lördag och söndag.")
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
   * Translated "Du måste välja ett startdatum från den vänstra panelen och ett slutdatum från den högra.".
   * 
   * @return translated "Du måste välja ett startdatum från den vänstra panelen och ett slutdatum från den högra."
   */
  @DefaultStringValue("Du måste välja ett startdatum från den vänstra panelen och ett slutdatum från den högra.")
  @Key("selectingDateSpanHelp")
  String selectingDateSpanHelp();

  /**
   * Translated "Prislista för biljetter".
   * 
   * @return translated "Prislista för biljetter"
   */
  @DefaultStringValue("Prislista för biljetter")
  @Key("ticketPriceList")
  String ticketPriceList();

  /**
   * Translated "Biljett".
   * 
   * @return translated "Biljett"
   */
  @DefaultStringValue("Biljett")
  @Key("tickettype")
  String tickettype();

  /**
   * Translated "Till".
   * 
   * @return translated "Till"
   */
  @DefaultStringValue("Till")
  @Key("to")
  String to();

  /**
   * Translated "Summa".
   * 
   * @return translated "Summa"
   */
  @DefaultStringValue("Summa")
  @Key("total")
  String total();
}
