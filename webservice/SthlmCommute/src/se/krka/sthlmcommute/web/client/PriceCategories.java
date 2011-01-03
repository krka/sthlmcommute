package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.NoSelectionModel;
import se.krka.travelopt.Prices;
import se.krka.travelopt.TicketType;
import se.krka.travelopt.localization.TravelOptLocale;

public class PriceCategories extends Composite {
    private final CellTable<TicketType> cellTable;
    private final RadioGroup radioGroup;

    private RadioGroup.Button lastSelected;
    private HelpSection helpSection;

    public PriceCategories(final TravelInterface travelInterface, ClientConstants clientConstants, final DelayedWork delayedWork, final TravelOptLocale locale) {

        cellTable = new CellTable<TicketType>();
        cellTable.setSelectionModel(new NoSelectionModel<Object>());
        cellTable.addColumn(new TextColumn<TicketType>() {
            @Override
            public String getValue(TicketType ticketType) {
                return ticketType.name();
            }
        }, "Name");
        cellTable.addColumn(new TextColumn<TicketType>() {
            @Override
            public String getValue(TicketType ticketType) {
                return ticketType.description();
            }
        }, "Description");

        radioGroup = new RadioGroup("priceCategories");
        radioGroup.addRadioButton("full", clientConstants.fullPrice());
        radioGroup.addRadioButton("reduced", clientConstants.reducedPrice());
        radioGroup.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                cellTable.setRowData(Prices.getPriceCategory(getSelected(), locale).getTicketTypes());
                travelInterface.doneWithPriceCategory();
                if (helpSection != null) {
                    helpSection.selectedPriceCategory();
                }

                RadioGroup.Button newSelected = radioGroup.getSelected();
                if (lastSelected != newSelected) {
                    delayedWork.requestWork();
                    lastSelected = newSelected;
                }
            }
        });

        HorizontalPanel panel = new HorizontalPanel();
        panel.setWidth("100%");
        panel.add(radioGroup);
        panel.setHorizontalAlignment(HasHorizontalAlignment.HorizontalAlignmentConstant.endOf(HasDirection.Direction.DEFAULT));
        DisclosurePanel disclosurePanel = createPricelist();
        disclosurePanel.setWidth("40em");
        panel.add(disclosurePanel);

        initWidget(UIUtil.wrapCaption(clientConstants.priceCategories(), panel));
    }

    private DisclosurePanel createPricelist() {
        return UIUtil.wrapDisclosure("Ticket price list", cellTable);
    }

    private RadioButton createButton(String name) {
        final RadioButton button = new RadioButton("priceCategories", name);
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
            }
        });
        return button;
    }

    public String getSelected() {
        RadioGroup.Button button = radioGroup.getSelected();
        if (button == null) {
            return null;
        }
        return button.getFormValue();
    }

    public void addListener(HelpSection helpSection) {
        this.helpSection = helpSection;
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }
}
