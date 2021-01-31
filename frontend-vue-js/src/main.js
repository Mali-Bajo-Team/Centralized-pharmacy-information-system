import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import Routes from './routes'

Vue.use(VueRouter);

//Here we register or routes
const router = new VueRouter({
    routes: Routes
});

Vue.config.productionTip = false

import Calendar from 'v-calendar/lib/components/calendar.umd'

Vue.component('v-calendar', Calendar)

Vue.component('NavbarPharmacist', require('./components/pharmacist/NavbarPharmacist.vue').default);



new Vue({
  render: h => h(App),
  router: router
}).$mount('#app')
