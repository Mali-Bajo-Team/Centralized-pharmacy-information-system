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
    navbar
  },
  data: () => ({
    links: [
      {
        title: "Home",
        to: "/patient"
      },
      {
        title: "Profile",
        to: "/patient/profile"
      },
      {
        title: "Drugs",
        to: "/patient/patientdrugs"
      },
      {
        title: "Pharmacies",
        to: "/patient/patientpharmacies"
      },
      {
        title: "Reservations",
        to: "/patient/drugreservation"
      },
      {
        title: "Subscriptions",
        to: "/patient/subscriptions"
      },
      {
        title: "EPrescription",
        children:[
          {
            title: "QR code search",
            to: "/patient/eprescription"
          },
          {
            title: "My EPrescriptions",
            to : "/patient/myeprescriptions"
          }
        ]
      },
      {
        title: "Consultations",
        to: "/patient/patientconsultations"
      },
      {
        title: "Consultants",
        children: [
          {
            title: "Pharmacists",
            to: "/patient/pharmacists"
          },
          {
            title: "Dermatologists",
            to: "/patient/dermatologists"
          }
        ]
      },
      {
        title: "History",
        children:[
          {
            title: "Pharmacist",
            to:"/patient/historypharmacist"
          },
          {
            title: "Dermatologist",
            to:"/patient/historydermatologist"
          }
        ]
      },
      {
        title:"Rating",
        to:"/patient/rating"
      }
    ]
  })
};
</script>