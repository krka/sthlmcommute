package se.krka.sthlmcommute.web.client.persistors;

import com.google.gwt.user.client.Cookies;
import se.krka.sthlmcommute.web.client.ClientPersistor;
import se.krka.sthlmcommute.web.client.OptimizeOptions;

public class OptimizePersistor implements ClientPersistor {
    private final OptimizeOptions optimizeOptions;

    public OptimizePersistor(OptimizeOptions optimizeOptions) {
        this.optimizeOptions = optimizeOptions;
    }

    @Override
    public void onExit() {
        Cookies.setCookie("optimizeOptions", String.valueOf(optimizeOptions.getRadioGroup().getSelected().getFormValue()));
        Cookies.setCookie("optimizeTickets", optimizeOptions.getTicketEditor().serialize());
    }

    @Override
    public void onLoad() {
        optimizeOptions.getRadioGroup().setSelected(Cookies.getCookie("optimizeOptions"));
        String optimizeTickets = Cookies.getCookie("optimizeTickets");
        if (optimizeTickets != null) {
            optimizeOptions.getTicketEditor().deserialize(optimizeTickets);
        }
    }
}
