<template>
  <v-container>
    <v-row>
      <!-- Left column for filter & search -->
      <v-col xl="4" sm="12" md="4">
        <v-card>
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2"> Search drugs </v-toolbar-title>
          </v-toolbar>

          <!--End of toolbar of the card-->

          <v-form class="pa-2">
            <v-text-field
              prepend-icon="mdi-magnify"
              label="Name of the drug"
            ></v-text-field>
          </v-form>
        </v-card>
      </v-col>
      <!-- End of the left column for filter & search -->

      <!-- Right column for drugs preview-->
      <v-col xl="8" sm="12" md="8">
        <v-card
          elevation="4"
          class="pa-4 ml-10 mb-10"
          v-for="drug in drugs"
          :key="drug.code"
        >
          <!-- Row for title & edit, delete buttons -->
          <v-row>
            <v-card-title>
              <h2>{{ drug.name }}</h2>
            </v-card-title>
          </v-row>
          <!-- End of the row for title & edit, delete buttons -->
          <!-- Row for type of drug and mark -->
          <v-row>
            <v-chip class="ml-4" color="teal" text-color="white" pill>
              <v-icon small left> mdi-pill </v-icon>

              {{ drug.typeOfDrug }}
            </v-chip>

            <v-spacer></v-spacer>
            <v-rating
              class="mr-10"
              v-model="drug.mark"
              color="accent"
              background-color="orange "
              half-increments
              readonly
            >
            </v-rating>
          </v-row>
          <!-- End of the row for type of drug and mark -->
        </v-card>
      </v-col>
      <!-- End of the right colum for drugs preview-->
    </v-row>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    drugs: [],
  }),
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_ALL_DRUGS_ENDPOINT
      )
      .then((resp) => {
        this.drugs = resp.data;
      });
  },
};
</script>