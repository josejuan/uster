<template>
    <div class="trips">
        <img alt="Trips" src="../assets/trips.png">
        <h1>Trip management</h1>
        <div class="alert alert-danger" v-if="!!error">
          <strong>ERROR!</strong> {{ error }}
        </div>
        <table class="table">
            <tr class="thead-dark">
                <th>ID</th>
                <th>Date</th>
                <th>Driver</th>
                <th>Car</th>
                <th>&nbsp;</th>
                <th>&nbsp;</th>
            </tr>
            <tr v-for="x in xs">
                <td>{{ x.id.substr(0, 5) }}</td>
                <td>{{ x.date }}</td>
                <td>{{ x.driver.name }}</td>
                <td>{{ x.vehicle.name }}</td>
                <td><button type="button" class="btn btn-link" @click="edit(x)">edit</button></td>
                <td><button type="button" class="btn btn-link" @click="remove(x)">remove</button></td>
            </tr>
        </table>
        <div class="card text-left">
            <div class="card-header font-weight-bold">
                New/edit trip
            </div>
            <div class="card-body">
                <form>
                    <div class="form-group">
                        <label for="tripId">Id</label>
                        <input type="text" readonly class="form-control" id="tripId" v-model="trip.id">
                    </div>
                    <div class="form-group">
                        <label for="tripDate">Date</label>
                        <input type="date" class="form-control" id="tripDate" v-model="trip.date">
                    </div>
                    <div class="form-group">
                        <label for="driver">Driver</label>
                        <select class="form-control" id="driver" v-model="trip.driver.id">
                          <option v-for="d in drivers" v-bind:value="d.id">{{ d.name }} ({{ d.license.id }} - {{ d.license.name }})</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="license">Car</label>
                        <select class="form-control" id="vehicle" v-model="trip.vehicle.id">
                          <option v-for="v in vehicles" v-bind:value="v.id">{{ v.name }} ({{ v.minimumLicenseRequired.id }} - {{ v.minimumLicenseRequired.name }})</option>
                        </select>
                    </div>
                </form>
                <button type="button" class="btn btn-primary" @click="save">Save</button>&nbsp;
                <button type="button" class="btn btn-warning" @click="cancel">Reset</button>
            </div>
        </div>
    </div>
</template>

<style scoped>
    .grid {
        margin: auto;
        border-collapse: collapse;
    }
    .grid th {
        background-color: lightgray;
        text-align: left;
        border: 1px solid black;
    }
    .grid td {
        text-align: left;
        border: 1px solid black;
    }
</style>

<script>
import Uster from '@/uster';

export default {
    name: 'Trips',
    data () {
        return {
            error: null,
            xs: [],
            drivers: [],
            vehicles: [],
            trip: {
                id: null,
                date: '',
                driver: {id: '', name: '', license: {id: '', name: ''} },
                vehicle: {id: '', name: '', minimumLicenseRequired: {id: '', name: ''} }
            }
        }
    },
    mounted() {
        this.reloadList();
        Uster.getDrivers().then(r => this.drivers = r.body, this.handleError);
        Uster.getVehicles().then(r => this.vehicles = r.body, this.handleError);
    },
    methods: {
        save() {
            (this.trip.id == null ? Uster.insertTrip: Uster.updateTrip)(this.trip).then(r => {
                this.edit(r.body);
                this.reloadList();
            }, this.handleError);
        },
        cancel() {
            this.trip = {
                id: null,
                date: '',
                driver: {id: '', name: '', license: {id: '', name: ''} },
                vehicle: {id: '', name: '', minimumLicenseRequired: {id: '', name: ''} }
            };
        },
        edit(x) {
            this.trip = JSON.parse(JSON.stringify(x));
        },
        remove(x) {
            this.trip = x;
            Uster.removeTrip(x).then(r => {
                 this.edit(r.body);
                 this.reloadList();
             }, this.handleError);
        },
        reloadList() {
            this.error = null;
            Uster.getTrips().then(r => this.xs = r.body, this.handleError);
        },
        handleError(e) {
            this.error = e.message || e.response.body.message;
        }
    }
}
</script>
