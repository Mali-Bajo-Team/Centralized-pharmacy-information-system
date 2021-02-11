<template>
  <v-container>
    <v-row>
      <!--Left column-->
      <v-col xl="4" sm="12" md="4">
        <v-card mt="5">
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2">
              Sort my consultations
            </v-toolbar-title>
          </v-toolbar>
        </v-card>
      </v-col>
      <!--End of left column-->

      <!--Right column-->
      <v-col xl="8" sm="12" md="8">
        <v-card
          v-for="consultation in consultations"
          :key="consultation.id"
          class="pa-2 ml-16 mr-16 mb-10"
        >
          <v-card-subtitle>
            <h3 class="ml-3">
              {{ consultation.pharmacyName }},
              {{ consultation.consultantName }}
              {{ consultation.consultantSurname }}
              <br />
              <br />
              {{ convertMsToString(consultation.startDate) }},
              {{ convertMsToString(consultation.endDate) }}
              <br />
              <br />
              {{ consultation.price }} $
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
import { getStringDateWithTimeFromMilliseconds } from "./../../../util/dateHandler";

export default {
  data: () => ({
    consultations: [],
  }),
  mounted() {
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_CONSULTATIONS_PHARMACIST_HISTORY_ENDPOINT,
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
        this.consultations = resp.data;
      })
      .catch((error) => {
        alert(error);
      });
  },
  methods: {
    convertMsToString(ms) {
      return getStringDateWithTimeFromMilliseconds(ms);
    },
  },
};
</script>
