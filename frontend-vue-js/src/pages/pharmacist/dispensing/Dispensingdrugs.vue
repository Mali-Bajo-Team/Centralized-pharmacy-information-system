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
            The reservation identificator is not valid !
        </v-alert>
      </v-col>
      <v-col> </v-col>
    </v-row>
  </v-app>
</template>

<script>
export default {
  data: () => ({
    reservation: null,
    reservationID: null,
    validationAlert: false, 
  }),
  methods: {
    checkDrugValidation() {
      var token = parseJwt(localStorage.getItem("JWT-CPIS"));
      var email = token.sub;

      this.consultants = [];

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
          if(this.reservation.valid === false){
              this.validationAlert = true;
          }else{
              this.validationAlert = false;
          }
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