<template>
  <v-container>
    <v-row>
      <!-- Left column for filter & search -->
      <v-col xl="4" sm="12" md="4">
        <!-- Filter card -->
        <v-card class="mt-5">
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2"> Filter offers </v-toolbar-title>
          </v-toolbar>
          <!--End of toolbar of the card-->
<br>
          <v-form class="pa-2">
            <v-select
              class="ml-4 mr-4"
              
              v-model="selectedOfferStatus"
              :items="allOfferStatus"
              label="Filter by offer status"
              chips
            >
            </v-select>
            <br>
          </v-form>
        </v-card>
        <!-- End of the filter card -->
      </v-col>
      <!-- End of the left column for filter & search -->
      <!-- Right column for drugs preview-->
      <v-col xl="8" sm="12" md="8"> </v-col>
      <!-- End of the right colum for drugs preview-->
    </v-row>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    supplierOffers: [],
    selectedOfferStatus: "",
    allOfferStatus: ["PENDING", "ACCEPTED", "REJECTED"],
  }),
  methods: {},
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PROCUREMENT_OFFERS_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        // alert("success");
        this.supplierOffers = resp.data;
      });
  },
};
</script>