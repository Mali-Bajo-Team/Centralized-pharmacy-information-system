<template>
  <v-container>
    <v-row>
      <v-col>
        <h1 class="ml-n primary--text">Schedule consultation</h1>
        <v-divider></v-divider>
        <!-- SEARCH USER -->
        <div class="mb-6 mt-4">
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
              <v-list-item-content @click="handleSelectItem(item)">
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
        </div>
       <!-- CHOOSE DATE CALENDAR -->
       <v-divider></v-divider>
        <div class="mt-4">
          <h4 class="ml-n primary--text ">Choose date for consultation</h4>
          <v-date-picker
            v-model="picker"
            v-bind:value="valueDate"
            v-on:input="onInputDate"
            color="green lighten-1"
          ></v-date-picker>
        </div>
         <v-divider></v-divider>
         <!-- CHOOSE START TIME -->
        <div  class="mb-8">
          <h3 class="ml-n primary--text">
            Choose starting time for consultation
          </h3>
          <v-time-picker
            v-bind:value="valueStartTime"
            v-on:input="onInputStartTime"
            format="ampm"
          ></v-time-picker>
        </div>
         <v-divider></v-divider>
         <!-- CHOOSE END TIME -->
        <h3 class="ml-n primary--text">Choose ending time for consultation</h3>
        <div>
          <v-time-picker
            v-bind:value="valueEndTime"
            v-on:input="onInputEndTime"
            format="ampm"
          ></v-time-picker>
        </div>
        <div class="mt-14">
        <v-btn depressed @click="scheduleConsultation" color="primary">
          Schedule
        </v-btn>
        </div>
      </v-col>
      <v-col> </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    picker: new Date().toISOString().substr(0, 10),
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
  }),
  methods: {
    handleSelectItem(item) {
      this.selectedPatient = item.id;
    },
    onInputStartTime(valueStartTime) {
      this.$emit("input", valueStartTime);
      this.examinationStartTime = valueStartTime;
    },
    onInputEndTime(valueEndTime) {
      this.$emit("input", valueEndTime);
      this.examinationEndTime = valueEndTime;
    },
    onInputDate(valueDate) {
      this.$emit("input", valueDate);
      this.valueDate = valueDate;
      console.log(valueDate);
    },
    scheduleConsultation() {
      var token = parseJwt(localStorage.getItem("JWT-CPIS"));
      var email = token.sub;

      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            "api/consultations/scheduleconsultation",
          {
            consultantEmail: email,
            startDate: this.valueDate + " " + this.examinationStartTime + ":00",
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
        });
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