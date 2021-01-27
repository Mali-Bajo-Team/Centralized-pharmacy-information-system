
import unregisterLanding from './components/unregistered/Landingpage.vue';
import systemadminLanding from './components/systemadmin/Landingpage.vue';

export default [
    { path: '/', component: unregisterLanding },
    { path: '/components/systemadmin/Landingpage.vue', component: systemadminLanding, name: 'systemadminLanding'},
]

