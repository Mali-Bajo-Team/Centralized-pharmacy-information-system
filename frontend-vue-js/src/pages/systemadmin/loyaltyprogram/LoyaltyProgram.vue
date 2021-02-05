<template>
  <v-container>
    <v-row>
      <!-- Left column -->
      <v-col xl="4" sm="12" md="4">
        <v-card height="300">
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2">
              Loyalty Program
            </v-toolbar-title>
          </v-toolbar>
        </v-card>
      </v-col>
      <!-- End of the left column -->

      <!-- Right column -->
      <v-col xl="8" sm="12" md="8">
        <v-card
        height="130"
          outlined
          shaped
          elevation="4"
          class="pa-4 ml-10 mb-10"
          v-for="category in loyaltyProgram.categories"
          :key="category.name"
        >
          <!-- Row for title -->
          <v-row>
            <v-card-title>
              <h2>{{ category.name }} category</h2>
            </v-card-title>
          </v-row>
          <!-- End of row for title -->
          <!-- Row with chips-categories -->
          <v-row justify="end">
            <v-chip class="ma-2" color="primary" outlined pill>
              <v-icon left> mdi-alpha-b-circle </v-icon>

              Points: {{ category.minimumPoints }}
            </v-chip>

            <v-chip class="ma-2" color="deep-purple accent-4" outlined>
              <v-icon left> mdi-fire </v-icon>
              Consultation: {{ category.consultationDiscount }}%
            </v-chip>

            <v-chip class="ma-2" color="orange darken-3" outlined>
              <v-icon left> mdi-fire </v-icon>
              Reservation: {{ category.reservationDiscount }}%
            </v-chip>
          </v-row>
          <!-- End of row with categories -->
        </v-card>
      </v-col>
      <!-- End of the right column -->
    </v-row>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    loyaltyProgram: {},
  }),
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_LOYALTY_PROGRAM,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        this.loyaltyProgram = resp.data[0];
      });
  },
};
</script>