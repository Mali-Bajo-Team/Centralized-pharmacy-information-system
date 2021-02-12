<template>
  <div>
    <v-row>
      <v-spacer></v-spacer>
      <v-col xl="9" lg="9" md="12" sm="12" cols="12">
        <v-card elevation="4" class="pa-5">
          <v-card-title>
            <h2>{{ pharmacy.name }}</h2>
            <v-btn
              text
              @click="unsubscribe"
              color="error"
              class="ml-4 mr-4"
              v-if="pharmacy.subscribed"
            >Unsubscribe</v-btn>
            <v-btn text @click="subscribe" color="primary" class="ml-4 mr-4" v-else>Subscribe</v-btn>
            <v-spacer></v-spacer>
            <v-rating
              v-model="pharmacy.rating"
              color="accent"
              background-color="accent "
              half-increments
              readonly
            ></v-rating>
          </v-card-title>
          <v-tabs fixed-tabs v-model="tab" background-color="transparent">
            <v-tab>Dermatologists</v-tab>
            <v-tab>Pharmacists</v-tab>
          </v-tabs>
          <v-tabs-items v-model="tab" class="pa-0">
            <v-tab-item class="pa-0">
              <consultants :endpoint="dermatologistEndpoint"></consultants>
            </v-tab-item>
            <v-tab-item class="pa-0">  
              <consultants :endpoint="pharmacistEndpoint"></consultants>
            </v-tab-item>
          </v-tabs-items>
        </v-card>
      </v-col>
      <v-spacer></v-spacer>
    </v-row>

    <v-snackbar v-model="snackbar" :timeout="2000" bottom class="mb-5" right>
      {{ snackbarText }}
      <template v-slot:action="{ attrs }">
        <v-btn color="primary" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import consultants from "@/components/consultantSearch/Container";

export default {
  components: {
    consultants
  },
  props: {
    id: {
      required: true
    }
  },
  data: () => ({
    tab: null,
    pharmacy: {},
    loading: true,
    snackbarText: "",
    snackbar: false
  }),
  mounted() {
    this.loadPharmacy();
  },
  watch: {
    id: function() {
      this.loadPharmacy();
    }
  },
  methods: {
    loadPharmacy: function() {
      this.loading = true;
      this.axios
        .get(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PHARMACY_DETAILS_ENDPOINT +
            "/" +
            this.id,
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
          this.performingAction = false;
          this.snackbar = true;
        });
    },
    subscribe: function() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PHARMACY_DETAILS_ENDPOINT +
            "/" +
            this.id +
            "/subscribe",
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
            }
          }
        )
        .then(() => {
          this.snackbarText = "Successfully subscribed.";
          this.snackbar = true;
          this.pharmacy.subscribed = true;
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
    unsubscribe: function() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PHARMACY_DETAILS_ENDPOINT +
            "/" +
            this.id +
            "/unsubscribe",
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
            }
          }
        )
        .then(() => {
          this.snackbarText = "Successfully unsubscribed.";
          this.snackbar = true;
          this.pharmacy.subscribed = false;
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
    }
  },
  computed: {
    dermatologistEndpoint: function() {
      return (
        process.env.VUE_APP_BACKEND_URL +
        process.env.VUE_APP_DERMATOLOGISTS_BY_PHARMACY_ENDPOINT +
        "/" +
        this.id
      );
    },
    pharmacistEndpoint: function() {
      return (
        process.env.VUE_APP_BACKEND_URL +
        process.env.VUE_APP_PHARMACIST_BY_PHARMACY_ENDPOINT +
        "/" +
        this.id
      );
    },
    drugEndpoint: function() {
      return (
        process.env.VUE_APP_BACKEND_URL +
        process.env.VUE_APP_DRUG_BY_PHARMACY_ENDPOINT +
        "/" +
        this.id
      );
    },
    predefinedEndpoint: function() {
      return (
        process.env.VUE_APP_BACKEND_URL +
        process.env.VUE_APP_PREDEFINED_BY_PHARMACY_ENDPOINT +
        "/" +
        this.id
      );
    }
  }
};
</script>