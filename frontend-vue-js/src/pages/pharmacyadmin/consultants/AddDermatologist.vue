<template>
  <v-row>
    <v-snackbar v-model="snackbar" :timeout="2000" bottom class="mb-5" right>
      {{ snackbarText }}
      <template v-slot:action="{ attrs }">
        <v-btn color="primary" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
      </template>
    </v-snackbar>

    <v-spacer></v-spacer>
    <v-col sm="12" md="8" lg="6" xl="6" cols="12">
      <v-card elevation="4" class="pa-5">
        <v-card-title>Employ dermatologist</v-card-title>
        <v-card-text>
          <v-stepper v-model="e6" vertical>
            <v-stepper-step :complete="e6 > 1" step="1">Choose a dermatologist</v-stepper-step>

            <v-stepper-content step="1">
              <v-data-table
                v-model="selected"
                single-select
                showSelect
                :headers="headers"
                :items="dermatologists"
                item-key="id"
                :search="search"
                dense
              >
                <template v-slot:top>
                  <v-text-field v-model="search" label="Search" class="mx-4"></v-text-field>
                </template>
              </v-data-table>

              <v-btn
                :disabled="selected.length == 0 || performingAction"
                color="primary"
                @click="e6 = e6 + 1"
              >Continue</v-btn>
              <v-progress-circular
                class="ml-4"
                indeterminate
                color="primary"
                v-show="performingAction"
              ></v-progress-circular>
            </v-stepper-content>

            <v-stepper-step :complete="e6 > 2" step="2">Choose working days</v-stepper-step>

            <v-stepper-content step="2">
              <v-row class="mb-3">
                <v-checkbox class="ml-4 mr-4" v-model="monday" primary label="Monday"></v-checkbox>
                <v-checkbox class="ml-4 mr-4" v-model="tuesday" primary label="Tuesday"></v-checkbox>
                <v-checkbox class="ml-4 mr-4" v-model="wednesday" primary label="Wednesday"></v-checkbox>
                <v-checkbox class="ml-4 mr-4" v-model="thursday" primary label="Thursday"></v-checkbox>
                <v-checkbox class="ml-4 mr-4" v-model="friday" primary label="Friday"></v-checkbox>
                <v-checkbox class="ml-4 mr-4" v-model="saturday" primary label="Sunday"></v-checkbox>
                <v-checkbox class="ml-4 mr-4" v-model="sunday" primary label="Sunday"></v-checkbox>
              </v-row>

              <v-btn
                :disabled="!(monday || tuesday || wednesday || thursday || friday || saturday || sunday) || performingAction"
                color="primary"
                @click="e6 = e6 + 1"
              >Continue</v-btn>
              <v-btn color="secondary" text @click="e6 = e6 - 1">Back</v-btn>
              <v-progress-circular
                class="ml-4"
                indeterminate
                color="primary"
                v-show="performingAction"
              ></v-progress-circular>
            </v-stepper-content>

            <v-stepper-step :complete="e6 > 3" step="3">Choose start time of shift</v-stepper-step>

            <v-stepper-content step="3">
              <v-time-picker class="mb-2" full-width format="ampm" no-title v-model="startTime"></v-time-picker>

              <v-btn
                :disabled="startTime == null || performingAction"
                color="primary"
                @click="e6 = e6 + 1"
              >Continue</v-btn>
              <v-btn color="secondary" text @click="e6 = e6 - 1">Back</v-btn>
              <v-progress-circular
                class="ml-4"
                indeterminate
                color="primary"
                v-show="performingAction"
              ></v-progress-circular>
            </v-stepper-content>

            <v-stepper-step :complete="e6 > 4" step="4">Choose end time of shift</v-stepper-step>

            <v-stepper-content step="4">
              <v-time-picker
                class="mb-2"
                full-width
                format="ampm"
                no-title
                v-model="endTime"
                :min="startTime"
              ></v-time-picker>

              <v-btn
                :disabled="endTime == null || performingAction"
                color="primary"
                @click="e6 = e6 + 1"
              >Continue</v-btn>
              <v-btn color="secondary" text @click="e6 = e6 - 1">Back</v-btn>
              <v-progress-circular
                class="ml-4"
                indeterminate
                color="primary"
                v-show="performingAction"
              ></v-progress-circular>
            </v-stepper-content>

            <v-stepper-step :complete="e6 > 5" step="5">Confirm</v-stepper-step>
            <v-stepper-content step="5">
              <v-btn :disabled="performingAction" color="success" @click="schedule">Employ</v-btn>
              <v-btn color="secondary" text @click="e6 = e6 - 1">Back</v-btn>
              <v-progress-circular
                class="ml-4"
                indeterminate
                color="primary"
                v-show="performingAction"
              ></v-progress-circular>
            </v-stepper-content>
          </v-stepper>
        </v-card-text>
      </v-card>
    </v-col>
    <v-spacer></v-spacer>
  </v-row>
</template>

<script>
import { getMillisecondsFromStringTimeAndDate } from "@/util/dateHandler";

import { validationMixin } from "vuelidate";
import { decimal, minValue } from "vuelidate/lib/validators";

export default {
  mixins: [validationMixin],
  validations: {
    price: {
      decimal,
      minValue: minValue(0)
    }
  },
  data: () => ({
    headers: [
      {
        text: "Name and surname",
        align: "start",
        sortable: true,
        value: "fullname"
      }
    ],
    dermatologists: [],
    selected: [],
    dermatologistDetails: null,
    e6: 1,
    performingAction: false,
    snackbarText: "",
    snackbar: false,
    search: "",
    monday: false,
    tuesday: false,
    wednesday: false,
    thursday: false,
    friday: false,
    saturday: false,
    sunday: false,
    startTime: null,
    endTime: null
  }),
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_DERMATOLOGIST_ENDPOINT +
          "/notemployees",
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
          }
        }
      )
      .then(response => {
        this.dermatologists = response.data;
        for (let dermatologist of this.dermatologists)
          dermatologist.fullname =
            dermatologist.name + " " + dermatologist.surname;
      })
      .catch(error => {
        if (
          error.response &&
          error.response.data &&
          error.response.data.message
        )
          this.snackbarText = error.response.data.message;
        else if (error.message) this.snackbarText = error.message;
        else this.snackbarText = "An unknown error has occured.";
        this.snackbar = true;
      });
  },
  methods: {
    schedule: function() {
      this.performingAction = true;
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_DERMATOLOGIST_ENDPOINT,
          {
            dermatologistId: this.selected[0].id,
            workingTimes: {
              monday: this.mondayTime,
              tuesday: this.tuesdayTime,
              wednesday: this.wednesdayTime,
              thursday: this.thursdayTime,
              friday: this.fridayTime,
              saturday: this.saturdayTime,
              sunday: this.sundayTime
            }
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
            }
          }
        )
        .then(() => {
          this.snackbarText = "Successfully added employee.";
          this.snackbar = true;
          this.performingAction = false;
        })
        .catch(error => {
          if (
            error.response &&
            error.response.data &&
            error.response.data.message
          )
            this.snackbarText = error.response.data.message;
          else if (error.message) this.snackbarText = error.message;
          else this.snackbarText = "An unknown error has occured.";
          this.snackbar = true;
          this.performingAction = false;
        });
    }
  },
  computed: {
    mondayTime: function() {
      if (!this.monday) return null;
      return { start: this.startTimeReal, end: this.endTimeReal };
    },
    tuesdayTime: function() {
      if (!this.tuesday) return null;
      getMillisecondsFromStringTimeAndDate(null, null);
      return { start: this.startTimeReal, end: this.endTimeReal };
    },
    wednesdayTime: function() {
      if (!this.wednesday) return null;
      return { start: this.startTimeReal, end: this.endTimeReal };
    },
    thursdayTime: function() {
      if (!this.thursday) return null;
      return { start: this.startTimeReal, end: this.endTimeReal };
    },
    fridayTime: function() {
      if (!this.friday) return null;
      return { start: this.startTimeReal, end: this.endTimeReal };
    },
    saturdayTime: function() {
      if (!this.saturday) return null;
      return { start: this.startTimeReal, end: this.endTimeReal };
    },
    sundayTime: function() {
      if (!this.sunday) return null;
      return { start: this.startTimeReal, end: this.endTimeReal };
    },
    startTimeReal: function() {
      let date = new Date();
      const [hours, minutes] = this.startTime.split(":");
      date.setHours(hours - 1);
      date.setMinutes(minutes);
      date.setMilliseconds(0);
      date.setSeconds(0);
      return date;
    },
    endTimeReal: function() {
      let date = new Date();
      const [hours, minutes] = this.endTime.split(":");
      date.setHours(hours - 1);
      date.setMinutes(minutes);
      date.setMilliseconds(0);
      date.setSeconds(0);
      return date;
    }
  }
};
</script>