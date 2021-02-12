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
            <v-stepper-step :complete="e6 > 1" step="1">Choose deadline</v-stepper-step>

            <v-stepper-content step="1">
              <v-date-picker full-width v-model="order.deadline" :min="today"></v-date-picker>

              <v-btn
                :disabled="order.deadline == null || performingAction"
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

            <v-stepper-step :complete="e6 > 2" step="2">Add drugs</v-stepper-step>

            <v-stepper-content step="2">
              <v-btn block color="primary" @click="showDialog = true">Add drug</v-btn>
              <v-data-table
                class="mt-3"
                :headers="headers2"
                :items="order.orderedDrugs"
                item-key="code"
                dense
              >
              </v-data-table>

              <v-btn
                :disabled="order.orderedDrugs.length == 0 || performingAction"
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

            <v-stepper-step :complete="e6 > 3" step="3">Confirm</v-stepper-step>
            <v-stepper-content step="3">
              <v-btn :disabled="performingAction" color="success" @click="schedule">Order</v-btn>
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

    <v-dialog v-model="showDialog" width="500" :retain-focus="false">
      <v-card>
        <!--Toolbar of the card-->
        <v-toolbar color="primary" dark dense flat>
          <v-toolbar-title class="body-2">
            <h3>Add drug to order</h3>
          </v-toolbar-title>
        </v-toolbar>
        <v-data-table
          v-model="selected"
          single-select
          showSelect
          :headers="headers"
          :items="drugs"
          item-key="code"
          :search="search"
          dense
        >
          <template v-slot:top>
            <v-text-field v-model="search" label="Search" class="mx-4"></v-text-field>
          </template>
        </v-data-table>

        <v-form class="pa-4 pb-0">
          <v-text-field
            v-model="price"
            v-show="showPrice()"
            label="Price"
            hint="The drug isn't currently available in the pharmacy, so a default price must be chosen."
          ></v-text-field>
          <v-text-field v-model="amount" label="Amount"></v-text-field>
        </v-form>
        <v-card-actions class="pa-4 pt-0">
          <v-spacer></v-spacer>
          <v-progress-circular class="mr-4" indeterminate color="primary" v-show="performingAction"></v-progress-circular>
          <v-btn
            :disabled="selected.length == 0 || amount == '' || (showPrice() && price == '')"
            color="success"
            @click="addDrug"
          >Add</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import { getStringDateFromMilliseconds } from "@/util/dateHandler";
import { getMillisecondsFromStringDate } from "../../../util/dateHandler";

export default {
  data: () => ({
    showDialog: false,
    headers: [
      {
        text: "Drug name",
        align: "start",
        sortable: true,
        value: "name"
      }
    ],
    headers2: [
      {
        text: "Drug name",
        align: "start",
        sortable: true,
        value: "name"
      },
      {
        text: "Amount",
        sortable: true,
        value: "amount"
      }
    ],
    drugs: [],
    selected: [],
    drugsInPharmacy: [],
    e6: 1,
    performingAction: false,
    snackbarText: "",
    snackbar: false,
    search: "",
    amount: "",
    price: "",
    order: {
      deadline: null,
      orderedDrugs: []
    }
  }),
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_ALL_DRUGS_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
          }
        }
      )
      .then(response => {
        this.drugs = response.data;
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

    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_DRUGS_IN_PHARMACY_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
          }
        }
      )
      .then(response => {
        this.drugsInPharmacy = [];
        for (let drug of response.data) this.drugsInPharmacy.push(drug.code);
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
    addDrug: function() {
      this.order.orderedDrugs.push({
        code: this.selected[0].code,
        name: this.selected[0].name,
        amount: this.amount,
        defaultPrice: this.showPrice() ? this.price : null
      });
      let drug = this.selected[0];
      let drugs = [];
      for (let dr of this.drugs) if (dr.code != drug.code) drugs.push(dr);
      this.drugs = drugs;
      this.showDialog = false;
      this.amount = "";
      this.price = "";
    },
    schedule: function() {
      this.performingAction = true;
      this.order.deadline = getMillisecondsFromStringDate(this.order.deadline);
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PROCUREMENT_ORDERS_ENDPOINT,
          this.order,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
            }
          }
        )
        .then(() => {
          this.snackbarText = "Successfully ordered.";
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
    },
    showPrice() {
      if (this.selected.length == 0) return false;
      for (let dif of this.drugsInPharmacy) {
        if (this.selected[0].code == dif) return false;
      }
      return true;
    }
  },
  computed: {
    today: function() {
      return getStringDateFromMilliseconds(new Date().getTime());
    }
  }
};
</script>