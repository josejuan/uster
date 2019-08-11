package com.uster.metrics;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

class PrometheusTest {

    @Test
    void longTimeRunningTest() {
        final Metrics m = new Prometheus();
        final ThreadLocalRandom rnd = ThreadLocalRandom.current();
        // one minute, go to your Prometheus instace to watch incoming traffic
        final long ttl = System.currentTimeMillis() + 60_000;
        while (System.currentTimeMillis() < ttl) {
            if (rnd.nextBoolean())
                m.timed("timed_test", () -> IntStream.range(0, rnd.nextInt(0, 100_000)).sum());
            if (rnd.nextBoolean())
                m.event("event_test");
            if (rnd.nextBoolean())
                m.sized("sized_test", rnd.nextDouble(-10.0, 10.0));
        }
    }

}