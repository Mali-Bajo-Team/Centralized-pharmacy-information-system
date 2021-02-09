<template>
  <v-app>
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
                to: "/dermatologist"
            },
            {
                title: "Profile",
                to: "/dermatologist/profile"
            },
            {
                title: "Drugs",
                to: "/dermatologist/dermatologistdrugs",
            },
            {
                title: "Patient search",
                to: "/dermatologist/dpatientsearch",
            },
        ]
    })
}
</script>