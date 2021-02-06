<template>
  <v-container>
    <v-row>
      <!-- Left column -->
      <v-col xl="4" sm="12" md="4">
        <v-card>
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2">
              <h3>Loyalty Program</h3>
            </v-toolbar-title>
          </v-toolbar>
          <!-- End of toolbar of the card -->
          <v-card-actions>
            <v-row justify="center">
              <v-col xl="8" md="8" sm="8">
                <br />
                <h4 v-if="loyaltyProgram.active">Status: Activeted program</h4>
                <h4 v-else>Status: Not activate program</h4>
                <br />
                <h4>
                  Active until:
                  {{
                    new Date(loyaltyProgram.activeUntil).toLocaleDateString(
                      "en-UE"
                    )
                  }}
                </h4>
                <br />
                <h4>
                  Points per consultation:
                  {{ loyaltyProgram.pointsPerConsultation }}
                </h4>
                <br /><br />
                <!-- Button for eddit loyalty program + DIALOG -->
                <v-row>
                  <v-col>
                    <v-dialog v-model="dialog" width="500">
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                          class="mx-2"
                          fab
                          dark
                          small
                          color="primary"
                          v-bind="attrs"
                          v-on="on"
                          @click="setChangeLoyaltyProgram"
                        >
                          <v-icon dark> mdi-pencil-outline </v-icon>
                        </v-btn>
                      </template>

                      <!-- Content inside dialog -->
                      <v-card>
                        <!--Toolbar of the card-->
                        <v-toolbar color="primary" dark dense flat>
                          <v-toolbar-title class="body-2">
                            <h3>Loyalty Program</h3>
                          </v-toolbar-title>
                        </v-toolbar>
                        <!-- End of toolbar of the card -->
                        <br />
                        <v-form class="ma-5">
                          <v-text-field
                            type="number"
                            v-model="changeLoyaltyProgram.pointsPerConsultation"
                            label="Points per consultation"
                          >
                          </v-text-field>
                          <!-- Data picker in dialog -->

                          <v-row justify="center">
                            <v-date-picker
                            required
                              elevation="4"
                              v-model="changeLoyaltyProgram.activeUntil"
                            ></v-date-picker>
                          </v-row>
                          <!-- End of the date picker in dialog -->
                          <v-switch
                            v-model="changeLoyaltyProgram.active"
                            label="Status of program  [Active or Not active]"
                          ></v-switch>
                        </v-form>
                        <v-divider></v-divider>

                        <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn
                            color="primary"
                            @click="confirmChangesToLoyaltyProgram()"
                          >
                            Confirm changes
                          </v-btn>
                        </v-card-actions>
                      </v-card>
                      <!-- End of the content inside dialog -->
                    </v-dialog>
                  </v-col>
                </v-row>
                <!-- End of button for edit loyalty program + DIALOG-->
                <br />
              </v-col>
            </v-row>
          </v-card-actions>
        </v-card>
      </v-col>
      <!-- End of the left column -->

      <!-- Right column -->
      <v-col xl="8" sm="12" md="8">
        <v-card
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
    selectedData: "",
    changeLoyaltyProgram: {
      active: false,
      activeUntil: "",
      pointsPerConsultation: 0,
    },
    loyaltyProgram: {},
    dialog: false,
  }),
  methods: {
    setChangeLoyaltyProgram() {
      this.changeLoyaltyProgram.active = this.loyaltyProgram.active;
      // TODO: Find how to map date to something
      // this.changeLoyaltyProgram.activeUntil =  new Date(this.loyaltyProgram.activeUntil).toLocaleDateString("en-UE");
      this.changeLoyaltyProgram.pointsPerConsultation = this.loyaltyProgram.pointsPerConsultation;
    },
    confirmChangesToLoyaltyProgram() {
      // TODO: Make ajax call to make these changes
      if(this.changeLoyaltyProgram.activeUntil == "") return;
      this.dialog = false;
      this.loyaltyProgram.active = this.changeLoyaltyProgram.active;
      this.loyaltyProgram.activeUntil = this.changeLoyaltyProgram.activeUntil;
      this.loyaltyProgram.pointsPerConsultation = this.changeLoyaltyProgram.pointsPerConsultation;

      this.axios
        .put(
          process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_LOYALTY_PROGRAM,
          {
            activeUntil: this.loyaltyProgram.activeUntil,
            pointsPerConsultation: this.loyaltyProgram.pointsPerConsultation,
            categories: null,
            active: this.loyaltyProgram.active,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          // TODO: Make some notification here
          alert("Successfuly");
        })
        .catch((error) => {
          // TODO: Make some tost notifications here
          alert("Error: " + error);
        });
    },
  },
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