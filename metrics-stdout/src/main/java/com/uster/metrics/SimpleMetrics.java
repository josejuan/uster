package com.uster.metrics;

import java.util.function.Supplier;

public class SimpleMetrics implements Metrics {
    @Override
    public void event(String sourceId) {
        System.out.printf("METRICS: event '%s'%n", sourceId);
    }

    @Override
    public void sized(String sourceId, double value) {
        System.out.printf("METRICS: sized '%s' with value %f%n", sourceId, value);
    }

    @Override
    public <T> T timed(String sourceId, Supplier<T> k) {
        final long t = System.currentTimeMillis();
        try {
            return k.get();
        } finally {
            System.out.printf("METRICS: timed '%s' take %d mS%n", sourceId, System.currentTimeMillis() - t);
        }
    }
}
