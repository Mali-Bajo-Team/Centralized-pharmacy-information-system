import unregisterLanding from './components/unregistered/Landingpage.vue';
import systemadminLanding from './components/systemadmin/Landingpage.vue';
import supplierLanding from './components/supplier/Landingpage.vue';
import pharmacyadminLanding from './components/pharmacyadmin/Landingpage.vue';
import pharmacistLanding from './components/pharmacist/Landingpage.vue';
import patientLanding from './components/patient/Landingpage.vue';
import dermatologistLanding from './components/dermatologist/Landingpage.vue';
import register from './components/unregistered/RegisterPage.vue';

export default [
    { path: '/', component: unregisterLanding },
    { path: '/components/register', component: register, name:'registerPage' },
    { path: '/components/systemadmin/Landingpage.vue', component: systemadminLanding, name: 'systemadminLanding' },
    { path: '/components/supplier/Landingpage.vue', component: supplierLanding, name: 'supplierLanding' },
    { path: '/components/pharmacyadmin/Landingpage.vue', component: pharmacyadminLanding, name: 'pharmacyadminLanding' },
    { path: '/components/pharmacist/Landingpage.vue', component: pharmacistLanding, name: 'pharmacistLanding' },
    { path: '/components/patient/Landingpage.vue', component: patientLanding, name: 'patientLanding' },
    { path: '/components/dermatologist/Landingpage.vue', component: dermatologistLanding, name: 'dermatologistLanding' },
]

