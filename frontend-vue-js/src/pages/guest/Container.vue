<template>
  <v-app>
    <navbar v-bind:items="links" v-bind:logout="false"></navbar>
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
        to: "/",
      },
      {
        title: "Drugs",
        to: "drugs",
      },
      {
        title: "Pharmacies",
        to: "pharmacies",
      },
      {
        title: "Log in",
        to: "login",
      },
      {
        title: "Register",
        to: "register",
      },
    ],
  }),
};
</script>