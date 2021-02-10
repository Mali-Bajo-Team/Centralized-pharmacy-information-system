<template>
  <v-container>
    <h3>Consultations</h3>
  </v-container>
</template>

<script>
import { getParsedToken } from "./../../../util/token";
export default {
  data: () => ({
    patientConsultations: [],
  }),
  methods: {},
  mounted() {
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_DERMATOLOGIST_CONSULTATION_ENDPOINT,
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
        console.log("success get of the patient dermatologist consultations");
        for (let consultation of resp.data) {
          this.patientConsultations.push(consultation);
        }
      })
      .catch((error) => {
        alert(error);
      });

    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_PHARMACIST_CONSULTATION_ENDPOINT,
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
        console.log("success get of the patient pharmacist consultations");
        for (let consultation of resp.data) {
          this.patientConsultations.push(consultation);
        }
      })
      .catch((error) => {
        alert(error);
      });
  },
};
</script>