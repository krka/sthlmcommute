package se.krka.sthlmcommute.web.client.async;

import com.google.gwt.core.client.RunAsyncCallback;

public abstract class GenericFailureAsyncCallback implements RunAsyncCallback {
    @Override
    public void onFailure(Throwable throwable) {
        // not sure what to do
    }

    @Override
    public abstract void onSuccess();
}
