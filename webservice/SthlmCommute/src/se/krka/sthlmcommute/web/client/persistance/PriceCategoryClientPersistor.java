package se.krka.sthlmcommute.web.client.persistance;

import com.google.gwt.user.client.Cookies;
import se.krka.sthlmcommute.web.client.PriceCategories;

public class PriceCategoryClientPersistor implements ClientPersistor {
    private final PriceCategories priceCategories;

    public PriceCategoryClientPersistor(PriceCategories priceCategories) {
        this.priceCategories = priceCategories;
    }

    @Override
    public void onExit() {
        Cookies.setCookie("priceCategory", priceCategories.getSelected());
    }

    @Override
    public void onLoad() {
        String priceCategory = Cookies.getCookie("priceCategory");
        priceCategories.getRadioGroup().setSelected(priceCategory);
    }
}
