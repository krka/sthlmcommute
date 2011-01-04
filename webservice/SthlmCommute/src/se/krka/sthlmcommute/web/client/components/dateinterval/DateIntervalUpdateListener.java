package se.krka.sthlmcommute.web.client.components.dateinterval;

import java.util.Date;

public interface DateIntervalUpdateListener {
    void intervalChanged(DateIntervalPicker picker, Date fromValue, Date toValue);
}
