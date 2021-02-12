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
        <v-card-title>Add predefined examination</v-card-title>
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
                @click="dermatologistSelected"
              >Continue</v-btn>
              <v-progress-circular
                class="ml-4"
                indeterminate
                color="primary"
                v-show="performingAction"
              ></v-progress-circular>
            </v-stepper-content>

            <v-stepper-step :complete="e6 > 2" step="2">Choose a date</v-stepper-step>

            <v-stepper-content step="2">
              <v-date-picker
                v-if="dermatologistDetails != null"
                full-width
                v-model="date"
                :min="today"
                :allowed-dates="allowedDates"
              ></v-date-picker>

              <v-btn
                :disabled="date == null || performingAction"
                color="primary"
                @click="dateSelected"
              >Continue</v-btn>
              <v-btn color="secondary" text @click="e6 = e6 - 1">Back</v-btn>
              <v-progress-circular
                class="ml-4"
                indeterminate
                color="primary"
                v-show="performingAction"
              ></v-progress-circular>
            </v-stepper-content>

            <v-stepper-step :complete="e6 > 3" step="3">Choose start time</v-stepper-step>

            <v-stepper-content step="3">
              <v-time-picker
                class="mb-2"
                full-width
                format="ampm"
                no-title
                v-model="startTime"
                :min="earliestTime"
                :max="latestTime"
              ></v-time-picker>

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

            <v-stepper-step :complete="e6 > 4" step="4">Choose end time</v-stepper-step>

            <v-stepper-content step="4">
              <v-time-picker
                class="mb-2"
                full-width
                format="ampm"
                no-title
                v-model="endTime"
                :min="startTime"
                :max="latestTime"
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

            <v-stepper-step :complete="e6 > 5" step="5">
              Choose price
              <br />
              <small>optional</small>
            </v-stepper-step>
            <v-stepper-content step="5">
              <v-text-field
                v-model="price"
                :error-messages="priceErrors"
                label="Price"
                @blur="$v.price.$touch()"
                @input="$v.price.$touch()"
              ></v-text-field>
              <v-btn
                :disabled="$v.price.$invalid || performingAction"
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

            <v-stepper-step :complete="e6 > 6" step="6">Confirm</v-stepper-step>
            <v-stepper-content step="6">
              <v-btn :disabled="performingAction" color="success" @click="schedule">Schedule</v-btn>
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
import {
  getMillisecondsFromStringDate,
  getStringDateFromMilliseconds,
  getTimeStringFromMilliseconds,
  getMillisecondsFromStringTimeAndDate
} from "@/util/dateHandler";

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
    date: null,
    earliestTime: null,
    latestTime: null,
    startTime: null,
    endTime: null,
    price: ""
  }),
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_DERMATOLOGIST_ENDPOINT,
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
    dermatologistSelected: function() {
      this.performingAction = true;
      this.axios
        .get(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_DERMATOLOGIST_ENDPOINT +
            "/" +
            this.selected[0].id,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
            }
          }
        )
        .then(response => {
          this.dermatologistDetails = response.data;
          this.performingAction = false;
          this.e6 = 2;
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
    },
    dateSelected: function() {
      this.earliestTime = getTimeStringFromMilliseconds(
        this.getWorkTimeStart(this.date)
      );
      this.latestTime = getTimeStringFromMilliseconds(
        this.getWorkTimeEnd(this.date)
      );
      this.e6 = this.e6 + 1;
    },
    allowedDates: function(date) {
      return this.getWorkTimeStart(date) != null;
    },
    getWorkTimeStart: function(date) {
      let day = new Date(getMillisecondsFromStringDate(date)).getDay();
      if (day == 1) return this.dermatologistDetails.monday_start;
      if (day == 2) return this.dermatologistDetails.tuesday_start;
      if (day == 3) return this.dermatologistDetails.wednesday_start;
      if (day == 4) return this.dermatologistDetails.thursday_start;
      if (day == 5) return this.dermatologistDetails.friday_start;
      if (day == 6) return this.dermatologistDetails.saturday_start;
      if (day == 0) return this.dermatologistDetails.sunday_start;
    },
    getWorkTimeEnd: function(date) {
      let day = new Date(getMillisecondsFromStringDate(date)).getDay();
      if (day == 1) return this.dermatologistDetails.monday_end;
      if (day == 2) return this.dermatologistDetails.tuesday_end;
      if (day == 3) return this.dermatologistDetails.wednesday_end;
      if (day == 4) return this.dermatologistDetails.thursday_end;
      if (day == 5) return this.dermatologistDetails.friday_end;
      if (day == 6) return this.dermatologistDetails.saturday_end;
      if (day == 0) return this.dermatologistDetails.sunday_end;
    },
    schedule: function() {
      this.performingAction = true;
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PREDEFINED_CONSULTATION_ENDPOINT,
          {
            consultantId: this.selected[0].id,
            start: getMillisecondsFromStringTimeAndDate(
              this.startTime,
              this.date
            ),
            end: getMillisecondsFromStringTimeAndDate(this.endTime, this.date),
            price: this.price == "" ? null : this.price
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
            }
          }
        )
        .then(() => {
          this.snackbarText = "Successfully scheduled.";
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
    today: function() {
      return getStringDateFromMilliseconds(new Date().getTime());
    },
    priceErrors() {
      const errors = [];
      if (!this.$v.price.$dirty) return errors;
      !this.$v.price.decimal && errors.push("Price must be a decimal number.");
      !this.$v.price.minValue && errors.push("Price must be positive.");
      return errors;
    }
  }
};
</script>