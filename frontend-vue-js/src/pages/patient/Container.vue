<template>
  <v-app style="background: rgba(0, 0, 0, 0)">
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
        children: [
          {
            title: "Drugs search",
            to: "/patient/patientdrugs",
          },
          {
            title: "Reservations",
            to: "/patient/drugreservation",
          },
        ],
      },
      {
        title: "Pharmacies",
        children: [
          {
            title: "Pharmacies search",
            to: "/patient/patientpharmacies",
          },
          {
            title: "Subscriptions",
            to: "/patient/subscriptions",
          },
          {
            title: "Consultations",
            to: "/patient/patientconsultations",
          },
        ],
      },

      {
        title: "EPrescription",
        children: [
          {
            title: "QR code search",
            to: "/patient/eprescription",
          },
          {
            title: "My EPrescriptions",
            to: "/patient/myeprescriptions",
          },
        ],
      },

      {
        title: "Consultants",
        children: [
          {
            title: "Pharmacists",
            to: "/patient/pharmacists",
          },
          {
            title: "Dermatologists",
            to: "/patient/dermatologists",
          },
        ],
      },
      {
        title: "History",
        children: [
          {
            title: "Pharmacist",
            to: "/patient/historypharmacist",
          },
          {
            title: "Dermatologist",
            to: "/patient/historydermatologist",
          },
        ],
      },
      {
        title: "Rating",
        to: "/patient/rating",
      },
    ],
  }),
};
</script>