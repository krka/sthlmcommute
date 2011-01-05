package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.*;
import se.krka.sthlmcommute.web.client.components.CouponListBox;
import se.krka.sthlmcommute.web.client.util.UIUtil;
import se.krka.travelopt.localization.TravelOptLocale;

public class WeekdayEditor extends Composite {
    private final Grid weekDayForm;
    private final UpdateListener listener;
    private final TravelOptLocale locale;

    public WeekdayEditor(UpdateListener listener, TravelOptLocale locale, ClientConstants clientConstants) {
        this.listener = listener;
        this.locale = locale;
        weekDayForm = createWeekDayForm();

        initWidget(UIUtil.wrapVertical(new Label(clientConstants.exceptions()), weekDayForm));
    }

    private Grid createWeekDayForm() {
        Grid grid = new Grid(2, 7);
        for (int day = 0; day < 7; day++) {
            int index = getIndexForWeekday(day);
            grid.setWidget(0, index, new Label(locale.weekDayName(day).substring(0, 3)));
            final CouponListBox listBox = new CouponListBox(true);
            listBox.addChangeHandler(new ChangeHandler() {
                @Override
                public void onChange(ChangeEvent changeEvent) {
                    listener.updated();
                }
            });

            grid.setWidget(1, index, listBox);
        }
        return grid;

    }

    private int getIndexForWeekday(int day) {
        return (7 + day - locale.firstDayOfWeek()) % 7;
    }

    public int getWeekday(int day) {
        int index = getIndexForWeekday(day);
        CouponListBox listBox = (CouponListBox) weekDayForm.getWidget(1, index);
        return listBox.getSelectedCoupon();
    }

    public void setWeekDay(int day, int value) {
        int index = getIndexForWeekday(day);
        CouponListBox listBox = (CouponListBox) weekDayForm.getWidget(1, index);
        listBox.setSelectedCoupon(value);
    }

    public int[] getCoupons() {
        int[] res = new int[7];
        for (int i = 0; i < 7; i++) {
            res[i] = getWeekday(i);
        }
        return res;
    }

    public void setWeekDays(int[] coupons) {
        for (int i = 0; i < 7; i++) {
            setWeekDay(i, coupons[i]);
        }
    }
}
