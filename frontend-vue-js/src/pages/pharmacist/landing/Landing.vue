<template>
  <v-container>
    <h3>Pharmacist</h3>
    <v-date-picker v-model="picker"  v-bind:value="valueDate"
      v-on:input="onInputDate" color="green lighten-1"></v-date-picker>
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
       picker: new Date().toISOString().substr(0, 10),
    examinationTime: null,
    value: null,
    valueDate: null,
  }),
  methods: {
    onInput(value) {
      this.$emit("input", value);
      this.examinationTime = value;
    }, onInputDate(valueDate) {
      this.$emit("input", valueDate);
         this.valueDate = valueDate;
      console.log(valueDate);
    },
    scheduleConsultation() {
      var token = parseJwt(localStorage.getItem("JWT-CPIS"));
      var email = token.sub;

      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            "api/consultations/scheduleconsultation",
          {
            consultantEmail: email,
            startDate: this.valueDate + " " + this.examinationTime + ":00",
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