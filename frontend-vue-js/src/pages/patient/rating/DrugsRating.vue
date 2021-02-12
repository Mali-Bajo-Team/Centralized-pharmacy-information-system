<template>
  <v-container>
    <v-row>
      <!--Left column-->
      <v-col xl="4" sm="12" md="4">
        <v-card>
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2"> Rate drug </v-toolbar-title>
          </v-toolbar>
          <!--End of toolbar of the card-->
          <v-card-text>
            <v-form>
              <v-select
                v-model="createRatingDTO.drugCode"
                :items="availableDrugsForRating"
                item-text="drugName"
                item-value="drugCode"
                label="Select drug you want to rate"
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
          v-for="drugRated in drugsRated"
          :key="drugRated.drugCode"
        >
          <v-row align="center">
            <v-card-subtitle>
              <h2 class="ml-3">
                {{ drugRated.drugName }},
                {{ drugRated.rating }}
              </h2>
            </v-card-subtitle>
            <v-spacer></v-spacer>

            <v-dialog
              width="500"
              :retain-focus="false"
              v-model="drugRated.showDialogForChangingRate"
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
                      @click="setUpdateDTO(drugRated)"
                    >
                      <v-icon dark left> mdi-pen </v-icon>
                      Change
                    </v-btn>
                  </template>
                  <span>Change rating of the drug</span>
                </v-tooltip>
              </template>
              <v-card>
                <!--Toolbar of the card-->
                <v-toolbar color="primary" dark dense flat>
                  <v-toolbar-title class="body-2">
                    <h3>Change rate of drug</h3>
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
                      drugRated.showDialogForChangingRate = !drugRated.showDialogForChangingRate
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
    availableDrugsForRating: [],
    drugsRated: [],
    updateRatingDTO: {
      id: 0,
      newRating: 1,
    },
    createRatingDTO: {
      patientEmail: getParsedToken().sub,
      drugCode: "",
      rating: 3,
    },
  }),
  mounted() {
    //get patient rated drugs
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_RATING_DRUGS_ENDPOINT,
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
        this.drugsRated = resp.data;
      })
      .catch((error) => {
        alert(error);
      });

    // get available drugs for reating
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_AVAILABLE_DRUGS_REATING_ENDPOINT,
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
        this.availableDrugsForRating = resp.data;
      })
      .catch((error) => {
        alert(error);
      });
  },
  methods: {
    makeNewRating() {
      for (let drug of this.drugsRated) {
        if (drug.drugCode == this.createRatingDTO.drugCode) {
          alert("You have already made a rating for thath drug");
          return;
        }
      }
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PATIENT_CREATE_DRUG_RATING_ENDPOINT,
          this.createRatingDTO,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((resp) => {
          alert("Successfully created rating for drugs.");
          this.drugsRated.push(resp.data);
        })
        .catch((error) => {
          alert(error);
        });
    },
    setUpdateDTO(drugRated) {
      this.updateRatingDTO.id = drugRated.id;
      this.updateRatingDTO.newRating = drugRated.rating;
    },
    confirmNewRate() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PATIENT_CHANGE_RATING_DRUGS_ENDPOINT,
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
          for (let drugRate of this.drugsRated) {
            if (drugRate.id == this.updateRatingDTO.id) {
              drugRate.rating = resp.data.rating;
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