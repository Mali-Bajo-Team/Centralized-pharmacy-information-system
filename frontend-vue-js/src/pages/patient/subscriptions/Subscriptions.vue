<template>
  <v-container>
    <v-row>
      <v-col>
        <v-card
          elevation="4"
          class="pa-4 ml-16 mr-16 mb-10"
          v-for="pharmacy in pharmaciesSubscriptions"
          :key="pharmacy.id"
        >
          <!-- Row for title & unsubscribe dialog-->
          <v-row align="center">
            <v-card-subtitle>
              <h4 class="ml-3">
                {{ pharmacy.name }} , {{ pharmacy.location }}
              </h4>
            </v-card-subtitle>
            <v-spacer></v-spacer>
            <!-- Make a response dialog -->
            <v-dialog
              v-model="pharmacy.showDialogForUnsubscribe"
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
                      class="ma-5"
                      fab
                      dark
                      small
                      color="red lighten-2"
                      @click="setPharmacyIdForUnsubscribe(pharmacy)"
                    >
                      <v-icon dark> mdi-bell-cancel-outline </v-icon>
                    </v-btn>
                  </template>
                  <span> Unsubscribe </span>
                </v-tooltip>
              </template>
              <v-card>
                <!--Toolbar of the card-->
                <v-toolbar color="primary" dark dense flat>
                  <v-toolbar-title class="body-2">
                    <h3>Unsubscribe</h3>
                  </v-toolbar-title>
                </v-toolbar>
                <!-- End of the toolbar of the card -->
                <v-card-text class="pa-5">
                  <br />
                  Are you sure you want cancel subscription on
                  {{ pharmacy.name }}
                </v-card-text>

                <v-card-actions class="pb-4">
                  <v-spacer> </v-spacer>
                  <v-btn
                    color="success"
                    dark
                    depressed
                    @click="confirmUnsubscribe()"
                  >
                    <v-icon dark left> mdi-checkbox-marked-circle </v-icon>
                    Confirm
                  </v-btn>
                  <v-btn
                    color="red lighten-3"
                    dark
                    depressed
                    @click="
                      pharmacy.showDialogForUnsubscribe = !pharmacy.showDialogForUnsubscribe
                    "
                    ><v-icon dark left> mdi-minus-circle </v-icon>
                    Close
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <!-- End of the make response dialog -->
          </v-row>
          <!-- End of the row for title & unsubscribe dialog-->
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { getParsedToken } from "./../../../util/token";
export default {
  data: () => ({
    patientEmail: getParsedToken().sub,
    pharmacyIdToUnsubscribe: 0,
    pharmaciesSubscriptions: [],
  }),
  mounted() {
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_SUBSCIPTIONS_ENDPOINT,
        {
          email: getParsedToken().sub,
        },
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        this.pharmaciesSubscriptions = [];
        for (let pharmacy of resp.data) {
          let tempObj = {
            showDialogForUnsubscribe: false,
            id: pharmacy.id,
            name: pharmacy.name,
            location: pharmacy.location,
            rating: pharmacy.rating,
          };
          this.pharmaciesSubscriptions.push(tempObj);
        }
      })
      .catch((error) => {
        alert("Error: " + error);
      });
  },
  methods: {
    setPharmacyIdForUnsubscribe(pharmacy) {
      console.log(pharmacy);
      this.pharmacyIdToUnsubscribe = pharmacy.id;
    },
    confirmUnsubscribe() {
      console.log("simulation of unsub");
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PHARMACIES_SUBSCRIPTION_ENDPOINT + '/'+
            this.pharmacyIdToUnsubscribe +
            "/unsubscribe",
          {
            email: getParsedToken().sub,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          this.pharmaciesSubscriptions = [];
          for(let pharmacy of  this.pharmaciesSubscriptions){
            if(pharmacy.id == this.pharmacyIdToUnsubscribe){
              this.pharmaciesSubscriptions.pop(pharmacy);
              break;
            }
          }
          console.log("success cancel subscibe");
        })
        .catch((error) => {
          alert("Error: " + error);
        });
    },
  },
};
</script>