package se.krka.sthlmcommute.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import se.krka.travelopt.*;
import se.krka.travelopt.localization.Locales;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SthlmCommute implements EntryPoint {
    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final String localeName = LocaleInfo.getCurrentLocale().getLocaleName();
        TravelOptLocale travelOptLocale = Locales.getLocale(localeName);
        ClientConstants clientConstants = GWT.create(ClientConstants.class);

        RootPanel.get("locales").add(getLocaleLinks(localeName));

        TravelInterface travelinterface = new TravelInterface(travelOptLocale, clientConstants);

        travelinterface.setup();
    }
    private final String[] locales = new String[]{"sv", "en"};

    private HTML getLocaleLinks(String localeName) {
        String href = Window.Location.getHref().replaceAll("locale=[a-zA-Z_]+", "");
        href = href.replaceAll("#.*", "");

        String append;
        if (href.endsWith("?") || href.endsWith("&")) {
            append = "locale=";
        } else {
            append = href.contains("?") ? "&locale=" : "?locale=";
        }

        String content = "";
        for (String code : locales) {
            String name = LocaleInfo.getLocaleNativeDisplayName(code);

            if (name == null) {
                name = "svenska";
            }

            if (content.length() > 0) {
                content += " ";
            }

            String linkName = "[" + name + "]";
            if (!localeName.equals(code)) {
                String link = href + append + code;
                content += "<a href=\"" + link + "\">" + linkName + "</a>";
            } else {
                content += linkName;
            }
        }
        return new HTML(content);
    }

}
