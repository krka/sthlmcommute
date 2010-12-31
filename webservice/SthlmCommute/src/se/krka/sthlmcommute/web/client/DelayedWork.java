package se.krka.sthlmcommute.web.client;

import com.google.gwt.user.client.Timer;

public class DelayedWork {

    public static final int DELAY = 100; // millis
    private final Timer latestTimer;
    private final Runnable job;

    public DelayedWork(Runnable job) {
        latestTimer = new MyTimer();
        this.job = job;
    }

    public void requestWork() {
        latestTimer.cancel();
        latestTimer.schedule(DELAY);
    }

    public void cancelWork() {
        latestTimer.cancel();
    }

    private class MyTimer extends Timer {
        @Override
        public void run() {
            job.run();
        }
    }
}
