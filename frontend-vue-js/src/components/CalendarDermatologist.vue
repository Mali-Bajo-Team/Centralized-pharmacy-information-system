<template>
  <v-app>
    <!-- SEARCH Pharmacis -->
    <div class="mb-3 mt-16">
      <h1 class="primary--text">Working calendar</h1>
      <h4 class="primary--text">
        Choose pharmacy for filtering working calendar
      </h4>
      <v-autocomplete
        v-model="model"
        :items="items"
        :loading="isLoading"
        :search-input.sync="search"
        chips
        clearable
        hide-details
        hide-selected
        item-text="name"
        item-value="symbol"
        label="Search for a pharmacy..."
        solo
      >
        <template v-slot:no-data>
          <v-list-item>
            <v-list-item-title>
              Search and choose
              <strong>pharmacy</strong>
            </v-list-item-title>
          </v-list-item>
        </template>
        <template v-slot:selection="{ attr, on, item, selected }">
          <v-chip
            v-bind="attr"
            :input-value="selected"
            color="blue-grey"
            class="white--text"
            v-on="on"
          >
            <v-icon left> mdi-medical-bag </v-icon>
            <span v-text="item.name"></span>
          </v-chip>
        </template>
        <template v-slot:item="{ item }">
          <v-list-item-avatar
            color="primary"
            class="headline font-weight-light white--text"
          >
            {{ item.name.charAt(0) }}
          </v-list-item-avatar>
          <v-list-item-content @click="handleSelectItem(item), getEvents()">
            <v-list-item-title
              v-text="item.name + '. Location: ' + item.location"
            ></v-list-item-title>
            <v-list-item-subtitle v-text="item.symbol"></v-list-item-subtitle>
          </v-list-item-content>
          <v-list-item-action>
            <v-icon>mdi-medical-bag</v-icon>
          </v-list-item-action>
        </template>
      </v-autocomplete>
    </div>

    <h4 class="primary--text">
      Click on event for more info or for start examination report
    </h4>
    <v-sheet tile height="54" class="d-flex">
      <v-btn icon class="ma-2" @click="$refs.calendar.prev()">
        <v-icon>mdi-chevron-left</v-icon>
      </v-btn>
      <v-select
        v-model="type"
        :items="types"
        dense
        outlined
        hide-details
        class="ma-2"
        label="type"
      ></v-select>
      <v-select
        v-model="mode"
        :items="modes"
        dense
        outlined
        hide-details
        label="event-overlap-mode"
        class="ma-2"
      ></v-select>
      <v-select
        v-model="weekday"
        :items="weekdays"
        dense
        outlined
        hide-details
        label="weekdays"
        class="ma-2"
      ></v-select>
      <v-spacer></v-spacer>
      <v-btn icon class="ma-2" @click="$refs.calendar.next()">
        <v-icon>mdi-chevron-right</v-icon>
      </v-btn>
    </v-sheet>
    <v-sheet height="600">
      <v-calendar
        ref="calendar"
        v-model="value"
        :weekdays="weekday"
        :type="type"
        :events="events"
        :event-overlap-mode="mode"
        :event-overlap-threshold="30"
        :event-color="getEventColor"
        @change="getEvents"
        @click:event="showExaminationDialog"
      ></v-calendar>
    </v-sheet>

    <!-- Start QUESTION DIALOG -->
    <v-dialog v-model="questionDialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">Examination report for {{ name }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row> </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="addPenaltie(), (questionDialog = false)"
          >
            The patient did not show up
          </v-btn>
          <v-btn
            color="primary"
            text
            @click="
              questionDialog = false;
              reportDialog = true;
            "
          >
            Start examination
          </v-btn>
          <v-btn color="primary" text @click="questionDialog = false">
            Cancel
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <!-- End QUESTION DIALOG-->

    <!-- Start REPORT DIALOG-->
    <v-dialog v-model="reportDialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">Examination report for {{ name }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-stepper v-model="e6" vertical>
              <v-stepper-step :complete="e6 > 1" step="1">
                Examination report
              </v-stepper-step>

              <v-stepper-content step="1">
                <v-card color="grey lighten-3" class="mb-12" height="200px">
                  <v-textarea v-model="report" color="teal">
                    <template v-slot:label>
                      <div>Report</div>
                    </template>
                  </v-textarea>
                </v-card>
                <v-btn color="primary" @click="e6 = 2, getDrugsWithoutAllergies()"> Continue </v-btn>
                <v-btn
                  text
                  @click="
                    questionDialog = true;
                    reportDialog = false;
                    e6 = 1;
                  "
                >
                  Cancel
                </v-btn>
              </v-stepper-content>

              <v-stepper-step :complete="e6 > 2" step="2">
                Prescribe drug
              </v-stepper-step>

              <v-stepper-content step="2">
                <v-card color="grey lighten-3" class="mb-12" height="400px">
                  <!-- CHOOSE DRUG FOR PERSCRIBE -->
                  <h4 class="mt-10 ml-n primary--text">
                    Choose drug (listed drugs was filtered from allergens)
                  </h4>
                  <v-select
                    v-model="drugsWithoutAllergies"
                    :items="drugsWithoutAllergies"
                    item-text="name"
                    item-value="typeOfDrug"
                    label="Select drug"
                    persistent-hint
                    return-object
                    outlined
                    single-line
                    v-bind:value="valueDrugsWithoutAllergies"
                    v-on:input="onInputWithoutAllergies"
                    @click="alertDrugsWithoutAllergies = false"
                  ></v-select>
                  <v-alert
                    :value="alertDrugsWithoutAllergies"
                    color="pink"
                    dark
                    border="top"
                    icon="mdi-account"
                    transition="scale-transition"
                  >
                    You must choose pharmacy!
                  </v-alert>
                </v-card>
                <v-btn color="primary" @click="e6 = 3"> Continue </v-btn>
                <v-btn
                  text
                  @click="
                    questionDialog = true;
                    reportDialog = false;
                    e6 = 1;
                  "
                >
                  Cancel
                </v-btn>
              </v-stepper-content>

              <v-stepper-step :complete="e6 > 3" step="3">
                Schedule an additional examination
              </v-stepper-step>

              <v-stepper-content step="3">
                <v-card
                  color="grey lighten-1"
                  class="mb-12"
                  height="200px"
                ></v-card>
                <v-btn color="primary" @click="e6 = 4"> Continue </v-btn>
                <v-btn
                  text
                  @click="
                    questionDialog = true;
                    reportDialog = false;
                    e6 = 1;
                  "
                >
                  Cancel
                </v-btn>
              </v-stepper-content>

              <v-stepper-step step="4"> Submit </v-stepper-step>
              <v-stepper-content step="4">
                <v-card
                  color="grey lighten-1"
                  class="mb-12"
                  height="200px"
                ></v-card>
                <v-btn
                  color="primary"
                  @click="
                    e6 = 1;
                    reportDialog = false;
                  "
                >
                  Submit
                </v-btn>
                <v-btn
                  text
                  @click="
                    questionDialog = true;
                    reportDialog = false;
                    e6 = 1;
                  "
                >
                  Cancel
                </v-btn>
              </v-stepper-content>
            </v-stepper>
          </v-container>
        </v-card-text>
      </v-card>
    </v-dialog>
    <!-- End REPORT DIALOG-->
  </v-app>
</template>

<script>
import { getStringDateWithTimeFromMilliseconds } from "./../util/dateHandler";

export default {
  data: () => ({
    selectedPharmacy: null,
    isLoading: false,
    items: [],
    model: null,
    search: null,
    e6: 1,
    report: "",
    dragEvent: null,
    name: "",
    patientId: null,
    questionDialog: false,
    reportDialog: false,
    consultants: [],
    response: null,
    consultationId: null,
    drugsWithoutAllergies: [],
    alertDrugsWithoutAllergies: false,
    valueDrugsWithoutAllergies: null,

    type: "month",
    types: ["month", "week", "day", "4day"],
    mode: "stack",
    modes: ["stack", "column"],
    weekday: [0, 1, 2, 3, 4, 5, 6],
    weekdays: [
      { text: "Sun - Sat", value: [0, 1, 2, 3, 4, 5, 6] },
      { text: "Mon - Sun", value: [1, 2, 3, 4, 5, 6, 0] },
      { text: "Mon - Fri", value: [1, 2, 3, 4, 5] },
      { text: "Mon, Wed, Fri", value: [1, 3, 5] },
    ],
    value: "",
    events: [],
    colors: [
      "blue",
      "indigo",
      // 'deep-purple',
      // 'cyan',
      // 'green',
      // 'orange',
      // 'grey darken-1'
    ],
    names: ["Pregled", "Holiday"],
  }),
  watch: {
    model(val) {
      if (val != null) this.tab = 0;
      else this.tab = null;
    },
    search(val) {
      // Items have already been loaded
      console.log(val);
      if (this.items.length > 0) return;
      this.isLoading = true;

      // Lazily load input items
      fetch(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PHARMACIES_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
        .then((res) => res.clone().json())
        .then((res) => {
          console.log(res);
          this.items = res;
        })
        .catch((err) => {
          console.log(err);
        })
        .finally(() => (this.isLoading = false));
    },
  },
  methods: {
    getDrugsWithoutAllergies() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL + "api/drugs/alldrugswithoutalergies",
          { paatientID: this.patientId },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((resp) => {
          this.drugsWithoutAllergies = resp.data;
        });
    },
    onInputWithoutAllergies(valueDrugsWithoutAllergies) {
      this.$emit("input", valueDrugsWithoutAllergies);
      this.valueDrugsWithoutAllergies = valueDrugsWithoutAllergies;
      this.alertDrugsWithoutAllergies = false;
      console.log(valueDrugsWithoutAllergies);
    },
    addPenaltie() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL + "api/patient/addpenaltie",
          { phatientID: this.patientId, consultationID: this.consultationId },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          this.$router.go();
        });
    },
    handleSelectItem(item) {
      this.selectedPharmacy = item.id;
    },
    showExaminationDialog(event) {
      this.name = event.event.name;
      this.patientId = event.event.patientId;
      this.consultationId = event.event.id;
      this.questionDialog = true;
      console.log(event.event.id);
    },
    getEvents() {
      var token = parseJwt(localStorage.getItem("JWT-CPIS"));
      var email = token.sub;

      this.consultants = [];
      if (this.selectedPharmacy === null) {
        this.axios
          .post(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_CONSULTANTEXAMINATIONS_ENDPOINT,
            { email: email },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
              },
            }
          )
          .then((resp) => {
            this.consultants = resp.data;
            const events = [];

            for (let i = 0; i < this.consultants.length; i++) {
              events.push({
                name:
                  " Patient: " +
                  this.consultants[i].patientName +
                  "    " +
                  this.consultants[i].patientSurname +
                  ". Pharmacy: " +
                  this.consultants[i].pharmacyName +
                  " " +
                  " Start: " +
                  getStringDateWithTimeFromMilliseconds(
                    this.consultants[i].startDate
                  ) +
                  ". End: " +
                  getStringDateWithTimeFromMilliseconds(
                    this.consultants[i].endDate
                  ),
                start: this.consultants[i].startDate,
                end: this.consultants[i].endDate,
                color: "primary",
                timed: 1,
                patientId: this.consultants[i].patientId,
                id: this.consultants[i].id,
              });
            }
            this.events = events;
          });
      } else {
        this.axios
          .post(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_CONSULTANTEXAMINATIONS_ENDPOINT,
            { email: email },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
              },
            }
          )
          .then((resp) => {
            this.consultants = resp.data;
            const events = [];

            for (let i = 0; i < this.consultants.length; i++) {
              if (this.consultants[i].pharmacyID === this.selectedPharmacy) {
                events.push({
                  name:
                    " Patient: " +
                    this.consultants[i].patientName +
                    "    " +
                    this.consultants[i].patientSurname +
                    ". Pharmacy: " +
                    this.consultants[i].pharmacyName +
                    " " +
                    " Start: " +
                    getStringDateWithTimeFromMilliseconds(
                      this.consultants[i].startDate
                    ) +
                    ". End: " +
                    getStringDateWithTimeFromMilliseconds(
                      this.consultants[i].endDate
                    ),
                  start: this.consultants[i].startDate,
                  end: this.consultants[i].endDate,
                  color: "primary",
                  timed: 1,
                  patientId: this.consultants[i].patientId,
                  id: this.consultants[i].id,
                });
              }
            }
            this.events = events;
          });
      }
    },
    getEventColor(event) {
      return event.color;
    },
    rnd(a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a;
    },
  },
};

function parseJwt(token) {
  var base64Payload = token.split(".")[1];
  var payload = Buffer.from(base64Payload, "base64");
  return JSON.parse(payload.toString());
}
</script>


