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
            <!-- Make a offer -->
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
              </v-card>
            </v-dialog>
            <!-- End of the make offer -->
          </v-row>
          <!-- End of the row for the title -->
          <v-row>
            <v-card-actions>
              <v-btn class="ml-3" color="orange lighten-2" text> Order details </v-btn>

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

                <v-card-text>
                  I'm a thing. But, like most politicians, he promised more than
                  he could deliver. You won't have time for sleeping, soldier,
                  not with all the bed making you'll be doing. Then we'll go
                  with that data file! Hey, you add a one and two zeros to that
                  or we walk! You're going to do his laundry? I've got to find a
                  way to escape.
                </v-card-text>
              </div>
            </v-expand-transition>
          </v-row>
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
  }),
  methods: {
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