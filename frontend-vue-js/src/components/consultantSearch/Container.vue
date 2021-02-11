<template>
  <v-row class="pa-5">
    <v-snackbar v-model="snackbar" :timeout="2000" bottom class="mb-5" right>
      {{ snackbarText }}
      <template v-slot:action="{ attrs }">
        <v-btn color="primary" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
      </template>
    </v-snackbar>

    <v-spacer></v-spacer>
    <v-col sm="12" md="4" lg="3" xl="3" cols="12">
      <!-- Search card -->
      <v-card>
        <v-toolbar color="primary" dark dense flat>
          <v-toolbar-title class="body-2">Search</v-toolbar-title>
        </v-toolbar>
        <v-form class="pa-4">
          <v-text-field v-model="filters.name" label="Name"></v-text-field>
          <v-text-field v-model="filters.surname" label="Surname"></v-text-field>
        </v-form>
      </v-card>

      <!-- Filter card -->
      <v-card class="mt-5">
        <!--Toolbar of the card-->
        <v-toolbar color="primary" dark dense flat>
          <v-toolbar-title class="body-2">Filter</v-toolbar-title>
        </v-toolbar>
        <!--End of toolbar of the card-->

        <v-form class="pa-4">
          <v-card-text>
            <h3>Rating</h3>
          </v-card-text>
          <v-range-slider
            :tick-labels="ratings"
            class="ml-4 mr-4"
            v-model="filters.ratingRange"
            step="25"
          ></v-range-slider>
          <br />
          <v-divider></v-divider>
          <br />
          <v-select
            class="ml-4 mr-4"
            v-model="filters.pharmacies"
            :items="pharmacies"
            label="Select pharmacies"
            multiple
            chips
          ></v-select>
        </v-form>
      </v-card>
    </v-col>

    <v-col sm="12" md="8" lg="6" xl="6" cols="12">
      <template v-if="loading">
        <v-skeleton-loader elevation="4" class="pa-4 mb-10" type="article, actions"></v-skeleton-loader>
        <v-skeleton-loader elevation="4" class="pa-4 mb-10" type="article, actions"></v-skeleton-loader>
      </template>

      <v-card elevation="4" class="pa-4 mb-10" v-show="showEmptyMessage">
        <v-card-title>No consultants found.</v-card-title>
      </v-card>

      <template v-for="cons in consultants">
        <onecons
          v-show="matchesFilters(cons)"
          :key="cons.id"
          :consultant="cons"
          :admin="admin"
          :endpoint="endpoint"
        ></onecons>
      </template>
    </v-col>
    <v-spacer></v-spacer>
  </v-row>
</template>

<script>
import onecons from "./Consultant";

export default {
  components: {
    onecons
  },
  props: {
    admin: {
      type: Boolean,
      default: false
    },
    endpoint: {
      type: String,
      default: ""
    }
  },
  data: () => ({
    loading: true,
    ratings: ["1", "2", "3", "4", "5"],
    consultants: [],
    pharmacies: [],
    filters: {
      name: "",
      surname: "",
      ratingRange: [0, 100],
      pharmacies: []
    },
    snackbarText: "",
    snackbar: false
  }),
  mounted: function() {
    this.axios
      .get(this.endpoint, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
        }
      })
      .then(response => {
        this.consultants = response.data;
        this.loading = false;
      })
      .catch(error => {
        if (
          error.response &&
          error.response.data &&
          error.response.data.message
        )
          this.snackbarText = error.response.data.message;
        else if (error.message) this.snackbarText = error.message;
        else this.snackbarText = "An unknown error has occured.";
        this.performingAction = false;
        this.snackbar = true;
      });

    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PHARMACIES_ENDPOINT
      )
      .then(response => {
        this.pharmacies = [];
        for (let pharmacy of response.data) this.pharmacies.push(pharmacy.name);
      })
      .catch(error => {
        if (
          error.response &&
          error.response.data &&
          error.response.data.message
        )
          this.snackbarText = error.response.data.message;
        else if (error.message) this.snackbarText = error.message;
        else this.snackbarText = "An unknown error has occured.";
        this.performingAction = false;
        this.snackbar = true;
      });
  },
  methods: {
    matchesFilters: function(consultant) {
      if (!consultant.name.toLowerCase().match(this.filters.name.toLowerCase()))
        return false;
      if (
        !consultant.surname
          .toLowerCase()
          .match(this.filters.surname.toLowerCase())
      )
        return false;

      if (consultant.rating < this.filters.ratingRange[0]) return false;
      if (consultant.rating > this.filters.ratingRange[1]) return false;

      if (this.filters.pharmacies.length == 0) return true;

      for (let worksin of consultant.pharmacies)
        for (let pharm of this.filters.pharmacies)
          if (pharm == worksin.name) return true;
      return false;
    }
  },
  computed: {
    showEmptyMessage: function() {
      return !this.loading && this.consultants.length == 0;
    }
  }
};
</script>