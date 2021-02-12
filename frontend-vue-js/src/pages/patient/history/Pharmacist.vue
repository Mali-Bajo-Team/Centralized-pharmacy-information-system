<template>
  <v-container>
    <v-row>
      <!--Left column-->
      <v-col xl="4" sm="12" md="4">
        <v-card mt="5">
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2">Sort my consultations</v-toolbar-title>
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
              <v-icon small right dark>mdi-arrow-up-bold</v-icon>
            </v-btn>
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text ml-6"
              @click="sortDownBy('price')"
            >
              Price
              <v-icon small right dark>mdi-arrow-down-bold</v-icon>
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
              <v-icon small right dark>mdi-arrow-up-bold</v-icon>
            </v-btn>
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text ml-6"
              @click="sortDownBy('startDate')"
            >
              Date
              <v-icon small right dark>mdi-arrow-down-bold</v-icon>
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
              <v-icon small right dark>mdi-arrow-up-bold</v-icon>
            </v-btn>
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text ml-6"
              @click="sortDownBy('consultationDuration')"
            >
              Duration
              <v-icon small right dark>mdi-arrow-down-bold</v-icon>
            </v-btn>
          </v-row>
          <!-- End of the sort by duration -->
        </v-card>
      </v-col>
      <!--End of left column-->

      <!--Right column-->
      <v-col xl="8" sm="12" md="8">
        <v-card
          elevation="4"
          class="pa-4 mb-10"
          v-for="consultation in consultations"
          :key="consultation.id"
        >
          <v-card-title>
            <h2>{{consultation.consultantName + " " +consultation.consultantSurname}}</h2>
            <v-spacer></v-spacer>
            <v-chip color="success" class="ml-0">{{ consultation.price }}€</v-chip>
          </v-card-title>
          <v-card-title class="pt-0">
            <v-chip>{{ consultation.pharmacyName }}</v-chip>
          </v-card-title>
          <v-divider class="ml-5 mr-5"></v-divider>
          <v-card-text class="ml-2">
            <span class="mr-2">From</span>
            <v-chip color="primary">{{ consultation.startDate }}</v-chip>
            <span class="ml-2 mr-2">to</span>
            <v-chip color="primary">{{ consultation.endDate }}</v-chip>
          </v-card-text>
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
    consultations: []
  }),
  mounted() {
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_CONSULTATIONS_PHARMACIST_HISTORY_ENDPOINT,
        {
          email: getParsedToken().sub
        },
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
          }
        }
      )
      .then(resp => {
        this.consultations = [];
        for (let consultation of resp.data) {
          let tempObj = {
            id: consultation.id,
            consultantEmail: consultation.consultantEmail,
            consultantId: consultation.consultantId,
            consultantName: consultation.consultantName,
            consultantSurname: consultation.consultantSurname,
            endDate: new Date(consultation.endDate).toLocaleString(),
            startDate: new Date(consultation.startDate).toLocaleString(),
            consultationDuration: consultation.endDate - consultation.startDate,
            pharmacyID: consultation.pharmacyID,
            pharmacyName: consultation.pharmacyName,
            price: consultation.price
          };
          this.consultations.push(tempObj);
        }
      })
      .catch(error => {
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
    }
  }
};
</script>
