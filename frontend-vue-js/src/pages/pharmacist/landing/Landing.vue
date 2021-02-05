<template>
  <v-container>
    <h3>Pharmacist {{selectedPatient}}</h3>
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
            <strong>patients</strong>
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
          <span  v-text="item.name"></span>
        </v-chip>
      </template>
      <template v-slot:item="{ item }">
        <v-list-item-avatar
          color="primary"
          class="headline font-weight-light white--text"
        >
          {{ item.name.charAt(0) }}
        </v-list-item-avatar>
        <v-list-item-content  @click="handleSelectItem(item)">
          <v-list-item-title v-text="item.name"></v-list-item-title>
          <v-list-item-subtitle v-text="item.symbol"></v-list-item-subtitle>
        </v-list-item-content>
        <v-list-item-action>
          <v-icon>mdi-account</v-icon>
        </v-list-item-action>
      </template>
    </v-autocomplete>


    <v-date-picker
      v-model="picker"
      v-bind:value="valueDate"
      v-on:input="onInputDate"
      color="green lighten-1"
    ></v-date-picker>
    <v-time-picker
      v-bind:value="value"
      v-on:input="onInput"
      format="ampm"
    ></v-time-picker>
    <v-btn depressed @click="scheduleConsultation" color="primary">
      Schedule
    </v-btn>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    picker: new Date().toISOString().substr(0, 10),
    examinationTime: null,
    value: null,
    valueDate: null,
    isLoading: false,
    items: [],
    model: null,
    search: null,
    tab: null,
    selectedPatient: null
  }),
  methods: {
       handleSelectItem(item){
       this.selectedPatient = item.id;
     },
    onInput(value) {
      this.$emit("input", value);
      this.examinationTime = value;
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
            startDate: this.valueDate + " " + this.examinationTime + ":00",
            patientId: this.selectedPatient
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