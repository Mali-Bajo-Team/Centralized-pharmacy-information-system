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

          <!-- Content in card -->
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
                <!-- Buttons for loyalty program + DIALOGS -->
                <v-row>
                  <!-- Edit loyalty program button -->
                  <v-col>
                    <v-dialog
                      v-model="dialogForEditingLoyaltyProgram"
                      width="500"
                    >
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
                          <v-text-field
                            type="date"
                            v-model="changeLoyaltyProgram.activeUntil"
                            label="Active until"
                          >
                          </v-text-field>
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
                  <!-- End of edit loyalty program button -->

                  <!-- Add new category button -->
                  <v-col>
                    <v-dialog v-model="dialogForAddingCategory" width="500">
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
                          <v-icon dark> mdi-account-plus-outline </v-icon>
                        </v-btn>
                      </template>

                      <!-- Content inside dialog -->
                      <v-card>
                        <!--Toolbar of the card-->
                        <v-toolbar color="primary" dark dense flat>
                          <v-toolbar-title class="body-2">
                            <h3>Add new user category</h3>
                          </v-toolbar-title>
                        </v-toolbar>
                        <!-- End of toolbar of the card -->
                        <br />
                        <v-form class="ma-5">
                          <v-text-field
                            v-model="userCategoryDTO.name"
                            label="Category name"
                          >
                          </v-text-field>
                          <v-text-field
                            type="number"
                            v-model="userCategoryDTO.minimumPoints"
                            label="Minimum points"
                          >
                          </v-text-field>

                          <v-text-field
                            type="number"
                            v-model="userCategoryDTO.reservationDiscount"
                            label="Reservation discount"
                          >
                          </v-text-field>

                          <v-text-field
                            type="number"
                            v-model="userCategoryDTO.consultationDiscount"
                            label="Consultation discount"
                          >
                          </v-text-field>
                        </v-form>
                        <v-divider></v-divider>

                        <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn
                            color="primary"
                            @click="confirmAddinguserCategoryDTO()"
                          >
                            Confirm changes
                          </v-btn>
                        </v-card-actions>
                      </v-card>
                      <!-- End of the content inside dialog -->
                    </v-dialog>
                  </v-col>
                  <!-- End of addin new category button -->
                </v-row>
                <!-- End of button for edit loyalty program + DIALOG-->
                <br />
              </v-col>
            </v-row>
          </v-card-actions>
          <!-- End of content in card -->
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
          <!-- Row for title & edit, delete buttons -->
          <v-row align="center">
            <v-card-title>
              <h2>{{ category.name }} category</h2>
            </v-card-title>
            <v-spacer></v-spacer>
            <!-- Buttons for editing & deleting categories -->
            <v-dialog
              v-model="dialogForEditingCategory"
              width="500"
              :retain-focus="false"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  class="mx-2"
                  fab
                  dark
                  elevation="0"
                  x-small
                  color="primary"
                  @click="editUserCategory(category)"
                  v-bind="attrs"
                  v-on="on"
                >
                  <v-icon dark> mdi-pencil-outline </v-icon>
                </v-btn>
              </template>

              <!-- Content inside dialog -->
              <v-card>
                <!--Toolbar of the card-->
                <v-toolbar color="primary" dark dense flat>
                  <v-toolbar-title class="body-2">
                    <h3>Edit user category</h3>
                  </v-toolbar-title>
                </v-toolbar>
                <!-- End of toolbar of the card -->
                <br />
                <v-form class="ma-5">
                  <v-text-field
                    v-model="userCategoryDTO.name"
                    label="Category name"
                    disabled
                  >
                  </v-text-field>
                  <v-text-field
                    type="number"
                    v-model="userCategoryDTO.minimumPoints"
                    label="Minimum points"
                  >
                  </v-text-field>

                  <v-text-field
                    type="number"
                    v-model="userCategoryDTO.reservationDiscount"
                    label="Reservation discount"
                  >
                  </v-text-field>

                  <v-text-field
                    type="number"
                    v-model="userCategoryDTO.consultationDiscount"
                    label="Consultation discount"
                  >
                  </v-text-field>
                </v-form>
                <v-divider></v-divider>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="primary" @click="confirmEditingCategoryDTO()">
                    Confirm changes
                  </v-btn>
                </v-card-actions>
              </v-card>
              <!-- End of the content inside dialog -->
            </v-dialog>

            <v-btn
              class="mx-2 mr-3"
              fab
              dark
              elevation="0"
              x-small
              color="red"
              @click="deleteCategory(category)"
            >
              <v-icon> mdi-minus </v-icon>
            </v-btn>
            <!-- End of buttons for editing & deleting categories -->
          </v-row>
          <!-- End of row for title & edit, delete buttons -->
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
    userCategoryDTO: {
      name: "",
      minimumPoints: 0,
      reservationDiscount: 0.0,
      consultationDiscount: 0.0,
    },
    loyaltyProgram: {},
    dialogForEditingLoyaltyProgram: false,
    dialogForAddingCategory: false,
    dialogForEditingCategory: false,
  }),
  methods: {
    editUserCategory(category) {
      this.userCategoryDTO.name = category.name;
      this.userCategoryDTO.minimumPoints = category.minimumPoints;
      this.userCategoryDTO.reservationDiscount = category.reservationDiscount;
      this.userCategoryDTO.consultationDiscount = category.consultationDiscount;
    },
    deleteCategory(category) {
      this.axios
        .delete(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CATEGORIES_ENDPOINT,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
            data: {
              name: category.name,
              minimumPoints: category.minimumPoints,
              reservationDiscount: category.reservationDiscount,
              consultationDiscount: category.consultationDiscount,
            },
          }
        )
        .then(() => {
          // TODO: Make some notification here
          this.updateLoyaltyProgram();
        })
        .catch((error) => {
          // TODO: Make some tost notifications here
          alert("Error: " + error);
        });
    },
    setChangeLoyaltyProgram() {
      this.changeLoyaltyProgram.active = this.loyaltyProgram.active;
      // TODO: Find how to map date to something
      // this.changeLoyaltyProgram.activeUntil =  new Date(this.loyaltyProgram.activeUntil).toLocaleDateString("en-UE");
      this.changeLoyaltyProgram.pointsPerConsultation = this.loyaltyProgram.pointsPerConsultation;
    },
    confirmChangesToLoyaltyProgram() {
      // TODO: Make ajax call to make these changes
      if (this.changeLoyaltyProgram.activeUntil == "") return;
      this.dialogForEditingLoyaltyProgram = false;
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
    updateLoyaltyProgram() {
      // UPDATE WITH NEW DATA
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
    confirmEditingCategoryDTO() {
      if (
        this.userCategoryDTO.name == "" ||
        this.userCategoryDTO.minimumPoints == 0 ||
        this.userCategoryDTO.reservationDiscount == 0 ||
        this.userCategoryDTO.consultationDiscount == 0
      )
        return;
      this.dialogForEditingCategory = false;
      this.axios
        .put(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CATEGORIES_ENDPOINT,
          {
            name: this.userCategoryDTO.name,
            minimumPoints: this.userCategoryDTO.minimumPoints,
            reservationDiscount: this.userCategoryDTO.reservationDiscount,
            consultationDiscount: this.userCategoryDTO.consultationDiscount,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          // TODO: Make some notification here
          // alert("Successfuly");
          this.updateLoyaltyProgram();
        })
        .catch((error) => {
          // TODO: Make some tost notifications here
          alert("Error: " + error);
        });
    },
    confirmAddinguserCategoryDTO() {
      if (
        this.userCategoryDTO.name == "" ||
        this.userCategoryDTO.minimumPoints == 0 ||
        this.userCategoryDTO.reservationDiscount == 0 ||
        this.userCategoryDTO.consultationDiscount == 0
      )
        return;
      this.dialogForAddingCategory = false;
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CATEGORIES_ENDPOINT,
          {
            name: this.userCategoryDTO.name,
            minimumPoints: this.userCategoryDTO.minimumPoints,
            reservationDiscount: this.userCategoryDTO.reservationDiscount,
            consultationDiscount: this.userCategoryDTO.consultationDiscount,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          // TODO: Make some notification here
          // alert("Successfuly");
          this.updateLoyaltyProgram();
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