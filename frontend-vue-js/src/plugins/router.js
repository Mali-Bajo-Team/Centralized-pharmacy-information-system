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
import Consultations from './../pages/patient/consultations/Consultations';
import PatientLanding from './../pages/patient/landing/Landing';
import PatientProfile from './../pages/patient/profile/Profile';
import PatientReservations from './../pages/patient/reservation/DrugReservation';
import Subscriptions from './../pages/patient/subscriptions/Subscriptions';
import EPrescription from './../pages/patient/eprescription/EPrescription';
import MyEPrescriptions from './../pages/patient/eprescription/MyEPrescriptions';
import PharmacistSearchPatient from './../pages/patient/consultants/Pharmacists';
import DermatologistSearchPatient from './../pages/patient/consultants/Dermatologists';
import PharmacistHistoryPatient from './../pages/patient/history/Pharmacist';
import DermatologistHistoryPatient from './../pages/patient/history/Dermatologist';
import PharmacyProfile from './../pages/patient/pharmacy/Pharmacy'
import PatientRating from './../pages/patient/rating/ConsultantsRating';
import PatientPharmaciesRating from './../pages/patient/rating/PharmaciesRating';
import PatientDrugsRating from './../pages/patient/rating/DrugsRating';



import Pharmacist from './../pages/pharmacist/Container';
import PharmacistLanding from './../pages/pharmacist/landing/Landing';
import PharmacistProfile from './../pages/pharmacist/profile/Profile';
import PharmacistPatientsearch from './../pages/pharmacist/patientsearch/Patientsearch.vue';
import DispensingDrugs from './../pages/pharmacist/dispensing/Dispensingdrugs.vue';

import Dermatologist from './../pages/dermatologist/Container';
import DermatologistLanding from './../pages/dermatologist/landing/Landing';
import DermatologistProfile from './../pages/dermatologist/profile/Profile';
import DermatologistPatientsearch from './../pages/dermatologist/patientsearch/Patientsearch.vue';

import SystemAdmin from './../pages/systemadmin/Container';
import SystemAdminLanding from './../pages/systemadmin/landing/Landing';
import SystemAdminLoyaltyProgram from './../pages/systemadmin/loyaltyprogram/LoyaltyProgram';
import SystemAdminComplaints from './../pages/systemadmin/complaints/Complaints';

import PharmacyAdmin from './../pages/pharmacyadmin/Container';
import PharmacyAdminLanding from './../pages/pharmacyadmin/landing/Landing';
import VacationRequests from './../pages/pharmacyadmin/vacation/Vacation';
import PharmacistSearch from './../pages/pharmacyadmin/consultants/Pharmacists';
import DermatologistSearch from './../pages/pharmacyadmin/consultants/Dermatologists';
import AddPharmacist from './../pages/pharmacyadmin/consultants/AddPharmacist';
import AddDermatologist from './../pages/pharmacyadmin/consultants/AddDermatologist';
import DrugOrders from './../pages/pharmacyadmin/orders/Container';
import Promotion from './../pages/pharmacyadmin/promotions/AddPromotion';
import DrugsInPharmacy from './../pages/pharmacyadmin/drugs/Container';
import ViewPredefined from './../pages/pharmacyadmin/consultations/ViewPredefined'
import AddPredefined from './../pages/pharmacyadmin/consultations/AddPredefined'

import Supplier from './../pages/supplier/Container';
import SupplierLanding from './../pages/supplier/landing/Landing';
import Orders from './../pages/supplier/orders/Orders';
import Offers from './../pages/supplier/offers/Offers';
import SupplierProfile from './../pages/supplier/profile/Profile';



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
            },
            {
                component: PatientReservations,
                name: 'drugreservation',
                path: 'drugreservation'
            },
            {
                component: Subscriptions,
                name: 'subscriptions',
                path: 'subscriptions'
            },
            {
                component: EPrescription,
                name: 'eprescription',
                path: 'eprescription'
            },
            {
                component: MyEPrescriptions,
                name: 'myeprescriptions',
                path: 'myeprescriptions'
            },
            {
                component: Consultations,
                name: 'patientconsultations',
                path: 'patientconsultations'
            },
            {
                component: PharmacistSearchPatient,
                name: 'pharmacistsearch',
                path: 'pharmacists'
            },
            {
                component: DermatologistSearchPatient,
                name: 'dermatologistsearch',
                path: 'dermatologists'
            },
            {
                component: DermatologistHistoryPatient,
                name: 'historydermatologist',
                path: 'historydermatologist'
            },
            {
                component: PharmacistHistoryPatient,
                name: 'historypharmacist',
                path: 'historypharmacist'
            },
            {
                component: PharmacyProfile,
                name: 'patientpharmacyprofile',
                path: 'pharmacy/:id',
                props: true
            },
            {
                component: PatientRating,
                name: 'rating',
                path: 'rating'
            },
            {
                component: PatientPharmaciesRating,
                name: 'ratingpharmacies',
                path: 'ratingpharmacies'
            },
            {
                component: PatientDrugsRating,
                name: 'ratingdrugs',
                path: 'ratingdrugs'
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
                name: 'Pharmacistprofile',
                path: 'profile'
            },
            {
                component: PharmacistPatientsearch,
                name: 'pphatientsearch',
                path: 'pphatientsearch'
            },
            {
                component: Drugs,
                name: 'pharmacistdrugs',
                path: 'pharmacistdrugs'
            },
            {
                component: DispensingDrugs,
                name: 'dispensingdrugs',
                path: 'dispensingdrugs'
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
                component: DermatologistProfile,
                name: 'Dermatologistprofile',
                path: 'profile'
            },
            {
                component: Drugs,
                name: 'dermatologistdrugs',
                path: 'dermatologistdrugs'
            },
            {
                component: DermatologistPatientsearch,
                name: 'dermatologistpatientsearch',
                path: 'dpatientsearch'
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
            {
                component: SystemAdminComplaints,
                name: 'systemadmincomplaints',
                path: 'systemadmincomplaints'
            },
            {
                component: VacationRequests,
                name: 'vacationrequests',
                path: 'vacationrequests'
            }
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
                path: 'alldrugs'
            },
            {
                component: VacationRequests,
                name: 'pharmacyvacationrequests',
                path: 'vacationrequests'
            },
            {
                component: PharmacistSearch,
                name: 'pharmacypharmacistsearch',
                path: 'pharmacists'
            },
            {
                component: DermatologistSearch,
                name: 'pharmacydermatologistsearch',
                path: 'dermatologists'
            },
            {
                component: DrugOrders,
                name: 'pharmacydrugorders',
                path: 'orders'
            },
            {
                component: Promotion,
                name: 'addpromotion',
                path: 'promotion'
            },
            {
                component: DrugsInPharmacy,
                name: 'drugsinpharmacy',
                path: 'drugs'
            },
            {
                component: AddPredefined,
                name: 'pharmacyadminaddpredefined',
                path: 'predefined/add'
            },
            {
                component: ViewPredefined,
                name: 'pharmacyadminviewpredefined',
                path: 'predefined'
            },
            {
                component: AddDermatologist,
                name: 'pharmacyadmindermatologistadd',
                path: 'dermatologists/add'
            },
            {
                component: AddPharmacist,
                name: 'pharmacyadminpharmacistadd',
                path: 'pharmacists/add'
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
            {
                component: SupplierProfile,
                name: 'supplierprofile',
                path: 'supplierprofile'
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
