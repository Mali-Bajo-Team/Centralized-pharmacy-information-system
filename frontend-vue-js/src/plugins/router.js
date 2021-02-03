import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

import Guest from './../pages/guest/Container';
import GuestLanding from './../pages/guest/landing/Landing';
import Login from './../pages/guest/login/Login';
import Registration from './../pages/guest/registration/Registration';

import Patient from './../pages/patient/Container';
import PatientLanding from './../pages/patient/landing/Landing';

import Pharmacist from './../pages/pharmacist/Container';
import PharmacistLanding from './../pages/pharmacist/landing/Landing';
import PharmacistProfile from './../pages/pharmacist/profile/Profile';

import Dermatologist from './../pages/dermatologist/Container';
import DermatologistLanding from './../pages/dermatologist/landing/Landing';

import SystemAdmin from './../pages/systemadmin/Container';
import SystemAdminLanding from './../pages/systemadmin/landing/Landing';

import PharmacyAdmin from './../pages/pharmacyadmin/Container';
import PharmacyAdminLanding from './../pages/pharmacyadmin/landing/Landing';

import Supplier from './../pages/supplier/Container';
import SupplierLanding from './../pages/supplier/landing/Landing';

const routes = [
    {
        component: Guest,
        path: '/',
        children: [
            {
                component: GuestLanding,
                name: 'guest',
                path: ''
            },
            {
                component: Login,
                name: 'login',
                path: 'login'
            },
            {
                component: Registration,
                name: 'registration',
                path: 'register'
            }
        ]
    },
    {
        component: Patient,
        path: '/patient',
        children: [
            {
                component: PatientLanding,
                name: 'patient',
                path: ''
            }
        ]
    },
    {
        component: Pharmacist,
        path: '/pharmacist',
        children: [
            {
                component: PharmacistLanding,
                name: 'pharmacist',
                path: ''
            },
            {
                component: PharmacistProfile,
                name: 'pharmacistProfile',
                path: '/pharmacistProfile'
            }
        ]
    },
    {
        component: Dermatologist,
        path: '/dermatologist',
        children: [
            {
                component: DermatologistLanding,
                name: 'dermatologist',
                path: ''
            }
        ]
    },
    {
        component: SystemAdmin,
        path: '/systemadmin',
        children: [
            {
                component: SystemAdminLanding,
                name: 'systemadmin',
                path: ''
            }
        ]
    },
    {
        component: PharmacyAdmin,
        path: '/pharmacyadmin',
        children: [
            {
                component: PharmacyAdminLanding,
                name: 'pharmacyadmin',
                path: ''
            }
        ]
    },
    {
        component: Supplier,
        path: '/supplier',
        children: [
            {
                component: SupplierLanding,
                name: 'supplier',
                path: ''
            }
        ]
    }
]

export default new VueRouter({
    routes
});
