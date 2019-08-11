package com.uster.backend.core;

import com.uster.model.Driver;
import com.uster.model.License;
import com.uster.model.Vehicle;

import java.util.stream.Stream;

public interface Backend {

    Stream<License> licenses();

    Vehicle upsert(Vehicle vehicle);

    Vehicle remove(Vehicle vehicle);

    Vehicle lookup(Vehicle vehicle);

    Stream<Vehicle> vehicles();

    Driver upsert(Driver driver);

    Driver remove(Driver driver);

    Driver lookup(Driver driver);

    Stream<Driver> drivers();

}
