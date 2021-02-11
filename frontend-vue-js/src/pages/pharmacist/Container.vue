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
        to: "/pharmacist",
      },
      {
        title: "Profile",
        to: "/pharmacist/profile",
      },
      {
        title: "Patient Search",
        to: "/pharmacist/pphatientsearch",
      },
      {
        title: "Drugs",
        to: "/pharmacist/pharmacistdrugs",
      },
      {
        title: "Dispensing",
        to: "/pharmacist/dispensingdrugs",
      },
    ],
  }),
};
</script>