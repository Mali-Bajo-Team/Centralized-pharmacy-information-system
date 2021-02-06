<template>
  <v-container>
    <v-row>
      <!-- Left column for filter & search -->
      <v-col xl="4" sm="12" md="4">
        <v-card>
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2">
              Search drugs
            </v-toolbar-title>
          </v-toolbar>

          <!--End of toolbar of the card-->

          <v-form class="pa-2">
              <v-text-field prepend-icon="mdi-magnify"  label="Name of the drug"></v-text-field>
          </v-form>
        </v-card>
      </v-col>
      <!-- End of the left column for filter & search -->

      <!-- Right column for drugs preview-->
      <v-col xl="8" sm="12" md="8">
        <p>Right</p>
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