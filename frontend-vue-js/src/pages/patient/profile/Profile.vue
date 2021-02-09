<template >
  <v-container v-if="patient">
    <!-- Card for the base info about patient -->
    <v-card width="600px" class="mx-auto">
      <v-card-title>{{ patient.name }} {{ patient.surname }} </v-card-title>
      <v-row v-if="patient.userCategoryDTO">
        <v-col xl="8" md="8" sm="12">
          <v-card-text>
            <!--Username-->
            <v-icon color="primary">mdi-account</v-icon>
            {{ patient.email }}
            <br /><br />

            <!--Adress-->
            <v-icon color="primary"> mdi-map-marker </v-icon>
            {{ patient.address }}
            <br /><br />

            <!--City-->
            <v-icon color="primary">mdi-city</v-icon>
            {{ patient.city }}
            <br /><br />

            <!--Country-->
            <v-icon color="primary">mdi-map</v-icon>
            {{ patient.country }}
            <br /><br />

            <!--Phone nubmer-->
            <v-icon color="primary"> mdi-phone-classic </v-icon>
            {{ patient.phoneNumber }}

            <br /><br />
            <v-divider></v-divider>
            <br />

            <!--Points-->
            <v-icon color="primary">mdi-star</v-icon>
            {{ patient.loyaltyPoints }} <i> points</i> <br /><br />

            <!--Category-->
            <v-chip color="primary">{{ patient.userCategoryDTO.name }} </v-chip>
            <br /><br />

            <!--Benefits-->

            <v-chip color="primary"
              >Reservation discount:{{
                patient.userCategoryDTO.reservationDiscount
              }}
              %</v-chip
            >
            <br /><br />

            <v-chip color="primary"
              >Consultation discount:{{
                patient.userCategoryDTO.consultationDiscount
              }}
              %</v-chip
            >
            <b> </b>
          </v-card-text>
        </v-col>

        <!--Dialog form-->
        <v-col xl="4" md="4" sm="12">
          <v-dialog width="500">
            <template v-slot:activator="{ on, attrs }">
              <v-btn fab dark large color="primary" v-bind="attrs" v-on="on">
                <v-icon dark> mdi-pencil </v-icon>
              </v-btn>
            </template>

            <v-card>
              <v-toolbar color="primary" dark dense flat>
                <v-toolbar-title class="body-2">
                  Change your personal information
                </v-toolbar-title>
              </v-toolbar>
              <v-card-text>
                <v-form>
                  <v-text-field
                    label="Change your name "
                    v-model="patientFormDTO.name"
                  ></v-text-field>
                  <v-text-field
                    label="Change your surname "
                    v-model="patientFormDTO.surname"
                  ></v-text-field>
                  <v-text-field
                    label="Change your address"
                    v-model="patientFormDTO.address"
                  ></v-text-field>
                  <v-text-field
                    label="Change your city"
                    v-model="patientFormDTO.city"
                  ></v-text-field>
                  <v-text-field
                    label="Change your country"
                    v-model="patientFormDTO.country"
                  ></v-text-field>
                  <v-text-field
                    label="Change your phone number"
                    v-model="patientFormDTO.phoneNumber"
                  ></v-text-field>
                  <v-text-field
                    label="Add drugs which cause you allergic reactions"
                    v-model="patientFormDTO.allergies"
                  ></v-text-field>
                </v-form>
              </v-card-text>

              <v-divider></v-divider>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" text @click="dialog = false">
                  Save changes
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-col>
      </v-row>
    </v-card>
    <!-- End of the card for the base info about patient -->
    <!-- Card for the complaints -->
    <v-card width="600px" class="mx-auto mb-14">
      <v-row justify="end">
        <v-card-subtitle class="ml-5 mr-5">
          Dear {{ patient.name }}, if you have some complain on some of the
          pharmacist/dermatologist or even a pharmacy, fell free to inform us.
        </v-card-subtitle>

        <v-card-actions class="mr-2 mb-2">
          <v-btn color="orange lighten-2" text> Make complain </v-btn>

          <v-spacer></v-spacer>

          <v-btn
            class="mr-5"
            icon
            @click="showComplaintPart = !showComplaintPart"
          >
            <v-icon>{{
              showComplaintPart ? "mdi-chevron-up" : "mdi-chevron-down"
            }}</v-icon>
          </v-btn>
        </v-card-actions>

        <v-expand-transition>
          <div class="pa-3" v-show="showComplaintPart">
            <v-divider></v-divider>

            <v-card-text class="ml-2">
              Dear {{ patient.name }}, we are so sorry to hear that you have
              somme issue. Because of that, there is area where you can enter
              what's bothering you. Besides that, please inform us on who you
              want complain, pharmacies, pharmacist or dermatologist, and which
              one.
            </v-card-text>

            <!-- Form for making a response -->
            <v-form class="ma-5">
              <v-textarea
                full-width
                outlined
                v-model="complaintDTO.content"
                label="Describe your problem"
              ></v-textarea>

              <v-select
                :items="possiblePharmaciesForComplaint"
                item-value="id"
                item-text="name"
                v-model="complaintDTO.pharmacyId"
                outlined
                label="Select pharmacy"
              >
                <template slot="selection" slot-scope="data">
                  <!-- HTML that describe how select should render selected items -->
                  {{ data.item.name }}
                </template>
                <template slot="item" slot-scope="data">
                  <!-- HTML that describe how select should render items when the select is open -->
                  {{ data.item.name }} , {{ data.item.location }}
                </template>
              </v-select>

              <v-select
                :items="possibleConsultantsForComplaint"
                item-value="email"
                item-text="email"
                v-model="complaintDTO.consultantEmail"
                outlined
                label="Select dermatologist/pharmacist"
              >
                <template slot="selection" slot-scope="data">
                  <!-- HTML that describe how select should render selected items -->
                  {{ data.item.name }} {{ data.item.lastName }}
                </template>
                <template slot="item" slot-scope="data">
                  <!-- HTML that describe how select should render items when the select is open -->
                  {{ data.item.name }} {{ data.item.lastName }} ,
                  {{ data.item.email }}
                </template>
              </v-select>
            </v-form>
            <!-- End of the form for making a response -->
            <!-- Buttons to confirm/cancel -->
            <v-card-actions class="pb-4 mr-4">
              <v-spacer> </v-spacer>
              <v-btn color="success" dark depressed @click="confirmComplaint()">
                <v-icon dark left> mdi-checkbox-marked-circle </v-icon>
                Confirm
              </v-btn>
              <v-btn
                color="red lighten-3"
                dark
                depressed
                @click="
                  (showComplaintPart = !showComplaintPart), resetComplaintDTO()
                "
                ><v-icon dark left> mdi-minus-circle </v-icon>
                Close
              </v-btn>
            </v-card-actions>
            <!-- Buttons to confirm/cancel-->
          </div>
        </v-expand-transition>
      </v-row>
    </v-card>
    <!-- End of the card for the complaints -->
  </v-container>
</template>
<script>
import { getParsedToken } from "./../../../util/token";
export default {
  data: () => ({
    selectedAccussed: {},
    possibleConsultantsForComplaint: [],
    possiblePharmaciesForComplaint: [],
    complaintDTO: {
      content: "",
      consultantEmail: null,
      patientEmail: getParsedToken().sub,
      pharmacyId: null,
    },
    showComplaintPart: false,
    patient: {},
    patientFormDTO: {
      name: "",
      surname: "",
      address: "",
      city: "",
      country: "",
      phoneNumber: "",
      allergies: "",
    },
    rules: {
      required: (value) => !!value || "Required.",
      min: (v) => v.length >= 8 || "Min 8 characters",
    },
    patientEmail: getParsedToken().sub,
  }),
  mounted() {
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_PROFILE_ENDPOINT,
        {
          email: getParsedToken().sub,
        },
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        this.patient = resp.data;
        this.patientFormDTO.name = this.patient.name;
        this.patientFormDTO.surname = this.patient.surname;
        this.patientFormDTO.address = this.patient.address;
        this.patientFormDTO.city = this.patient.city;
        this.patientFormDTO.country = this.patient.country;
        this.patientFormDTO.phoneNumber = this.patient.phoneNumber;
      })
      .catch((error) => {
        alert("Error: " + error);
      });

    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_CONSULTANTS_ENDPOINT,
        {
          email: getParsedToken().sub,
        },
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        this.possibleConsultantsForComplaint = resp.data;
      })
      .catch((error) => {
        alert("Error: " + error);
      });

    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_PATIENT_PHARMACIES_ENDPOINT,
        {
          email: getParsedToken().sub,
        },
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        this.possiblePharmaciesForComplaint = resp.data;
      })
      .catch((error) => {
        alert("Error: " + error);
      });
  },
  methods: {
    resetComplaintDTO() {
      this.complaintDTO.pharmacyId = null;
      this.complaintDTO.consultantEmail = null;
    },
    confirmComplaint() {
      if (
        this.complaintDTO.pharmacyId != null &&
        this.complaintDTO.consultantEmail != null
      ) {
        alert(
          "Please select pharmacy or dermatologist/pharmacist, you cant make a two complaint at same time"
        );
        this.resetComplaintDTO();
      }

      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_COMPLAINTS_ENDPOINT,
          this.complaintDTO,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          alert("Success created a complaint");
          this.resetComplaintDTO();
        })
        .catch((error) => {
          alert("Error: " + error);
        });
    },
  },
};
</script>