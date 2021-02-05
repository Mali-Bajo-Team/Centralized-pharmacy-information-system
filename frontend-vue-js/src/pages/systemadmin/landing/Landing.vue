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
          <v-stepper
            style="margin: 3%"
            v-model="stepper.pharmacyRegStep"
            vertical
          >
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
                height="450px"
              >
                <v-text-field
                  v-model="pharmacyForm.name"
                  :error-messages="pharmacyNameErrors"
                  @blur="$v.pharmacyForm.name.$touch()"
                  @input="$v.pharmacyForm.name.$touch()"
                  label="Name"
                ></v-text-field>
                <v-text-field
                  v-model="pharmacyForm.city"
                  :error-messages="pharmacyCityErrors"
                  @blur="$v.pharmacyForm.city.$touch()"
                  @input="$v.pharmacyForm.city.$touch()"
                  label="City"
                ></v-text-field>
                <v-text-field
                  v-model="pharmacyForm.country"
                  :error-messages="pharmacyCountryErrors"
                  @blur="$v.pharmacyForm.country.$touch()"
                  @input="$v.pharmacyForm.country.$touch()"
                  label="Country"
                ></v-text-field>
                <v-text-field
                  v-model="pharmacyForm.description"
                  :error-messages="pharmacyDescriptionErrors"
                  @blur="$v.pharmacyForm.description.$touch()"
                  @input="$v.pharmacyForm.description.$touch()"
                  label="Description about pharmacy"
                ></v-text-field>
                <v-text-field
                  v-model="pharmacyForm.dermatologistConsultationPrice"
                  :error-messages="dermatologistConsultationPriceErrors"
                  @blur="
                    $v.pharmacyForm.dermatologistConsultationPrice.$touch()
                  "
                  @input="
                    $v.pharmacyForm.dermatologistConsultationPrice.$touch()
                  "
                  label="Dermatologist price per consultation [ € ]"
                ></v-text-field>
                <v-text-field
                  v-model="pharmacyForm.pharmacistConsultationPrice"
                  :error-messages="pharmacistConsultationPriceErrors"
                  @blur="$v.pharmacyForm.pharmacistConsultationPrice.$touch()"
                  @input="$v.pharmacyForm.pharmacistConsultationPrice.$touch()"
                  label="Pharmacist price per consultation [ € ]"
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
                    <v-text-field
                      v-model="pharmacyForm.pharmacyAdministrator.name"
                      :error-messages="nameErrorsPharmacyAdministrator"
                      label="Name"
                      @blur="
                        $v.pharmacyForm.pharmacyAdministrator.name.$touch()
                      "
                      @input="
                        $v.pharmacyForm.pharmacyAdministrator.name.$touch()
                      "
                    ></v-text-field>
                    <v-text-field
                      v-model="pharmacyForm.pharmacyAdministrator.surname"
                      :error-messages="surnameErrorsPharmacyAdministrator"
                      label="Surname"
                      @blur="
                        $v.pharmacyForm.pharmacyAdministrator.surname.$touch()
                      "
                      @input="
                        $v.pharmacyForm.pharmacyAdministrator.surname.$touch()
                      "
                    ></v-text-field>
                    <v-text-field
                      :error-messages="phoneErrorsPharmacyAdministrator"
                      label="Phone number"
                      @blur="
                        $v.pharmacyForm.pharmacyAdministrator.phone.$touch()
                      "
                      @input="
                        $v.pharmacyForm.pharmacyAdministrator.phone.$touch()
                      "
                      v-model="pharmacyForm.pharmacyAdministrator.phone"
                    ></v-text-field>
                    <v-text-field
                      :error-messages="addressErrorsPharmacyAdministrator"
                      label="Home address"
                      @blur="
                        $v.pharmacyForm.pharmacyAdministrator.address.$touch()
                      "
                      @input="
                        $v.pharmacyForm.pharmacyAdministrator.address.$touch()
                      "
                      v-model="pharmacyForm.pharmacyAdministrator.address"
                    ></v-text-field>
                  </v-col>

                  <v-col>
                    <v-text-field
                      :error-messages="cityErrorsPharmacyAdministrator"
                      label="City"
                      @blur="
                        $v.pharmacyForm.pharmacyAdministrator.city.$touch()
                      "
                      @input="
                        $v.pharmacyForm.pharmacyAdministrator.city.$touch()
                      "
                      v-model="pharmacyForm.pharmacyAdministrator.city"
                    ></v-text-field>
                    <v-text-field
                      :error-messages="countryErrorsPharmacyAdministrator"
                      label="Country"
                      @blur="
                        $v.pharmacyForm.pharmacyAdministrator.country.$touch()
                      "
                      @input="
                        $v.pharmacyForm.pharmacyAdministrator.country.$touch()
                      "
                      v-model="pharmacyForm.pharmacyAdministrator.country"
                    ></v-text-field>
                    <v-text-field
                      :error-messages="emailErrorsPharmacyAdministrator"
                      label="Email"
                      @blur="
                        $v.pharmacyForm.pharmacyAdministrator.email.$touch()
                      "
                      @input="
                        $v.pharmacyForm.pharmacyAdministrator.email.$touch()
                      "
                      v-model="pharmacyForm.pharmacyAdministrator.email"
                    ></v-text-field>
                    <v-text-field
                      :append-icon="
                        dermatologistForm.showPassword
                          ? 'mdi-eye'
                          : 'mdi-eye-off'
                      "
                      :errorMessages="passwordErrorsPharmacyAdministrator"
                      :type="
                        pharmacyForm.pharmacyAdministrator.showPassword
                          ? 'text'
                          : 'password'
                      "
                      label="Password"
                      @click:append="
                        pharmacyForm.pharmacyAdministrator.showPassword = !pharmacyForm
                          .pharmacyAdministrator.showPassword
                      "
                      @blur="
                        $v.pharmacyForm.pharmacyAdministrator.password.$touch()
                      "
                      @input="
                        $v.pharmacyForm.pharmacyAdministrator.password.$touch()
                      "
                      v-model="pharmacyForm.pharmacyAdministrator.password"
                    ></v-text-field>
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
              <v-btn color="primary" @click="confirmPharmacyRegistration()">
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
          <v-stepper
            style="margin: 3%"
            v-model="stepper.dermatologistRegStep"
            vertical
          >
            <!-- Register a dermatologist -->
            <v-stepper-step
              :complete="stepper.dermatologistRegStep > 1"
              step="1"
            >
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
                    <v-text-field
                      v-model="dermatologistForm.name"
                      :error-messages="nameErrorsDermatologist"
                      label="Name"
                      @blur="$v.dermatologistForm.name.$touch()"
                      @input="$v.dermatologistForm.name.$touch()"
                    ></v-text-field>
                    <v-text-field
                      v-model="dermatologistForm.surname"
                      :error-messages="surnameErrorsDermatologist"
                      label="Surname"
                      @blur="$v.dermatologistForm.surname.$touch()"
                      @input="$v.dermatologistForm.surname.$touch()"
                    ></v-text-field>
                    <v-text-field
                      :error-messages="phoneErrorsDermatologist"
                      label="Phone number"
                      @blur="$v.dermatologistForm.phone.$touch()"
                      @input="$v.dermatologistForm.phone.$touch()"
                      v-model="dermatologistForm.phone"
                    ></v-text-field>
                    <v-text-field
                      :error-messages="addressErrorsDermatologist"
                      label="Home address"
                      @blur="$v.dermatologistForm.address.$touch()"
                      @input="$v.dermatologistForm.address.$touch()"
                      v-model="dermatologistForm.address"
                    ></v-text-field>
                  </v-col>

                  <v-col>
                    <v-text-field
                      :error-messages="cityErrorsDermatologist"
                      label="City"
                      @blur="$v.dermatologistForm.city.$touch()"
                      @input="$v.dermatologistForm.city.$touch()"
                      v-model="dermatologistForm.city"
                    ></v-text-field>
                    <v-text-field
                      :error-messages="countryErrorsDermatologist"
                      label="Country"
                      @blur="$v.dermatologistForm.country.$touch()"
                      @input="$v.dermatologistForm.country.$touch()"
                      v-model="dermatologistForm.country"
                    ></v-text-field>
                    <v-text-field
                      :error-messages="emailErrorsDermatologist"
                      label="Email"
                      @blur="$v.dermatologistForm.email.$touch()"
                      @input="$v.dermatologistForm.email.$touch()"
                      v-model="dermatologistForm.email"
                    ></v-text-field>
                    <v-text-field
                      :append-icon="
                        dermatologistForm.showPassword
                          ? 'mdi-eye'
                          : 'mdi-eye-off'
                      "
                      :errorMessages="passwordErrorsDermatologist"
                      :type="
                        dermatologistForm.showPassword ? 'text' : 'password'
                      "
                      label="Password"
                      @click:append="
                        dermatologistForm.showPassword = !dermatologistForm.showPassword
                      "
                      @blur="$v.dermatologistForm.password.$touch()"
                      @input="$v.dermatologistForm.password.$touch()"
                      v-model="dermatologistForm.password"
                    ></v-text-field>
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
              <v-btn
                color="primary"
                @click="confirmDermatologistRegistration()"
              >
                Confirm
              </v-btn>
              <v-btn text @click="stepper.dermatologistRegStep = 1">
                Back
              </v-btn>
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
          <v-stepper
            style="margin: 3%"
            v-model="stepper.supplierRegStep"
            vertical
          >
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
                    <v-text-field
                      v-model="supplierForm.name"
                      :error-messages="nameErrorsSupplier"
                      label="Name"
                      @blur="$v.supplierForm.name.$touch()"
                      @input="$v.supplierForm.name.$touch()"
                    ></v-text-field>
                    <v-text-field
                      v-model="supplierForm.surname"
                      :error-messages="surnameErrorsSupplier"
                      label="Surname"
                      @blur="$v.supplierForm.surname.$touch()"
                      @input="$v.supplierForm.surname.$touch()"
                    ></v-text-field>
                    <v-text-field
                      v-model="supplierForm.phone"
                      :error-messages="phoneErrorsSupplier"
                      label="Phone number"
                      @blur="$v.supplierForm.phone.$touch()"
                      @input="$v.supplierForm.phone.$touch()"
                    ></v-text-field>
                    <v-text-field
                      v-model="supplierForm.address"
                      :error-messages="addressErrorsSupplier"
                      label="Home address"
                      @blur="$v.supplierForm.address.$touch()"
                      @input="$v.supplierForm.address.$touch()"
                    ></v-text-field>
                  </v-col>

                  <v-col>
                    <v-text-field
                      v-model="supplierForm.city"
                      :error-messages="cityErrorsSupplier"
                      label="City"
                      @blur="$v.supplierForm.city.$touch()"
                      @input="$v.supplierForm.city.$touch()"
                    ></v-text-field>
                    <v-text-field
                      v-model="supplierForm.country"
                      :error-messages="countryErrorsSupplier"
                      label="Country"
                      @blur="$v.supplierForm.country.$touch()"
                      @input="$v.supplierForm.country.$touch()"
                    ></v-text-field>
                    <v-text-field
                      v-model="supplierForm.email"
                      :error-messages="emailErrorsSupplier"
                      label="Email"
                      @blur="$v.supplierForm.email.$touch()"
                      @input="$v.supplierForm.email.$touch()"
                    ></v-text-field>
                    <v-text-field
                      v-model="supplierForm.password"
                      :append-icon="
                        supplierForm.showPassword ? 'mdi-eye' : 'mdi-eye-off'
                      "
                      :errorMessages="passwordErrorsSupplier"
                      :type="supplierForm.showPassword ? 'text' : 'password'"
                      label="Password"
                      @click:append="
                        supplierForm.showPassword = !supplierForm.showPassword
                      "
                      @blur="$v.supplierForm.password.$touch()"
                      @input="$v.supplierForm.password.$touch()"
                    ></v-text-field>
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
              <v-btn color="primary" @click="confirmSupplierRegistration()">
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
            <v-stepper-step
              :complete="stepper.systemAdministratorRegStep > 1"
              step="1"
            >
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
                    <v-text-field
                      v-model="systemAdministratorForm.name"
                      :error-messages="nameErrorsSystemAdministrator"
                      label="Name"
                      @blur="$v.systemAdministratorForm.name.$touch()"
                      @input="$v.systemAdministratorForm.name.$touch()"
                    ></v-text-field>
                    <v-text-field
                      v-model="systemAdministratorForm.surname"
                      :error-messages="surnameErrorsSystemAdministrator"
                      label="Surname"
                      @blur="$v.systemAdministratorForm.surname.$touch()"
                      @input="$v.systemAdministratorForm.surname.$touch()"
                    ></v-text-field>
                    <v-text-field
                      v-model="systemAdministratorForm.phone"
                      :error-messages="phoneErrorsSystemAdministrator"
                      label="Phone number"
                      @blur="$v.systemAdministratorForm.phone.$touch()"
                      @input="$v.systemAdministratorForm.phone.$touch()"
                    ></v-text-field>
                    <v-text-field
                      v-model="systemAdministratorForm.address"
                      :error-messages="addressErrorsSystemAdministrator"
                      label="Home address"
                      @blur="$v.systemAdministratorForm.address.$touch()"
                      @input="$v.systemAdministratorForm.address.$touch()"
                    ></v-text-field>
                  </v-col>

                  <v-col>
                    <v-text-field
                      v-model="systemAdministratorForm.city"
                      :error-messages="cityErrorsSystemAdministrator"
                      label="City"
                      @blur="$v.systemAdministratorForm.city.$touch()"
                      @input="$v.systemAdministratorForm.city.$touch()"
                    ></v-text-field>
                    <v-text-field
                      v-model="systemAdministratorForm.country"
                      :error-messages="countryErrorsSystemAdministrator"
                      label="Country"
                      @blur="$v.systemAdministratorForm.country.$touch()"
                      @input="$v.systemAdministratorForm.country.$touch()"
                    ></v-text-field>
                    <v-text-field
                      v-model="systemAdministratorForm.email"
                      :error-messages="emailErrorsSystemAdministrator"
                      label="Email"
                      @blur="$v.systemAdministratorForm.email.$touch()"
                      @input="$v.systemAdministratorForm.email.$touch()"
                    ></v-text-field>
                    <v-text-field
                      v-model="systemAdministratorForm.password"
                      :append-icon="
                        systemAdministratorForm.showPassword
                          ? 'mdi-eye'
                          : 'mdi-eye-off'
                      "
                      :errorMessages="passwordErrorsSystemAdministrator"
                      :type="
                        systemAdministratorForm.showPassword
                          ? 'text'
                          : 'password'
                      "
                      label="Password"
                      @click:append="
                        systemAdministratorForm.showPassword = !systemAdministratorForm.showPassword
                      "
                      @blur="$v.systemAdministratorForm.password.$touch()"
                      @input="$v.systemAdministratorForm.password.$touch()"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-card>
              <!-- End of the form for system administrator -->

              <!-- Buttons -->
              <v-btn
                color="primary"
                @click="stepper.systemAdministratorRegStep = 2"
              >
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
              <v-btn
                color="primary"
                @click="confirmSystemAdministratorRegistration()"
              >
                Confirm
              </v-btn>
              <v-btn text @click="stepper.systemAdministratorRegStep = 1">
                Back
              </v-btn>
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
                height="400px"
              >
                <v-text-field
                  v-model="drugForm.drug.name"
                  :error-messages="drugNameErrors"
                  label="Name of the drug"
                  @blur="$v.drugForm.drug.name.$touch()"
                  @input="$v.drugForm.drug.name.$touch()"
                ></v-text-field>
                <v-text-field
                  v-model="drugForm.drug.code"
                  :error-messages="drugCodeErrors"
                  @blur="$v.drugForm.drug.code.$touch()"
                  @input="$v.drugForm.drug.code.$touch()"
                  label="Code of the drug"
                ></v-text-field>
                <v-text-field
                  v-model="drugForm.drug.loyaltyPoints"
                  :error-messages="drugLoyaltyPointsErrors"
                  @blur="$v.drugForm.drug.loyaltyPoints.$touch()"
                  @input="$v.drugForm.drug.loyaltyPoints.$touch()"
                  label="Loyalty points"
                ></v-text-field>
                <v-select
                  v-model="alternateDrug"
                  :items="alternateDrugs"
                  item-text="name"
                  item-value="code"
                  label="Alternate drugs"
                  outlined
                ></v-select>
                <v-select
                  v-model="drugForm.drug.typeOfDrug"
                  :items="typesOfDrug"
                  label="Type of the drug"
                  outlined
                ></v-select>
              </v-card>
              <!-- End of the content -->

              <!-- Buttons -->
              <v-btn color="primary" @click="stepper.drugRegStep = 2">
                Continue
              </v-btn>
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
                    <v-text-field
                      v-model="drugForm.specification.manufacturer"
                      :error-messages="manufacturerErrors"
                      @blur="$v.drugForm.specification.manufacturer.$touch()"
                      @input="$v.drugForm.specification.manufacturer.$touch()"
                      label="Manufacturer"
                    ></v-text-field>
                    <v-text-field
                      v-model="drugForm.specification.contraindications"
                      :error-messages="contraindicationsErrors"
                      @blur="
                        $v.drugForm.specification.contraindications.$touch()
                      "
                      @input="
                        $v.drugForm.specification.contraindications.$touch()
                      "
                      label="Contraindications"
                    ></v-text-field>
                    <v-row>
                      <v-col>
                        <v-text-field
                          v-model="drugForm.specification.ingredients.name"
                          :error-messages="ingredientsNameErrors"
                          @blur="
                            $v.drugForm.specification.ingredients.name.$touch()
                          "
                          @input="
                            $v.drugForm.specification.ingredients.name.$touch()
                          "
                          label="Ingredient name"
                        ></v-text-field>
                      </v-col>

                      <v-col>
                        <v-text-field
                          v-model="drugForm.specification.ingredients.amount"
                          :error-messages="ingredientsAmountErrors"
                          @blur="
                            $v.drugForm.specification.ingredients.amount.$touch()
                          "
                          @input="
                            $v.drugForm.specification.ingredients.amount.$touch()
                          "
                          label="Amount"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                    <v-text-field
                      v-model="drugForm.specification.recommendedDailyDose"
                      :error-messages="recommendedDailyDoseErrors"
                      @blur="
                        $v.drugForm.specification.recommendedDailyDose.$touch()
                      "
                      @input="
                        $v.drugForm.specification.recommendedDailyDose.$touch()
                      "
                      label="Recommended daily dose"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-card>
              <!-- End of the form -->
              <v-btn color="primary" @click="stepper.drugRegStep = 3">
                Continue
              </v-btn>
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
              <v-btn color="primary" @click="confirmDrugRegistration()">
                Confirm
              </v-btn>
              <v-btn text @click="stepper.drugRegStep = 2"> Back </v-btn>
            </v-stepper-content>
            <!-- End of the confirmation of the drug registration -->
          </v-stepper>
          <!-- End of the drug registration -->
        </v-expansion-panel-content>
      </v-expansion-panel>
      <!-- End of register a drug panel -->
    </v-expansion-panels>
  </v-container>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, email, minLength, numeric } from "vuelidate/lib/validators";

export default {
  mixins: [validationMixin],
  data: () => ({
    stepper: {
      pharmacyRegStep: 1,
      systemAdministratorRegStep: 1,
      dermatologistRegStep: 1,
      supplierRegStep: 1,
      drugRegStep: 1,
      loyaltyRegStep: 1,
    },
    pharmacyForm: {
      name: "",
      city: "",
      country: "",
      description: "",
      dermatologistConsultationPrice: 0,
      pharmacistConsultationPrice: 0,
      pharmacyAdministrator: {
        password: "",
        showPassword: false,
        email: "",
        name: "",
        surname: "",
        city: "",
        address: "",
        country: "",
        phone: "",
      },
    },
    alternateDrug: "",
    typesOfDrug: [],
    alternateDrugs: [],
    dermatologistForm: {
      email: "",
      password: "",
      showPassword: false,
      name: "",
      surname: "",
      address: "",
      city: "",
      country: "",
      phone: "",
    },
    supplierForm: {
      email: "",
      password: "",
      showPassword: false,
      name: "",
      surname: "",
      address: "",
      city: "",
      country: "",
      phone: "",
    },
    systemAdministratorForm: {
      email: "",
      password: "",
      showPassword: false,
      name: "",
      surname: "",
      address: "",
      city: "",
      country: "",
      phone: "",
    },
    drugForm: {
      drug: {
        name: "",
        code: "",
        loyaltyPoints: "",
      },
      specification: {
        manufacturer: "",
        contraindications: "",
        ingredients: {
          name: "",
          amount: 0.0,
        },
        recommendedDailyDose: 0,
      },
    },
  }),
  validations: {
    dermatologistForm: {
      password: {
        required,
        minLength: minLength(6),
      },
      email: {
        required,
        email,
      },
      name: {
        required,
      },
      surname: {
        required,
      },
      address: {
        required,
      },
      city: {
        required,
      },
      country: {
        required,
      },
      phone: {
        required,
        numeric,
      },
    },
    supplierForm: {
      password: {
        required,
        minLength: minLength(6),
      },
      email: {
        required,
        email,
      },
      name: {
        required,
      },
      surname: {
        required,
      },
      address: {
        required,
      },
      city: {
        required,
      },
      country: {
        required,
      },
      phone: {
        required,
        numeric,
      },
    },
    systemAdministratorForm: {
      password: {
        required,
        minLength: minLength(6),
      },
      email: {
        required,
        email,
      },
      name: {
        required,
      },
      surname: {
        required,
      },
      address: {
        required,
      },
      city: {
        required,
      },
      country: {
        required,
      },
      phone: {
        required,
        numeric,
      },
    },
    drugForm: {
      drug: {
        name: {
          required,
        },
        code: {
          required,
        },
        loyaltyPoints: {
          required,
          numeric,
        },
      },
      specification: {
        manufacturer: {
          required,
        },
        contraindications: {
          required,
        },
        ingredients: {
          name: {
            required,
          },
          amount: {
            required,
            numeric,
          },
        },
        recommendedDailyDose: {
          required,
          numeric,
        },
      },
    },
    pharmacyForm: {
      name: {
        required,
      },
      city: {
        required,
      },
      country: {
        required,
      },
      description: {
        required,
      },
      dermatologistConsultationPrice: {
        required,
        numeric,
      },
      pharmacistConsultationPrice: {
        required,
        numeric,
      },
      pharmacyAdministrator: {
        password: {
          required,
          minLength: minLength(6),
        },
        email: {
          required,
          email,
        },
        name: {
          required,
        },
        surname: {
          required,
        },
        city: {
          required,
        },
        address: {
          required,
        },
        country: {
          required,
        },
        phone: {
          required,
          numeric,
        },
      },
    },
  },
  methods: {
    confirmPharmacyRegistration() {
      // TODO: Make some check if is all validation okey
      this.registerPharmacy();
    },
    confirmDrugRegistration() {
      // TODO: Make some check if is all validation okey
      this.registerDrug();
    },
    confirmDermatologistRegistration() {
      // TODO: Make some check if is all validation okey
      this.registerDermatologist();
    },
    confirmSupplierRegistration() {
      // TODO: Make some check if is all validation okey
      this.registerSupplier();
    },
    confirmSystemAdministratorRegistration() {
      // TODO: Make some check if is all validation okey
      this.registerSystemAdministrator();
    },
    registerPharmacy() {
      alert("bam");
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PHARMACIES_REGISTER_ENDPOINT,
          {
            name: this.pharmacyForm.name,
            city: this.pharmacyForm.city,
            country: this.pharmacyForm.country,
            description: this.pharmacyForm.description,
            dermatologistConsultationPrice: this.pharmacyForm.dermatologistConsultationPrice,
            pharmacistConsultationPrice: this.pharmacyForm.pharmacistConsultationPrice,
            pharmacyAdministrator: {
              password: this.pharmacyForm.pharmacyAdministrator.password,
              email: this.pharmacyForm.pharmacyAdministrator.email,
              name: this.pharmacyForm.pharmacyAdministrator.name,
              surname: this.pharmacyForm.pharmacyAdministrator.surname,
              city: this.pharmacyForm.pharmacyAdministrator.city,
              address: this.pharmacyForm.pharmacyAdministrator.address,
              country: this.pharmacyForm.pharmacyAdministrator.country,
              mobile: this.pharmacyForm.pharmacyAdministrator.phone,
            },
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          // TODO: Make some notification here
          alert("Successfuly added new pharmacy");
        })
        .catch((error) => {
          // TODO: Make some tost notifications here
          alert("Error during pharmacy registration: " + error);
        });
    },
    registerDrug() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_DRUG_REGISTRATION_ENDPOINT,
          {
            drug: {
              name: this.drugForm.drug.name,
              code: this.drugForm.drug.code,
              loyaltyPoints: this.drugForm.drug.loyaltyPoints,
              typeOfDrug: this.drugForm.drug.typeOfDrug,
              alternateDrugs: [
                {
                  code: this.alternateDrug,
                },
              ],
            },
            specification: {
              manufacturer: this.drugForm.specification.manufacturer,
              contraindications: this.drugForm.specification.contraindications,
              recommendedDailyDose: this.drugForm.specification
                .recommendedDailyDose,
              ingredients: [this.drugForm.specification.ingredients],
            },
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          // TODO: Make some notification here
          alert("Successfuly added new drug");
        })
        .catch((error) => {
          // TODO: Make some tost notifications here
          alert("Error during drug registration: " + error);
        });
    },
    registerDermatologist() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_DERMATOLOGIST_REGISTRATION_ENDPOINT,
          {
            password: this.dermatologistForm.password,
            email: this.dermatologistForm.email,
            name: this.dermatologistForm.name,
            surname: this.dermatologistForm.surname,
            city: this.dermatologistForm.city,
            address: this.dermatologistForm.address,
            country: this.dermatologistForm.country,
            mobile: this.dermatologistForm.phone,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          // TODO: Make some notification here
          alert("Successfuly added new dermatologist");
        })
        .catch((error) => {
          // TODO: Make some tost notifications here
          alert("Error during dermatologist registration: " + error);
        });
    },
    registerSupplier() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_SUPPLIER_REGISTRATION_ENDPOINT,
          {
            password: this.supplierForm.password,
            email: this.supplierForm.email,
            name: this.supplierForm.name,
            surname: this.supplierForm.surname,
            city: this.supplierForm.city,
            address: this.supplierForm.address,
            country: this.supplierForm.country,
            mobile: this.supplierForm.phone,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          // TODO: Make some notification here
          alert("Successfuly added new supplier");
        })
        .catch((error) => {
          // TODO: Make some tost notifications here
          alert("Error during supplier registration: " + error);
        });
    },
    registerSystemAdministrator() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_SYSTEM_ADMINISTRATOR_REGISTRATION_ENDPOINT,
          {
            password: this.systemAdministratorForm.password,
            email: this.systemAdministratorForm.email,
            name: this.systemAdministratorForm.name,
            surname: this.systemAdministratorForm.surname,
            city: this.systemAdministratorForm.city,
            address: this.systemAdministratorForm.address,
            country: this.systemAdministratorForm.country,
            mobile: this.systemAdministratorForm.phone,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          // TODO: Make some notification here
          alert("Successfuly added new system administrator");
        })
        .catch((error) => {
          // TODO: Make some tost notifications here
          alert("Error during system administrator registration: " + error);
        });
    },
  },
  computed: {
    // TODO: Find how to encapsulate those methods, because they are same as in register
    passwordErrorsPharmacyAdministrator() {
      const errors = [];
      if (!this.$v.pharmacyForm.pharmacyAdministrator.password.$dirty)
        return errors;
      !this.$v.pharmacyForm.pharmacyAdministrator.password.required &&
        errors.push("Password is required.");
      !this.$v.pharmacyForm.pharmacyAdministrator.password.minLength &&
        errors.push("Password is too short.");
      return errors;
    },
    emailErrorsPharmacyAdministrator() {
      const errors = [];
      if (!this.$v.pharmacyForm.pharmacyAdministrator.email.$dirty)
        return errors;
      !this.$v.pharmacyForm.pharmacyAdministrator.email.required &&
        errors.push("Email is required.");
      !this.$v.pharmacyForm.pharmacyAdministrator.email.email &&
        errors.push("Email is not valid.");
      return errors;
    },
    nameErrorsPharmacyAdministrator() {
      const errors = [];
      if (!this.$v.pharmacyForm.pharmacyAdministrator.name.$dirty)
        return errors;
      !this.$v.pharmacyForm.pharmacyAdministrator.name.required &&
        errors.push("Name is required.");
      return errors;
    },
    surnameErrorsPharmacyAdministrator() {
      const errors = [];
      if (!this.$v.pharmacyForm.pharmacyAdministrator.surname.$dirty)
        return errors;
      !this.$v.pharmacyForm.pharmacyAdministrator.surname.required &&
        errors.push("Surname is required.");
      return errors;
    },
    cityErrorsPharmacyAdministrator() {
      const errors = [];
      if (!this.$v.pharmacyForm.pharmacyAdministrator.city.$dirty)
        return errors;
      !this.$v.pharmacyForm.pharmacyAdministrator.city.required &&
        errors.push("City is required.");
      return errors;
    },
    countryErrorsPharmacyAdministrator() {
      const errors = [];
      if (!this.$v.pharmacyForm.pharmacyAdministrator.country.$dirty)
        return errors;
      !this.$v.pharmacyForm.pharmacyAdministrator.country.required &&
        errors.push("Country is required.");
      return errors;
    },
    addressErrorsPharmacyAdministrator() {
      const errors = [];
      if (!this.$v.pharmacyForm.pharmacyAdministrator.address.$dirty)
        return errors;
      !this.$v.pharmacyForm.pharmacyAdministrator.address.required &&
        errors.push("Home address is required.");
      return errors;
    },
    phoneErrorsPharmacyAdministrator() {
      const errors = [];
      if (!this.$v.pharmacyForm.pharmacyAdministrator.phone.$dirty)
        return errors;
      !this.$v.pharmacyForm.pharmacyAdministrator.phone.required &&
        errors.push("Phone number is required.");
      !this.$v.pharmacyForm.pharmacyAdministrator.phone.numeric &&
        errors.push("Phone number should only contain numbers.");
      return errors;
    },
    pharmacistConsultationPriceErrors() {
      const errors = [];
      if (!this.$v.pharmacyForm.pharmacistConsultationPrice.$dirty)
        return errors;
      !this.$v.pharmacyForm.pharmacistConsultationPrice.required &&
        errors.push("Pharmacist consultation price is required.");
      !this.$v.pharmacyForm.pharmacistConsultationPrice.numeric &&
        errors.push(
          "Pharmacist consultation price should only contain numbers."
        );
      return errors;
    },
    dermatologistConsultationPriceErrors() {
      const errors = [];
      if (!this.$v.pharmacyForm.dermatologistConsultationPrice.$dirty)
        return errors;
      !this.$v.pharmacyForm.dermatologistConsultationPrice.required &&
        errors.push("Dermatologist consultation price is required.");
      !this.$v.pharmacyForm.dermatologistConsultationPrice.numeric &&
        errors.push(
          "Dermatologist consultation price should only contain numbers."
        );
      return errors;
    },
    pharmacyDescriptionErrors() {
      const errors = [];
      if (!this.$v.pharmacyForm.description.$dirty) return errors;
      !this.$v.pharmacyForm.description.required &&
        errors.push("Description is required.");
      return errors;
    },
    pharmacyCountryErrors() {
      const errors = [];
      if (!this.$v.pharmacyForm.country.$dirty) return errors;
      !this.$v.pharmacyForm.country.required &&
        errors.push("Country is required.");
      return errors;
    },
    pharmacyCityErrors() {
      const errors = [];
      if (!this.$v.pharmacyForm.city.$dirty) return errors;
      !this.$v.pharmacyForm.city.required && errors.push("City is required.");
      return errors;
    },
    pharmacyNameErrors() {
      const errors = [];
      if (!this.$v.pharmacyForm.name.$dirty) return errors;
      !this.$v.pharmacyForm.name.required && errors.push("Name is required.");
      return errors;
    },
    drugLoyaltyPointsErrors() {
      const errors = [];
      if (!this.$v.drugForm.drug.loyaltyPoints.$dirty) return errors;
      !this.$v.drugForm.drug.loyaltyPoints.required &&
        errors.push("Drug loyalty points is required.");
      return errors;
    },
    drugCodeErrors() {
      const errors = [];
      if (!this.$v.drugForm.drug.code.$dirty) return errors;
      !this.$v.drugForm.drug.code.required &&
        errors.push("Drug code is required.");
      return errors;
    },
    drugNameErrors() {
      const errors = [];
      if (!this.$v.drugForm.drug.name.$dirty) return errors;
      !this.$v.drugForm.drug.name.required &&
        errors.push("Drug name is required.");
      return errors;
    },
    recommendedDailyDoseErrors() {
      const errors = [];
      if (!this.$v.drugForm.specification.recommendedDailyDose.$dirty)
        return errors;
      !this.$v.drugForm.specification.recommendedDailyDose.required &&
        errors.push("Recommended daily dose is required.");
      !this.$v.drugForm.specification.recommendedDailyDose.numeric &&
        errors.push("Recommended daily dose should only contain numbers.");
      return errors;
    },
    ingredientsAmountErrors() {
      const errors = [];
      if (!this.$v.drugForm.specification.ingredients.amount.$dirty)
        return errors;
      !this.$v.drugForm.specification.ingredients.amount.required &&
        errors.push("Ingredient amount is required.");
      !this.$v.drugForm.specification.ingredients.amount.numeric &&
        errors.push("Ingredient amount should only contain numbers.");
      return errors;
    },
    ingredientsNameErrors() {
      const errors = [];
      if (!this.$v.drugForm.specification.ingredients.name.$dirty)
        return errors;
      !this.$v.drugForm.specification.ingredients.name.required &&
        errors.push("Ingredient name is required.");
      return errors;
    },
    contraindicationsErrors() {
      const errors = [];
      if (!this.$v.drugForm.specification.contraindications.$dirty)
        return errors;
      !this.$v.drugForm.specification.contraindications.required &&
        errors.push("Contraindications is required.");
      return errors;
    },
    manufacturerErrors() {
      const errors = [];
      if (!this.$v.drugForm.specification.manufacturer.$dirty) return errors;
      !this.$v.drugForm.specification.manufacturer.required &&
        errors.push("Manufacturer is required.");
      return errors;
    },
    passwordErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.password.$dirty) return errors;
      !this.$v.dermatologistForm.password.required &&
        errors.push("Password is required.");
      !this.$v.dermatologistForm.password.minLength &&
        errors.push("Password is too short.");
      return errors;
    },
    emailErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.email.$dirty) return errors;
      !this.$v.dermatologistForm.email.required &&
        errors.push("Email is required.");
      !this.$v.dermatologistForm.email.email &&
        errors.push("Email is not valid.");
      return errors;
    },
    nameErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.name.$dirty) return errors;
      !this.$v.dermatologistForm.name.required &&
        errors.push("Name is required.");
      return errors;
    },
    surnameErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.surname.$dirty) return errors;
      !this.$v.dermatologistForm.surname.required &&
        errors.push("Surname is required.");
      return errors;
    },
    cityErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.city.$dirty) return errors;
      !this.$v.dermatologistForm.city.required &&
        errors.push("City is required.");
      return errors;
    },
    countryErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.country.$dirty) return errors;
      !this.$v.dermatologistForm.country.required &&
        errors.push("Country is required.");
      return errors;
    },
    addressErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.address.$dirty) return errors;
      !this.$v.dermatologistForm.address.required &&
        errors.push("Home address is required.");
      return errors;
    },
    phoneErrorsDermatologist() {
      const errors = [];
      if (!this.$v.dermatologistForm.phone.$dirty) return errors;
      !this.$v.dermatologistForm.phone.required &&
        errors.push("Phone number is required.");
      !this.$v.dermatologistForm.phone.numeric &&
        errors.push("Phone number should only contain numbers.");
      return errors;
    },
    passwordErrorsSupplier() {
      const errors = [];
      if (!this.$v.supplierForm.password.$dirty) return errors;
      !this.$v.supplierForm.password.required &&
        errors.push("Password is required.");
      !this.$v.supplierForm.password.minLength &&
        errors.push("Password is too short.");
      return errors;
    },
    emailErrorsSupplier() {
      const errors = [];
      if (!this.$v.supplierForm.email.$dirty) return errors;
      !this.$v.supplierForm.email.required && errors.push("Email is required.");
      !this.$v.supplierForm.email.email && errors.push("Email is not valid.");
      return errors;
    },
    nameErrorsSupplier() {
      const errors = [];
      if (!this.$v.supplierForm.name.$dirty) return errors;
      !this.$v.supplierForm.name.required && errors.push("Name is required.");
      return errors;
    },
    surnameErrorsSupplier() {
      const errors = [];
      if (!this.$v.supplierForm.surname.$dirty) return errors;
      !this.$v.supplierForm.surname.required &&
        errors.push("Surname is required.");
      return errors;
    },
    cityErrorsSupplier() {
      const errors = [];
      if (!this.$v.supplierForm.city.$dirty) return errors;
      !this.$v.supplierForm.city.required && errors.push("City is required.");
      return errors;
    },
    countryErrorsSupplier() {
      const errors = [];
      if (!this.$v.supplierForm.country.$dirty) return errors;
      !this.$v.supplierForm.country.required &&
        errors.push("Country is required.");
      return errors;
    },
    addressErrorsSupplier() {
      const errors = [];
      if (!this.$v.supplierForm.address.$dirty) return errors;
      !this.$v.supplierForm.address.required &&
        errors.push("Home address is required.");
      return errors;
    },
    phoneErrorsSupplier() {
      const errors = [];
      if (!this.$v.supplierForm.phone.$dirty) return errors;
      !this.$v.supplierForm.phone.required &&
        errors.push("Phone number is required.");
      !this.$v.supplierForm.phone.numeric &&
        errors.push("Phone number should only contain numbers.");
      return errors;
    },
    passwordErrorsSystemAdministrator() {
      const errors = [];
      if (!this.$v.systemAdministratorForm.password.$dirty) return errors;
      !this.$v.systemAdministratorForm.password.required &&
        errors.push("Password is required.");
      !this.$v.systemAdministratorForm.password.minLength &&
        errors.push("Password is too short.");
      return errors;
    },
    emailErrorsSystemAdministrator() {
      const errors = [];
      if (!this.$v.systemAdministratorForm.email.$dirty) return errors;
      !this.$v.systemAdministratorForm.email.required &&
        errors.push("Email is required.");
      !this.$v.systemAdministratorForm.email.email &&
        errors.push("Email is not valid.");
      return errors;
    },
    nameErrorsSystemAdministrator() {
      const errors = [];
      if (!this.$v.systemAdministratorForm.name.$dirty) return errors;
      !this.$v.systemAdministratorForm.name.required &&
        errors.push("Name is required.");
      return errors;
    },
    surnameErrorsSystemAdministrator() {
      const errors = [];
      if (!this.$v.systemAdministratorForm.surname.$dirty) return errors;
      !this.$v.systemAdministratorForm.surname.required &&
        errors.push("Surname is required.");
      return errors;
    },
    cityErrorsSystemAdministrator() {
      const errors = [];
      if (!this.$v.systemAdministratorForm.city.$dirty) return errors;
      !this.$v.systemAdministratorForm.city.required &&
        errors.push("City is required.");
      return errors;
    },
    countryErrorsSystemAdministrator() {
      const errors = [];
      if (!this.$v.systemAdministratorForm.country.$dirty) return errors;
      !this.$v.systemAdministratorForm.country.required &&
        errors.push("Country is required.");
      return errors;
    },
    addressErrorsSystemAdministrator() {
      const errors = [];
      if (!this.$v.systemAdministratorForm.address.$dirty) return errors;
      !this.$v.systemAdministratorForm.address.required &&
        errors.push("Home address is required.");
      return errors;
    },
    phoneErrorsSystemAdministrator() {
      const errors = [];
      if (!this.$v.systemAdministratorForm.phone.$dirty) return errors;
      !this.$v.systemAdministratorForm.phone.required &&
        errors.push("Phone number is required.");
      !this.$v.systemAdministratorForm.phone.numeric &&
        errors.push("Phone number should only contain numbers.");
      return errors;
    },
  },
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_ALL_DRUGS_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        this.alternateDrugs = [];
        for (let drug of resp.data) {
          this.alternateDrugs.push(drug);
        }
      });

    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_ALL_DRUGS_TYPES_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        this.typesOfDrug = [];
        for (let drugType of resp.data) {
          this.typesOfDrug.push(drugType.name);
        }
      });
  },
};
</script>