<template>
  <v-container>
    <v-row>
      <v-col>
        <v-card
          elevation="4"
          class="pa-4 ml-16 mr-16 mb-10"
          v-for="order in allOrders"
          :key="order.id"
        >
          <!-- Row for title -->
          <v-row align="center">
            <v-card-title>
              <h3 class="ml-3">{{ order.pharmacy.name }}</h3>
            </v-card-title>
            <v-spacer></v-spacer>
            <!-- Make a offer dialog -->
            <v-dialog
              v-model="order.showAddOfferDialog"
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
                      class="mr-6"
                      fab
                      dark
                      small
                      color="primary"
                    >
                      <v-icon dark> mdi-plus </v-icon>
                    </v-btn>
                  </template>
                  <span> Make offer </span>
                </v-tooltip>
              </template>
              <v-card>
                <!--Toolbar of the card-->
                <v-toolbar color="primary" dark dense flat>
                  <v-toolbar-title class="body-2">
                    <h3>Make offer</h3>
                  </v-toolbar-title>
                </v-toolbar>
                <!-- End of the toolbar of the card -->
                <!-- Form for making a offer -->
                <v-form class="ma-5">
                  <v-text-field v-model="offerDto.price" type="number" label="Total price">
                  </v-text-field>
                  <v-text-field v-model="offerDto.shipmentDate" type="date" label="My shipment date">
                  </v-text-field>
                </v-form>
                <!-- End of the form for making a offer -->
                <v-card-actions>
                  <v-spacer> </v-spacer>
                  <v-btn
                    color="success"
                    dark
                    depressed
                    @click="confirmOfferCreation(order)"
                  >
                    <v-icon dark left> mdi-checkbox-marked-circle </v-icon>
                    Confirm
                  </v-btn>
                  <v-btn
                    color="blue"
                    dark
                    depressed
                    @click="
                      order.showAddOfferDialog = !order.showAddOfferDialog
                    "
                    ><v-icon dark left> mdi-minus-circle </v-icon>
                    Close
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <!-- End of the make offer -->
          </v-row>
          <!-- End of the row for the title -->
          <!-- Row for order details -->
          <v-row>
            <v-card-actions>
              <v-btn class="ml-3" color="orange lighten-2" text>
                Order details
              </v-btn>

              <v-spacer></v-spacer>

              <v-btn
                icon
                @click="
                  order.showExtensionDetails = !order.showExtensionDetails
                "
              >
                <v-icon>{{
                  order.showExtensionDetails
                    ? "mdi-chevron-up"
                    : "mdi-chevron-down"
                }}</v-icon>
              </v-btn>
            </v-card-actions>

            <v-expand-transition>
              <div v-show="order.showExtensionDetails">
                <v-divider></v-divider>
                <br />
                <v-card
                  class="pl-2 mr-4 mb-5 ml-8"
                  elevation="4"
                  v-for="orderedDrug in order.orderedDrugs"
                  :key="orderedDrug.drug.code"
                >
                  <v-card-text>
                    We need a {{ orderedDrug.amount }} amount of the
                    {{ orderedDrug.drug.name }} drug [code:
                    {{ orderedDrug.drug.code }}]. And we are created this order
                    at date {{ convertMsToString(order.timestamp) }}, and our
                    deadline date to make your offer is
                    {{ convertMsToString(order.deadline) }}, if you are need
                    more information, you can contact us on our website or go to
                    the {{ order.pharmacy.location }}
                  </v-card-text>
                </v-card>
              </div>
            </v-expand-transition>
          </v-row>
          <!-- End of the row with order details-->
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { getStringDateFromMilliseconds } from "./../../../util/dateHandler";

export default {
  data: () => ({
    allOrders: [],
    offerDto: {
      price: "",
      shipmentDate: "",
    },
  }),
  methods: {
    confirmOfferCreation(selectedOrder) {
      alert("simulation of creation" + this.offerDto.price + " " + this.offerDto.shipmentDate);

       this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PROCUREMENT_OFFERS_ENDPOINT,
          {
            id: this.offerDto.id,
            shipmentDate: this.offerDto.shipmentDate,
            price: this.offerDto.price,
            order:{
              id: selectedOrder.id
            }
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          // TODO: Make some notification here
          alert("Success create");
        })
        .catch((error) => {
          // TODO: Make some tost notifications here
          alert("Error: " + error);
        });
    
    },
    convertMsToString(ms) {
      return getStringDateFromMilliseconds(ms);
    },
  },
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PROCUREMENT_ORDERS_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        this.allOrders = [];
        for (let order of resp.data) {
          let tempObj = {
            id: order.id,
            deadline: order.deadline,
            orderedDrugs: order.orderedDrugs,
            pharmacy: order.pharmacy,
            status: order.status,
            timestamp: order.timestamp,
            showAddOfferDialog: false,
            showExtensionDetails: false,
          };

          this.allOrders.push(tempObj);
        }
      });
  },
};
</script>