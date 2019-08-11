package com.uster.server.rest;

import com.uster.model.Driver;
import com.uster.model.License;
import com.uster.model.Trip;
import com.uster.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin(origins = "*")
@RestController("management")
@RequestMapping("/uster")
@Validated
public class Controller {

    @Autowired
    private UsterService service;

    @RequestMapping(path = "/licenses", method = GET)
    public /* Stream */ List<License> licenses() {
        return service.uster.lookupLicenses().collect(toList());
    }

    @RequestMapping(path = "/vehicles", method = PUT)
    public Vehicle updateVehicle(@RequestBody @NotNull final Vehicle vehicle) {
        if (vehicle.getId() == null)
            throw new IllegalArgumentException("'id' cannot be empty");
        return service.uster.upsert(vehicle);
    }

    @RequestMapping(path = "/vehicles", method = POST)
    public Vehicle insertVehicle(@RequestBody final Vehicle vehicle) {
        if (vehicle.getId() != null)
            throw new IllegalArgumentException("'id' must be empty");
        return service.uster.upsert(vehicle);
    }

    @RequestMapping(path = "/vehicles/{vehicleId}", method = DELETE)
    public Vehicle removeVehicle(final @NotBlank @PathVariable("vehicleId") String vehicleId) {
        return service.uster.remove(Vehicle.builder().id(vehicleId).build());
    }

    @RequestMapping(path = "/vehicles/{vehicleId}", method = GET)
    public Vehicle lookupVehicle(final @NotBlank @PathVariable("vehicleId") String vehicleId) {
        return service.uster.lookup(Vehicle.builder().id(vehicleId).build());
    }

    @RequestMapping(path = "/vehicles", method = GET)
    public /* Stream */ List<Vehicle> vehicles() {
        return service.uster.vehicles().collect(toList());
    }

    @RequestMapping(path = "/drivers", method = PUT)
    public Driver updateDriver(@RequestBody @NotNull final Driver driver) {
        if (driver.getId() == null)
            throw new IllegalArgumentException("'id' cannot be empty");
        return service.uster.upsert(driver);
    }

    @RequestMapping(path = "/drivers", method = POST)
    public Driver insertDriver(@RequestBody final Driver driver) {
        if (driver.getId() != null)
            throw new IllegalArgumentException("'id' must be empty");
        return service.uster.upsert(driver);
    }

    @RequestMapping(path = "/drivers/{driverId}", method = DELETE)
    public Driver removeDriver(final @NotBlank @PathVariable("driverId") String driverId) {
        return service.uster.remove(Driver.builder().id(driverId).build());
    }

    @RequestMapping(path = "/drivers/{driverId}", method = GET)
    public Driver lookupDriver(final @NotBlank @PathVariable("driverId") String driverId) {
        return service.uster.lookup(Driver.builder().id(driverId).build());
    }

    @RequestMapping(path = "/drivers", method = GET)
    public /* Stream */ List<Driver> drivers() {
        return service.uster.drivers().collect(toList());
    }

    @RequestMapping(path = "/trips", method = PUT)
    public Trip updateTrip(@RequestBody @NotNull final Trip trip) {
        if (trip.getId() == null)
            throw new IllegalArgumentException("'id' cannot be empty");
        return service.uster.upsert(trip);
    }

    @RequestMapping(path = "/trips", method = POST)
    public Trip insertTrip(@RequestBody final Trip trip) {
        if (trip.getId() != null)
            throw new IllegalArgumentException("'id' must be empty");
        return service.uster.upsert(trip);
    }

    @RequestMapping(path = "/trips/{tripId}", method = DELETE)
    public Trip removeTrip(final @NotBlank @PathVariable("tripId") String tripId) {
        return service.uster.remove(Trip.builder().id(tripId).build());
    }

    @RequestMapping(path = "/trips/{tripId}", method = GET)
    public Trip lookupTrip(final @NotBlank @PathVariable("tripId") String tripId) {
        return service.uster.lookup(Trip.builder().id(tripId).build());
    }

    @RequestMapping(path = "/trips", method = GET)
    public /* Stream */ List<Trip> trips() {
        return service.uster.trips().collect(toList());
    }

}
