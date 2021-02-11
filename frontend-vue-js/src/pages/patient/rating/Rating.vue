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
                v-model="rating"
                background-color="accent lighten-3"
                color="accent"
                medium
              ></v-rating>
              <v-btn
                small
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
            <v-card-subtitle>
                <h2 class="ml-3">
                {{consultantRated.consultantName}} {{consultantRated.consultantSurname}}, {{consultantRated.rating}}
              
                </h2>
            </v-card-subtitle>
            <v-card-actions class="pb-4">
                <v-spacer> </v-spacer>
                <v-btn
                    color="accent"
                    dark
                    depressed
                   
                  >
                    <v-icon dark left> mdi-pen </v-icon>
                    Change
                  </v-btn>

            </v-card-actions>
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
    consultantsRated:[],
    consultants: ["Ana Perisic", "Vladislav Maksimovic"],
  }),
  mounted() {
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env
            .VUE_APP_PATIENT_RATING_CONSULTANTS_ENDPOINT,
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
};
</script>