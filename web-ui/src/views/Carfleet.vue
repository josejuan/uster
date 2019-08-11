<template>
    <div class="carfleet">
        <img alt="Car fleet" src="../assets/carfleet.png">
        <h1>Our incredible car fleet</h1>
        <div class="alert alert-danger" v-if="!!error">
          <strong>ERROR!</strong> {{ error }}
        </div>
        <table class="table">
            <tr class="thead-dark">
                <th>ID</th>
                <th>Name</th>
                <th title="Minimum required license">L</th>
                <th>&nbsp;</th>
                <th>&nbsp;</th>
            </tr>
            <tr v-for="x in xs">
                <td>{{ x.id.substr(0, 5) }}</td>
                <td>{{ x.name }}</td>
                <td title="Minimum required license">{{ x.minimumLicenseRequired.id }}</td>
                <td><button type="button" class="btn btn-link" @click="edit(x)">edit</button></td>
                <td><button type="button" class="btn btn-link" @click="remove(x)">remove</button></td>
            </tr>
        </table>
        <div class="card text-left">
            <div class="card-header font-weight-bold">
                New/edit vehicle
            </div>
            <div class="card-body">
                <form>
                    <div class="form-group">
                        <label for="vehicleId">Id</label>
                        <input type="text" readonly class="form-control" id="vehicleId" v-model="vehicle.id">
                    </div>
                    <div class="form-group">
                        <label for="vehicleName">Name</label>
                        <input type="text" class="form-control" id="vehicleName" v-model="vehicle.name">
                    </div>
                    <div class="form-group">
                        <label for="vehiclePlate">Number plate</label>
                        <input type="text" class="form-control" id="vehiclePlate" v-model="vehicle.numberPlate">
                    </div>
                    <div class="form-group">
                        <label for="minimumLicenseRequired">Minimum required license</label>
                        <select class="form-control" id="minimumLicenseRequired" v-model="vehicle.minimumLicenseRequired.id">
                          <option v-for="l in licenses" v-bind:value="l.id">{{ l.id }} - {{ l.name }}</option>
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
    name: 'Carfleet',
    data () {
        return {
            error: null,
            xs: [],
            licenses: [],
            vehicle: {
                id: null,
                name: '',
                numberPlate: '',
                minimumLicenseRequired: {id: '', name: ''}
            }
        }
    },
    mounted() {
        this.reloadList();
        Uster.getLicenses().then(r => this.licenses = r.body, console.error);
    },
    methods: {
        save() {
            (this.vehicle.id == null ? Uster.insertVehicle: Uster.updateVehicle)(this.vehicle).then(r => {
                this.edit(r.body);
                this.reloadList();
            }, this.handleError);
        },
        cancel() {
            this.vehicle = {
                id: null,
                name: '',
                numberPlate: '',
                minimumLicenseRequired: {id: '', name: ''}
            };
        },
        edit(x) {
            this.vehicle = JSON.parse(JSON.stringify(x));
        },
        remove(x) {
            this.vehicle = x;
            Uster.removeVehicle(x).then(r => {
                 this.edit(r.body);
                 this.reloadList();
             }, this.handleError);
        },
        reloadList() {
            this.error = null;
            Uster.getVehicles().then(r => this.xs = r.body, this.handleError);
        },
        handleError(e) {
            this.error = e.message;
        }
    }
}
</script>
