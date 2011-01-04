package se.krka.sthlmcommute.web.client.persistance;

import com.google.gwt.user.client.Cookies;
import se.krka.sthlmcommute.web.client.PriceCategories;

public class PriceCategoryClientPersistor implements ClientPersistor {
    public static final String PRICE_CATEGORY = "priceCategory";
    private final PriceCategories priceCategories;

    public PriceCategoryClientPersistor(PriceCategories priceCategories) {
        this.priceCategories = priceCategories;
    }

    @Override
    public void onExit() {
        Cookies.setCookie(PRICE_CATEGORY, priceCategories.getSelected());
    }

    @Override
    public void onLoad() {
        String priceCategory = Cookies.getCookie(PRICE_CATEGORY);
        priceCategories.getRadioGroup().setSelected(priceCategory);
    }
}
