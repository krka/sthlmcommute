package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.NoSelectionModel;
import se.krka.sthlmcommute.web.client.async.AsyncWidget;
import se.krka.sthlmcommute.web.client.async.AsyncWidgetLoader;
import se.krka.sthlmcommute.web.client.async.AsyncWidgetUsage;
import se.krka.sthlmcommute.web.client.components.RadioGroup;
import se.krka.travelopt.Prices;
import se.krka.travelopt.TicketType;
import se.krka.travelopt.localization.TravelOptLocale;

public class PriceCategories extends Composite {
    private final RadioGroup radioGroup;

    private RadioGroup.Button lastSelected;
    private final AsyncWidget<CellTable<TicketType>> asyncWidget;

    public PriceCategories(ClientConstants clientConstants, final DelayedWork delayedWork, final TravelOptLocale locale) {

        asyncWidget = new AsyncWidget<CellTable<TicketType>>(new AsyncWidgetLoader<CellTable<TicketType>>() {
            @Override
            public CellTable<TicketType> load() {
                CellTable<TicketType> cellTable = new CellTable<TicketType>();
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
                return cellTable;
            }
        });


        radioGroup = new RadioGroup("priceCategories");
        radioGroup.addRadioButton("full", clientConstants.fullPrice());
        radioGroup.addRadioButton("reduced", clientConstants.reducedPrice());
        radioGroup.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                asyncWidget.runASAP(new AsyncWidgetUsage<CellTable<TicketType>>() {
                    @Override
                    public void run(CellTable<TicketType> widget) {
                        widget.setRowData(Prices.getPriceCategory(getSelected(), locale).getTicketTypes());
                    }
                });

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
        return UIUtil.wrapDisclosure("Ticket price list", asyncWidget);
    }

    public String getSelected() {
        RadioGroup.Button button = radioGroup.getSelected();
        if (button == null) {
            return null;
        }
        return button.getFormValue();
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }
}
