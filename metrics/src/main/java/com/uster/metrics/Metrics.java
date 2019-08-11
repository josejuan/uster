package com.uster.metrics;

import java.util.function.Supplier;

public interface Metrics {
    void event(String sourceId);
    void sized(String sourceId, double value);
    <T> T timed(String sourceId, Supplier<T> k);
}
