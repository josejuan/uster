<template>
    <div class="licenses">
        <img alt="Licenses" src="../assets/license.png">
        <h1>Official driver's licenses</h1>
        <div class="alert alert-danger" v-if="!!error">
          <strong>ERROR!</strong> {{ error }}
        </div>
        <table class="table">
            <tr class="thead-dark">
                <th>ID</th>
                <th>Name</th>
            </tr>
            <tr v-for="x in xs" :key="x.id">
                <td>{{ x.id }}</td>
                <td>{{ x.name }}</td>
            </tr>
        </table>
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
    name: 'Licenses',
    data () {
        return {
            error: null,
            xs: []
        }
    },
    mounted() {
        Uster.getLicenses().then(result => this.xs = result.body, this.handleError);
    },
    methods: {
        handleError(e) {
            this.error = e.message || e.response.body.message;
        }
    }
}
</script>
