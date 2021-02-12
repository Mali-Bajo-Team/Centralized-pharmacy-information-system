<template>
  <v-container>
    <v-row>
      <!--Left column-->
      <v-col xl="4" sm="12" md="4">
        <v-card>
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2"> Rate pharmacy </v-toolbar-title>
          </v-toolbar>
          <!--End of toolbar of the card-->
          <v-card-text>
            <v-form>
              <v-select
                v-model="createRatingDTO.pharmacyId"
                :items="availablePharmacyForRating"
                item-text="name"
                item-value="id"
                label="Select pharmacy you want to rate"
              ></v-select>
              <v-rating
                v-model="createRatingDTO.rating"
                background-color="accent lighten-3"
                color="accent"
                medium
              ></v-rating>
              <v-btn
                width="110px"
                color="primary"
                class="ma-2 white--text mr-6"
                @click="makeNewRating()"
              >
                Rate
                <v-icon small right dark> mdi-star </v-icon>
              </v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
      <!--End of the left column-->

      <!--Right column-->
      <v-col xl="8" sm="12" md="8">
        <v-card
          elevation="4"
          class="pa-4 ml-10 mb-10"
          v-for="pharmacyRated in pharmaciesRated"
          :key="pharmacyRated.pharmacyId"
        >
          <v-row align="center">
            <v-card-subtitle>
              <h2 class="ml-3">
                {{ pharmacyRated.pharmacyName }},
                {{ pharmacyRated.rating }}
              </h2>
            </v-card-subtitle>
            <v-spacer></v-spacer>

            <v-dialog
              width="500"
              :retain-focus="false"
              v-model="pharmacyRated.showDialogForChangingRate"
            >
              <template #activator="{ on: dialog }">
                <v-tooltip bottom>
                  <template #activator="{ on: tooltip }">
                    <v-btn
                      v-on="{ ...tooltip, ...dialog }"
                      elevation="0"
                      right
                      class="mr-5"
                      dark
                      color="accent"
                      @click="setUpdateDTO(pharmacyRated)"
                    >
                      <v-icon dark left> mdi-pen </v-icon>
                      Change
                    </v-btn>
                  </template>
                  <span>Change rating of the pharmacy</span>
                </v-tooltip>
              </template>
              <v-card>
                <!--Toolbar of the card-->
                <v-toolbar color="primary" dark dense flat>
                  <v-toolbar-title class="body-2">
                    <h3>Change rate of pharmacy</h3>
                  </v-toolbar-title>
                </v-toolbar>
                <!-- End of the toolbar of the card -->
                <v-card-text class="pa-5">
                  <br />
                  <v-form>
                    <v-rating
                      background-color="accent lighten-3"
                      color="accent"
                      medium
                      v-model="updateRatingDTO.newRating"
                    ></v-rating>
                  </v-form>
                </v-card-text>

                <v-card-actions class="pb-4">
                  <v-spacer> </v-spacer>
                  <v-btn
                    color="success"
                    dark
                    depressed
                    @click="confirmNewRate()"
                  >
                    <v-icon dark left> mdi-checkbox-marked-circle </v-icon>
                    Confirm
                  </v-btn>
                  <v-btn
                    color="red lighten-3"
                    dark
                    depressed
                    @click="
                      pharmacyRated.showDialogForChangingRate = !pharmacyRated.showDialogForChangingRate
                    "
                    ><v-icon dark left> mdi-minus-circle </v-icon>
                    Close
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-row>
        </v-card>
      </v-col>
      <!--End of the right column-->
    </v-row>
  </v-container>
</template>


<script>
import { getParsedToken } from "./../../../util/token";
export default {
  data: () => ({
    pharmaciesRated: [],
    updateRatingDTO: {
      id: 0,
      newRating: 1,
    },
    createRatingDTO: {
      pharmacyId: 0,
      rating: 3,
      patientEmail: getParsedToken().sub,
    },
    availablePharmacyForRating: [],
  }),
  mounted() {
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_PHARMACIES_ENDPOINT,
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
        this.availablePharmacyForRating = resp.data;
      })
      .catch((error) => {
        alert(error);
      });

    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_RATING_PHARMACIES_ENDPOINT,
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
        this.pharmaciesRated = resp.data;
      })
      .catch((error) => {
        alert(error);
      });
  },
  methods: {
    setUpdateDTO(pharmacyRated) {
      this.updateRatingDTO.id = pharmacyRated.id;
      this.updateRatingDTO.newRating = pharmacyRated.rating;
    },
    makeNewRating() {
      for(let pharmacy of this.pharmaciesRated){
        if(pharmacy.pharmacyId == this.createRatingDTO.pharmacyId){
          alert("You have already made a rating for thath pharmacy");
          return;
        }
      }
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PATIENT_CREATE_PHARMACY_RATING_ENDPOINT,
          this.createRatingDTO,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((resp) => {
          alert("Successfully created rating for pharmacy.");
          this.pharmaciesRated.push(resp.data);
        })
        .catch((error) => {
          alert(error);
        });
    },

    confirmNewRate() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PATIENT_CHANGE_RATING_PHARMACIES_ENDPOINT,
          {
            newRating: this.updateRatingDTO.newRating,
            id: this.updateRatingDTO.id,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((resp) => {
          console.log(resp.data);
          alert("Successfully confirmed!");
          for (let pharmacyRate of this.pharmaciesRated) {
            if (pharmacyRate.id == this.updateRatingDTO.id) {
              pharmacyRate.rating = resp.data.rating;
              break;
            }
          }
        })
        .catch((error) => {
          alert(error);
        });
    },
  },
};
</script>