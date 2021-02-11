<template>  
  <v-app style="background: rgba(0,0,0,0);">
    <navbar v-bind:items="links"></navbar>
    <v-main>
      <v-container fluid>
        <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import navbar from "../../components/Navbar";

export default {
  components: {
    navbar,
  },
  data: () => ({
    links: [
      {
        title: "Home",
        to: "/patient",
      },
      {
        title: "Profile",
        to: "/patient/profile",
      },
      {
        title: "Drugs",
        to: "/patient/patientdrugs",
      },
      {
        title: "Pharmacies",
        to: "/patient/patientpharmacies",
      },
      {
        title: "Reservations",
        to: "/patient/drugreservation",
      },
      {
        title: "Subscriptions",
        to: "/patient/subscriptions",
      },
      {
        title: "EPrescription",
        to: "/patient/eprescription",
      },
      {
        title: "Consultations",
        to: "/patient/patientconsultations",
      },
    ],
  }),
};
</script>