<template>
    <div class="drivers">
        <img alt="Drivers" src="../assets/drivers.png">
        <h1>Our superspecialist drivers</h1>
        <div class="alert alert-danger" v-if="!!error">
          <strong>ERROR!</strong> {{ error }}
        </div>
        <table class="table">
            <tr class="thead-dark">
                <th>ID</th>
                <th>Name</th>
                <th title="Current certified driver license">L</th>
                <th>&nbsp;</th>
                <th>&nbsp;</th>
            </tr>
            <tr v-for="x in xs">
                <td>{{ x.id.substr(0, 5) }}</td>
                <td>{{ x.name }}</td>
                <td title="Current certified driver license">{{ x.license.id }}</td>
                <td><button type="button" class="btn btn-link" @click="edit(x)">edit</button></td>
                <td><button type="button" class="btn btn-link" @click="remove(x)">remove</button></td>
            </tr>
        </table>
        <div class="card text-left">
            <div class="card-header font-weight-bold">
                New/edit driver
            </div>
            <div class="card-body">
                <form>
                    <div class="form-group">
                        <label for="driverId">Id</label>
                        <input type="text" readonly class="form-control" id="driverId" v-model="driver.id">
                    </div>
                    <div class="form-group">
                        <label for="driverName">Name</label>
                        <input type="text" class="form-control" id="driverName" v-model="driver.name">
                    </div>
                    <div class="form-group">
                        <label for="license">Certified license</label>
                        <select class="form-control" id="license" v-model="driver.license.id">
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
    name: 'Drivers',
    data () {
        return {
            error: null,
            xs: [],
            licenses: [],
            driver: {
                id: null,
                name: '',
                license: {id: '', name: ''}
            }
        }
    },
    mounted() {
        this.reloadList();
        Uster.getLicenses().then(r => this.licenses = r.body, this.handleError);
    },
    methods: {
        save() {
            (this.driver.id == null ? Uster.insertDriver: Uster.updateDriver)(this.driver).then(r => {
                this.edit(r.body);
                this.reloadList();
            }, this.handleError);
        },
        cancel() {
            this.driver = {
                id: null,
                name: '',
                license: {id: '', name: ''}
            };
        },
        edit(x) {
            this.driver = JSON.parse(JSON.stringify(x));
        },
        remove(x) {
            this.driver = x;
            Uster.removeDriver(x).then(r => {
                 this.edit(r.body);
                 this.reloadList();
             }, this.handleError);
        },
        reloadList() {
            this.error = null;
            Uster.getDrivers().then(r => this.xs = r.body, this.handleError);
        },
        handleError(e) {
            this.error = e.message || e.response.body.message;
        }
    }
}
</script>
