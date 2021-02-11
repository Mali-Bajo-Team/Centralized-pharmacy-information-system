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
        to: "/systemadmin",
      },
      {
        title: "Loyalty Program",
        to: "/systemadmin/loyaltyprogram",
      },
      {
        title: "Drugs",
        to: "/systemadmin/systemadmindrugs",
      },
      {
        title:"Pharmacies",
        to:"/systemadmin/systemadminpharmacies"
      },
      {
        title: "Complaints",
        to: "/systemadmin/systemadmincomplaints"
      },
      {
        title: "Vacation requests",
        to: "/systemadmin/vacationrequests"
      }
    ],
  }),
};
</script>