import Swagger from 'swagger-client'

var _api = null;

// connect to our backend api
Swagger({ url: 'http://localhost:9092/v2/api-docs' }).then(c => window._uster = _api = c.apis.controller);

// decouple the api shape
const api = {

    getLicenses() { return _api.licensesUsingGET() },

    getVehicles() { return _api.vehiclesUsingGET() },
    insertVehicle(vehicle) { return _api.insertVehicleUsingPOST({vehicle: vehicle}) },
    updateVehicle(vehicle) { return _api.updateVehicleUsingPUT({vehicle: vehicle}) },
    removeVehicle(vehicle) { return _api.removeVehicleUsingDELETE({vehicleId: vehicle.id}) },

    getDrivers() { return _api.driversUsingGET() },
    insertDriver(driver) { return _api.insertDriverUsingPOST({driver: driver}) },
    updateDriver(driver) { return _api.updateDriverUsingPUT({driver: driver}) },
    removeDriver(driver) { return _api.removeDriverUsingDELETE({driverId: driver.id}) },

    getTrips() { return _api.tripsUsingGET() },
    insertTrip(trip) { return _api.insertTripUsingPOST({trip: trip}) },
    updateTrip(trip) { return _api.updateTripUsingPUT({trip: trip}) },
    removeTrip(trip) { return _api.removeTripUsingDELETE({tripId: trip.id}) }

};

export default api;