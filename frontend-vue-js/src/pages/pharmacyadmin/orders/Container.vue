<template>
  <v-row class="pa-5">
    <v-spacer></v-spacer>
    <v-col sm="8" md="6" lg="6" xl="6" cols="12">
      <template v-if="loading">
        <v-skeleton-loader elevation="4" class="pa-4 mb-10 mx-auto" type="article, actions"></v-skeleton-loader>
        <v-skeleton-loader elevation="4" class="pa-4 mb-10 mx-auto" type="article, actions"></v-skeleton-loader>
      </template>

      <template v-if="orders">
          <ordercomp v-for="ord in orders" :key="ord.id" :orderProp="ord"></ordercomp>
      </template>

      <v-card elevation="4" class="pa-4 mb-10" v-show="showEmptyMessage">
        <v-card-title>There are no drug orders.</v-card-title>
      </v-card>

      <v-snackbar v-model="snackbar" :timeout="2000" bottom class="mb-5" right>
        {{ snackbarText }}
        <template v-slot:action="{ attrs }">
          <v-btn color="primary" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
        </template>
      </v-snackbar>
    </v-col>

    <v-spacer></v-spacer>
  </v-row>
</template>

<script>
import ordercomp from "./Order";

export default {
  components: {
    ordercomp
  },
  data: () => ({
    orders: [],
    loading: true,
    endpoint: "",
    snackbarText: "",
    snackbar: false
  }),
  mounted() {
    this.endpoint =
      process.env.VUE_APP_BACKEND_URL +
      process.env.VUE_APP_PROCUREMENT_ORDERS_ENDPOINT;
    this.axios
      .get(this.endpoint, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
        }
      })
      .then(response => {
        this.orders = response.data;
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
      });
  },
  computed: {
    showEmptyMessage: function() {
      return !this.loading && this.orders.length == 0;
    }
  }
};
</script>