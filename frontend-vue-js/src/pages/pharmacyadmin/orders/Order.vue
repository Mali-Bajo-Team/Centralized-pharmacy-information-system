<template>
  <div>
    <v-lazy :options="{
          threshold: .2
        }" transition="fade-transition">
      <v-card elevation="4" class="pa-4 mb-10" v-show="!finished">
        <v-card-title>
          <span class="mr-2">{{ "Order #" + order.id }}</span>
          <v-chip color="primary">{{ status.toUpperCase() }}</v-chip>
        </v-card-title>

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
          <v-btn v-show="editable" :to="'/pharmacyadmin/orders/edit/' + order.id" color="info" :disabled="performingAction">Edit</v-btn>
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
            @click="viewOffers()"
          >View offers</v-btn>
          <v-progress-circular class="ml-4" indeterminate color="primary" v-show="performingAction && !offerDialog"></v-progress-circular>
        </v-card-actions>
      </v-card>
    </v-lazy>

    <v-dialog v-model="offerDialog" :retain-focus="false" max-width="500px">
      <v-card>
        <v-toolbar color="primary" dark dense flat>
          <v-toolbar-title class="body-2">
            <h3>Offers for order #{{order.id}}</h3>
          </v-toolbar-title>
        </v-toolbar>

        <v-card-text>
          <v-data-table
            :headers="offerHeaders"
            :items="offers"
            :items-per-page="5"
            class="elevation-2 mt-5"
          >
            <template v-slot:item.id="{ item }">
              <v-btn
                v-if="showAcceptButton"
                class="ma-0"
                color="success"
                @click="approve(item.id)"
                :disabled="performingAction"
              >Accept</v-btn>
              <v-chip v-else :color="item.color">{{ item.status }}</v-chip>
            </template>
          </v-data-table>
        </v-card-text>

        <v-card-actions class="pa-4 pt-0">
          <v-btn color="secondary" text @click="offerDialog = false">Close</v-btn>
          <v-spacer></v-spacer>
          <v-progress-circular class="ml-4" indeterminate color="primary" v-show="performingAction"></v-progress-circular>
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
import { getParsedToken } from "./../../../util/token";

export default {
  props: {
    orderProp: {
      type: Object,
      required: true
    }
  },
  mounted() {
    this.order = this.orderProp;
    this.orderEndpoint =
      process.env.VUE_APP_BACKEND_URL +
      process.env.VUE_APP_PROCUREMENT_ORDERS_ENDPOINT +
      "/" +
      this.order.id;
    this.order.deadline = new Date(this.order.deadline).toLocaleDateString();
    this.order.timestamp = new Date(this.order.deadline).toLocaleString();
  },
  data: () => ({
    order: {},
    headers: [
      {
        text: "Name",
        align: "start",
        value: "drug.name"
      },
      { text: "Code", value: "drug.code" },
      { text: "Amount", value: "amount" },
      { text: "", value: "id" }
    ],
    snackbarText: "",
    snackbar: false,
    rejecting: false,
    performingAction: false,
    finished: false,
    orderEndpoint: "",
    offerDialog: false,
    offers: [],
    offerHeaders: [
      {
        text: "Price",
        align: "start",
        value: "price"
      },
      { text: "Shipment date", value: "shipmentDate" },
      { text: "", value: "id" }
    ]
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
    },
    viewOffers: function() {
      this.performingAction = true;
      this.axios
        .get(this.orderEndpoint + "/offers", {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
          }
        })
        .then(response => {
          this.offers = response.data;
          for (let offer of this.offers) {
            offer.price = offer.price + "€";
            offer.shipmentDate = new Date(
              offer.shipmentDate
            ).toLocaleDateString();
            if (offer.status == "ACCEPTED") offer.color = "success";
            else if (offer.status == "REJECTED") offer.color = "error";
            else if (offer.status == "PENDING") offer.color = "warning";
          }
          this.performingAction = false;
          this.offerDialog = true;
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
    approve: function(id) {
      this.performingAction = true;
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PROCUREMENT_OFFERS_ENDPOINT +
            "/" +
            id +
            "/accept",
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
            }
          }
        )
        .then(() => {
          this.snackbarText = "Offer successfully accepted.";
          this.performingAction = false;
          this.snackbar = true;
          this.offerDialog = false;
          this.refresh();
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
    }
  },
  computed: {
    editable() {
      return (
        this.status == "Has no offers" &&
        this.order.creatorEmail == getParsedToken().sub
      );
    },
    hasOffers() {
      return !(this.status == "Has no offers");
    },
    showAcceptButton() {
      return (
        this.order.creatorEmail == getParsedToken().sub &&
        this.status == "Waiting for review"
      );
    },
    status() {
      if (this.order.status == "WAITING_FOR_OFFERS") return "Has no offers";
      if (this.order.status == "FINISHED") return "Finished";
      if (
        this.order.status == "WAITING_FOR_SELECTION" &&
        this.order.deadline <= this.now
      )
        return "Waiting for offer deadline";
      return "Waiting for review";
    },
    now() {
      let withTime = new Date();
      return new Date(
        withTime.getTime() - (withTime.getTime() % (24 * 1000 * 60 * 60))
      );
    }
  }
};
</script>