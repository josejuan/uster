package com.uster.business;

import com.uster.backend.FakeBackend;
import com.uster.metrics.SimpleMetrics;
import org.junit.jupiter.api.BeforeEach;

class UsterTestWithDefaultBackendSimpleMetrics extends UsterTest {

    @BeforeEach
    void initialize() {
        uster = new Uster(new FakeBackend(), new SimpleMetrics());
    }

}
