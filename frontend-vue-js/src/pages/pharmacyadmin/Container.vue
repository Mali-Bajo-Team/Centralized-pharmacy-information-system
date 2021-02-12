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
        to: "/pharmacyadmin"
      },
      {
        title: "Drugs",
        children: [
          {
            title: "All drugs",
            to: "/pharmacyadmin/alldrugs"
          },
          {
            title: "Drugs in pharmacy",
            to: "/pharmacyadmin/drugs"
          },
          {
            title: "Orders",
            to: "/pharmacyadmin/orders"
          },
          {
            title: "Add order",
            to: "/pharmacyadmin/orders/add"
          }
        ]
      },
      {
        title: "Employees",
        children: [
          {
            title: "Pharmacists",
            to: "/pharmacyadmin/pharmacists"
          },
          {
            title: "Add pharmacist",
            to: "/pharmacyadmin/pharmacists/add"
          },
          {
            title: "Dermatologists",
            to: "/pharmacyadmin/dermatologists"
          },
          {
            title: "Add dermatologist",
            to: "/pharmacyadmin/dermatologists/add"
          },
          {
            title: "Vacation requests",
            to: "/pharmacyadmin/vacationrequests"
          }
        ]
      },
      {
        title: "Consultations",
        children: [
          {
            title: "Add predefined",
            to: "/pharmacyadmin/predefined/add"
          },
          {
            title: "View predefined",
            to: "/pharmacyadmin/predefined"
          }
        ]
      },
      {
        title: "Add promotion",
        to: "/pharmacyadmin/promotion"
      },
      {
        title: "My profile",
        children: [
          {
            title: "Personal info",
            to: "/pharmacyadmin/profile"
          },
          {
            title: "Change password",
            to: "/pharmacyadmin/password"
          }
        ]
      }
    ]
  })
};
</script>