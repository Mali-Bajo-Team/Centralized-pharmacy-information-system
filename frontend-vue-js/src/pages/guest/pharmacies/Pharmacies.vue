<template>
  <v-container>
    <v-row>
      <!--Left column-->
      <v-col xl="4" sm="12" md="4">
        <v-card>
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2">Search pharmacies</v-toolbar-title>
          </v-toolbar>

          <!--End of toolbar of the card-->

          <!--Search form-->
          <v-card-text>
            <v-row no-gutters>
              <v-col>
                <v-text-field label="Name of pharmacy" v-model="searchFieldForName"></v-text-field>
              </v-col>
            </v-row>

            <v-text-field label="Place of pharmacy" v-model="searchFieldForPlace"></v-text-field>
          </v-card-text>
        </v-card>
        <!--End of search form-->
        <br />
        <br />
        <v-card>
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2">Filter pharmacies</v-toolbar-title>
          </v-toolbar>

          <!--End of toolbar of the card-->

          <!--Filter form-->
          <v-card-title>Filter pharmacies by grade [1-5]</v-card-title>
          <v-card-text>
            <v-row no-gutters>
              <v-range-slider :tick-labels="marks" class="ml-4 mr-4" v-model="markRange" step="25"></v-range-slider>
            </v-row>
          </v-card-text>
        </v-card>
        <!--End of filter form-->
        <br />
        <br />
        <!-- Sorting pharmacies -->
        <v-card class="mt-5">
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2">Sort pharmacies</v-toolbar-title>
          </v-toolbar>
          <!--End of toolbar of the card-->
          <v-form class="ma-4 ml-2">
            <!-- Sort by pharmacy name -->
            <v-row justify="center" class="pa-3">
              <v-btn
                small
                width="110px"
                color="green lighten-2"
                class="ma-2 white--text mr-6"
                @click="sortBy('name')"
              >
                Name
                <v-icon small right dark>mdi-arrow-up-bold</v-icon>
              </v-btn>
              <v-btn
                small
                width="110px"
                color="green lighten-2"
                class="ma-2 white--text ml-6"
                @click="sortDownBy('name')"
              >
                Name
                <v-icon small right dark>mdi-arrow-down-bold</v-icon>
              </v-btn>
            </v-row>
            <!-- End of the sort by pharmacy name -->
            <!-- Sort by location -->
            <v-row justify="center" class="pa-3">
              <v-btn
                small
                width="110px"
                color="green lighten-2"
                class="ma-2 white--text mr-6"
                @click="sortBy('city')"
              >
                City
                <v-icon small right dark>mdi-arrow-up-bold</v-icon>
              </v-btn>
              <v-btn
                small
                width="110px"
                color="green lighten-2"
                class="ma-2 white--text ml-6"
                @click="sortDownBy('city')"
              >
                City
                <v-icon small right dark>mdi-arrow-down-bold</v-icon>
              </v-btn>
            </v-row>
            <!-- End of the sort by location -->
            <!-- Sort by rating -->
            <v-row justify="center" class="pa-3">
              <v-btn
                small
                width="110px"
                color="green lighten-2"
                class="ma-2 white--text mr-6"
                @click="sortBy('rating')"
              >
                Rating
                <v-icon small right dark>mdi-arrow-up-bold</v-icon>
              </v-btn>
              <v-btn
                small
                width="110px"
                color="green lighten-2"
                class="ma-2 white--text ml-6"
                @click="sortDownBy('rating')"
              >
                Rating
                <v-icon small right dark>mdi-arrow-down-bold</v-icon>
              </v-btn>
            </v-row>
            <!-- End of the sort by ratings -->
          </v-form>
        </v-card>
        <!-- End of the pharmacies sorting -->
      </v-col>
      <!--End of left column-->

      <!--Right column-->
      <v-col xl="8" sm="12" md="8">
        <v-card
          elevation="4"
          class="pa-4 ml-10 mb-10"
          v-for="pharmacy in filteredPharmacies"
          :key="pharmacy.location"
        >
          <v-row>
            <v-col>
              <v-card-title>
                <h2>{{ pharmacy.name }}</h2>
              </v-card-title>

              <v-card-text>
                <v-chip color="primary">
                  <v-icon color="secondary">mdi-map-marker</v-icon>
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
                ></v-rating>
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
    searchFieldForPlace: "",
    searchFieldForName: "",
    markRange: [0, 100],
    marks: ["1", "2", "3", "4", "5"]
  }),
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PHARMACIES_ENDPOINT
      )
      .then(resp => {
        this.pharmacies = resp.data;
      });
  },
  methods: {
    sortBy(prop) {
      this.pharmacies.sort((a, b) => (a[prop] < b[prop] ? -1 : 1));
    },
    sortDownBy(prop) {
      this.pharmacies.sort((a, b) => (a[prop] > b[prop] ? -1 : 1));
    },
    isMatchedPharmacy(pharmacy) {
      //search by name of pharmacy
      if (
        !pharmacy.name
          .toLowerCase()
          .match(this.searchFieldForName.toLowerCase())
      )
        return false;

      //search by location of pharmacy
      if (
        !pharmacy.location
          .toLowerCase()
          .match(this.searchFieldForPlace.toLowerCase())
      )
        return false;

      let minMark = this.markRange[0] / 25 + 1;
      let maxMark = this.markRange[1] / 25 + 1;
      if (pharmacy.rating < minMark || pharmacy.rating > maxMark) return false;

      return true;
    }
  },
  computed: {
    filteredPharmacies: function() {
      return this.pharmacies.filter(pharmacy => {
        return this.isMatchedPharmacy(pharmacy);
      });
    }
  }
};
</script>