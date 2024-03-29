package se.krka.sthlmcommute.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import se.krka.sthlmcommute.web.client.persistance.ClientPersistance;
import se.krka.travelopt.Util;
import se.krka.travelopt.localization.Locales;
import se.krka.travelopt.localization.TravelOptLocale;

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
        ClientMessages clientMessages = GWT.create(ClientMessages.class);

        setupHTMLTexts(clientConstants, clientMessages);

        RootPanel.get("locales").add(getLocaleLinks(localeName));

        final ClientPersistance persistance = new ClientPersistance();

        TravelInterface travelinterface = new TravelInterface(travelOptLocale, clientConstants, persistance, clientMessages);

        travelinterface.addComponents();

        Window.addCloseHandler(new CloseHandler<Window>() {
            @Override
            public void onClose(CloseEvent<Window> windowCloseEvent) {
                persistance.onExit();
            }
        });
    }

    private void setupHTMLTexts(ClientConstants clientConstants, ClientMessages clientMessages) {
        setElement("help", clientConstants.help());
        setElement("contact", clientConstants.contact());
        setElement("sendEmailTo", clientMessages.sendEmailTo("kristofer.karlsson@gmail.com"));
        RootPanel.get("introtext").add(new HTML(SafeHtmlUtils.fromTrustedString(clientConstants.introText())));
    }

    private void setElement(String element, String value) {
        RootPanel.get(element).add(new HTML(SafeHtmlUtils.htmlEscape(value)));
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
