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
          <br />
          <!-- Sort by price -->
          <v-row justify="center" class="pa-3">
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text mr-6"
              @click="sortBy('price')"
            >
              Price
              <v-icon small right dark> mdi-arrow-up-bold </v-icon>
            </v-btn>
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text ml-6"
              @click="sortDownBy('price')"
            >
              Price
              <v-icon small right dark> mdi-arrow-down-bold </v-icon>
            </v-btn>
          </v-row>
          <!-- End of the sort by price -->
          <!-- Sort by date -->
          <v-row justify="center" class="pa-3">
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text mr-6"
              @click="sortBy('startDate')"
            >
              Date
              <v-icon small right dark> mdi-arrow-up-bold </v-icon>
            </v-btn>
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text ml-6"
              @click="sortDownBy('startDate')"
            >
              Date
              <v-icon small right dark> mdi-arrow-down-bold </v-icon>
            </v-btn>
          </v-row>
          <!-- End of the sort by date -->
          <!-- Sort by duration -->
          <v-row justify="center" class="pa-3">
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text mr-6"
              @click="sortBy('consultationDuration')"
            >
              Duration
              <v-icon small right dark> mdi-arrow-up-bold </v-icon>
            </v-btn>
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text ml-6"
              @click="sortDownBy('consultationDuration')"
            >
              Duration
              <v-icon small right dark> mdi-arrow-down-bold </v-icon>
            </v-btn>
          </v-row>
          <!-- End of the sort by duration -->
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
        this.consultations = [];
        for (let consultation of resp.data) {
          let tempObj = {
            consultantEmail: consultation.consultantEmail,
            consultantId: consultation.consultantId,
            consultantName: consultation.consultantName,
            consultantSurname: consultation.consultantSurname,
            endDate: consultation.endDate,
            startDate: consultation.startDate,
            consultationDuration: consultation.endDate - consultation.startDate,
            patientId: consultation.patientId,
            patientName: consultation.patientName,
            patientSurname: consultation.patientSurname,
            pharmacyID: consultation.pharmacyID,
            pharmacyName: consultation.pharmacyName,
            price: consultation.price,
          };
          this.consultations.push(tempObj);
        }
      })
      .catch((error) => {
        alert(error);
      });
  },
  methods: {
    convertMsToString(ms) {
      return getStringDateWithTimeFromMilliseconds(ms);
    },
    sortBy(prop) {
      this.consultations.sort((a, b) => (a[prop] < b[prop] ? -1 : 1));
    },
    sortDownBy(prop) {
      this.consultations.sort((a, b) => (a[prop] > b[prop] ? -1 : 1));
    },
  },
};
</script>
