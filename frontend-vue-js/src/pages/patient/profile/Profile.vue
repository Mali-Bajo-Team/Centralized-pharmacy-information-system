<template >
  <v-card width="600px" class="mx-auto" v-if="patient">
    <v-card-title>{{patient.name }} {{ patient.surname }} </v-card-title>
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
          <v-chip color="primary">  </v-chip>
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
                <v-text-field>
                
                </v-text-field>
                <v-text-field label="Change your surname " v-model="patientFormDTO.surname" ></v-text-field>
                <v-text-field label="Change your address"></v-text-field>
                <v-text-field label="Change your phone number"></v-text-field>
                <v-text-field
                  label="Add drugs which cause you allergic reactions"
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
</template>
<script>
import { getParsedToken } from "./../../../util/token";
export default {
  data: () => ({
    patient: {},
    patientFormDTO:{
      name:"",
      surname:""  
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
        this.patientFormDTO.name=this.patient.name;
        this.patientFormDTO.surname=this.patient.surname;
      })
      .catch((error) => {
        alert("Error: " + error);
      });
  },
};
</script>