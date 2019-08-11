package com.uster.backend;

import com.uster.backend.core.BackendTest;
import com.uster.extra.DerbyBackend;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.concurrent.atomic.AtomicBoolean;

public class HibernateBackendTest extends BackendTest {
    // support for multiple tests
    private final static AtomicBoolean setted = new AtomicBoolean(false);

    @BeforeAll
    public static void setUpClass() throws Exception {
        synchronized (setted) {
            if (!setted.get()) {
                setted.set(true);
                DerbyBackend.setup();
            }
        }
    }

    @BeforeEach
    void initialize() {
        backend = new HibernateBackend();
    }

}