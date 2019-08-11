package com.uster.business;

import com.uster.backend.core.Backend;
import com.uster.metrics.Metrics;
import com.uster.model.Driver;
import com.uster.model.License;
import com.uster.model.Trip;
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

    private static void ensureLicenseMatch(final Driver driver, final Vehicle vehicle) {
        if (driver.getLicense().getId().compareTo(vehicle.getMinimumLicenseRequired().getId()) < 0)
            throw new IllegalStateException(String.format("The driver '%s' with '%s' license cannot drive the car '%s', require minimum the '%s' license",
                    driver.getName(), driver.getLicense().getName(), vehicle.getName(), vehicle.getMinimumLicenseRequired().getName()));
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

    public Trip upsert(final Trip trip) {
        // RULE: the driver should have a valid license for that vehicle
        ensureLicenseMatch(backend.lookup(trip.getDriver()), backend.lookup(trip.getVehicle()));

        return metrics.timed(metric("upsertTrip"), () -> backend.upsert(trip));
    }

    public Trip remove(final Trip trip) {
        return metrics.timed(metric("removeTrip"), () -> backend.remove(trip));
    }

    public Trip lookup(final Trip trip) {
        return metrics.timed(metric("lookupTrip"), () -> backend.lookup(trip));
    }

    public Stream<Trip> trips() {
        return metrics.timed(metric("trips"), backend::trips);
    }

}
