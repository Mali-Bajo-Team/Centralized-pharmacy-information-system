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
              <h4>{{ order.pharmacy.name }}</h4>
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
          <v-row
            ><v-col>
              <h5 class="ml-4">
                Created order: {{ convertMsToString(order.timestamp) }}
              </h5>
              <h5 class="ml-4">
                Deadline to make offer: {{ convertMsToString(order.deadline) }}
              </h5>
            </v-col>
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
          };

          this.allOrders.push(tempObj);
        }
      });
  },
};
</script>