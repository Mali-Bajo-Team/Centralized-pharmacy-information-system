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
          v-for="offer in filteredOffers"
          :key="offer.id"
        >
          <!-- Row, offer for pharmacy, status & price -->
          <v-row align="center">
            <v-card-title>
              <h4>Offer for {{ offer.order.pharmacy.name }}</h4>
            </v-card-title>
            <v-spacer></v-spacer>
            <v-chip
              class="mr-2"
              color="light-blue lighten-2"
              text-color="white"
              pill
            >
              {{ offer.status.toLowerCase() }}
            </v-chip>
            <v-chip
              class="mr-2"
              color="light-blue lighten-2"
              text-color="white"
              pill
            >
              {{ offer.price }} €
            </v-chip>
          </v-row>
          <!-- End of the row, offer for pharmacy, status & price -->
          <!-- Row, Shipment date & edit offer -->
          <v-row align="center">
            <v-card-subtitle>
              My shipment date: {{ convertMsToString(offer.shipmentDate) }}
            </v-card-subtitle>
            <v-spacer></v-spacer>
            <!-- Edit a offer -->
            <v-dialog
              v-model="offer.showEditDialog"
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
                      class="mr-3"
                      fab
                      dark
                      x-small
                      color="primary"
                      @click="setOfferDto(offer)"
                    >
                      <v-icon dark> mdi-pencil </v-icon>
                    </v-btn>
                  </template>
                  <span>Edit offer</span>
                </v-tooltip>
              </template>
              <v-card>
                <!--Toolbar of the card-->
                <v-toolbar color="primary" dark dense flat>
                  <v-toolbar-title class="body-2">
                    <h3>Edit offer for {{ offer.order.pharmacy.name }}</h3>
                  </v-toolbar-title>
                </v-toolbar>
                <!-- End of toolbar of the card -->
                <br />
                <v-form class="ma-5">
                  <v-text-field
                    type="number"
                    label="Price"
                    v-model="offerDto.price"
                  >
                  </v-text-field>
                  <v-text-field
                    placeholder="dd-mm-yyyy"
                    type="date"
                    label="Shipment date"
                    v-model="offerDto.shipmentDate"
                  >
                  </v-text-field>
                </v-form>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                    color="success"
                    dark
                    depressed
                    @click="confirmOfferEdit(offer)"
                  >
                    <v-icon dark left> mdi-checkbox-marked-circle </v-icon>
                    Confirm
                  </v-btn>
                  <v-btn
                    color="blue"
                    dark
                    depressed
                    @click="offer.showEditDialog = !offer.showEditDialog"
                    ><v-icon dark left> mdi-minus-circle </v-icon>
                    Close
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <!-- End of the edit offer -->
          </v-row>
          <!-- End of the row, Shipment date & edit offer -->
        </v-card>
      </v-col>
      <!-- End of the right colum for offers preview-->
    </v-row>
  </v-container>
</template>

<script>
import { getStringDateFromMilliseconds } from "./../../../util/dateHandler";

export default {
  data: () => ({
    supplierOffers: [],
    selectedOfferStatus: "",
    allOfferStatus: ["Without", "PENDING", "ACCEPTED", "REJECTED"],
    offerDto: {
      price: "",
      shipmentDate: "",
    },
  }),
  methods: {

    setOfferDto(offer) {
      this.offerDto.price = offer.price;
      this.offerDto.shipmentDate = getStringDateFromMilliseconds(
        offer.shipmentDate
      );
    },
    confirmOfferEdit(offer) {
      alert("Edit confirm simulation" + offer.id);
    },
    convertMsToString(ms) {
      return getStringDateFromMilliseconds(ms);
    },
    isMatchedOffer(offer){
      console.log(offer.shipmentDate);

      if(this.selectedOfferStatus.toLowerCase() != "without" && !offer.status.toLowerCase().match(this.selectedOfferStatus.toLowerCase())) return false;
      return true;
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
            id: offer.id,
            showEditDialog: false,
            price: offer.price,
            shipmentDate: offer.shipmentDate,
            status: offer.status,
            order: {
              timestamp: offer.order.timestamp,
              deadline: offer.order.deadline,
              status: offer.order.status,
              pharmacy: offer.order.pharmacy,
              orderedDrugs: offer.order.orderedDrugs,
            },
          };
          this.supplierOffers.push(tempObj);
          counter = counter + 1;
        }
      });
  },
  computed: {
    filteredOffers: function(){
      return this.supplierOffers.filter((offer) =>{
        return this.isMatchedOffer(offer);
      });
    },
  },
};
</script>