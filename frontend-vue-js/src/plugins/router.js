import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

import Guest from './../pages/guest/Container';
import GuestLanding from './../pages/guest/landing/Landing';
import Login from './../pages/guest/login/Login';
import Registration from './../pages/guest/registration/Registration';
import Drugs from './../pages/guest/drugs/Drugs';
import Pharmacies from './../pages/guest/pharmacies/Pharmacies';

import Patient from './../pages/patient/Container';
import PatientLanding from './../pages/patient/landing/Landing';
import PatientProfile from './../pages/patient/profile/Profile';

import Pharmacist from './../pages/pharmacist/Container';
import PharmacistLanding from './../pages/pharmacist/landing/Landing';
import PharmacistProfile from './../pages/pharmacist/profile/Profile';
import PharmacistPatientsearch from './../pages/pharmacist/patientsearch/Patientsearch.vue';

import Dermatologist from './../pages/dermatologist/Container';
import DermatologistLanding from './../pages/dermatologist/landing/Landing';

import SystemAdmin from './../pages/systemadmin/Container';
import SystemAdminLanding from './../pages/systemadmin/landing/Landing';
import SystemAdminLoyaltyProgram from './../pages/systemadmin/loyaltyprogram/LoyaltyProgram';

import PharmacyAdmin from './../pages/pharmacyadmin/Container';
import PharmacyAdminLanding from './../pages/pharmacyadmin/landing/Landing';

import Supplier from './../pages/supplier/Container';
import SupplierLanding from './../pages/supplier/landing/Landing';
import Orders from './../pages/supplier/orders/Orders';
import Offers from './../pages/supplier/offers/Offers';


import MandatoryPasswordChange from './../pages/mandatoryPasswordChange/Page'

import { getParsedToken, getToken } from '../util/token';

const routes = [
    {
        component: Guest,
        path: '/',
        meta: {
            guest: true
        },
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
            },
            {
                component: Drugs,
                name: 'drugs',
                path: 'drugs'
            },
            {
                component: Pharmacies,
                name: 'pharmacies',
                path: 'pharmacies'
            }
        ]
    },
    {
        component: Patient,
        path: '/patient',
        meta: {
            requiresAuth: true,
            role: 'PATIENT'
        },
        children: [
            {
                component: PatientLanding,
                name: 'patient',
                path: ''
            },
            {
                component: PatientProfile,
                name: 'profile',
                path: 'profile'
            },
            {
                component: Drugs,
                name: 'patientdrugs',
                path: 'patientdrugs'
            },
            {
                component: Pharmacies,
                name: 'patientpharmacies',
                path: 'patientpharmacies'
            }
        ]
    },
    {
        component: Pharmacist,
        path: '/pharmacist',
        meta: {
            requiresAuth: true,
            role: 'PHARMACIST'
        },
        children: [
            {
                component: PharmacistLanding,
                name: 'pharmacist',
                path: ''
            },
            {
                component: PharmacistProfile,
                name: 'profile',
                path: 'profile'
            },
            {
                component: PharmacistPatientsearch,
                name: 'search',
                path: 'search'
            },
            {
                component: Drugs,
                name: 'pharmacistdrugs',
                path: 'pharmacistdrugs'
            }
        ]
    },
    {
        component: Dermatologist,
        path: '/dermatologist',
        meta: {
            requiresAuth: true,
            role: 'DERMATOLOGIST'
        },
        children: [
            {
                component: DermatologistLanding,
                name: 'dermatologist',
                path: ''
            },
            {
                component: Drugs,
                name: 'dermatologistdrugs',
                path: 'dermatologistdrugs'
            }
        ]
    },
    {
        component: SystemAdmin,
        path: '/systemadmin',
        meta: {
            requiresAuth: true,
            role: 'ADMIN'
        },
        children: [
            {
                component: SystemAdminLanding,
                name: 'systemadmin',
                path: ''
            },
            {
                component: SystemAdminLoyaltyProgram,
                name: 'loyaltyprogram',
                path: 'loyaltyprogram'
            },
            {
                component: Drugs,
                name: 'systemadmindrugs',
                path: 'systemadmindrugs'
            },
            {
                component: Pharmacies,
                name: 'systemadminpharmacies',
                path: 'systemadminpharmacies'
            },
        ]
    },
    {
        component: PharmacyAdmin,
        path: '/pharmacyadmin',
        meta: {
            requiresAuth: true,
            role: 'PHARMACY_ADMIN'
        },
        children: [
            {
                component: PharmacyAdminLanding,
                name: 'pharmacyadmin',
                path: ''
            },
            {
                component: Drugs,
                name: 'pharmacyadmindrugs',
                path: 'pharmacyadmindrugs'
            }
        ]
    },
    {
        component: Supplier,
        path: '/supplier',
        meta: {
            requiresAuth: true,
            role: 'SUPPLIER'
        },
        children: [
            {
                component: SupplierLanding,
                name: 'supplier',
                path: ''
            },
            {
                component: Orders,
                name: 'orders',
                path: 'orders'
            },
            {
                component: Offers,
                name: 'offers',
                path: 'offers'
            },
            {
                component: Drugs,
                name: 'supplierdrugs',
                path: 'supplierdrugs'
            },
        ]
    },
    {
        component: MandatoryPasswordChange,
        path: '/activate',
        meta: {
            requiresAuth: true,
            npc: true
        }
    }
]

let router = new VueRouter({
    routes
});

function getHomePage(role) {
    if (role == 'PATIENT')
        return '/patient'
    else if (role == 'ADMIN')
        return '/systemadmin'
    else if (role == 'PHARMACY_ADMIN')
        return '/pharmacyadmin'
    else if (role == 'PHARMACIST')
        return '/pharmacist'
    else if (role == 'DERMATOLOGIST')
        return '/dermatologist'
    else if (role == 'SUPPLIER')
        return '/supplier'
    else
        return '/'
}

router.beforeEach((to, from, next) => {
    let token = getToken();

    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (token == null) {
            next({
                path: '/login',
                params: { nextUrl: to.fullPath }
            })
        } else {
            let parsedToken = getParsedToken()

            if (to.matched.some(record => record.meta.npc)) {
                if (parsedToken.npc)
                    next()
                else
                    next(getHomePage(parsedToken.role))
            }

            if (to.matched.some(record => record.meta.role && record.meta.role == parsedToken.role)) {
                if (!parsedToken.npc)
                    next()
                else
                    next('/activate')
            } else {
                next(getHomePage(parsedToken.role))
            }
        }
    } else if (to.matched.some(record => record.meta.guest)) {
        if (token == null) {
            next()
        }
        else {
            let parsedToken = getParsedToken()
            next(getHomePage(parsedToken.role))
        }
    } else {
        next()
    }
})

export default router
