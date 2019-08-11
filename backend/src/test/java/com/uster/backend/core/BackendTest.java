package com.uster.backend.core;

import com.uster.model.License;
import com.uster.model.Vehicle;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BackendTest {
    // should be settled by implementation tests
    protected Backend backend;

    @Test
    public void test() {

        // licenses lookup
        final License l = backend
                .licenses()
                .findAny()
                .orElseThrow(IllegalStateException::new);

        // vehicle creation
        final Vehicle newv = Vehicle.builder()
                .name(UUID.randomUUID().toString())
                .numberPlate(UUID.randomUUID().toString().substring(0, 20))
                .minimumLicenseRequired(l)
                .build();

        final Vehicle createdv = backend.upsert(newv);

        assertEquals(newv.withId(createdv.getId()), createdv, "new vehicle should be equal than the created one");

        // vehicle modification
        final String newName = UUID.randomUUID().toString();

        backend.upsert(createdv.withName(newName));
        assertEquals(newName, backend.lookup(createdv).getName(), "modified name should persist");

        // vehicle in list
        assertEquals(1, backend.vehicles().filter(v -> createdv.getId().equals(v.getId())).count(), "created vehicle should be in list");

        // vehicle remove
        backend.remove(createdv);

        assertEquals(0, backend.vehicles().filter(v -> createdv.getId().equals(v.getId())).count(), "removed vehicle should not be in list");

    }

}