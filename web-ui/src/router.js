import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import Drivers from './views/Drivers.vue'
import Carfleet from './views/Carfleet.vue'
import Trips from './views/Trips.vue'
import Licenses from './views/Licenses.vue'

Vue.use(Router)

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'home',
            component: Home
        },
        {
            path: '/drivers',
            name: 'drivers',
            component: Drivers
        },
        {
            path: '/carfleet',
            name: 'carfleet',
            component: Carfleet
        },
        {
            path: '/trips',
            name: 'trips',
            component: Trips
        },
        {
            path: '/licenses',
            name: 'licenses',
            component: Licenses
        },
        {
            path: '/about',
            name: 'about',
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
        }
    ]
})
