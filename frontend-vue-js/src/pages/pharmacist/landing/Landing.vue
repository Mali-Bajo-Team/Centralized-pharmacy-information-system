<template>
  <v-container>
    <h3>Pharmacist</h3>
    <v-time-picker
      v-bind:value="value"
      v-on:input="onInput"
      format="ampm"
    ></v-time-picker>
    <v-btn depressed @click="scheduleConsultation" color="primary">
      Schedule
    </v-btn>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    examinationTime: null,
    value: null,
  }),
  methods: {
    onInput(value) {
      this.$emit("input", value);
      this.examinationTime = value;
    },
    scheduleConsultation() {
      var token = parseJwt(localStorage.getItem("JWT-CPIS"));
      var email = token.sub;

      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            "api/consultations/scheduleconsultation",
          {   consultantEmail: email,
               startDate: '2021-02-03 ' + this.examinationTime +':00'
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((resp) => {
          this.pharmacist = resp.data;
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