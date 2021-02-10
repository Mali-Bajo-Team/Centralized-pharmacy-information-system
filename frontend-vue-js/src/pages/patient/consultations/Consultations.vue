<template>
  <v-container>
    <v-row>
      <v-col>
        <v-card
          elevation="4"
          class="pa-4 ml-16 mr-16 mb-10"
          v-for="consultation in patientConsultations"
          :key="consultation.id"
        >
          <!-- Row for title & cancel consulation dialog-->
          <v-row align="center">
            <v-card-subtitle>
              <h4 class="ml-3">
                {{ consultation.pharmacyName }} ,
                {{ consultation.consultantName }}
                {{ consultation.consultantSurname }} ,
                {{ convertMsToString(consultation.startDate) }}
              </h4>
            </v-card-subtitle>
            <v-spacer></v-spacer>
            <!-- Make a response dialog -->
            <v-dialog
              v-model="consultation.showCancelConsultationDialog"
              width="500"
              :retain-focus="false"
            >
              <template #activator="{ on: dialog }">
                <v-tooltip bottom>
                  <template #activator="{ on: tooltip }">
                    <v-btn
                      v-on="{ ...tooltip, ...dialog }"
                      elevation="0"
                      left
                      class="ma-5"
                      fab
                      dark
                      small
                      color="red lighten-2"
                      v-if="
                        isAvailableCancelConsultation(consultation.startDate)
                      "
                      @click="setConsultationForCancel(consultation)"
                    >
                      <v-icon dark> mdi-delete </v-icon>
                    </v-btn>
                  </template>
                  <span> Cancel consultation </span>
                </v-tooltip>
              </template>
              <v-card>
                <!--Toolbar of the card-->
                <v-toolbar color="primary" dark dense flat>
                  <v-toolbar-title class="body-2">
                    <h3>Cancel consultation</h3>
                  </v-toolbar-title>
                </v-toolbar>
                <!-- End of the toolbar of the card -->
                <v-card-text class="pa-5">
                  <br />
                  Are you sure you want cancel consultation on
                  {{ consultation.pharmacyName }} ,
                  {{ consultation.consultantName }}
                  {{ consultation.consultantSurname }}
                </v-card-text>

                <v-card-actions class="pb-4">
                  <v-spacer> </v-spacer>
                  <v-btn
                    color="success"
                    dark
                    depressed
                    @click="confirmCancelConsultation()"
                  >
                    <v-icon dark left> mdi-checkbox-marked-circle </v-icon>
                    Confirm
                  </v-btn>
                  <v-btn
                    color="red lighten-3"
                    dark
                    depressed
                    @click="
                      consultation.showCancelConsultationDialog = !consultation.showCancelConsultationDialog
                    "
                    ><v-icon dark left> mdi-minus-circle </v-icon>
                    Close
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <!-- End of the make response dialog -->
          </v-row>
          <!-- End of the row for title & cancel consulation dialog-->
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { getParsedToken } from "./../../../util/token";
import {
  getStringDateWithTimeFromMilliseconds,
  getTodayDateString,
  isAvailableToCancelConsultation,
} from "./../../../util/dateHandler";

export default {
  data: () => ({
    patientConsultations: [],
    consultationDTO: {
      email: "",
      consultationId: 0,
    },
  }),
  methods: {
    isAvailableCancelConsultation(consultationDate) {
      if (isAvailableToCancelConsultation(consultationDate)) {
        return true;
      }
      console.log("not valid date !!");

      return false;
    },
    convertMsToString(ms) {
      return getStringDateWithTimeFromMilliseconds(ms);
    },
    getTodayDateInString() {
      return getTodayDateString();
    },
    setConsultationForCancel(consultation) {
      this.consultationDTO.email = consultation.consultantEmail;
      this.consultationDTO.id = consultation.id;
    },
    confirmCancelConsultation() {
       this.axios
        .delete(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PATIENT_CANCEL_CONSULTATION_ENDPOINT,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
            data: {
                email: this.consultationDTO.email,
                consultationId: this.consultationDTO.id
            },
          }
        )
        .then(() => {
          alert("Successfully cancelled consultation.");
            for(let tempConsultation of this.patientConsultations){
                if(tempConsultation.id == this.consultationDTO.id){
                    this.patientConsultations.pop(tempConsultation);
                    break;
                }
            }
        })
        .catch((error) => {
          alert(error);
        });
    },
  },
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