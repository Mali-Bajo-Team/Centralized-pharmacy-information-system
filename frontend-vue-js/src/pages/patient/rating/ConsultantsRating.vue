<template>
  <v-container>
    <v-row>
      <!--Left column-->
      <v-col xl="4" sm="12" md="4">
        <v-card>
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2"> Rate consultant </v-toolbar-title>
          </v-toolbar>
          <!--End of toolbar of the card-->
          <v-card-text>
            <v-form>
              <v-select
                :items="consultants"
                label="Select consultant you want to rate"
              ></v-select>
              <v-rating
                background-color="accent lighten-3"
                color="accent"
                medium
              ></v-rating>
              <v-btn
                
                width="110px"
                color="primary"
                class="ma-2 white--text mr-6"
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
          v-for="consultantRated in consultantsRated"
          :key="consultantRated.id"
        >
          <v-row align="center">
            <v-card-subtitle>
              <h2 class="ml-3">
                {{ consultantRated.consultantName }}
                {{ consultantRated.consultantSurname }},
                {{ consultantRated.rating }}
              </h2>
            </v-card-subtitle>
            <v-spacer></v-spacer>

            <v-dialog
              width="500"
              :retain-focus="false"
              v-model="consultantRated.showDialogForChangingRate"
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
                      @click="setUpdateDTO(consultantRated)"
                    >
                      <v-icon dark left> mdi-pen </v-icon>
                      Change
                    </v-btn>
                  </template>
                  <span>Change rating of the consultant</span>
                </v-tooltip>
              </template>
              <v-card>
                <!--Toolbar of the card-->
                <v-toolbar color="primary" dark dense flat>
                  <v-toolbar-title class="body-2">
                    <h3>Change rate of consultant</h3>
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
                      consultantRated.showDialogForChangingRate = !consultantRated.showDialogForChangingRate
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
    consultantsRated: [],
    consultants: ["Ana Perisic", "Vladislav Maksimovic"],
    updateRatingDTO: {
      id: 0,
      newRating: 1,
    },
  }),
  mounted() {
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_RATING_CONSULTANTS_ENDPOINT,
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
        this.consultantsRated = resp.data;
      })
      .catch((error) => {
        alert(error);
      });
  },
  methods: {
    setUpdateDTO(consultantRated) {
      this.updateRatingDTO.id=consultantRated.id;
      this.updateRatingDTO.newRating=consultantRated.rating;
    },
    confirmNewRate() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PATIENT_CHANGE_RATING_CONSULTANTS_ENDPOINT,
          {
            newRating: this.updateRatingDTO.newRating,
            id: this.updateRatingDTO.id

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
          for(let consultantRate of this.consultantsRated){
            if(consultantRate.id == this.updateRatingDTO.id){
              consultantRate.rating = resp.data.rating;
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