<template>
  <v-container>
    <v-row>
      <v-col>
        <v-row>
          <!-- SEARCH EXAMINITED USER -->
          <div class="mb-16">
               <h1 class="ml-n primary--text">Examinited patients</h1>
            <v-card>
              <v-card-title>
                <v-text-field
                  v-model="searchExaminitedUsers"
                  append-icon="mdi-magnify"
                  label="Search"
                  single-line
                  hide-details
                ></v-text-field>
              </v-card-title>
              <v-data-table
                :headers="headers"
                :items="desserts"
                :search="search"
              ></v-data-table>
            </v-card>
          </div>
        </v-row>
       
        <v-row>
          <h1 class="ml-n primary--text">Schedule consultation</h1>
          <!-- SEARCH USER -->
          <div class="ml-4 mb-6 mt-15">
            <h4 class="ml-n primary--text">Choose user for consultation</h4>
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
          </div>
        </v-row>
        <v-divider></v-divider>
        <v-row>
          <!-- CHOOSE DATE CALENDAR -->
          <div class="mr-10 mt-6">
            <h4 class="ml-n primary--text">Choose date for consultation</h4>
            <v-date-picker
              v-model="picker"
              v-bind:value="valueDate"
              v-on:input="onInputDate"
              color="green lighten-1"
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
              Choose starting time for consultation
            </h4>
            <v-time-picker
              v-bind:value="valueStartTime"
              v-on:input="onInputStartTime"
              format="ampm"
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
              Choose ending time for consultation
            </h4>
            <v-time-picker
              v-bind:value="valueEndTime"
              v-on:input="onInputEndTime"
              format="ampm"
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
    alertStartTime: false,
    alertEndTime: false,
    examinationStartTime: null,
    examinationEndTime: null,
    valueStartTime: null,
    valueEndTime: null,
    valueDate: null,
    isLoading: false,
    items: [],
    model: null,
    search: null,
    tab: null,
    selectedPatient: null,
    scheduleAlert: false,
    scheduleSucces: false,
    searchExaminitedUsers: "",
    headers: [
      {
        text: "Dessert (100g serving)",
        align: "start",
        filterable: false,
        value: "name",
      },
      { text: "Calories", value: "calories" },
      { text: "Fat (g)", value: "fat" },
      { text: "Carbs (g)", value: "carbs" },
      { text: "Protein (g)", value: "protein" },
      { text: "Iron (%)", value: "iron" },
    ],
    desserts: [
      {
        name: "Frozen Yogurt",
        calories: 159,
        fat: 6.0,
        carbs: 24,
        protein: 4.0,
        iron: "1%",
      },
      {
        name: "Ice cream sandwich",
        calories: 237,
        fat: 9.0,
        carbs: 37,
        protein: 4.3,
        iron: "1%",
      },
      {
        name: "Eclair",
        calories: 262,
        fat: 16.0,
        carbs: 23,
        protein: 6.0,
        iron: "7%",
      },
      {
        name: "Cupcake",
        calories: 305,
        fat: 3.7,
        carbs: 67,
        protein: 4.3,
        iron: "8%",
      },
      {
        name: "Gingerbread",
        calories: 356,
        fat: 16.0,
        carbs: 49,
        protein: 3.9,
        iron: "16%",
      },
      {
        name: "Jelly bean",
        calories: 375,
        fat: 0.0,
        carbs: 94,
        protein: 0.0,
        iron: "0%",
      },
      {
        name: "Lollipop",
        calories: 392,
        fat: 0.2,
        carbs: 98,
        protein: 0,
        iron: "2%",
      },
      {
        name: "Honeycomb",
        calories: 408,
        fat: 3.2,
        carbs: 87,
        protein: 6.5,
        iron: "45%",
      },
      {
        name: "Donut",
        calories: 452,
        fat: 25.0,
        carbs: 51,
        protein: 4.9,
        iron: "22%",
      },
      {
        name: "KitKat",
        calories: 518,
        fat: 26.0,
        carbs: 65,
        protein: 7,
        iron: "6%",
      },
    ],
  }),
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
    scheduleConsultation() {
      var token = parseJwt(localStorage.getItem("JWT-CPIS"));
      var email = token.sub;
      if (
        this.selectedPatient === null ||
        this.valueDate === null ||
        this.examinationStartTime === null ||
        this.examinationEndTime === null
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
      } else {
        this.axios
          .post(
            process.env.VUE_APP_BACKEND_URL +
              "api/consultations/scheduleconsultation",
            {
              consultantEmail: email,
              startDate:
                this.valueDate + " " + this.examinationStartTime + ":00",
              endDate: this.valueDate + " " + this.examinationEndTime + ":00",
              patientId: this.selectedPatient,
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
      fetch("http://localhost:8081/api/users/patients", {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
        },
      })
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