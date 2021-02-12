<template>
  <v-row class="pa-5">
    <v-snackbar v-model="snackbar" :timeout="2000" bottom class="mb-5" right>
      {{ snackbarText }}
      <template v-slot:action="{ attrs }">
        <v-btn color="primary" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
      </template>
    </v-snackbar>

    <v-spacer></v-spacer>

    <!-- Left column for filter & search -->
    <v-col sm="12" md="4" lg="4" xl="4" cols="12">
      <!-- Search card -->
      <v-card>
        <!--Toolbar of the card-->
        <v-toolbar color="primary" dark dense flat>
          <v-toolbar-title class="body-2">Sort</v-toolbar-title>
        </v-toolbar>
        <!--End of toolbar of the card-->
        <!-- Sort by price -->
        <v-row justify="center" class="pa-3 mt-2">
          <v-btn
            small
            width="110px"
            color="green lighten-2"
            class="ma-2 white--text mr-6"
            @click="sortBy('price')"
          >
            Price
            <v-icon small right dark>mdi-arrow-up-bold</v-icon>
          </v-btn>
          <v-btn
            small
            width="110px"
            color="green lighten-2"
            class="ma-2 white--text ml-6"
            @click="sortDownBy('price')"
          >
            Price
            <v-icon small right dark>mdi-arrow-down-bold</v-icon>
          </v-btn>
        </v-row>
        <!-- End of the sort by price -->
        <!-- Sort by date -->
        <v-row justify="center" class="pa-3">
          <v-btn
            small
            width="110px"
            color="green lighten-2"
            class="ma-2 white--text mr-6"
            @click="sortBy('startDate')"
          >
            Date
            <v-icon small right dark>mdi-arrow-up-bold</v-icon>
          </v-btn>
          <v-btn
            small
            width="110px"
            color="green lighten-2"
            class="ma-2 white--text ml-6"
            @click="sortDownBy('startDate')"
          >
            Date
            <v-icon small right dark>mdi-arrow-down-bold</v-icon>
          </v-btn>
        </v-row>
        <!-- End of the sort by date -->
        <!-- Sort by duration -->
        <v-row justify="center" class="pa-3">
          <v-btn
            small
            width="110px"
            color="green lighten-2"
            class="ma-2 white--text mr-6"
            @click="sortBy('consultationDuration')"
          >
            Duration
            <v-icon small right dark>mdi-arrow-up-bold</v-icon>
          </v-btn>
          <v-btn
            small
            width="110px"
            color="green lighten-2"
            class="ma-2 white--text ml-6"
            @click="sortDownBy('consultationDuration')"
          >
            Duration
            <v-icon small right dark>mdi-arrow-down-bold</v-icon>
          </v-btn>
        </v-row>
        <!-- End of the sort by duration -->
      </v-card>
      <!-- End of the search card-->
    </v-col>
    <!-- End of the left column for filter & search -->

    <v-col sm="12" md="8" lg="8" xl="8" cols="12">
      <v-card
        elevation="4"
        class="pa-4 mb-10"
        v-for="consultation in consultations"
        :key="consultation.id"
      >
        <v-card-title>
          <h2>{{consultation.consultantName + " " +consultation.consultantSurname}}</h2>
          <v-spacer></v-spacer>
          <v-chip color="success" class="ml-0">{{ consultation.price }}€</v-chip>
        </v-card-title>

        <v-divider class="ml-5 mr-5"></v-divider>
        <v-card-text class="ml-2">
          <span class="mr-2">From</span>
          <v-chip color="primary">{{ consultation.startDate }}</v-chip>
          <span class="ml-2 mr-2">to</span>
          <v-chip color="primary">{{ consultation.endDate }}</v-chip>
        </v-card-text>

        <v-card-actions>
          <v-btn
            color="success"
            @click="schedule(consultation)"
            :disabled="performingAction"
          >Schedule</v-btn>
        </v-card-actions>
      </v-card>
    </v-col>

    <v-spacer></v-spacer>
  </v-row>
</template>

<script>
export default {
  props: {
    endpoint: {
      type: String,
      required: true
    }
  },
  data: () => ({
    consultations: [],
    snackbarText: "",
    snackbar: false,
    addingDrug: false,
    performingAction: false,
    showMakeReservation: false
  }),
  watch: {
    endpoint: function() {
      this.load();
    }
  },
  mounted() {
    this.load();
  },
  methods: {
    load: function() {
      this.axios
        .get(this.endpoint, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
          }
        })
        .then(resp => {
          this.consultations = [];
          for (let consultation of resp.data) {
            let tempObj = {
              id: consultation.id,
              consultantEmail: consultation.consultantEmail,
              consultantId: consultation.consultantId,
              consultantName: consultation.consultantName,
              consultantSurname: consultation.consultantSurname,
              endDate: new Date(consultation.endDate).toLocaleString(),
              startDate: new Date(consultation.startDate).toLocaleString(),
              consultationDuration:
                consultation.endDate - consultation.startDate,
              pharmacyID: consultation.pharmacyID,
              pharmacyName: consultation.pharmacyName,
              price: consultation.price
            };
            this.consultations.push(tempObj);
          }
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
    sortBy(prop) {
      this.consultations.sort((a, b) => (a[prop] < b[prop] ? -1 : 1));
    },
    sortDownBy(prop) {
      this.consultations.sort((a, b) => (a[prop] > b[prop] ? -1 : 1));
    },
    schedule: function(consultation) {
      this.performingAction=true;
      this.axios
        .get(process.env.VUE_APP_BACKEND_URL+ process.env.VUE_APP_PATIENT_RESERVE_CONSULATION_ENDPOINT +
            "/" +
            consultation.id, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
          }
        })
        .then(() => {
          this.load();
          this.performingAction=false;
          this.snackbarText="Successfully scheduled.";
          this.snackbar=true;
          
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
  }
};
</script>