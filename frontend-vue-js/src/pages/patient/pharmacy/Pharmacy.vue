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
            <v-tab>Location</v-tab>
            <v-tab>Dermatologists</v-tab>
            <v-tab>Pharmacists</v-tab>
            <v-tab>Available drugs</v-tab>
            <v-tab>Consultations</v-tab>
          </v-tabs>
          <v-tabs-items v-model="tab">
            <v-tab-item>
              <vl-map data-projection="EPSG:4326" style="height: 400px;" v-if="!loading">
                <vl-view
                  ref="mapView"
                  :zoom.sync="zoom"
                  :center.sync="center"
                  :rotation.sync="rotation"
                ></vl-view>

                <vl-layer-tile>
                  <vl-source-osm></vl-source-osm>
                </vl-layer-tile>

                <vl-feature v-if="marker">
                  <vl-geom-point ref="geom" :coordinates="marker"></vl-geom-point>
                  <vl-style-box>
                    <vl-style-icon src="@/assets/icon1.png" :scale="1.2" :anchor="[0.5, 1]"></vl-style-icon>
                  </vl-style-box>
                </vl-feature>

                <vl-overlay v-if="marker" id="overlay" :position="marker">
                  <template>
                    <v-chip
                      color="white"
                      text-color="primary"
                      class="pl-2 pr-2 pt-0 pb-0"
                      style="transform: translateX(-50%); border: 3px #000000 !important;"
                      label
                    >{{pharmacy.address}}</v-chip>
                  </template>
                </vl-overlay>
              </vl-map>
            </v-tab-item>

            <v-tab-item>
              <consultants :endpoint="dermatologistEndpoint"></consultants>
            </v-tab-item>
            <v-tab-item>
              <consultants :endpoint="pharmacistEndpoint"></consultants>
            </v-tab-item>
            <v-tab-item>
              <drugs :endpoint="drugEndpoint" :pharmacyId="id"></drugs>
            </v-tab-item>
            <v-tab-item>
              <exams :endpoint="predefinedEndpoint"></exams>
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
import drugs from "./DrugComponent";
import exams from "./ConsultationComponent";

export default {
  components: {
    consultants,
    drugs,
    exams
  },
  props: {
    id: {
      required: true
    }
  },
  data: () => ({
    marker: null,
    zoom: 15,
    center: [19.833549, 45.267136],
    rotation: 0,
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
          this.marker = [this.pharmacy.longitude, this.pharmacy.latitude];
          this.center = this.marker;
          this.zoom = 15;
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