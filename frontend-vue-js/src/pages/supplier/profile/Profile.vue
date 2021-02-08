<template >
  <v-card width="600px" class="mx-auto ma-5">
    <v-card-title class="ml-8"
      >{{ supplier.name }} {{ supplier.surname }}
    </v-card-title>
    <v-row class="ma-5" align="end">
      <!-- Base information -->
      <v-col xl="8" md="8" sm="12">
        <v-card-text>
          <!--Username-->
          <v-icon class="mr-3" color="primary">mdi-account</v-icon>
          {{ supplierEmail }}
          <br /><br />

          <!--Adress-->
          <v-icon class="mr-3" color="primary"> mdi-map-marker </v-icon>
          {{ supplier.address }}
          <br /><br />

          <!--City-->
          <v-icon class="mr-3" color="primary">mdi-city</v-icon>
          {{ supplier.city }}
          <br /><br />

          <!--Country-->
          <v-icon class="mr-3" color="primary">mdi-map</v-icon>
          {{ supplier.country }}
          <br /><br />

          <!--Phone nubmer-->
          <v-icon class="mr-3" color="primary"> mdi-phone-classic </v-icon>
          {{ supplier.phone }}
        </v-card-text>
      </v-col>
      <!-- End of the base information -->

      <!--Dialog form-->
      <v-col xl="4" md="4" sm="12">
        <v-dialog v-model="showEditDialog" width="500">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              class="ma-5"
              fab
              dark
              large
              color="primary"
              v-bind="attrs"
              v-on="on"
            >
              <v-icon dark> mdi-pencil </v-icon>
            </v-btn>
          </template>
          <!-- Dialog form -->
          <v-card>
            <v-toolbar color="primary" dark dense flat>
              <v-toolbar-title class="body-2">
                Change your personal information
              </v-toolbar-title>
            </v-toolbar>
            <!-- Content -->
            <v-card-text>
              <br><br>
              <v-form>
                <v-text-field
                  label="Change your name "
                  v-model="supplierFormDTO.name"
                ></v-text-field>
                <v-text-field
                  label="Change your surname "
                  v-model="supplierFormDTO.surname"
                ></v-text-field>
                <v-text-field
                  label="Change your address"
                  v-model="supplierFormDTO.address"
                ></v-text-field>
                <v-text-field
                  label="Change your city"
                  v-model="supplierFormDTO.city"
                ></v-text-field>
                <v-text-field
                  label="Change your country"
                  v-model="supplierFormDTO.country"
                ></v-text-field>
                <v-text-field
                type="number"
                  label="Change your phone number"
                  v-model="supplierFormDTO.phone"
                ></v-text-field>
              </v-form>
            </v-card-text>
            <!-- End of the content -->

            <v-card-actions>
               <v-spacer></v-spacer>
                  <v-btn
                    color="success"
                    dark
                    depressed
                    @click="confirmChanges()"
                  >
                    <v-icon dark left> mdi-checkbox-marked-circle </v-icon>
                    Confirm
                  </v-btn>
                  <v-btn
                    color="red lighten-3"
                    dark
                    depressed
                    @click="showEditDialog = !showEditDialog"
                    ><v-icon dark left> mdi-minus-circle </v-icon>
                    Close
                  </v-btn>
            </v-card-actions>
          </v-card>
          <!-- End of the dialog form-->
        </v-dialog>
      </v-col>
    </v-row>
  </v-card>
</template>
<script>
import { getParsedToken } from "./../../../util/token";
export default {
  data: () => ({
    showEditDialog: false,
    supplier: {},
    patient: {},
    supplierFormDTO: {
      name: "",
      surname: "",
      address: "",
      city: "",
      country: "",
      phone: "",
    },
    rules: {
      required: (value) => !!value || "Required.",
      min: (v) => v.length >= 8 || "Min 8 characters",
    },
    supplierEmail: getParsedToken().sub,
  }),
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_PROFILE_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        // alert("success");

        this.supplier = resp.data;
        this.supplierFormDTO.name = this.supplier.name;
        this.supplierFormDTO.surname = this.supplier.surname;
        this.supplierFormDTO.address = this.supplier.address;
        this.supplierFormDTO.city = this.supplier.city;
        this.supplierFormDTO.country = this.supplier.country;
        this.supplierFormDTO.phone = this.supplier.phone;
      });
  },
};
</script>