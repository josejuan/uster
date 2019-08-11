package com.uster.business;

import com.uster.backend.FakeBackend;
import com.uster.metrics.Prometheus;
import org.junit.jupiter.api.BeforeEach;

abstract class UsterTestWithDefaultBackendPrometheus extends UsterTest {

    @BeforeEach
    void initialize() {
        uster = new Uster(new FakeBackend(), new Prometheus());
    }

}
