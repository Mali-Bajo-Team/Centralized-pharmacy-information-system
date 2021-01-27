import allUsers from './components/allUsers.vue';
import unregisterLanding from './components/unregistered/Landingpage.vue';

export default [
    { path: '/', component: unregisterLanding},
    { path: '/allUsers', component: allUsers},
]