<template>
  <v-container>
    <v-row>
      <!-- Left column for qr-code-upload, filter & search -->
      <v-col xl="4" sm="12" md="4">
        <!-- Upload QR code card -->
        <v-card>
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2"> Search drugs </v-toolbar-title>
          </v-toolbar>
          <!--End of the toolbar of the card-->

          <v-card-subtitle class="ma-1"> Upload your QR code</v-card-subtitle>
          <v-form class="pa-5">
            <qrcode-capture @decode="onDecode" />
          </v-form>
        </v-card>
        <!--End of the upload of the QR code card -->
        <v-card class="mt-5">
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2"> Sort pharmacies categories </v-toolbar-title>
          </v-toolbar>
          <!--End of toolbar of the card-->
          <v-form class="ma-4 ml-2">
            <v-row class="ml-6 mb-6">
              <v-switch
                v-model="sortByPrice"
                label="price"
                color="indigo"
                value="indigo"
                hide-details
              ></v-switch>
              <v-switch
              class="ml-11"
                v-model="sortByRating"
                label="rating"
                color="indigo"
                value="indigo"
                hide-details
              ></v-switch>
            </v-row>
            <v-divider></v-divider>
             <v-row class="ml-6 mt-2 pb-6">
              <v-switch
                v-model="sortByLocation"
                label="location"
                color="indigo"
                value="indigo"
                hide-details
              ></v-switch>
              <v-switch
               class="ml-6"
                v-model="sortByName"
                label="name"
                color="indigo"
                value="indigo"
                hide-details
              ></v-switch>
            </v-row>

          </v-form>
        </v-card>
      </v-col>
      <!-- Left column for qr-code-upload, filter & search -->

      <!-- Right column for pharmacies -->
      <v-col xl="8" sm="12" md="8">
        <v-card
          elevation="4"
          class="pa-4 ml-10 mb-10"
          v-for="pharmacy in pharmaciesWithRequiredDrugsAmount"
          :key="pharmacy.pharmacyId"
        >
          <!-- Row for title, location & buy confirmation dialog -->
          <v-row align="center">
            <v-card-subtitle>
              <h3>{{ pharmacy.pharmacyName }}</h3>
            </v-card-subtitle>
            <v-spacer></v-spacer>
            <v-chip small class="ml-4" color="teal" text-color="white" pill>
              {{ pharmacy.totalPrice }} €
            </v-chip>
            <!-- Buy confirmation dialog -->
            <v-dialog
              v-model="pharmacy.showConfirmDialog"
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
                      class="mr-10 ml-3"
                      fab
                      dark
                      x-small
                      color="green darken-2"
                      ><v-icon dark> mdi-basket </v-icon>
                    </v-btn>
                  </template>
                  <span>Buy</span>
                </v-tooltip>
              </template>
              <v-card>
                <!--Toolbar of the card-->
                <v-toolbar color="primary" dark dense flat>
                  <v-toolbar-title class="body-2">
                    <h3>Confirmation</h3>
                  </v-toolbar-title>
                </v-toolbar>
                <!-- End of toolbar of the card -->
                <br />
                <v-form class="ma-5">
                  <p>Make some confirmation question ...</p>
                </v-form>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                    color="primary"
                    @click="
                      pharmacy.showConfirmDialog = !pharmacy.showConfirmDialog
                    "
                  >
                    Close
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <!-- End of the buy confirmation dialog  -->
          </v-row>
          <!-- End of the row for title, location & buy confirmation dialog  -->
          <v-row class="mb-1">
            <v-chip
              small
              class="ma-2 ml-4"
              color="teal"
              text-color="white"
              pill
            >
              <v-icon small left> mdi-pill </v-icon>

              {{ pharmacy.pharmacyLocation }}
            </v-chip>
            <v-spacer></v-spacer>
            <!-- Mark of drug -->
            <v-rating
              class="mr-10"
              v-model="pharmacy.pharmacyRating"
              color="accent"
              background-color="orange "
              half-increments
              readonly
            >
            </v-rating>
            <!-- End of mark of the drug -->
          </v-row>
        </v-card>
      </v-col>
      <!-- End of the right column for the pharmacies -->
    </v-row>
  </v-container>
</template>

<script>
import { QrcodeCapture } from "vue-qrcode-reader";

export default {
  components: { QrcodeCapture },
  data() {
    return {
      sortByRating: false,
      sortByPrice: false,
      sortByLocation: false,
      sortByName: false,
      readedQr: {},
      pharmaciesWithRequiredDrugsAmount: [],
    };
  },

  methods: {
    onDecode(readedQr) {
      this.readedQr = JSON.parse(readedQr);
      console.log(this.readedQr);
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PHARMACIES_REQUIRED_DRUGS,
          this.readedQr,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((resp) => {
          this.pharmaciesWithRequiredDrugsAmount = [];
          for (let pharmacy of resp.data) {
            let tempObj = {
              showConfirmDialog: false,
              pharmacyId: pharmacy.pharmacyId,
              totalPrice: pharmacy.totalPrice,
              pharmacyRating: pharmacy.pharmacyRating,
              pharmacyName: pharmacy.pharmacyName,
              pharmacyLocation: pharmacy.pharmacyLocation,
            };
            this.pharmaciesWithRequiredDrugsAmount.push(tempObj);
          }
        })
        .catch((error) => {
          alert("Error: " + error);
        });
    },
  },
};
</script>