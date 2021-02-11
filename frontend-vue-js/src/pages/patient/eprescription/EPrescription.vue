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
            <v-toolbar-title class="body-2">
              Sort pharmacies categories
            </v-toolbar-title>
          </v-toolbar>
          <!--End of toolbar of the card-->
          <v-form class="ma-4 ml-2">
            <!-- Sort by price -->
            <v-row justify="center" class="pa-3">
              <v-btn
                width="110px"
                small
                color="green lighten-2"
                class="ma-2 white--text mr-6"
                @click="sortBy('totalPrice')"
              >
                Price
                <v-icon small right dark> mdi-arrow-up-bold </v-icon>
              </v-btn>
              <v-btn
                small
                width="110px"
                color="green lighten-2"
                class="ma-2 white--text ml-6"
                @click="sortDownBy('totalPrice')"
              >
                Price
                <v-icon small right dark> mdi-arrow-down-bold </v-icon>
              </v-btn>
            </v-row>
            <!-- End of the sort by price -->

            <!-- Sort by rating -->
            <v-row justify="center" class="pa-3">
              <v-btn
                small
                width="110px"
                color="green lighten-2"
                class="ma-2 white--text mr-6"
                @click="sortBy('pharmacyRating')"
              >
                Rating
                <v-icon small right dark> mdi-arrow-up-bold </v-icon>
              </v-btn>
              <v-btn
                small
                width="110px"
                color="green lighten-2"
                class="ma-2 white--text ml-6"
                @click="sortDownBy('pharmacyRating')"
              >
                Rating
                <v-icon small right dark> mdi-arrow-down-bold </v-icon>
              </v-btn>
            </v-row>
            <!-- End of the sort by ratings -->

            <!-- Sort by location -->
            <v-row justify="center" class="pa-3">
              <v-btn
                small
                width="110px"
                color="green lighten-2"
                class="ma-2 white--text mr-6"
                @click="sortBy('pharmacyLocation')"
              >
                Location
                <v-icon small right dark> mdi-arrow-up-bold </v-icon>
              </v-btn>
              <v-btn
                small
                width="110px"
                color="green lighten-2"
                class="ma-2 white--text ml-6"
                @click="sortDownBy('pharmacyLocation')"
              >
                Location
                <v-icon small right dark> mdi-arrow-down-bold </v-icon>
              </v-btn>
            </v-row>
            <!-- End of the sort by location -->

            <!-- Sort by pharmacy name -->
            <v-row justify="center" class="pa-3">
              <v-btn
                small
                width="110px"
                color="green lighten-2"
                class="ma-2 white--text mr-6"
                @click="sortBy('pharmacyName')"
              >
                Name
                <v-icon small right dark> mdi-arrow-up-bold </v-icon>
              </v-btn>
              <v-btn
                small
                width="110px"
                color="green lighten-2"
                class="ma-2 white--text ml-6"
                @click="sortDownBy('pharmacyName')"
              >
                Name
                <v-icon small right dark> mdi-arrow-down-bold </v-icon>
              </v-btn>
            </v-row>
            <!-- End of the sort by pharmacy name -->
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
                <v-card-text class="pa-5">
                  <br />
                  Are you sure you want a buy all drugs from this pharmacy ?
                </v-card-text>

                <v-card-actions class="pb-4">
                  <v-spacer> </v-spacer>
                  <v-btn
                    color="success"
                    dark
                    depressed
                    @click="confirmEPrescriptionReservation(pharmacy)"
                  >
                    <v-icon dark left> mdi-checkbox-marked-circle </v-icon>
                    Confirm
                  </v-btn>
                  <v-btn
                    color="red lighten-3"
                    dark
                    depressed
                    @click="
                      pharmacy.showConfirmDialog = !pharmacy.showConfirmDialog
                    "
                    ><v-icon dark left> mdi-minus-circle </v-icon>
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
import { getParsedToken } from "./../../../util/token";
export default {
  components: { QrcodeCapture },
  data() {
    return {
      sortByRating: false,
      sortByPrice: false,
      sortByLocation: false,
      sortByName: false,
      patientEmail: getParsedToken().sub,
      readedQr: {},
      pharmaciesWithRequiredDrugsAmount: [],
    };
  },
  methods: {
    confirmEPrescriptionReservation(pharmacy) {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_EPRESCRIPTION_SAVE_ENDPOINT,
          {
            patientEmail:this.patientEmail,
            pharmacyId: pharmacy.pharmacyId,
            prescribedDrugs: this.readedQr,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          alert("success");
          // this.pharmaciesWithRequiredDrugsAmount = [];
          // for (let pharmacy of resp.data) {
          //   let tempObj = {
          //     showConfirmDialog: false,
          //     pharmacyId: pharmacy.pharmacyId,
          //     totalPrice: pharmacy.totalPrice,
          //     pharmacyRating: pharmacy.pharmacyRating,
          //     pharmacyName: pharmacy.pharmacyName,
          //     pharmacyLocation: pharmacy.pharmacyLocation,
          //   };
          //   this.pharmaciesWithRequiredDrugsAmount.push(tempObj);
          // }
        })
        .catch((error) => {
          alert("Error: " + error);
        });
    },
    sortBy(prop) {
      this.pharmaciesWithRequiredDrugsAmount.sort((a, b) =>
        a[prop] < b[prop] ? -1 : 1
      );
    },
    sortDownBy(prop) {
      this.pharmaciesWithRequiredDrugsAmount.sort((a, b) =>
        a[prop] > b[prop] ? -1 : 1
      );
    },
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