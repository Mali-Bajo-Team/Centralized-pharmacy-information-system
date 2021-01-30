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

Vue.component('Navbar', require('./components/Navbar.vue').default);

new Vue({
  render: h => h(App),
  router: router
}).$mount('#app')
