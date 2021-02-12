<template>
  <v-container>
    <v-row>
      <!--Left column-->
      <v-col xl="4" md="4" sm="12">
        <v-card>
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2">
              Insert date and time of schedulling
            </v-toolbar-title>
          </v-toolbar>
          <!--End of toolbar of the card-->
          <v-card-text>
            <v-form>
              <v-text-field
                type="date"
                label="Insert date of reservation"
                v-model="dateBind"
              >
              </v-text-field>
              <br />
              <v-menu
                ref="menu"
                v-model="menu2"
                :close-on-content-click="false"
                :nudge-right="40"
                :return-value.sync="time"
                transition="scale-transition"
                offset-y
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                    v-model="time"
                    label="Picker in menu"
                    prepend-icon="mdi-clock-time-four-outline"
                    readonly
                    v-bind="attrs"
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-time-picker
                  v-if="menu2"
                  v-model="time"
                  full-width
                  @click:minute="$refs.menu.save(time)"
                ></v-time-picker>
              </v-menu>

              <v-btn
                width="110px"
                color="primary"
                class="ma-2 white--text mr-6"
                @click="chooseDateAndTimeForSchedulling()"
              >
                CHOOSE
                <v-icon small right dark> mdi-checkbox-marked-circle </v-icon>
              </v-btn>
            </v-form>
          </v-card-text>
        </v-card>
        <br /><br />
        <v-card mt="5">
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2"> Sort pharmacies </v-toolbar-title>
          </v-toolbar>
          <br />
          <!-- Sort by price -->
          <v-row justify="center" class="pa-3">
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text mr-6"
              @click="sortBy('consultationPrice')"
            >
              Price
              <v-icon small right dark> mdi-arrow-up-bold </v-icon>
            </v-btn>
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text ml-6"
              @click="sortDownBy('consultationPrice')"
            >
              Price
              <v-icon small right dark> mdi-arrow-down-bold </v-icon>
            </v-btn>
            <br />
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text mr-6"
              @click="sortBy('pharmacyRating')"
            >
              Rating
              <v-icon small right dark> mdi-arrow-up-bold </v-icon>
            </v-btn>
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text ml-6"
              @click="sortDownBy('pharmacyRating')"
            >
              Rating
              <v-icon small right dark> mdi-arrow-down-bold </v-icon>
            </v-btn>
          </v-row>
        </v-card>
      </v-col>
      <!--End of the left column-->
      <!--Right column-->
      <v-col xl="8" md="8" sm="12">
        <v-card
          elevation="4"
          class="pa-4 ml-10 mb-10"
          v-for="consultation in consultations"
          :key="consultation.pharmacyId"
        >
          <v-card-title>
            {{ consultation.pharmacyName }}
          </v-card-title>

          <v-card-subtitle>
            {{ consultation.pharmacyLocation }} ,
            {{ consultation.consultationPrice }} $,
            {{ consultation.pharmacyRating }}</v-card-subtitle
          >

          <v-card-actions>
            <v-btn color="orange lighten-2" text> Show free pharmacists </v-btn>

            <v-spacer></v-spacer>

            <v-btn
              icon
              color="orange lighten-2"
              @click="
                consultation.showPharmacyFreePharmacist = !consultation.showPharmacyFreePharmacist
              "
            >
              <v-icon>{{
                consultation.showPharmacyFreePharmacist
                  ? "mdi-chevron-up"
                  : "mdi-chevron-down"
              }}</v-icon>
            </v-btn>
          </v-card-actions>

          <v-expand-transition>
            <div v-show="consultation.showPharmacyFreePharmacist">
              <v-divider></v-divider>
              <v-card height="40px">
                <v-row align="center" justify="center">
                  <v-btn
                    small
                    width="110px"
                    color="green lighten-2"
                    class="ma-2 white--text mr-6"
                    @click="
                      sortArrayBy(
                        'consultantRating',
                        consultation.freePharmacist
                      )
                    "
                    >Rating
                    <v-icon small right dark> mdi-arrow-up-bold </v-icon>
                  </v-btn>
                  <v-btn
                    small
                    width="110px"
                    color="green lighten-2"
                    class="ma-2 white--text mr-6"
                    @click="
                      sortArrayDownBy(
                        'consultantRating',
                        consultation.freePharmacist
                      )
                    "
                  >
                    Rating
                    <v-icon small right dark> mdi-arrow-down-bold </v-icon>
                  </v-btn>
                </v-row>
              </v-card>
              <v-divider></v-divider>

              <v-card
                class="mt-5"
                v-for="pharmacist in consultation.freePharmacist"
                :key="pharmacist.consultantId"
              >
                <v-row>
                  <v-col>
                    <v-card-text>
                      <h3>
                        {{ pharmacist.consultantName }}
                        {{ pharmacist.consultantSurname }}
                      </h3>
                    </v-card-text>
                  </v-col>
                  <v-col>
                    <v-rating
                      color="accent"
                      v-model="pharmacist.consultantRating"
                    >
                    </v-rating>
                  </v-col>
                  <v-divider></v-divider>
                  <v-col>
                    <v-card-actions>
                      <v-btn
                        color="primary"
                        @click="scheduleExamination(pharmacist, consultation)"
                      >
                        Schedule
                      </v-btn>
                    </v-card-actions>
                  </v-col>
                </v-row>
              </v-card>
            </div>
          </v-expand-transition>
        </v-card>
      </v-col>
      <!--End of the right column-->
    </v-row>
  </v-container>
</template>
<script>
import {
  getStringDateFromMilliseconds,
  getTodayDateString,
} from "./../../../util/dateHandler";

// import { getParsedToken } from "./../../../util/token";

export default {
  data() {
    return {
      consultations: [],
      dateBind: "",
      menu2: false,
      time: "",
      dateAndTimeSchedullingDTO: {
        examinationStart: "",
      },
    };
  },

  methods: {
    scheduleExamination(pharmacist, consultation) {
      console.log(pharmacist + " " + consultation);
      alert("hasas");
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CONSULTATIONS_SCHEDULE,
          {
            // startDate:  this.dateBind + " " + this.time + ":00",
            // patientEmail: "cpisuser+dragana@gmail.com",
            // consultantId: pharmacist.consultantId,
            // pharmacyID: consultation.pharmacyId,
            // consultantEmail: pharmacist.consultantEmail,
            // patientId: 2,
            startDate: "2022-01-01 12:00:00",
            patientEmail: "cpisuser+dragana@gmail.com",
            consultantId: 13,
            pharmacyID: 1,
            consultantEmail: "cpisuser+pharmacist13@gmail.com",
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          alert("Successfuly, check email for more information");
        })
        .catch((error) => {
          alert("Error: " + error);
        });
    },
    sortBy(prop) {
      this.consultations.sort((a, b) => (a[prop] < b[prop] ? -1 : 1));
    },
    sortArrayBy(prop, array) {
      array.sort((a, b) => (a[prop] < b[prop] ? -1 : 1));
    },
    sortDownBy(prop) {
      this.consultations.sort((a, b) => (a[prop] > b[prop] ? -1 : 1));
    },
    sortArrayDownBy(prop, array) {
      array.sort((a, b) => (a[prop] > b[prop] ? -1 : 1));
    },
    isDateValid() {
      var todayDate = Date.parse(getTodayDateString());
      var examinationStartDate = Date.parse(
        getStringDateFromMilliseconds(this.dateBind)
      );
      if (todayDate > examinationStartDate) return false;
      return true;
    },
    chooseDateAndTimeForSchedulling() {
      if (!this.isDateValid() || this.dateBind == "" || this.time == "") {
        alert("Not valid date.");
        return;
      }
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PATIENT_SCHEDULLING_CONSULTATIONS_ENDPOINT,
          {
            examinationStart: this.dateBind + " " + this.time + ":00",
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((resp) => {
          alert("Successfuly");
          this.consultations = [];
          for (let consultation of resp.data) {
            let tempObj = {
              showPharmacyFreePharmacist: false,
              consultationPrice: consultation.consultationPrice,
              freePharmacist: consultation.freePharmacist,
              pharmacyId: consultation.pharmacyId,
              pharmacyLocation: consultation.pharmacyLocation,
              pharmacyName: consultation.pharmacyName,
              pharmacyRating: consultation.pharmacyRating,
            };

            this.consultations.push(tempObj);
          }
        })
        .catch((error) => {
          alert("Error: " + error);
        });
    },
  },
};
</script>