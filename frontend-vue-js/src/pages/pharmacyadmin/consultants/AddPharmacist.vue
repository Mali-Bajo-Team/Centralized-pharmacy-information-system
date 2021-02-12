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
        <v-card-title>Employ pharmacist</v-card-title>
        <v-card-text>
          <v-stepper v-model="e6" vertical>
            <v-stepper-step :complete="e6 > 1" step="1">Enter pharmacist information</v-stepper-step>

            <v-stepper-content step="1">
              <v-row>
                <v-col>
                  <v-text-field
                    v-model="dermatologistForm.name"
                    :error-messages="nameErrorsDermatologist"
                    label="Name"
                    @blur="$v.dermatologistForm.name.$touch()"
                    @input="$v.dermatologistForm.name.$touch()"
                  ></v-text-field>
                  <v-text-field
                    v-model="dermatologistForm.surname"
                    :error-messages="surnameErrorsDermatologist"
                    label="Surname"
                    @blur="$v.dermatologistForm.surname.$touch()"
                    @input="$v.dermatologistForm.surname.$touch()"
                  ></v-text-field>
                  <v-text-field
                    :error-messages="phoneErrorsDermatologist"
                    label="Phone number"
                    @blur="$v.dermatologistForm.phone.$touch()"
                    @input="$v.dermatologistForm.phone.$touch()"
                    v-model="dermatologistForm.phone"
                  ></v-text-field>
                  <v-text-field
                    :error-messages="addressErrorsDermatologist"
                    label="Home address"
                    @blur="$v.dermatologistForm.address.$touch()"
                    @input="$v.dermatologistForm.address.$touch()"
                    v-model="dermatologistForm.address"
                  ></v-text-field>
                </v-col>

                <v-col>
                  <v-text-field
                    :error-messages="cityErrorsDermatologist"
                    label="City"
                    @blur="$v.dermatologistForm.city.$touch()"
                    @input="$v.dermatologistForm.city.$touch()"
                    v-model="dermatologistForm.city"
                  ></v-text-field>
                  <v-text-field
                    :error-messages="countryErrorsDermatologist"
                    label="Country"
                    @blur="$v.dermatologistForm.country.$touch()"
                    @input="$v.dermatologistForm.country.$touch()"
                    v-model="dermatologistForm.country"
                  ></v-text-field>
                  <v-text-field
                    :error-messages="emailErrorsDermatologist"
                    label="Email"
                    @blur="$v.dermatologistForm.email.$touch()"
                    @input="$v.dermatologistForm.email.$touch()"
                    v-model="dermatologistForm.email"
                  ></v-text-field>
                  <v-text-field
                    :append-icon="
                        dermatologistForm.showPassword
                          ? 'mdi-eye'
                          : 'mdi-eye-off'
                      "
                    :errorMessages="passwordErrorsDermatologist"
                    :type="
                        dermatologistForm.showPassword ? 'text' : 'password'
                      "
                    label="Password"
                    @click:append="
                        dermatologistForm.showPassword = !dermatologistForm.showPassword
                      "
                    @blur="$v.dermatologistForm.password.$touch()"
                    @input="$v.dermatologistForm.password.$touch()"
                    v-model="dermatologistForm.password"
                  ></v-text-field>
                </v-col>
              </v-row>

              <v-btn
                :disabled="$v.dermatologistForm.$invalid || performingAction"
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
import { required, email, minLength, numeric } from "vuelidate/lib/validators";

export default {
  mixins: [validationMixin],
  validations: {
    dermatologistForm: {
      password: {
        required,
        minLength: minLength(6)
      },
      email: {
        required,
        email
      },
      name: {
        required
      },
      surname: {
        required
      },
      address: {
        required
      },
      city: {
        required
      },
      country: {
        required
      },
      phone: {
        required,
        numeric
      }
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
    dermatologistForm: {
      email: "",
      password: "",
      showPassword: false,
      name: "",
      surname: "",
      address: "",
      city: "",
      country: "",
      phone: ""
    },
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
  methods: {
    schedule: function() {
      this.performingAction = true;
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PHARMACIST_ENDPOINT,
          {
            pharmacist: {
              password: this.dermatologistForm.password,
              email: this.dermatologistForm.email,
              name: this.dermatologistForm.name,
              surname: this.dermatologistForm.surname,
              city: this.dermatologistForm.city,
              address: this.dermatologistForm.address,
              country: this.dermatologistForm.country,
              mobile: this.dermatologistForm.phone
            },
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
    },
    passwordErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.password.$dirty) return errors;
      !this.$v.dermatologistForm.password.required &&
        errors.push("Password is required.");
      !this.$v.dermatologistForm.password.minLength &&
        errors.push("Password is too short.");
      return errors;
    },
    emailErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.email.$dirty) return errors;
      !this.$v.dermatologistForm.email.required &&
        errors.push("Email is required.");
      !this.$v.dermatologistForm.email.email &&
        errors.push("Email is not valid.");
      return errors;
    },
    nameErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.name.$dirty) return errors;
      !this.$v.dermatologistForm.name.required &&
        errors.push("Name is required.");
      return errors;
    },
    surnameErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.surname.$dirty) return errors;
      !this.$v.dermatologistForm.surname.required &&
        errors.push("Surname is required.");
      return errors;
    },
    cityErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.city.$dirty) return errors;
      !this.$v.dermatologistForm.city.required &&
        errors.push("City is required.");
      return errors;
    },
    countryErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.country.$dirty) return errors;
      !this.$v.dermatologistForm.country.required &&
        errors.push("Country is required.");
      return errors;
    },
    addressErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.address.$dirty) return errors;
      !this.$v.dermatologistForm.address.required &&
        errors.push("Home address is required.");
      return errors;
    },
    phoneErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.phone.$dirty) return errors;
      !this.$v.dermatologistForm.phone.required &&
        errors.push("Phone number is required.");
      !this.$v.dermatologistForm.phone.numeric &&
        errors.push("Phone number should only contain numbers.");
      return errors;
    }
  }
};
</script>