<template>
  <v-container>
    <v-row>
      <!--Left column-->
      <v-col xl="4" sm="12" md="4">
        <v-card mt="5">
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2">
              Sort my EPrescriptions
            </v-toolbar-title>
          </v-toolbar>
          <br />
          <!-- Sort by date -->
          <v-row justify="center" class="pa-3">
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text mr-6"
            >
              Date
              <v-icon small right dark> mdi-arrow-up-bold </v-icon>
            </v-btn>
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text ml-6"
            >
              Date
              <v-icon small right dark> mdi-arrow-down-bold </v-icon>
            </v-btn>
          </v-row>
        </v-card>
        <!-- End of the sort by date -->
        <br /><br />
        <!--Toolbar of the card-->
        <v-card>
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2">
              Filter my EPrescriptions by status
            </v-toolbar-title>
          </v-toolbar>

          <!--End of toolbar of the card-->

          <!--Filter form-->
          <v-card-text> </v-card-text>
        </v-card>

        <!--End of filter form-->
        <br /><br />
      </v-col>
      <!--End of left column-->

      <!--Right column-->
      <v-col xl="8" sm="12" md="8">
        <v-card
          class="pa-2 ml-16 mr-16 mb-10"
          v-for="prescription in prescriptions"
          :key="prescription.prescriptionId"
        >
          <v-card-subtitle>
            <h3 class="ml-3">
              {{ prescription.status }}
            </h3>
          </v-card-subtitle>
          <v-spacer></v-spacer>
        </v-card>
      </v-col>
      <!--End of right column-->
    </v-row>
  </v-container>
</template>

<script>
import { getParsedToken } from "./../../../util/token";
export default {
  data: () => ({
    prescriptions: [],
  }),
  mounted() {
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_MY_EPRESCRIPTIONS_ENDPOINT,
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
        this.prescriptions = resp.data;
      })
      .catch((error) => {
        alert(error);
      });
  },
};
</script>