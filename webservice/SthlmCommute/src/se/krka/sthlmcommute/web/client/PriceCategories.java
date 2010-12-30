package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class PriceCategories extends Composite {
    private final TravelInterface travelInterface;
    private final HelpInfo helpInfo;

    public PriceCategories(TravelInterface travelInterface, ClientConstants clientConstants) {
        this.travelInterface = travelInterface;
        CaptionPanel root = new CaptionPanel(clientConstants.priceCategories());
        VerticalPanel panel = new VerticalPanel();
        root.add(panel);
        helpInfo = new HelpInfo("Are you paying full price or do you get a discount for being young or being a senior citizen?");
        panel.add(helpInfo);
        panel.add(createButton(clientConstants.fullPrice()));
        panel.add(createButton(clientConstants.reducedPrice()));
        initWidget(root);
    }

    private RadioButton createButton(String name) {
        RadioButton button = new RadioButton("priceCategories", name);
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                helpInfo.setVisible(false);
                travelInterface.doneWithPriceCategory();
            }
        });
        return button;
    }
}