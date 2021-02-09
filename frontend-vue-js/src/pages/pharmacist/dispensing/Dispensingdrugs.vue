<template>
  <v-app>
    <v-row>
      <v-col> </v-col>
      <v-col>
        <h1 class="primary--text">Dispensing drugs</h1>
        <h5 class="mt-5 primary--text">Check is reservation valid</h5>
        <v-text-field
          filled
          label="Reservation identificator"
          placeholder=""
          v-model="reservationID"
        ></v-text-field>
        <v-btn depressed @click="checkDrugValidation" color="primary">
          Check
        </v-btn>
        <v-alert
          :value="validationAlert"
          color="pink"
          dark
          border="top"
          icon="mdi-pill"
          transition="scale-transition"
        >
          The reservation identificator is not valid! Or it is already dispensed!
        </v-alert>
        <v-alert
          :value="dispenseSuccs"
          color="green"
          dark
          border="top"
          icon="mdi-pill"
          transition="scale-transition"
        >
          The reservation succesfully dispensed !
        </v-alert>

        <!-- Dialog for disepnsing reservation -->
        <v-dialog v-model="dialog" width="500">
          <v-card>
            <v-card-title class="headline grey lighten-2">
              Confirm dispensing
            </v-card-title>

            <v-card-text>
              <div class="mt-4">
                <h4>
                  Reservation identificator : {{ reservation.reservationID }}
                </h4>
                <h4>Amount : {{ reservation.amount }}</h4>
                <h4>Phatient name : {{ reservation.phatientName }}</h4>
                <h4>Pharmacy name : {{ reservation.pharmacyName }}</h4>
                <h4>Status : {{ reservation.valid }}</h4>
              </div>
            </v-card-text>
            <v-divider></v-divider>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="primary"
                text
                @click="
                  dispensDrug(reservation.reservationID), (dialog = false)
                "
              >
                Confirm
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-col>
      <v-col> </v-col>
    </v-row>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    reservation: "",
    reservationID: null,
    validationAlert: false,
    dialog: false,
    dispenseSuccs: false,
  }),
  methods: {
    checkDrugValidation() {
      var token = parseJwt(localStorage.getItem("JWT-CPIS"));
      var email = token.sub;

      if (this.reservationID !== null && this.reservationID !== "") {
        this.axios
          .post(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_DRUGVALIDATIONCHECK_ENDPOINT,
            {
              reservationID: this.reservationID,
              consultantEmail: email,
            },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
              },
            }
          )
          .then((resp) => {
            this.reservation = resp.data;
            if (this.reservation.valid === false) {
              this.validationAlert = true;
            } else {
              this.validationAlert = false;
              this.dialog = true;
            }
          });
      } else {
        this.validationAlert = true;
      }
    },
    dispensDrug() {
      var token = parseJwt(localStorage.getItem("JWT-CPIS"));
      var email = token.sub;
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_DISPENSEDRUG_ENDPOINT,
          {
            reservationID: this.reservationID,
            consultantEmail: email,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((resp) => {
            console.log(resp);
          this.dispenseSuccs = true;
        });
    },
  },
};

function parseJwt(token) {
  var base64Payload = token.split(".")[1];
  var payload = Buffer.from(base64Payload, "base64");
  return JSON.parse(payload.toString());
}
</script>
<style>
</style>