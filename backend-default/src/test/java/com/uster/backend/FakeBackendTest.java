package com.uster.backend;

import com.uster.backend.core.BackendTest;
import org.junit.jupiter.api.BeforeEach;

public class FakeBackendTest extends BackendTest {

    @BeforeEach
    public void initialize() {
        backend = new FakeBackend();
    }

}