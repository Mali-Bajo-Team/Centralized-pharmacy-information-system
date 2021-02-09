<template>
  <v-container>
    <h3>Subscriptions</h3>
  </v-container>
</template>

<script>
import { getParsedToken } from "./../../../util/token";
export default {
  data: () => ({
    patientEmail: getParsedToken().sub,
    pharmaciesSubscriptions: [],
  }),
  mounted() {
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_SUBSCIPTIONS_ENDPOINT,
        {
          email: getParsedToken().sub,
        },
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        this.pharmaciesSubscriptions = resp.data;
        alert("success")
      })
      .catch((error) => {
        alert("Error: " + error);
      });
  },
  methods: {},
};
</script>