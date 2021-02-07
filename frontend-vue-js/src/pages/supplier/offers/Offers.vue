<template>
  <v-container>
    <v-row>
      <!-- Left column for offers filter -->
      <v-col xl="4" sm="12" md="4">
        <!-- Filter card -->
        <v-card class="mt-5">
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2"> Filter offers </v-toolbar-title>
          </v-toolbar>
          <!--End of toolbar of the card-->
          <br />
          <v-form class="pa-2">
            <v-select
              class="ml-4 mr-4"
              v-model="selectedOfferStatus"
              :items="allOfferStatus"
              label="Filter by offer status"
              chips
            >
            </v-select>
            <br />
          </v-form>
        </v-card>
        <!-- End of the filter card -->
      </v-col>
      <!-- End of the left column for filter -->
      <!-- Right column for offers preview-->
      <v-col xl="8" sm="12" md="8">
        <v-card
          elevation="4"
          class="pa-4 ml-10 mb-10"
          v-for="offer in supplierOffers"
          :key="offer.id"
        >
          <v-row align="center">
            <v-card-title>
              <h5>
                Shipment data: {{ convertMsToString(offer.shipmentDate) }}
              </h5>
              <h5>
                
              </h5>
              
            </v-card-title>
            <v-spacer></v-spacer>
            <v-chip class="mr-2" color="teal" text-color="white" pill>
              {{ offer.price }} $
            </v-chip>
          </v-row>
        </v-card>
      </v-col>
      <!-- End of the right colum for offers preview-->
    </v-row>
  </v-container>
</template>

<script>
import { getStringDateFromMilliseconds } from "./../../../util/dateHandler"

export default {
  data: () => ({
    supplierOffers: [],
    selectedOfferStatus: "",
    allOfferStatus: ["PENDING", "ACCEPTED", "REJECTED"],
  }),
  methods: {
    convertMsToString(ms){
      return getStringDateFromMilliseconds(ms);
    },
  },
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PROCUREMENT_OFFERS_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        // this.supplierOffers = resp.data;
        let counter = 0;
        for (let offer of resp.data) {
          let tempObj = {
            id: counter,
            price: offer.price,
            shipmentDate: offer.shipmentDate,
            status: offer.status,
            order:{
              timestamp: offer.order.timestamp,
              deadline: offer.order.deadline,
              status: offer.order.status,
              pharmacy: offer.order.pharmacy,
              orderedDrugs: offer.order.orderedDrugs
            }
          };
          this.supplierOffers.push(tempObj);
          counter = counter + 1;
        }
      });
  },
};
</script>