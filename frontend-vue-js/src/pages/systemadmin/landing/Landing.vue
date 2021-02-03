<template>
  <v-container>
    <v-expansion-panels>
      <!-- Register a pharmacy & pharmacy administrator panel -->
      <v-expansion-panel>
        <v-expansion-panel-header>
          Register a pharmacy & pharmacy administrator
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <!-- Pharmacy registration -->
          <v-stepper style="margin: 3%" v-model="stepper.pharmacyRegStep" vertical>
            <!-- Register a pharmacy -->
            <v-stepper-step :complete="stepper.pharmacyRegStep > 1" step="1">
              Register a pharmacy
            </v-stepper-step>
            <v-stepper-content step="1">
              <!-- Content -->
              <v-card
                style="padding: 2%"
                color="grey lighten-5"
                class="mb-12"
                height="400px"
              >
                <v-text-field label="Name of the pharmacy"></v-text-field>
                <v-text-field label="Location of the pharmacy"></v-text-field>
                <v-text-field label="Description about pharmacy"></v-text-field>
                <v-text-field
                  label="Dermatologist price per consultation"
                ></v-text-field>
                <v-text-field
                  label="Pharmacist price per consultation"
                ></v-text-field>
              </v-card>
              <!-- End of the content -->

              <!-- Buttons -->
              <v-btn color="primary" @click="stepper.pharmacyRegStep = 2">
                Continue
              </v-btn>
              <v-btn text> Cancel </v-btn>
              <!-- End of the buttons -->
            </v-stepper-content>
            <!-- End of the register a pharmacy -->

            <!-- Register a pharmacy administrator -->
            <v-stepper-step :complete="stepper.pharmacyRegStep > 2" step="2">
              Register a pharmacy administrator
            </v-stepper-step>
            <v-stepper-content step="2">
              <!-- Form, or like like one -->
              <v-card
                style="padding: 2%"
                color="grey lighten-5"
                class="mb-12"
                height="300px"
              >
                <v-row>
                  <v-col>
                    <v-text-field label="Name"></v-text-field>
                    <v-text-field label="Surname"></v-text-field>
                    <v-text-field label="Phone number"></v-text-field>
                    <v-text-field label="Address"></v-text-field>
                  </v-col>

                  <v-col>
                    <v-text-field label="City"></v-text-field>
                    <v-text-field label="Country"></v-text-field>
                    <v-text-field label="Email"></v-text-field>
                    <v-text-field label="Password"></v-text-field>
                  </v-col>
                </v-row>
              </v-card>
              <!-- End of the form -->
              <v-btn color="primary" @click="stepper.pharmacyRegStep = 3">
                Continue
              </v-btn>
              <v-btn text @click="stepper.pharmacyRegStep = 1"> Back </v-btn>
            </v-stepper-content>
            <!-- End of the register a pharmacy administrator -->

            <!-- Confirmation of the pharmacy registration -->
            <v-stepper-step step="3">
              Confirm pharmacy registration
            </v-stepper-step>
            <v-stepper-content step="3">
              <p>
                Are you sure you want add pharmacy with this pharmacy
                administrator ?
              </p>
              <v-btn color="primary" @click="stepper.pharmacyRegStep = 1">
                Confirm
              </v-btn>
              <v-btn text @click="stepper.pharmacyRegStep = 2"> Back </v-btn>
            </v-stepper-content>
            <!-- End of the confirmation of the pharmacy registration -->
          </v-stepper>
          <!-- End of the pharmacy registration -->
        </v-expansion-panel-content>
      </v-expansion-panel>
      <!-- End of register a pharmacy & pharmacy administrator panel -->

      <!-- Register a dermatologist panel -->
      <v-expansion-panel>
        <v-expansion-panel-header>
          Register a dermatologist
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <!-- Register a dermatologist stepper -->
          <v-stepper style="margin: 3%" v-model="stepper.dermatologistRegStep" vertical>
            <!-- Register a dermatologist -->
            <v-stepper-step :complete="stepper.dermatologistRegStep > 1" step="1">
              Register a dermatologist
            </v-stepper-step>
            <v-stepper-content step="1">
              <!-- Form for dermatologist, or like like one -->
              <v-card
                style="padding: 2%"
                color="grey lighten-5"
                class="mb-12"
                height="300px"
              >
                <v-row>
                  <v-col>
                    <v-text-field label="Name"></v-text-field>
                    <v-text-field label="Surname"></v-text-field>
                    <v-text-field label="Phone number"></v-text-field>
                    <v-text-field label="Address"></v-text-field>
                  </v-col>

                  <v-col>
                    <v-text-field label="City"></v-text-field>
                    <v-text-field label="Country"></v-text-field>
                    <v-text-field label="Email"></v-text-field>
                    <v-text-field label="Password"></v-text-field>
                  </v-col>
                </v-row>
              </v-card>
              <!-- End of the form for dermatologist -->

              <!-- Buttons -->
              <v-btn color="primary" @click="stepper.dermatologistRegStep = 2">
                Continue
              </v-btn>
              <v-btn text> Cancel </v-btn>
              <!-- End of the buttons -->
            </v-stepper-content>
            <!-- End of the register a dermatologist -->

            <!-- Confirmation of the dermatologist registration -->
            <v-stepper-step step="2">
              Confirm dermatologist registration
            </v-stepper-step>
            <v-stepper-content step="2">
              <p>
                Are you sure you want add a dermatologist with this informations
                ?
              </p>
              <v-btn color="primary" @click="stepper.dermatologistRegStep = 1">
                Confirm
              </v-btn>
              <v-btn text @click="stepper.dermatologistRegStep = 1"> Back </v-btn>
            </v-stepper-content>
            <!-- End of the confirmation of the dermatologist registration -->
          </v-stepper>
          <!-- End of the register a dermatologist stepper -->
        </v-expansion-panel-content>
      </v-expansion-panel>
      <!-- End of register a dermatologist panel -->

      <!-- Register a supplier panel -->
      <v-expansion-panel>
        <v-expansion-panel-header>
          Register a supplier
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <!-- Register a supplier stepper -->
          <v-stepper style="margin: 3%" v-model="stepper.supplierRegStep" vertical>
            <!-- Register a supplier -->
            <v-stepper-step :complete="stepper.supplierRegStep > 1" step="1">
              Register a supplier
            </v-stepper-step>
            <v-stepper-content step="1">
              <!-- Form for supplier, or like like one -->
              <v-card
                style="padding: 2%"
                color="grey lighten-5"
                class="mb-12"
                height="300px"
              >
                <v-row>
                  <v-col>
                    <v-text-field label="Name"></v-text-field>
                    <v-text-field label="Surname"></v-text-field>
                    <v-text-field label="Phone number"></v-text-field>
                    <v-text-field label="Address"></v-text-field>
                  </v-col>

                  <v-col>
                    <v-text-field label="City"></v-text-field>
                    <v-text-field label="Country"></v-text-field>
                    <v-text-field label="Email"></v-text-field>
                    <v-text-field label="Password"></v-text-field>
                  </v-col>
                </v-row>
              </v-card>
              <!-- End of the form for supplier -->

              <!-- Buttons -->
              <v-btn color="primary" @click="stepper.supplierRegStep = 2">
                Continue
              </v-btn>
              <v-btn text> Cancel </v-btn>
              <!-- End of the buttons -->
            </v-stepper-content>
            <!-- End of the register a supplier -->

            <!-- Confirmation of the supplier registration -->
            <v-stepper-step step="2">
              Confirm supplier registration
            </v-stepper-step>
            <v-stepper-content step="2">
              <p>
                Are you sure you want add a supplier with this informations ?
              </p>
              <v-btn color="primary" @click="stepper.supplierRegStep = 1">
                Confirm
              </v-btn>
              <v-btn text @click="stepper.supplierRegStep = 1"> Back </v-btn>
            </v-stepper-content>
            <!-- End of the confirmation of the supplier registration -->
          </v-stepper>
          <!-- End of the register a system administrator stepper -->
        </v-expansion-panel-content>
      </v-expansion-panel>
      <!-- End of register a supplier panel -->

      <!-- Register a system administrator panel  -->
      <v-expansion-panel>
        <v-expansion-panel-header>
          Register a system administrator
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <!-- Register a system administrator stepper -->
          <v-stepper
            style="margin: 3%"
            v-model="stepper.systemAdministratorRegStep"
            vertical
          >
            <!-- Register a system administrator -->
            <v-stepper-step :complete="stepper.systemAdministratorRegStep > 1" step="1">
              Register a system administrator
            </v-stepper-step>
            <v-stepper-content step="1">
              <!-- Form for system administrator, or like like one -->
              <v-card
                style="padding: 2%"
                color="grey lighten-5"
                class="mb-12"
                height="300px"
              >
                <v-row>
                  <v-col>
                    <v-text-field label="Name"></v-text-field>
                    <v-text-field label="Surname"></v-text-field>
                    <v-text-field label="Phone number"></v-text-field>
                    <v-text-field label="Address"></v-text-field>
                  </v-col>

                  <v-col>
                    <v-text-field label="City"></v-text-field>
                    <v-text-field label="Country"></v-text-field>
                    <v-text-field label="Email"></v-text-field>
                    <v-text-field label="Password"></v-text-field>
                  </v-col>
                </v-row>
              </v-card>
              <!-- End of the form for system administrator -->

              <!-- Buttons -->
              <v-btn color="primary" @click="stepper.systemAdministratorRegStep = 2">
                Continue
              </v-btn>
              <v-btn text> Cancel </v-btn>
              <!-- End of the buttons -->
            </v-stepper-content>
            <!-- End of the register a system administrator -->

            <!-- Confirmation of the system administrator registration -->
            <v-stepper-step step="2">
              Confirm system administrator registration
            </v-stepper-step>
            <v-stepper-content step="2">
              <p>
                Are you sure you want add a system administrator with this
                informations ?
              </p>
              <v-btn color="primary" @click="stepper.systemAdministratorRegStep = 1">
                Confirm
              </v-btn>
              <v-btn text @click="stepper.systemAdministratorRegStep = 1"> Back </v-btn>
            </v-stepper-content>
            <!-- End of the confirmation of the system administrator registration -->
          </v-stepper>
          <!-- End of the register a system administrator stepper -->
        </v-expansion-panel-content>
      </v-expansion-panel>
      <!-- End of loyalty program define -->

      <!-- Register a drug panel -->
      <v-expansion-panel>
        <v-expansion-panel-header> Register a drug </v-expansion-panel-header>
        <v-expansion-panel-content>
          <!-- Drug registration -->
          <v-stepper style="margin: 3%" v-model="stepper.drugRegStep" vertical>
            <!-- Register a drug -->
            <v-stepper-step :complete="stepper.drugRegStep > 1" step="1">
              Register a drug
            </v-stepper-step>
            <v-stepper-content step="1">
              <!-- Content -->
              <v-card
                style="padding: 2%"
                color="grey lighten-5"
                class="mb-12"
                height="300px"
              >
                <v-text-field label="Name of the drug"></v-text-field>
                <v-text-field label="Code of the drug"></v-text-field>
                <v-text-field label="Loyalty points"></v-text-field>
                <v-select
                  :items="typesOfDrug"
                  label="Type of the drug"
                  outlined
                ></v-select>
              </v-card>
              <!-- End of the content -->

              <!-- Buttons -->
              <v-btn color="primary" @click="stepper.drugRegStep = 2"> Continue </v-btn>
              <v-btn text> Cancel </v-btn>
              <!-- End of the buttons -->
            </v-stepper-content>
            <!-- End of the register a drug -->

            <!-- Register a drug specification -->
            <v-stepper-step :complete="stepper.drugRegStep > 2" step="2">
              Register a drug specification
            </v-stepper-step>
            <v-stepper-content step="2">
              <!-- Form, or like like one -->
              <v-card
                style="padding: 2%"
                color="grey lighten-5"
                class="mb-12"
                height="300px"
              >
                <v-row>
                  <v-col>
                    <v-text-field label="Contraindications"></v-text-field>
                    <v-text-field label="Drug composition"></v-text-field>
                    <v-text-field label="Recommended daily dose"></v-text-field>
                    <v-text-field label="Alternate drugs"></v-text-field>
                  </v-col>
                </v-row>
              </v-card>
              <!-- End of the form -->
              <v-btn color="primary" @click="stepper.drugRegStep = 3"> Continue </v-btn>
              <v-btn text @click="stepper.drugRegStep = 1"> Back </v-btn>
            </v-stepper-content>
            <!-- End of the register a drug specification -->

            <!-- Confirmation of the drug registration -->
            <v-stepper-step step="3">
              Confirm drug registration
            </v-stepper-step>
            <v-stepper-content step="3">
              <p>
                Are you sure you want add drug with this information and this
                specification ?
              </p>
              <v-btn color="primary" @click="stepper.drugRegStep = 1"> Confirm </v-btn>
              <v-btn text @click="stepper.drugRegStep = 2"> Back </v-btn>
            </v-stepper-content>
            <!-- End of the confirmation of the drug registration -->
          </v-stepper>
          <!-- End of the drug registration -->
        </v-expansion-panel-content>
      </v-expansion-panel>
      <!-- End of register a drug panel -->

      <!-- Define a loyalty program panel -->
      <v-expansion-panel>
        <v-expansion-panel-header>
          Define a loyalty program
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <!-- Define a loyalty program -->
          <v-stepper style="margin: 3%" v-model="stepper.loyaltyRegStep" vertical>
            <!-- Register a loyalty program -->
            <v-stepper-step :complete="stepper.loyaltyRegStep > 1" step="1">
              Define a loyalty program
            </v-stepper-step>
            <v-stepper-content step="1">
              <!-- Content -->
              <v-card
                style="padding: 2%"
                color="grey lighten-5"
                class="mb-12"
                height="100px"
              >
                <v-text-field label="Points per consultation"></v-text-field>
              </v-card>
              <!-- End of the content -->

              <!-- Buttons -->
              <v-btn color="primary" @click="stepper.loyaltyRegStep = 2">
                Continue
              </v-btn>
              <v-btn text> Cancel </v-btn>
              <!-- End of the buttons -->
            </v-stepper-content>
            <!-- End of loyalty program definition -->

            <!-- Scale of the loyalty program  -->
            <v-stepper-step :complete="stepper.loyaltyRegStep > 2" step="2">
              Loyalty program scale
            </v-stepper-step>
            <v-stepper-content step="2">
              <!-- Form, or like like one -->
              <v-card
                style="padding: 2%"
                color="grey lighten-5"
                class="mb-12"
                height="150px"
              >
                <v-row>
                  <v-col>
                    <v-text-field label="From range"></v-text-field>
                    <v-text-field label="To range"></v-text-field>
                  </v-col>
                </v-row>
              </v-card>
              <!-- End of the form -->
              <v-btn color="primary" @click="stepper.loyaltyRegStep = 3">
                Continue
              </v-btn>
              <v-btn text @click="stepper.loyaltyRegStep = 1"> Back </v-btn>
            </v-stepper-content>
            <!-- End of the scale of loyalty program definition -->

            <!-- Confirmation of the loyalty program -->
            <v-stepper-step step="3">
              Confirm definition of loyalty program
            </v-stepper-step>
            <v-stepper-content step="3">
              <p>
                Are you sure you want to define new loyalty program with this
                informations ?
              </p>
              <v-btn color="primary" @click="stepper.loyaltyRegStep = 1">
                Confirm
              </v-btn>
              <v-btn text @click="stepper.loyaltyRegStep = 2"> Back </v-btn>
            </v-stepper-content>
            <!-- End of the confirmation of the loyalty program -->
          </v-stepper>
          <!-- End of the define a loyalty program -->
        </v-expansion-panel-content>
      </v-expansion-panel>
      <!-- End of loyalty program define -->
    </v-expansion-panels>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    stepper: {
      pharmacyRegStep: 1,
      systemAdministratorRegStep: 1,
      dermatologistRegStep: 1,
      supplierRegStep: 1,
      drugRegStep: 1,
      loyaltyRegStep: 1,
    },
    typesOfDrug: ["Antibiotik", "Anestetik"],
  }),
};
</script>