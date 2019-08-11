package com.uster.business;

import com.uster.backend.core.Backend;
import com.uster.metrics.Metrics;
import com.uster.model.Driver;
import com.uster.model.License;
import com.uster.model.Vehicle;

import java.util.stream.Stream;

public class Uster {

    private static final String METRICS_PREFFIX = "uster_";
    private final Backend backend;
    private final Metrics metrics;

    public Uster(Backend backend, Metrics metrics) {
        this.backend = backend;
        this.metrics = metrics;

        this.metrics.event(metric("creation"));
    }

    private static String metric(String name) {
        return METRICS_PREFFIX + name;
    }

    public Stream<License> lookupLicenses() {
        return metrics.timed(metric("lookupLicenses"), backend::licenses);
    }

    public Vehicle upsert(final Vehicle vehicle) {
        return metrics.timed(metric("upsertVehicle"), () -> backend.upsert(vehicle));
    }

    public Vehicle remove(final Vehicle vehicle) {
        return metrics.timed(metric("removeVehicle"), () -> backend.remove(vehicle));
    }

    public Vehicle lookup(final Vehicle vehicle) {
        return metrics.timed(metric("lookupVehicle"), () -> backend.lookup(vehicle));
    }

    public Stream<Vehicle> vehicles() {
        return metrics.timed(metric("vehicles"), backend::vehicles);
    }

    public Driver upsert(final Driver driver) {
        return metrics.timed(metric("upsertDriver"), () -> backend.upsert(driver));
    }

    public Driver remove(final Driver driver) {
        return metrics.timed(metric("removeDriver"), () -> backend.remove(driver));
    }

    public Driver lookup(final Driver driver) {
        return metrics.timed(metric("lookupDriver"), () -> backend.lookup(driver));
    }

    public Stream<Driver> drivers() {
        return metrics.timed(metric("drivers"), backend::drivers);
    }

}
