package com.uster.business;

import com.uster.backend.HibernateBackend;
import com.uster.backend.HibernateBackendTest;
import com.uster.metrics.SimpleMetrics;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class UsterTestWithHibernateBackendSimpleMetrics extends UsterTest {

    @BeforeAll
    static void initialize() throws Exception {
        HibernateBackendTest.setUpClass();
    }

    @BeforeEach
    void initializeOne() {
        uster = new Uster(new HibernateBackend(), new SimpleMetrics());
    }

}
