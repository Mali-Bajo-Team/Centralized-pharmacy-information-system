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
          let tempObj = {
            showCancelConsultationDialog: false,
            consultantEmail: consultation.consultantEmail,
            consultantId: consultation.consultantId,
            consultantName: consultation.consultantName,
            consultantSurname: consultation.consultantSurname,
            endDate: consultation.endDate,
            id: consultation.id,
            patientId: consultation.patientId,
            patientName: consultation.patientName,
            patientSurname: consultation.patientSurname,
            pharmacyID: consultation.pharmacyID,
            pharmacyName: consultation.pharmacyName,
            price: consultation.price,
            startDate: consultation.startDate,
          };
          this.patientConsultations.push(tempObj);
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
          let tempObj = {
            showCancelConsultationDialog: false,
            consultantEmail: consultation.consultantEmail,
            consultantId: consultation.consultantId,
            consultantName: consultation.consultantName,
            consultantSurname: consultation.consultantSurname,
            endDate: consultation.endDate,
            id: consultation.id,
            patientId: consultation.patientId,
            patientName: consultation.patientName,
            patientSurname: consultation.patientSurname,
            pharmacyID: consultation.pharmacyID,
            pharmacyName: consultation.pharmacyName,
            price: consultation.price,
            startDate: consultation.startDate,
          };
          this.patientConsultations.push(tempObj);
        }
      })
      .catch((error) => {
        alert(error);
      });
  },
};
</script>