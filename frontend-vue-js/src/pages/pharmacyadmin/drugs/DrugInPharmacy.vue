<template>
  <div>
    <v-lazy :options="{
          threshold: .2
        }" transition="fade-transition">
      <v-card elevation="4" class="pa-4 mb-10" v-show="!finished">
        <v-card-title class="ml-2">
          <h2>{{ drug.name }}</h2>
          <v-spacer></v-spacer>
          <v-chip color="primary" class="ml-0 mr-4">{{ drug.drugForm }}</v-chip>
          <v-chip color="primary" class="ml-0">{{ drug.drugClass }}</v-chip>
        </v-card-title>

        <v-card-text>
          <span class="mr-2">Available amount:</span>
          <v-chip :color="amountColor">{{ drug.amount }}</v-chip>
        </v-card-text>
        <v-card-actions>
          <v-btn color="error" @click="fire" :disabled="performingAction">Remove from pharmacy</v-btn>
          <v-expand-transition>
            <v-btn
              @click="addingPrice = true; pricesShowing = false"
              v-show="pricesShowing"
              color="success"
              :disabled="performingAction"
            >Add price</v-btn>
          </v-expand-transition>
          <v-btn @click="pricesShowing = !pricesShowing" color="accent" text>
            {{ pricesShowing ? "Hide" : "Show" }} prices
            <v-icon>
              {{
              pricesShowing ? "mdi-chevron-up" : "mdi-chevron-down"
              }}
            </v-icon>
          </v-btn>
          <v-spacer></v-spacer>
          <v-progress-circular
            class="ml-4"
            indeterminate
            color="primary"
            v-show="performingAction && !addingPrice"
          ></v-progress-circular>
        </v-card-actions>
        <v-expand-transition>
          <v-card-text v-show="pricesShowing">
            <v-row class="pa-5" v-if="$refs.calendar">
              <v-btn v-if="$refs.calendar" fab text small color="grey darken-2" @click="prev">
                <v-icon>mdi-chevron-left</v-icon>
              </v-btn>
              <v-spacer></v-spacer>
              <span v-if="$refs.calendar">{{ $refs.calendar.title }}</span>
              <v-spacer></v-spacer>
              <v-btn v-if="$refs.calendar" fab text small color="grey darken-2" @click="next">
                <v-icon>mdi-chevron-right</v-icon>
              </v-btn>
            </v-row>

            <v-calendar
              v-model="focus"
              ref="calendar"
              color="accent"
              type="month"
              @change="updateRange"
            >
              <template v-slot:day="{ date }">
                <v-row class="fill-height">
                  <v-spacer></v-spacer>
                  <v-col class="pa-4">
                    <v-chip color="success" outlined v-if="prices[date]">{{prices[date]}}€</v-chip>
                  </v-col>
                  <v-spacer></v-spacer>
                </v-row>
              </template>
            </v-calendar>
          </v-card-text>
        </v-expand-transition>
      </v-card>
    </v-lazy>

    <v-dialog v-model="addingPrice" width="500" :retain-focus="false">
      <v-card>
        <!--Toolbar of the card-->
        <v-toolbar color="primary" dark dense flat>
          <v-toolbar-title class="body-2">
            <h3>Add price for {{drug.name}}</h3>
          </v-toolbar-title>
        </v-toolbar>
        <v-form class="pa-4 pb-0">
          <v-text-field
            class="mb-5"
            v-model="defaultPrice"
            :error-messages="defaultPriceErrors"
            label="Price"
            @blur="$v.defaultPrice.$touch()"
            @input="$v.defaultPrice.$touch()"
          ></v-text-field>

          <span class="mt-5 grey-lighten2">Choose validity range:</span>
          <v-date-picker class="mt-3 mb-0" full-width v-model="validityRange" range :min="today"></v-date-picker>
        </v-form>
        <v-card-actions class="pa-4 pt-0">
          <v-spacer></v-spacer>
          <v-progress-circular class="mr-4" indeterminate color="primary" v-show="performingAction"></v-progress-circular>
          <v-btn :disabled="performingAction" color="success" @click="addPrice()">Confirm</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-snackbar v-model="snackbar" :timeout="2000" bottom class="mb-5" right>
      {{ snackbarText }}
      <template v-slot:action="{ attrs }">
        <v-btn color="primary" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import {
  getMillisecondsFromStringDate,
  getStringDateFromMilliseconds,
  getTodayDateString
} from "./../../../util/dateHandler";

import { validationMixin } from "vuelidate";
import { required, decimal, minValue } from "vuelidate/lib/validators";

export default {
  mixins: [validationMixin],
  validations: {
    defaultPrice: {
      required,
      decimal,
      minValue: minValue(0)
    }
  },
  props: {
    drug: {
      type: Object,
      required: true
    }
  },
  mounted() {
    this.endpoint =
      process.env.VUE_APP_BACKEND_URL +
      process.env.VUE_APP_DRUGS_IN_PHARMACY_ENDPOINT +
      "/" +
      this.drug.code;
  },
  data: () => ({
    finished: false,
    performingAction: false,
    snackbarText: "",
    snackbar: false,
    endpoint: "",
    pricesShowing: false,
    prices: new Map(),
    focus: "",
    addingPrice: false,
    defaultPrice: "",
    validityRange: []
  }),
  methods: {
    fire: function() {
      this.performingAction = true;
      this.axios
        .delete(this.endpoint, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
          }
        })
        .then(() => {
          this.snackbarText = "Drug successfully removed from pharmacy.";
          this.finished = true;
          this.snackbar = true;
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
          this.performingAction = false;
          this.snackbar = true;
        });
    },
    updateRange: function({ start, end }) {
      this.axios
        .post(
          this.endpoint + "/price/search",
          {
            start: getMillisecondsFromStringDate(start.date),
            end: getMillisecondsFromStringDate(end.date)
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
            }
          }
        )
        .then(response => {
          this.prices = new Map();
          for (let price of response.data) {
            this.prices[getStringDateFromMilliseconds(price.date)] =
              price.price;
          }
        })
        .catch(error => {
          if (
            error.response &&
            error.response.data &&
            error.response.data.message
          )
            this.snackbarText = error.response.data.message;
          else if (error.message) this.snackbarText = error.message;
          else
            this.snackbarText =
              "An unknown error has occured while fetching prices.";
          this.performingAction = false;
          this.snackbar = true;
        });
    },
    prev() {
      this.$refs.calendar.prev();
    },
    next() {
      console.log("here");
      this.$refs.calendar.next();
    },
    addPrice: function() {
      this.$v.$touch();
      if (this.$v.$invalid) return;

      if (this.validityRange.length == 0) {
        this.snackbarText = "Validity range is required.";
        this.snackbar = true;
        return;
      }

      this.performingAction = true;

      let date1 = 0;
      let date2 = 0;
      if (this.validityRange.length == 0) {
        date1 = getMillisecondsFromStringDate(this.validityRange[0]);
        date2 = getMillisecondsFromStringDate(this.validityRange[0]);
      } else {
        date1 = getMillisecondsFromStringDate(this.validityRange[0]);
        date2 = getMillisecondsFromStringDate(this.validityRange[1]);

        if (date1 > date2) {
          var temp = date1;
          date2 = date1;
          date1 = temp;
        }
      }

      this.axios
        .post(
          this.endpoint + "/price",
          {
            start: date1,
            end: date2,
            price: this.defaultPrice
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
            }
          }
        )
        .then(() => {
          this.defaultPrice = "";
          this.validityRange = [];
          this.snackbarText = "Successfuly added price.";
          this.snackbar = true;
          this.prev();
          setTimeout(() => this.next(), 100);
          this.performingAction = false;
          this.addingPrice = false;
          this.pricesShowing = true;
        })
        .catch(error => {
          if (
            error.response &&
            error.response.data &&
            error.response.data.message
          )
            this.snackbarText = error.response.data.message;
          else if (error.message) this.snackbarText = error.message;
          else
            this.snackbarText =
              "An unknown error has occured while fetching prices.";
          this.performingAction = false;
          this.snackbar = true;
        });
    }
  },
  computed: {
    amountColor() {
      if (this.drug.amount == 0) return "error";
      else return "success";
    },
    defaultPriceErrors() {
      const errors = [];
      if (!this.$v.defaultPrice.$dirty) return errors;
      !this.$v.defaultPrice.required &&
        errors.push("Default price is required.");
      !this.$v.defaultPrice.decimal &&
        errors.push("Default price must be a decimal number.");
      !this.$v.defaultPrice.minValue &&
        errors.push("Default price must be positive.");
      return errors;
    },
    today() {
      return getTodayDateString();
    }
  }
};
</script>