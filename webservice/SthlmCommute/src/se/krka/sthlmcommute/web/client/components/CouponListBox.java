package se.krka.sthlmcommute.web.client.components;

import com.google.gwt.user.client.ui.ListBox;

public class CouponListBox extends ListBox {
    public CouponListBox(boolean includeDefault) {
        if (includeDefault) {
            addItem("--", "-1");
        }
        for (int i = 0; i < 9; i++) {
            addItem("" + i, "" + i);
        }
        addItem("9+", "9");
        setSelectedIndex(0);
    }

    public int getSelectedCoupon() {
        int index = getSelectedIndex();
        return Integer.parseInt(getValue(index));
    }

    public void setSelectedCoupon(int value) {
        String vS = String.valueOf(value);
        int n = getItemCount();
        for (int i = 0; i < n; i++) {
            String s = getValue(i);
            if (s.equals(vS)) {
                setSelectedIndex(i);
                return;
            }
        }
    }


    public String serialize() {
        return getSelectedCoupon() + "";
    }

    public void deserialize(String data) {
        setSelectedCoupon(Integer.parseInt(data));
    }
}
