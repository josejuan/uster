package com.uster.backend;

import com.uster.backend.core.Backend;
import com.uster.model.Driver;
import com.uster.model.License;
import com.uster.model.Trip;
import com.uster.model.Vehicle;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class FakeBackend implements Backend {
    private static Map<String, Vehicle> vehicles = new ConcurrentHashMap<>();
    private static Map<String, Driver> drivers = new ConcurrentHashMap<>();
    private static Map<String, Trip> trips = new ConcurrentHashMap<>();

    @Override
    public Stream<License> licenses() {
        return Stream.of(
                new License("A", "Basic license"),
                new License("B", "Normal license"),
                new License("C", "Pro license"),
                new License("D", "NASA license"));
    }

    @Override
    public Vehicle upsert(Vehicle vehicle) {
        final Vehicle withId = vehicle.getId() == null ? vehicle.withId(UUID.randomUUID().toString()) : vehicle;
        vehicles.put(withId.getId(), withId);
        return lookup(withId);
    }

    @Override
    public Vehicle remove(Vehicle vehicle) {
        final Vehicle current = lookup(vehicle);
        vehicles.remove(current.getId());
        return current.withId(null);
    }

    @Override
    public Vehicle lookup(Vehicle vehicle) {
        return vehicles.get(vehicle.getId());
    }

    @Override
    public Stream<Vehicle> vehicles() {
        return vehicles.values().stream();
    }

    @Override
    public Driver upsert(Driver driver) {
        final Driver withId = driver.getId() == null ? driver.withId(UUID.randomUUID().toString()) : driver;
        drivers.put(withId.getId(), withId);
        return lookup(withId);
    }

    @Override
    public Driver remove(Driver driver) {
        final Driver current = lookup(driver);
        drivers.remove(current.getId());
        return current.withId(null);
    }

    @Override
    public Driver lookup(Driver driver) {
        return drivers.get(driver.getId());
    }

    @Override
    public Stream<Driver> drivers() {
        return drivers.values().stream();
    }

    @Override
    public Trip upsert(Trip trip) {
        final Trip withId = trip.getId() == null ? trip.withId(UUID.randomUUID().toString()) : trip;
        trips.put(withId.getId(), withId);
        return lookup(withId);
    }

    @Override
    public Trip remove(Trip trip) {
        final Trip current = lookup(trip);
        trips.remove(current.getId());
        return current.withId(null);
    }

    @Override
    public Trip lookup(Trip trip) {
        return trips.get(trip.getId());
    }

    @Override
    public Stream<Trip> trips() {
        return trips.values().stream();
    }
}
