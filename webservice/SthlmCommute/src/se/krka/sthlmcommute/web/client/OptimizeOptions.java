package se.krka.sthlmcommute.web.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;
import se.krka.sthlmcommute.web.client.components.RadioGroup;
import se.krka.sthlmcommute.web.client.util.DelayedWork;
import se.krka.sthlmcommute.web.client.util.UIUtil;
import se.krka.travelopt.localization.TravelOptLocale;

public class OptimizeOptions extends Composite {
    public static final String GROUP_NAME = "extendGroup";
    public static final String ONLY = "only";
    public static final String EXTEND = "extend";
    private final RadioGroup radioGroup;
    private final CouponEditor couponEditor;

    public OptimizeOptions(final DelayedWork delayedWork, TravelOptLocale locale, ClientConstants clientConstants) {
        radioGroup = new RadioGroup(GROUP_NAME);

        couponEditor = new CouponEditor(new UpdateListener() {
            @Override
            public void updated() {
                delayedWork.requestWork();
            }
        }, locale, clientConstants);

        Panel root = new VerticalPanel();
        root.setWidth("60em");

        ClickHandler handler = new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                couponEditor.setVisible(isEnabled());
                delayedWork.requestWork();
            }
        };

        radioGroup.addClickHandler(handler);
        radioGroup.addRadioButton(ONLY, clientConstants.optimizeOptions_option_onlySchedule());
        radioGroup.addRadioButton(EXTEND, clientConstants.optimizeOptions_option_extended());
        radioGroup.setSelected(ONLY);

        VerticalPanel hidden = new VerticalPanel();
        hidden.setWidth("4em");

        root.add(UIUtil.wrapCaption(clientConstants.optimizeSettings(), UIUtil.wrapFlow(radioGroup, UIUtil.wrapHorizontal(hidden, couponEditor))));
        initWidget(root);

        couponEditor.setVisible(false);

    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public CouponEditor getCouponEditor() {
        return couponEditor;
    }

    public boolean isEnabled() {
        RadioGroup.Button selected = radioGroup.getSelected();
        if (selected == null) {
            return false;
        }
        return selected.getFormValue().equals(EXTEND);
    }
}
