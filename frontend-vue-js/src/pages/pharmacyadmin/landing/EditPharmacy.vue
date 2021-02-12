<template>
  <v-row class="pa-5">
    <v-spacer></v-spacer>
    <v-col sm="8" md="6" lg="4" xl="4" cols="12">
      <v-card elevation="4" class="pa-5">
        <v-card-title>Log in</v-card-title>

        <v-text-field v-model="pharmacy.name" label="Name"></v-text-field>

        <v-text-area v-model="pharmacy.description" label="Description"></v-text-area>

        <v-text-field v-model="pharmacy.street" label="Street"></v-text-field>

        <v-text-field v-model="pharmacy.city" label="City"></v-text-field>

        <v-text-field v-model="pharmacy.country" label="Country"></v-text-field>

        <v-text-field
          v-model="pharmacy.dermatologistConsultationPrice"
          label="Dermatologist examination price"
        ></v-text-field>

        <v-text-field
          v-model="pharmacy.pharmacistConsultationPrice"
          label="Pharmacist consultation price"
        ></v-text-field>

        <v-card-actions class="pt-5">
          <v-spacer></v-spacer>
          <v-btn color="primary" @click="geocode">Update</v-btn>
        </v-card-actions>

        <v-overlay :absolute="true" :value="loading" :opacity="0.7" color="#ffffff">
          <v-progress-circular indeterminate color="secondary"></v-progress-circular>
        </v-overlay>

        <v-snackbar v-model="snackbar" :timeout="2000" bottom class="mb-5" right>
          {{ snackbarText }}
          <template v-slot:action="{ attrs }">
            <v-btn color="primary" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
          </template>
        </v-snackbar>
      </v-card>
    </v-col>

    <v-spacer></v-spacer>
  </v-row>
</template>

<script>
export default {
  data() {
    return {
      pharmacy: {},
      loading: true,
      snackbarText: "",
      snackbar: false
    };
  },
  mounted() {
    this.loadPharmacy();
  },
  methods: {
    loadPharmacy: function() {
      this.loading = true;
      this.axios
        .get(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PHARMACY_PROFILE_ENDPOINT,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
            }
          }
        )
        .then(response => {
          this.pharmacy = response.data;
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
    },
    geocode: function() {
      this.axios
        .get("https://nominatim.openstreetmap.org/search", {
          params: {
            format: "json",
            city: this.pharmacy.city,
            street: this.pharmacy.street,
            country: this.pharmacy.country
          }
        })
        .then(response => {
          if (response.data.length == 0) {
            this.snackbarText =
              "Geocoding failed. Keeping old coordinates and saving pharmacy.";
            this.snackbar = true;
            this.savePharmacy();
          } else {
            this.pharmacy.latitude = response.data[0].lat;
            this.pharmacy.longitude = response.data[0].lon;
            this.snackbarText = "Geocoding successful. Saving pharmacy.";
            this.snackbar = true;
            this.savePharmacy();
          }
        })
        .catch(() => {
          this.snackbarText =
            "Geocoding failed. Keeping old coordinates and saving pharmacy.";
          this.snackbar = true;
          this.savePharmacy();
        });
    },
    savePharmacy: function() {
      this.loading = true;
      this.axios
        .put(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PHARMACY_PROFILE_ENDPOINT,
          this.pharmacy,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
            }
          }
        )
        .then(() => {
          this.snackbarText = "Successfully updated.";
          this.snackbar = true;
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
    }
  }
};
</script>