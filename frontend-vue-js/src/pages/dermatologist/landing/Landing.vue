<template>
  <v-container>
    <v-row>
      <v-col>
        <v-row>
          <!-- SEARCH EXAMINITED USER -->
          <div class="mb-16">
            <h1 class="ml-n primary--text">Examinited patients</h1>
            <v-card>
              <v-data-table
                :headers="headers"
                :items="examinitedPatients"
                :search="search"
              ></v-data-table>
            </v-card>
          </div>
        </v-row>
        <v-divider></v-divider>
        <v-row>
          <h1 class="ml-n primary--text mt-4">Schedule examination</h1>
          <!-- SEARCH USER -->
          <div class="ml-4 mb-6 mt-16">
            <h4 class="ml-n primary--text">Choose patient for examination</h4>
            <v-autocomplete
              v-model="model"
              :items="items"
              :loading="isLoading"
              :search-input.sync="search"
              chips
              clearable
              hide-details
              hide-selected
              item-text="name"
              item-value="symbol"
              label="Search for a patient..."
              solo
            >
              <template v-slot:no-data>
                <v-list-item>
                  <v-list-item-title>
                    Search and choose
                    <strong>patient</strong>
                  </v-list-item-title>
                </v-list-item>
              </template>
              <template v-slot:selection="{ attr, on, item, selected }">
                <v-chip
                  v-bind="attr"
                  :input-value="selected"
                  color="blue-grey"
                  class="white--text"
                  v-on="on"
                >
                  <v-icon left> mdi-account </v-icon>
                  <span v-text="item.name"></span>
                </v-chip>
              </template>
              <template v-slot:item="{ item }">
                <v-list-item-avatar
                  color="primary"
                  class="headline font-weight-light white--text"
                >
                  {{ item.name.charAt(0) }}
                </v-list-item-avatar>
                <v-list-item-content
                  @click="handleSelectItem(item), (alertUser = false)"
                >
                  <v-list-item-title
                    v-text="item.name + ' ' + item.surname"
                  ></v-list-item-title>
                  <v-list-item-subtitle
                    v-text="item.symbol"
                  ></v-list-item-subtitle>
                </v-list-item-content>
                <v-list-item-action>
                  <v-icon>mdi-account</v-icon>
                </v-list-item-action>
              </template>
            </v-autocomplete>
            <v-alert
              :value="alertUser"
              color="pink"
              dark
              border="top"
              icon="mdi-account"
              transition="scale-transition"
            >
              You must choose user!
            </v-alert>
            <!-- CHOOSE PHARRMACY -->
            <h4 class="mt-10 ml-n primary--text">
              Choose pharmacy for examination
            </h4>
            <v-select
              v-model="pharmaciesForDermatologist"
              :items="pharmaciesForDermatologist"
              item-text="name"
              item-value="location"
              label="Select pharmacy"
              persistent-hint
              return-object
              outlined
              single-line
              v-bind:value="valuePharmacy"
              v-on:input="onInputPharmacy"
              @click = "alertPharmacy = false"
            ></v-select>
             <v-alert
              :value="alertPharmacy"
              color="pink"
              dark
              border="top"
              icon="mdi-account"
              transition="scale-transition"
            >
              You must choose pharmacy!
            </v-alert>
          </div>
        </v-row>
        <v-row>
          <!-- CHOOSE DATE CALENDAR -->
          <div class="mr-10 mt-6">
            <h4 class="ml-n primary--text">Choose date for examination</h4>
            <v-date-picker
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

          <!-- CHOOSE START TIME -->
          <div class="mr-10 mb-8 mt-6">
            <h4 class="ml-n primary--text">
              Choose starting time for examination
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
          <!-- CHOOSE END TIME -->
          <div>
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
          </div>
          <div class="mt-14"></div>
        </v-row>
        <v-row>
          <v-btn depressed @click="scheduleConsultation" color="primary">
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
            The schedule must match the working hours of the pharmacist. The
            appointment should not be prepared with another examination or
            consultation that the patient has scheduled (in any pharmacy). And
            also it is not possible to schedule consultations in the past.
          </v-alert>
        </v-row>

        <!-- SEND VACATION REQUEST -->
        <v-row>
          <div class="mt-5">
            <v-divider></v-divider>
            <h1 class="ml-n primary--text">Send vacation request</h1>
            <h4 class="ml-n mt-6 primary--text">Pick vacation date range</h4>
            <v-date-picker
              v-model="vacationRequestDateRange"
              range
            ></v-date-picker>
            <v-alert
              :value="vacatonrequestAlert"
              color="pink"
              dark
              border="top"
              icon="mdi-account"
              transition="scale-transition"
            >
              You must pick date range for your vacation request
            </v-alert>
            <v-alert
              :value="vacatonrequestSuccess"
              color="green"
              dark
              border="top"
              icon="mdi-account"
              transition="scale-transition"
            >
              Successfully sent
            </v-alert>
            <v-text-field
              v-model="dateRangeText"
              label="Vacation date range"
              prepend-icon="mdi-calendar"
              readonly
            ></v-text-field>
          </div>
        </v-row>
        <v-row>
          <v-btn depressed @click="sendVacationRequest" color="primary">
            Send
          </v-btn>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    picker: new Date().toISOString().substr(0, 10),
    alertUser: false,
    alertDate: false,
    vacatonrequestAlert: false,
    vacatonrequestSuccess: false,
    alertStartTime: false,
    alertEndTime: false,
    examinationStartTime: null,
    examinationEndTime: null,
    valueStartTime: null,
    valueEndTime: null,
    valueDate: null,
    valuePharmacy: null,
    isLoading: false,
    items: [],
    model: null,
    search: null,
    tab: null,
    selectedPatient: null,
    scheduleAlert: false,
    scheduleSucces: false,
    searchExaminitedUsers: "",
    examinitedPatients: [],
    vacationRequestDateRange: [],
    pharmaciesForDermatologist: [],
    alertPharmacy: false,
    headers: [
      {
        text: "Patient name",
        align: "start",
        filterable: false,
        value: "name",
      },
      { text: "Patient surname", value: "surname" },
      { text: "Examinited date", value: "examinitedDate" },
    ],
  }),
  computed: {
    dateRangeText() {
      return this.vacationRequestDateRange.join(" ~ ");
    },
  },
  created() {
    var token = parseJwt(localStorage.getItem("JWT-CPIS"));
    var email = token.sub;

    this.consultants = [];
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_CONSULTANT_EXAMPAT_ENDPOINT,
        { consultantEmail: email },
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        this.examinitedPatients = resp.data;

        this.examinitedPatients.forEach(function (entry) {
          entry.examinitedDate = new Date(entry.examinitedDate)
            .toISOString()
            .substring(0, 10);
        });
      });

    //Get pharmacies for phatient
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL + "api/pharmacies/allfordermatologist",
        { dermatologistEmail: email },
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        this.pharmaciesForDermatologist = resp.data;
      });
  },
  methods: {
    handleSelectItem(item) {
      this.selectedPatient = item.id;
    },
    onInputStartTime(valueStartTime) {
      this.$emit("input", valueStartTime);
      this.examinationStartTime = valueStartTime;
      this.alertStartTime = false;
    },
    onInputEndTime(valueEndTime) {
      this.$emit("input", valueEndTime);
      this.examinationEndTime = valueEndTime;
      this.alertEndTime = false;
    },
    onInputDate(valueDate) {
      this.$emit("input", valueDate);
      this.valueDate = valueDate;
      this.alertDate = false;
      console.log(valueDate);
    },
    onInputPharmacy(valuePharmacy) {
      this.$emit("input", valuePharmacy);
      this.valuePharmacy = valuePharmacy;
      this.alertDate = false;
      console.log(valuePharmacy);
    },
    scheduleConsultation() {
      var token = parseJwt(localStorage.getItem("JWT-CPIS"));
      var email = token.sub;
      if (
        this.selectedPatient === null ||
        this.valueDate === null ||
        this.examinationStartTime === null ||
        this.examinationEndTime === null || this.valuePharmacy === null
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
        if (this.valuePharmacy === null) {
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
              patientId: this.selectedPatient,
              pharmacyID: this.valuePharmacy.id,
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
    sendVacationRequest() {
      var token = parseJwt(localStorage.getItem("JWT-CPIS"));
      var email = token.sub;

      if (
        Date.parse(this.vacationRequestDateRange[1]) <
        Date.parse(this.vacationRequestDateRange[0])
      ) {
        let temp = this.vacationRequestDateRange[1];
        this.vacationRequestDateRange[1] = this.vacationRequestDateRange[0];
        this.vacationRequestDateRange[0] = temp;
      }

      if (this.vacationRequestDateRange === null) {
        this.vacatonrequestAlert = true;
      } else {
        this.axios
          .post(
            process.env.VUE_APP_BACKEND_URL +
              process.env.VUE_APP_VACATIONREQUEST_CREATE_ENDPOINT,
            {
              consultantEmail: email,
              startVacationReqDate:
                this.vacationRequestDateRange[0] + " 12:00:00",
              endVacatonReqDate: this.vacationRequestDateRange[1] + " 12:00:00",
            },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
              },
            }
          )
          .then((resp) => {
            this.pharmacist = resp.data;
            this.vacatonrequestAlert = false;
            this.vacatonrequestSuccess = true;
          })
          .catch((error) => {
            this.errorMessage = error.message;
            console.error("There was an error!", error);
            this.vacatonrequestAlert = true;
            this.vacatonrequestSuccess = false;
          });
      }
    },
  },
  watch: {
    model(val) {
      if (val != null) this.tab = 0;
      else this.tab = null;
    },
    search(val) {
      // Items have already been loaded
      console.log(val);
      if (this.items.length > 0) return;
      this.isLoading = true;

      // Lazily load input items
      fetch(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_ALL_PATIENTS_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
        .then((res) => res.clone().json())
        .then((res) => {
          console.log(res);
          this.items = res;
        })
        .catch((err) => {
          console.log(err);
        })
        .finally(() => (this.isLoading = false));
    },
  },
};

function parseJwt(token) {
  var base64Payload = token.split(".")[1];
  var payload = Buffer.from(base64Payload, "base64");
  return JSON.parse(payload.toString());
}
</script>

<style scoped>
</style>