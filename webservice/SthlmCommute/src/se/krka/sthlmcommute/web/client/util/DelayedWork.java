package se.krka.sthlmcommute.web.client.util;

import com.google.gwt.user.client.Timer;

public class DelayedWork {

    public static final int DELAY = 100; // millis
    private final Timer latestTimer;
    private final Runnable job;

    private final int delayMillis;

    public DelayedWork(Runnable job, int delayMillis) {
        this.delayMillis = delayMillis;
        latestTimer = new MyTimer();
        this.job = job;
    }

    public DelayedWork(Runnable job) {
        this(job, DELAY);
    }

    public void requestWork() {
        latestTimer.cancel();
        latestTimer.schedule(delayMillis);
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
