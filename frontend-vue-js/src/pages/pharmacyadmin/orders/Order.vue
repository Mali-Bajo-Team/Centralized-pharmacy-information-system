<template>
  <div>
    <v-lazy :options="{
          threshold: .2
        }" transition="fade-transition">
      <v-card elevation="4" class="pa-4 mb-10" v-show="!finished">
        <v-card-title>{{ "Order #" + order.id }}</v-card-title>

        <v-card-text>
          <v-row>
            <v-col>
              <span class="mr-2">Created at:</span>
              <v-chip color="primary">{{ order.timestamp }}</v-chip>
            </v-col>
            <v-col>
              <span class="mr-2">Deadline for offers:</span>
              <v-chip color="primary">{{ order.deadline }}</v-chip>
            </v-col>
          </v-row>
        </v-card-text>
        <v-card-text>
          <v-data-table
            :headers="headers"
            :items="order.orderedDrugs"
            :items-per-page="5"
            class="elevation-2"
          ></v-data-table>
        </v-card-text>

        <v-card-actions>
          <v-btn v-show="editable" color="info" :disabled="performingAction">Edit</v-btn>
          <v-btn
            v-show="editable"
            color="error"
            @click="deleteOrder"
            :disabled="performingAction"
          >Delete</v-btn>
          <v-btn
            v-show="hasOffers"
            color="info"
            :disabled="performingAction"
          >View offers</v-btn>
          <v-progress-circular class="ml-4" indeterminate color="primary" v-show="performingAction"></v-progress-circular>
        </v-card-actions>
      </v-card>
    </v-lazy>

    <v-snackbar v-model="snackbar" :timeout="2000" bottom class="mb-5" right>
      {{ snackbarText }}
      <template v-slot:action="{ attrs }">
        <v-btn color="primary" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import { getParsedToken } from "./../../../util/token";

export default {
  props: {
    order: {
      type: Object,
      required: true
    }
  },
  mounted() {
    this.orderEndpoint =
      process.env.VUE_APP_BACKEND_URL +
      process.env.VUE_APP_PROCUREMENT_ORDERS_ENDPOINT +
      "/" +
      this.order.id;
    this.order.deadline = new Date(this.order.deadline).toLocaleDateString();
    this.order.timestamp = new Date(this.order.deadline).toLocaleString();
  },
  data: () => ({
    headers: [
      {
        text: "Name",
        align: "start",
        value: "drug.name"
      },
      { text: "Code", value: "drug.code" },
      { text: "Amount", value: "amount" }
    ],
    snackbarText: "",
    snackbar: false,
    rejecting: false,
    performingAction: false,
    finished: false,
    orderEndpoint: ""
  }),
  methods: {
    refresh: function() {
      this.axios
        .get(this.orderEndpoint, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
          }
        })
        .then(response => {
          let temp = response.data;
          temp.deadline = new Date(this.order.deadline).toLocaleDateString();
          temp.timestamp = new Date(this.order.deadline).toLocaleString();
          this.order = temp;
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
    deleteOrder: function() {
      this.performingAction = true;
      this.axios
        .delete(this.orderEndpoint, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
          }
        })
        .then(() => {
          this.finished = true;
          this.snackbarText = "Order sucessfully deleted.";
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
          this.refresh();
        });
    }
  },
  computed: {
    editable() {
      return (
        this.order.status == "WAITING_FOR_OFFERS" &&
        this.order.creatorEmail == getParsedToken().sub
      );
    },
    hasOffers() {
      return this.order.status == "WAITING_FOR_SELECTION";
    }
  }
};
</script>