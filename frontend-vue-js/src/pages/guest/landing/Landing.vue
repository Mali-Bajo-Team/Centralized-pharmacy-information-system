<template>
  <v-container>
    <v-row>
      <!--Left column-->
      <v-col :cols="4">
        <v-card>
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2">
              Search pharmacies
            </v-toolbar-title>
          </v-toolbar>

          <!--End of toolbar of the card-->

          <!--Search form-->
          <v-card-text>
            <v-row no-gutters>
              <v-col>
                <v-text-field label="Name of pharmacy"></v-text-field>
              </v-col>
            </v-row>

            <v-text-field label="Place of pharmacy"></v-text-field>
          </v-card-text>

          <v-card-actions>
            <v-btn color="primary">
              <v-icon> mdi-magnify </v-icon>
              Search
            </v-btn>
          </v-card-actions>
        </v-card>
        <!--End of search form-->
        <br /><br />
        <v-card>
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2">
              Filter pharmacies
            </v-toolbar-title>
          </v-toolbar>

          <!--End of toolbar of the card-->

          <!--Filter form-->
          <v-card-title> Filter pharmacies by grade [1-5] </v-card-title>
          <v-card-text>
            <v-row no-gutters>
              <v-col>
                <v-text-field label="From grade"></v-text-field>
              </v-col>
            </v-row>

            <v-text-field label="To grade"></v-text-field>
          </v-card-text>
          <v-divider> </v-divider>
          <v-card-title> Filter pharmacies by distance [m] </v-card-title>
          <v-card-text>
            <v-row no-gutters>
              <v-col>
                <v-text-field label="From distance"></v-text-field>
              </v-col>
            </v-row>

            <v-text-field label="To distance"></v-text-field>
          </v-card-text>
          <v-card-actions>
            <v-btn color="primary">
              <v-icon> mdi-filter </v-icon>
              Filter
            </v-btn>
          </v-card-actions>
        </v-card>
        <!--End of filter form-->
      </v-col>

      <!--End of left column-->

      <!--Right column-->
      <v-col :cols="8">
        <v-card
          elevation="4"
          class="pa-4 ml-10 mb-10"
          v-for="pharmacy in pharmacies"
          :key="pharmacy.location"
        >
          <v-row>
            <v-col>
              <v-card-title>
                {{ pharmacy.name }}
              </v-card-title>

              <v-card-text>
                <v-chip color="primary">
                  <v-icon color="secondary"> mdi-map-marker </v-icon>

                  {{ pharmacy.location }}
                </v-chip>
              </v-card-text>
            </v-col>

            <v-col>
              <v-card-title>
                <v-rating
                  v-model="pharmacy.rating"
                  color="accent"
                  background-color="accent "
                  half-increments
                  readonly
                >
                </v-rating>
              </v-card-title>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
      <!--End of right column-->
    </v-row>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    pharmacies: [],
  }),
  mounted() {
    this.axios
      .get(process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_PHARMACIES_ENDPOINT,)
      .then((resp) => {
        this.pharmacies = resp.data;
      });
  },


};
</script>