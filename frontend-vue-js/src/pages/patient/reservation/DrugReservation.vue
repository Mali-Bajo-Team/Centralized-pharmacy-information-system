<template>
  <v-container>
    <v-card
      width="600px"
      class="mx-auto mb-15"
      v-for="reservation in reservations"
      :key="reservation.reservationId"
    >
      <v-card-title>Reservation {{ reservation.reservationId }}</v-card-title>
      <v-row>
        <v-col xl="8" md="8" sm="12">
          <v-card-text
            >{{ reservation.drugName }}, {{ reservation.pharmacyName }},
            {{ reservation.amount }},
            {{ convertMsToString(reservation.deadline) }}</v-card-text
          >
          <v-spacer></v-spacer>
        </v-col>
        <v-col>
          <v-card-actions class="pb-4">
            <v-spacer> </v-spacer>
            <v-btn
              elevation="0"
              left
              top
              class="ma-5"
              fab
              dark
              small
              color="red lighten-2"
              @click="cancelDrugReservation(reservation)"
            >
              <v-icon dark>mdi-delete</v-icon></v-btn
            >
          </v-card-actions>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script>
import { getStringDateFromMilliseconds } from "./../../../util/dateHandler";
import { getParsedToken } from "./../../../util/token";
export default {
  data: () => ({
    reservations: [],
  }),
  mounted() {
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_RESERVATIONS_ENDPOINT,
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
        this.reservations = resp.data;
      })
      .catch((error) => {
        alert("Error: " + error);
      });
  },
  methods: {
    convertMsToString(ms) {
      return getStringDateFromMilliseconds(ms);
    },
    cancelDrugReservation(reservation) {
      alert("Pozvana funkcija" + reservation.reservationId);
      this.axios
        .delete(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PATIENT_CANCELLED_RESERVATIONS_ENDPOINT,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
            data: {
              reservationId: reservation.reservationId,
              pharmacyID: reservation.pharmacyID,
              drugCode: reservation.drugCode,
              amount: reservation.amount,
            },
          }
        )
        .then(() => {
          alert("Successfully cancelled reservation.");
        })
        .catch((error) => {
          alert(error);
        });
    },
  },
};
</script>