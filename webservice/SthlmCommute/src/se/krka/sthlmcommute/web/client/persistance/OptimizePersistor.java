package se.krka.sthlmcommute.web.client.persistance;

import com.google.gwt.user.client.Cookies;
import se.krka.sthlmcommute.web.client.OptimizeOptions;

public class OptimizePersistor implements ClientPersistor {
    public static final String OPTIMIZE_COUPONS = "optimizeCoupons";
    public static final String OPTIMIZE_OPTIONS = "optimizeOptions";
    private final OptimizeOptions optimizeOptions;

    public OptimizePersistor(OptimizeOptions optimizeOptions) {
        this.optimizeOptions = optimizeOptions;
    }

    @Override
    public void onExit() {
        Cookies.setCookie(OPTIMIZE_OPTIONS, String.valueOf(optimizeOptions.getRadioGroup().getSelected().getFormValue()));
        Cookies.setCookie(OPTIMIZE_COUPONS, optimizeOptions.getCouponEditor().serialize());
    }

    @Override
    public void onLoad() {
        String option = Cookies.getCookie(OPTIMIZE_OPTIONS);
        if (option != null) {
            optimizeOptions.getRadioGroup().setSelected(option);
        }
        String optimizeCoupons = Cookies.getCookie(OPTIMIZE_COUPONS);
        if (optimizeCoupons != null) {
            optimizeOptions.getCouponEditor().deserialize(optimizeCoupons);
        }
    }
}
