package com.uster.business;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class UsterTest {

    Uster uster;

    @Test
    void lookupLicensesDefault() {
        assertTrue(uster.lookupLicenses().count() > 0, "any license should be retrieved");
    }
}