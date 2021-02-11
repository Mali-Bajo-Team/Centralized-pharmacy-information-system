<template>
  <v-app>
    <!-- SEARCH Pharmacis -->
    <div class="mb-3 mt-16">

      
    </div>

    <h4 class="primary--text">
      Click on event for more info or for start examination report
    </h4>
    <v-sheet tile height="54" class="d-flex">
      <v-btn icon class="ma-2" @click="$refs.calendar.prev()">
        <v-icon>mdi-chevron-left</v-icon>
      </v-btn>
      <v-select
        v-model="type"
        :items="types"
        dense
        outlined
        hide-details
        class="ma-2"
        label="type"
      ></v-select>
      <v-select
        v-model="mode"
        :items="modes"
        dense
        outlined
        hide-details
        label="event-overlap-mode"
        class="ma-2"
      ></v-select>
      <v-select
        v-model="weekday"
        :items="weekdays"
        dense
        outlined
        hide-details
        label="weekdays"
        class="ma-2"
      ></v-select>
      <v-spacer></v-spacer>
      <v-btn icon class="ma-2" @click="$refs.calendar.next()">
        <v-icon>mdi-chevron-right</v-icon>
      </v-btn>
    </v-sheet>
    <v-sheet height="600">
      <v-calendar
        ref="calendar"
        v-model="value"
        :weekdays="weekday"
        :type="type"
        :events="events"
        :event-overlap-mode="mode"
        :event-overlap-threshold="30"
        :event-color="getEventColor"
        @change="getEvents"
        @click:event="showExaminationDialog"
      ></v-calendar>
    </v-sheet>

    <!-- Start QUESTION DIALOG -->
    <v-dialog v-model="questionDialog" persistent max-width="1000px">
      <v-card>
        <v-card-title>
          <span class="headline">Examination report for {{ name }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row> </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="addPenaltie(), (questionDialog = false)"
          >
            The patient did not show up
          </v-btn>
          <v-btn
            color="primary"
            text
            @click="
              getDrugsWithoutAllergies();
            
            "
          >
            Start examination
          </v-btn>
          <v-btn color="primary" text @click="questionDialog = false">
            Cancel
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <!-- End QUESTION DIALOG-->

    <!-- Start REPORT DIALOG-->
    <v-dialog v-model="reportDialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">Examination report for {{ name }}</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-stepper v-model="e6" vertical>
              <v-stepper-step :complete="e6 > 1" step="1">
                Examination report
              </v-stepper-step>

              <v-stepper-content step="1">
                <v-card color="grey lighten-3" class="mb-12" height="200px">
                  <v-textarea v-model="report" color="teal">
                    <template v-slot:label>
                      <div>Report</div>
                    </template>
                  </v-textarea>
                </v-card>
                <v-btn color="primary" @click="e6 = 2"> Continue </v-btn>
                <v-btn
                  text
                  @click="
                    questionDialog = true;
                    reportDialog = false;
                    e6 = 1;
                  "
                >
                  Cancel
                </v-btn>
              </v-stepper-content>

              <v-stepper-step :complete="e6 > 2" step="2">
                Prescribe drug
              </v-stepper-step>

              <v-stepper-content step="2">
                <v-card color="grey lighten-3" class="mb-12" height="700px">
                  <!-- CHOOSE DRUG FOR PERSCRIBE -->
                  <h4 class="ml-n primary--text">
                    Choose {{ alternateDrugTxt }} (listed drugs was filtered
                    from allergens)
                  </h4>
                  <v-select
                    class="ml-16 mr-16"
                    v-model="selecteddrugWithoutAllergies"
                    :items="drugsWithoutAllergies"
                    item-text="name"
                    label="Select drug/alternate drug"
                    persistent-hint
                    return-object
                    outlined
                    single-line
                    v-bind:value="valueDrugsWithoutAllergies"
                    v-on:input="onInputWithoutAllergies"
                    @click="alertDrugsWithoutAllergies = false"
                  ></v-select>
                  <v-alert
                    :value="alertselectDrug"
                    color="pink"
                    dark
                    border="top"
                    icon="mdi-account"
                    transition="scale-transition"
                  >
                    You must select drug!
                  </v-alert>
                  <h4 class="ml-n primary--text">
                    Determine the duration of therapy
                  </h4>
                  <v-text-field
                    solo
                    class="mr-16 ml-16"
                    label="Duration of therapy"
                    v-model="durationOfPerscirbe"
                  ></v-text-field>
                   <v-alert
                    :value="alertdurathiontherapy"
                    color="pink"
                    dark
                    border="top"
                    icon="mdi-account"
                    transition="scale-transition"
                  >
                    You must determine duration of therapy!
                  </v-alert>
                  <!-- DRUG SPECIFICATION -->
                  <h4 class="ml-n primary--text">Drug specification</h4>
                  <v-btn
                    class="ml-16"
                    depressed
                    color="primary"
                    @click="showDescription"
                  >
                    Show
                  </v-btn>

                  <h3 class="mt-1 mb-6 ml-2 BLACK--text">
                    {{ drugSpecification }}
                  </h3>
                  <h4 class="ml-n primary--text">
                    Check the availability of the drug in the current pharmacy
                  </h4>
                  <v-btn
                    class="ml-16"
                    depressed
                    color="primary"
                    @click="checkDrugAvailability"
                  >
                    CHECK
                  </v-btn>
                  <v-alert
                    :value="alertIsDrugAvailable"
                    color="pink"
                    dark
                    border="top"
                    icon="mdi-pill"
                    transition="scale-transition"
                  >
                    Drug is not available in current pharmacy, you can perscribe
                    one of alternate drugs and perscribe it!
                  </v-alert>
                  <v-alert
                    :value="succesIsDrugAvailable"
                    color="green"
                    dark
                    border="top"
                    icon="mdi-account"
                    transition="scale-transition"
                  >
                    Drug is available, you can perscribe it!
                  </v-alert>
                  <h4 class="ml-n mt-6 primary--text">Perscirbe drug</h4>
                  <v-btn
                    class="ml-16"
                    depressed
                    color="primary"
                    @click="prescribeDrug"
                  >
                    PERSCRIBE
                  </v-btn>

                  <v-alert
                    :value="succDrugsWithoutAllergies"
                    color="green"
                    dark
                    border="top"
                    icon="mdi-account"
                    transition="scale-transition"
                  >
                    Drug is Successfully prescribed!!
                  </v-alert>
                </v-card>
                <v-btn color="primary" @click="e6 = 3"> Continue </v-btn>
                <v-btn
                  text
                  @click="
                    questionDialog = true;
                    reportDialog = false;
                    e6 = 1;
                  "
                >
                  Cancel
                </v-btn>
              </v-stepper-content>
              <!-- SCHEDULE AN ADITIONAL EXAMINATIOn -->
              <v-stepper-step :complete="e6 > 3" step="3">
                Schedule an additional examination
              </v-stepper-step>

              <v-stepper-content step="3">
                <v-card color="grey lighten-3" class="mb-12" height="2100px">
                  <h4 class="ml-n primary--text">
                    Choose predefined examination date and time
                  </h4>
                  <v-select
                    class="ml-16 mr-16"
                    v-model="selectpredefinedDate"
                    :items="predefinedDate"
                    item-text="startDate"
                    item-value="startDate"
                    label="Select one predefined date"
                    persistent-hint
                    return-object
                    outlined
                    single-line
                    v-bind:value="valuePredefinedDate"
                    v-on:input="onInputPredefinedDate"
                  ></v-select>
                  <v-btn
                    depressed
                    class="ml-16"
                    @click="scheduleConsultationPredefined"
                    color="primary"
                  >
                    Schedule
                  </v-btn>
                  <v-alert
                    :value="scheduleSucces"
                    color="green"
                    dark
                    border="top"
                    icon="mdi-account"
                    transition="scale-transition"
                  >
                    Successfully scheduled
                  </v-alert>
                  <v-alert
                    :value="scheduleAlert"
                    color="pink"
                    dark
                    border="top"
                    icon="mdi-account"
                    transition="scale-transition"
                  >
                    The schedule must match the working hours of the pharmacist.
                    The appointment should not be prepared with another
                    examination or consultation that the patient has scheduled
                    (in any pharmacy). And also it is not possible to schedule
                    consultations in the past.
                  </v-alert>
                  <h4 class="ml-n mt-10 primary--text">
                    Define new examination date and time
                  </h4>
                  <!-- DEFINE DATE CALENDAR -->
                  <div class="mr-16 ml-16">
                    <h4 class="ml-n primary--text">
                      Define date for new examination
                    </h4>
                    <v-date-picker
                      class="ml-"
                      v-model="picker"
                      v-bind:value="valueDate"
                      v-on:input="onInputDate"
                      color="primary lighten-1"
                    ></v-date-picker>
                    <v-alert
                      :value="alertDate"
                      color="pink"
                      dark
                      border="top"
                      icon="mdi-account"
                      transition="scale-transition"
                    >
                      You must pick date!
                    </v-alert>
                  </div>
                  <!-- DEFINE START TIME -->
                  <div class="mr-10 mb-8 ml-16 mt-6">
                    <h4 class="ml-n primary--text">
                      Define starting time for examination
                    </h4>
                    <v-time-picker
                      v-bind:value="valueStartTime"
                      v-on:input="onInputStartTime"
                      format="ampm"
                      color="primary lighten-1"
                    ></v-time-picker>
                    <v-alert
                      :value="alertStartTime"
                      color="pink"
                      dark
                      border="top"
                      icon="mdi-account"
                      transition="scale-transition"
                    >
                      You must pick start date!
                    </v-alert>
                  </div>
                  <!-- DEFINE END TIME -->
                  <div class="ml-16">
                    <h4 class="ml-n mt-6 primary--text">
                      Choose ending time for examination
                    </h4>
                    <v-time-picker
                      v-bind:value="valueEndTime"
                      v-on:input="onInputEndTime"
                      format="ampm"
                      color="primary lighten-1"
                    ></v-time-picker>

                    <v-alert
                      :value="alertEndTime"
                      color="pink"
                      dark
                      border="top"
                      icon="mdi-account"
                      transition="scale-transition"
                    >
                      You must pick end time!
                    </v-alert>
                    <v-btn
                      depressed
                      class="mt-5"
                      @click="scheduleConsultation"
                      color="primary"
                    >
                      Schedule
                    </v-btn>
                    <v-alert
                      :value="scheduleSucces"
                      color="green"
                      dark
                      border="top"
                      icon="mdi-account"
                      transition="scale-transition"
                    >
                      Successfully scheduled
                    </v-alert>
                    <v-alert
                      :value="scheduleAlert"
                      color="pink"
                      dark
                      border="top"
                      icon="mdi-account"
                      transition="scale-transition"
                    >
                      The schedule must match the working hours of the
                      pharmacist. The appointment should not be prepared with
                      another examination or consultation that the patient has
                      scheduled (in any pharmacy). And also it is not possible
                      to schedule consultations in the past.
                    </v-alert>
                  </div>
                </v-card>
                <v-btn color="primary" @click="e6 = 4"> Continue </v-btn>
                <v-btn
                  text
                  @click="
                    questionDialog = true;
                    reportDialog = false;
                    e6 = 1;
                  "
                >
                  Cancel
                </v-btn>
              </v-stepper-content>

              <v-stepper-step step="4"> Finish </v-stepper-step>
              <v-stepper-content step="4">
                <v-btn
                  color="primary"
                  @click="
                  refreshPage(),
                    e6 = 1,
                    reportDialog = false
                    
                  "
                >
                  Finish
                </v-btn>
              </v-stepper-content>
            </v-stepper>
          </v-container>
        </v-card-text>
      </v-card>
    </v-dialog>
    <!-- End REPORT DIALOG-->
  </v-app>
</template>

<script>
import { getStringDateWithTimeFromMilliseconds } from "./../util/dateHandler";

export default {
  data: () => ({
    alertdurathiontherapy: false,
    alertselectDrug: false,
    picker: new Date().toISOString().substr(0, 10),
    selectedPharmacy: null,
    isLoading: false,
    e6: 1,
    report: "",
    dragEvent: null,
    name: "",
    patientId: null,
    questionDialog: false,
    reportDialog: false,
    consultants: [],
    response: null,
    consultationId: null,
    selecteddrugWithoutAllergies: null,
    drugsWithoutAllergies: null,
    succDrugsWithoutAllergies: false,
    valueDrugsWithoutAllergies: null,
    drugSpecification: "",
    isPerscribeButtonDisabled: true,
    succesIsDrugAvailable: false,
    alertIsDrugAvailable: false,
    durationOfPerscirbe: null,
    alternateDrugTxt: "drug",
    selectpredefinedDate: null,
    predefinedDate: null,
    valuePredefinedDate: null,

    pharmacyID: null,
    valueStartTime: null,
    valueEndTime: null,
    valueDate: null,
    alertDate: false,
    alertStartTime: false,
    alertEndTime: false,
    examinationStartTime: null,
    examinationEndTime: null,
    scheduleSucces: false,
    scheduleAlert: false,
    type: "month",
    types: ["month", "week", "day", "4day"],
    mode: "stack",
    modes: ["stack", "column"],
    weekday: [0, 1, 2, 3, 4, 5, 6],
    weekdays: [
      { text: "Sun - Sat", value: [0, 1, 2, 3, 4, 5, 6] },
      { text: "Mon - Sun", value: [1, 2, 3, 4, 5, 6, 0] },
      { text: "Mon - Fri", value: [1, 2, 3, 4, 5] },
      { text: "Mon, Wed, Fri", value: [1, 3, 5] },
    ],
    value: "",
    events: [],
    colors: [
      "blue",
      "indigo",
      // 'deep-purple',
      // 'cyan',
      // 'green',
      // 'orange',
      // 'grey darken-1'
    ],
    names: ["Pregled", "Holiday"],
  }),
  
  methods: {
    refreshPage(){
       this.$router.go();
    },
    onInputStartTime(valueStartTime) {
      this.$emit("input", valueStartTime);
      this.examinationStartTime = valueStartTime;
      this.alertStartTime = false;
      console.log(this.examinationStartTime);
    },
    onInputEndTime(valueEndTime) {
      this.$emit("input", valueEndTime);
      this.examinationEndTime = valueEndTime;
      this.alertEndTime = false;
      console.log(this.examinationEndTime);
    },
    onInputPredefinedDate(valuePredefinedDate) {
      this.$emit("input", valuePredefinedDate);
      this.valuePredefinedDate = valuePredefinedDate;
      console.log(
        "AAAAAAA" +
          getStringDateWithTimeFromMilliseconds(valuePredefinedDate.startDate)
      );
      this.valueDate = getStringDateWithTimeFromMilliseconds(
        valuePredefinedDate.startDate
      ).substring(0, 10);
      this.examinationStartTime = getStringDateWithTimeFromMilliseconds(
        valuePredefinedDate.startDate
      ).substring(11, 16);
      this.examinationEndTime = getStringDateWithTimeFromMilliseconds(
        valuePredefinedDate.endDate
      ).substring(11, 16);

      console.log(valuePredefinedDate);
    },
    onInputDate(valueDate) {
      this.$emit("input", valueDate);
      this.valueDate = valueDate;
      this.alertDate = false;
      console.log(this.valueDate);
    },
    scheduleConsultationPredefined() {
      var token = parseJwt(localStorage.getItem("JWT-CPIS"));
      var email = token.sub;
      if (
        this.selectedPatient === null ||
        this.valueDate === null ||
        this.examinationStartTime === null ||
        this.examinationEndTime === null ||
        this.pharmacyID === null
      ) {
        if (this.selectedPatient === null) {
          this.alertUser = true;
        }
        if (this.valueDate === null) {
          this.alertDate = true;
        }
        if (this.examinationStartTime === null) {
          this.alertStartTime = true;
        }
        if (this.examinationEndTime === null) {
          this.alertEndTime = true;
        }
        if (this.pharmacyID === null) {
          this.alertPharmacy = true;
        }
      } else {
        this.axios
          .post(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_CONSULTATIONS_SCHEDULE,
            {
              consultantEmail: email,
              startDate:
              this.valueDate + " " + this.examinationStartTime + ":00",
              endDate: this.valueDate + " " + this.examinationEndTime + ":00",
              patientId: this.patientId,
              pharmacyID: this.pharmacyID,
              id: this.consultationId,
              predefinedConsultationID: this.selectpredefinedDate.id,
            },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
              },
            }
          )
          .then((resp) => {
            this.pharmacist = resp.data;
            this.scheduleAlert = false;
            this.scheduleSucces = true;
          })
          .catch((error) => {
            this.errorMessage = error.message;
            console.error("There was an error!", error);
            this.scheduleAlert = true;
            this.scheduleSucces = false;
          });
      }
    },
      scheduleConsultation() {
      var token = parseJwt(localStorage.getItem("JWT-CPIS"));
      var email = token.sub;
      if (
        this.selectedPatient === null ||
        this.valueDate === null ||
        this.examinationStartTime === null ||
        this.examinationEndTime === null ||
        this.pharmacyID === null
      ) {
        if (this.selectedPatient === null) {
          this.alertUser = true;
        }
        if (this.valueDate === null) {
          this.alertDate = true;
        }
        if (this.examinationStartTime === null) {
          this.alertStartTime = true;
        }
        if (this.examinationEndTime === null) {
          this.alertEndTime = true;
        }
        if (this.pharmacyID === null) {
          this.alertPharmacy = true;
        }
      } else {
        this.axios
          .post(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_CONSULTATIONS_SCHEDULE,
            {
              consultantEmail: email,
              startDate:
              this.valueDate + " " + this.examinationStartTime + ":00",
              endDate: this.valueDate + " " + this.examinationEndTime + ":00",
              patientId: this.patientId,
              pharmacyID: this.pharmacyID,
              id: this.consultationId,
            },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
              },
            }
          )
          .then((resp) => {
            this.pharmacist = resp.data;
            this.scheduleAlert = false;
            this.scheduleSucces = true;
          })
          .catch((error) => {
            this.errorMessage = error.message;
            console.error("There was an error!", error);
            this.scheduleAlert = true;
            this.scheduleSucces = false;
          });
      }
    },
    prescribeDrug() {
        this.alertselectDrug = false;
         this.alertdurathiontherapy = false;
       if (this.selecteddrugWithoutAllergies  == null || this.durationOfPerscirbe==null || this.alertIsDrugAvailable == true) {
         if(this.selecteddrugWithoutAllergies  == null){
            this.alertselectDrug = true;
         }
          if(this.durationOfPerscirbe == null){
            this.alertdurathiontherapy = true;
         }
       }else{
             this.alertselectDrug = false;
            this.axios
              .post(
                process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_DRUGRECOMMENDATION_ENDPOINT,
                {
                  patientID: this.patientId,
                  consultationID: this.consultationId,
                  drugCode: this.selecteddrugWithoutAllergies.code,
                  duration: parseInt(this.durationOfPerscirbe),
                  consultationReport: this.report,
                },
                {
                  headers: {
                    Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
                  },
                }
              )
              .then(() => {
                this.succDrugsWithoutAllergies = true;
              });
       }
    },
    checkDrugAvailability() {
      this.alertdurathiontherapy = false;
      if (this.selecteddrugWithoutAllergies != null) {
        this.alertselectDrug = false;
        this.axios
          .post(
            process.env.VUE_APP_BACKEND_URL +
             process.env.VUE_APP_CHECK_DRUG_ENDPOINT,
            {
              patientID: this.patientId,
              consultationID: this.consultationId,
              drugCode: this.selecteddrugWithoutAllergies.code,
            },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
              },
            }
          )
          .then((response) => {
            var isDrugAvailable = response.data.available;

            if (isDrugAvailable) {
              this.alertIsDrugAvailable = false;
              this.succesIsDrugAvailable = true;
            } else {
              this.drugsWithoutAllergies = response.data.alternateDrugsDTO;
              this.alertIsDrugAvailable = true;
              this.succesIsDrugAvailable = false;
              this.alternateDrugTxt = " ALTERNATE DRUG ";
            }
          });
      } else {
        this.alertselectDrug = true;
      }
    },
    showDescription() {
      if (this.valueDrugsWithoutAllergies != null) {
        this.alertselectDrug = false;
        this.drugSpecification =
          "Manufacturer is " +
          this.selecteddrugWithoutAllergies.drugSpecificationDTO.manufacturer +
          ". Contraindications of drug are " +
          this.selecteddrugWithoutAllergies.drugSpecificationDTO
            .contraindications +
          " . Recommended daily dose is " +
          this.selecteddrugWithoutAllergies.drugSpecificationDTO
            .recommendedDailyDose +
          " . Ingredients are " +
          this.selecteddrugWithoutAllergies.drugSpecificationDTO.ingredients;
      } else {
        this.alertselectDrug = true;
      }
    },
    getDrugsWithoutAllergies() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_DRUGS_WITHOUT_ALERGIES,
          { paatientID: this.patientId },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((resp) => {
          this.drugsWithoutAllergies = resp.data;
          this.getPredefinedDates();
        });
    },
    getPredefinedDates() {
      var token = parseJwt(localStorage.getItem("JWT-CPIS"));
      var email = token.sub;

      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CONSULTATIONS_PREDEFINED_EXAMINATIONS_ENDPOINT,
          { email: email, consultationID: this.consultationId },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((resp) => {
          this.predefinedDate = resp.data;
          //Convert dates
          for (let i = 0; i < this.predefinedDate.length; i++) {
            this.predefinedDate[
              i
            ].startDate = getStringDateWithTimeFromMilliseconds(
              this.predefinedDate[i].startDate
            );
          }
                this.questionDialog = false;
            this.reportDialog = true;
        });
    },
    onInputWithoutAllergies(valueDrugsWithoutAllergies) {
      this.$emit("input", valueDrugsWithoutAllergies);
      this.valueDrugsWithoutAllergies = valueDrugsWithoutAllergies;
      this.alertDrugsWithoutAllergies = false;
      console.log(valueDrugsWithoutAllergies);
    },
    addPenaltie() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_PATIENT_ADD_PENALTIES_ENDPOINT,
          { phatientID: this.patientId,
          consultationID: this.consultationId
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then(() => {
          this.$router.go();
        });
    },
    showExaminationDialog(event) {
      this.name = event.event.name;
      this.patientId = event.event.patientId;
      this.consultationId = event.event.id;
      this.pharmacyID = event.event.pharmacyID;
      this.questionDialog = true;
      console.log(event.event);
    },
    getEvents() {
      var token = parseJwt(localStorage.getItem("JWT-CPIS"));
      var email = token.sub;

      this.consultants = [];
      if (this.selectedPharmacy === null) {
        this.axios
          .post(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_CONSULTANTEXAMINATIONS_ENDPOINT,
            { email: email },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
              },
            }
          )
          .then((resp) => {
            this.consultants = resp.data;
            const events = [];

            for (let i = 0; i < this.consultants.length; i++) {
              events.push({
                name:
                  this.consultants[i].patientName +
                  "    " +
                  this.consultants[i].patientSurname +
                  ". Pharmacy: " +
                  this.consultants[i].pharmacyName +
                  " " +
                  " Start: " +
                  getStringDateWithTimeFromMilliseconds(
                    this.consultants[i].startDate
                  ) +
                  ". End: " +
                  getStringDateWithTimeFromMilliseconds(
                    this.consultants[i].endDate
                  ),
                start: this.consultants[i].startDate,
                end: this.consultants[i].endDate,
                color: "primary",
                timed: 1,
                patientId: this.consultants[i].patientId,
                id: this.consultants[i].id,
                pharmacyID: this.consultants[i].pharmacyID,
              });
            }
            this.events = events;
          });
      } else {
        this.axios
          .post(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_CONSULTANTEXAMINATIONS_ENDPOINT,
            { email: email },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
              },
            }
          )
          .then((resp) => {
            this.consultants = resp.data;
            const events = [];

            for (let i = 0; i < this.consultants.length; i++) {
              if (this.consultants[i].pharmacyID === this.selectedPharmacy) {
                events.push({
                  name:
                    this.consultants[i].patientName +
                    "    " +
                    this.consultants[i].patientSurname +
                    ". Pharmacy: " +
                    this.consultants[i].pharmacyName +
                    " " +
                    " Start: " +
                    getStringDateWithTimeFromMilliseconds(
                      this.consultants[i].startDate
                    ) +
                    ". End: " +
                    getStringDateWithTimeFromMilliseconds(
                      this.consultants[i].endDate
                    ),
                  start: this.consultants[i].startDate,
                  end: this.consultants[i].endDate,
                  color: "primary",
                  timed: 1,
                  patientId: this.consultants[i].patientId,
                  id: this.consultants[i].id,
                  pharmacyID: this.consultants[i].pharmacyID,
                });
              }
            }
            this.events = events;
          });
      }
    },
    getEventColor(event) {
      return event.color;
    },
    rnd(a, b) {
      return Math.floor((b - a + 1) * Math.random()) + a;
    },
  },
};

function parseJwt(token) {
  var base64Payload = token.split(".")[1];
  var payload = Buffer.from(base64Payload, "base64");
  return JSON.parse(payload.toString());
}
</script>


