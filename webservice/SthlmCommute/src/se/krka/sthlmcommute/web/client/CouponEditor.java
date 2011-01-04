package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.*;
import se.krka.sthlmcommute.web.client.components.CouponListBox;
import se.krka.sthlmcommute.web.client.util.UIUtil;
import se.krka.travelopt.localization.TravelOptLocale;

public class CouponEditor extends Composite {

    private final CouponListBox coupon;
    private final WeekdayEditor weekdays;

    public CouponEditor(final UpdateListener listener, TravelOptLocale locale, ClientConstants clientConstants) {
        coupon = new CouponListBox(false);

        weekdays = new WeekdayEditor(listener, locale, clientConstants);

        Panel root = new VerticalPanel();

        root.add(new Label(clientConstants.couponsPerDay()));
        root.add(coupon);
        root.add(UIUtil.spacer("1px", "1em"));
        root.add(weekdays);
        initWidget(UIUtil.wrapCaption(clientConstants.coupons(), root));

        coupon.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent changeEvent) {
                listener.updated();
            }
        });
    }

    public Weekdays getWeekdays() {
        return new Weekdays(coupon.getSelectedCoupon(), weekdays.getCoupons());
    }

    public WeekdayEditor getWeekdayEditor() {
        return weekdays;
    }

    public CouponListBox getCoupon() {
        return coupon;
    }

    public String serialize() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(coupon.serialize());
        for (int i = 0; i < 7; i++) {
            stringBuilder.append(",");
            stringBuilder.append(weekdays.getWeekday(i));
        }
        return stringBuilder.toString();
    }

    public void deserialize(String data) {
        String[] values = data.split(",");
        coupon.deserialize(values[0]);
        for (int i = 0; i < 7; i++) {
            weekdays.setWeekDay(i, Integer.parseInt(values[i + 1]));
        }
    }
}
