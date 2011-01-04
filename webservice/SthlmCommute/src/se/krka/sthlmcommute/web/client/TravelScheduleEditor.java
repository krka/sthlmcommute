package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import se.krka.sthlmcommute.web.client.components.dateinterval.DateIntervalPicker;
import se.krka.sthlmcommute.web.client.components.dateinterval.DateIntervalUpdateListener;
import se.krka.travelopt.localization.TravelOptLocale;

import java.util.Date;

public class TravelScheduleEditor extends Composite {
    private final DateIntervalPicker rangeEditor;
    private final CouponEditor couponEditor;

    private ScheduleEntry active;

    public TravelScheduleEditor(TravelOptLocale locale, final TravelScheduleList travelScheduleList, ClientConstants clientConstants) {
        VerticalPanel root = new VerticalPanel();
        rangeEditor = new DateIntervalPicker(clientConstants);

        couponEditor = new CouponEditor(new UpdateListener() {
            @Override
            public void updated() {
                travelScheduleList.updateCoupons(active, couponEditor.getWeekdays());
            }
        }, locale, clientConstants);

        root.add(rangeEditor);
        root.add(couponEditor);
        initWidget(root);
        setVisible(false);

        rangeEditor.addListener(new DateIntervalUpdateListener() {
            @Override
            public void intervalChanged(DateIntervalPicker picker, Date fromValue, Date toValue) {
                if (fromValue != null && toValue != null) {
                    travelScheduleList.updateScheduleEntryInterval(active, fromValue, toValue);
                }
            }
        });
    }

    public void setActive(ScheduleEntry entry) {
        active = entry;
        if (entry == null) {
            setVisible(false);
            return;
        }
        Date from = entry.getInterval().getFrom();
        rangeEditor.getFrom().setValue(from);
        if (from != null) {
            rangeEditor.getFrom().setCurrentMonth(from);
        }
        Date to = entry.getInterval().getTo();
        rangeEditor.getTo().setValue(to);
        if (to != null) {
            rangeEditor.getTo().setCurrentMonth(to);
        }

        rangeEditor.applyHighlight();

        int defaultValue = entry.getWeekdays().getDefaultValue();
        couponEditor.getCoupon().setSelectedCoupon(defaultValue);
        couponEditor.getWeekdayEditor().setWeekDays(entry.getWeekdays().getRawCoupons());
        setVisible(true);
    }

    public DateIntervalPicker getRangeEditor() {
        return rangeEditor;
    }

    public CouponEditor getCouponEditor() {
        return couponEditor;
    }
}
