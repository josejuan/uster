package com.uster.metrics;

import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class Prometheus implements Metrics {

    static {
        try {
            new HTTPServer("0.0.0.0", 9091, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Map<String, Counter> counters = new ConcurrentHashMap<>();
    private static final Map<String, Histogram> sizeders = new ConcurrentHashMap<>();
    private static final String PREFFIX = "uster_";

    private static String mkName(String sourceId) {
        return PREFFIX + sourceId;
    }

    private Counter lookupCounter(String sourceId) {
        return counters.computeIfAbsent(sourceId, ignore -> Counter.build().name(mkName(sourceId)).help("Uster counter").register());
    }

    private Histogram lookupSized(String sourceId) {
        return sizeders.computeIfAbsent(sourceId, ignore -> Histogram.build().name(mkName(sourceId)).help("Uster histogram").register());
    }

    @Override
    public void event(String sourceId) {
        lookupCounter(sourceId).inc();
    }

    @Override
    public void sized(String sourceId, double value) {
        lookupSized(sourceId).observe(value);
    }

    @Override
    public <T> T timed(String sourceId, Supplier<T> k) {
        final Histogram.Timer t = lookupSized(sourceId).startTimer();
        try {
            return k.get();
        } finally {
            t.observeDuration();
        }
    }
}
