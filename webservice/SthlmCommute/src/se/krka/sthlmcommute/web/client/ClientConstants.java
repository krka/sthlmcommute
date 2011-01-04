package se.krka.sthlmcommute.web.client;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.Header;

/**
 * Interface to represent the constants contained in resource bundle:
 * '/home/krka/dev/sthlmcommute/webservice/SthlmCommute/src/se/krka/sthlmcommute/web/client/ClientConstants.properties'.
 */
public interface ClientConstants extends com.google.gwt.i18n.client.Constants {

    @Key("days")
    String days();

    @Key("fullprice")
    String fullPrice();

    @Key("reducedprice")
    String reducedPrice();

    @Key("when")
    String when();

    @Key("choosePriceCategory")
    String choosePriceCategories();

    String choosePriceCategoriesHelp();
}
