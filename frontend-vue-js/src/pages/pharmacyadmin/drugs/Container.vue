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
          <v-text-field v-model="filters.name" label="Drug name"></v-text-field>
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
          <v-select
            class="ml-4 mr-4"
            v-model="filters.drugClasses"
            :items="drugClasses"
            label="Select drug type"
            multiple
            chips
            clearable
          ></v-select>
          <v-select
            class="ml-4 mr-4"
            v-model="filters.drugForms"
            :items="drugForms"
            label="Select drug form"
            multiple
            chips
            clearable
          ></v-select>
        </v-form>
      </v-card>
    </v-col>

    <v-col sm="12" md="8" lg="6" xl="6" cols="12">
      <template v-if="loading">
        <v-skeleton-loader elevation="4" class="pa-4 mb-10 mx-auto" type="article, actions"></v-skeleton-loader>
        <v-skeleton-loader elevation="4" class="pa-4 mb-10 mx-auto" type="article, actions"></v-skeleton-loader>
      </template>

      <v-card elevation="4" class="pa-4 mb-10" v-show="showEmptyMessage">
        <v-card-title>No consultants found.</v-card-title>
      </v-card>

      <template v-for="drug in drugs">
        <oneitem v-show="matchesFilters(drug)" :key="drug.code" :drug="drug"></oneitem>
      </template>
    </v-col>
    <v-spacer></v-spacer>
  </v-row>
</template>

<script>
import oneitem from "./DrugInPharmacy";

export default {
  components: {
    oneitem
  },
  data: () => ({
    loading: true,
    drugs: [],
    drugClasses: [],
    drugForms: [],
    filters: {
      name: "",
      drugForms: [],
      drugClasses: []
    },
    snackbarText: "",
    snackbar: false
  }),
  mounted: function() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_DRUGS_IN_PHARMACY_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
          }
        }
      )
      .then(response => {
        this.drugs = response.data;
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
        this.snackbar = true;
        this.loading = false;
      });

    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_ALL_DRUGS_TYPES_ENDPOINT
      )
      .then(response => {
        this.drugClasses = [];
        for (let drc of response.data) this.drugClasses.push(drc.name);
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
        this.snackbar = true;
      });

    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_ALL_DRUGS_FORMS_ENDPOINT
      )
      .then(response => {
        this.drugForms = [];
        for (let drf of response.data) this.drugForms.push(drf.name);
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
        this.snackbar = true;
      });
  },
  methods: {
    matchesFilters: function(drug) {
      if (!drug.name.toLowerCase().match(this.filters.name.toLowerCase()))
        return false;

      if (
        this.filters.drugClasses.length == 0 &&
        this.filters.drugForms.length == 0
      )
        return true;

      if (this.filters.drugClasses.length > 0)
        for (let drc of this.filters.drugClasses)
          if (drug.drugClass == drc) return true;

      if (this.filters.drugForms.length > 0)
        for (let drf of this.filters.drugForms)
          if (drug.drugForm == drf) return true;

      return false;
    }
  },
  computed: {
    showEmptyMessage: function() {
      return !this.loading && this.drugs.length == 0;
    }
  }
};
</script>