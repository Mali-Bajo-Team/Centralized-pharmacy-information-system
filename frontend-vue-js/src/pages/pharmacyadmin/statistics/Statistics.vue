<template>
  <v-row>
    <v-col>
      <v-card>
        <v-card-title>Profit report</v-card-title>
        <v-sparkline :value="profitValue" :labels="profitLabels"></v-sparkline>
      </v-card>
    </v-col>
    <v-col>
      <v-card>
        <v-card-title>Drug use report</v-card-title>Month:
        <v-sparkline :value="drugValueMonth" :labels="drugLabelsMonth"></v-sparkline>Year:
        <v-sparkline :value="drugValueYear" :labels="drugLabelsYear"></v-sparkline>Quartal:
        <v-sparkline :value="drugValueQuarter" :labels="drugLabelsQuarter"></v-sparkline>
      </v-card>
    </v-col>
    <v-col>
      <v-card>
        <v-card-title>Consultation report</v-card-title>Month:
        <v-sparkline :value="consValueMonth" :labels="consLabelsMonth"></v-sparkline>Year:
        <v-sparkline :value="consValueYear" :labels="consLabelsYear"></v-sparkline>Quartal:
        <v-sparkline :value="consValueQuarter" :labels="consLabelsQuarter"></v-sparkline>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
export default {
  data() {
    return {
      profitValue: [],
      profitLabels: [],
      consValueMonth: [],
      consLabelsMonth: [],
      consValueYear: [],
      consLabelsYear: [],
      consValueQuarter: [],
      consLabelsQuarter: [],
      drugValueMonth: [],
      drugLabelsMonth: [],
      drugValueYear: [],
      drugLabelsYear: [],
      drugValueQuarter: [],
      drugLabelsQuarter: []
    };
  },
  mounted() {
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL + "api/reports/profit",
        {
          end: new Date(),
          start: new Date(new Date().getTime() - 10 * 24 * 60 * 60 * 1000)
        },
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
          }
        }
      )
      .then(response => {
        this.profitValue = response.data.values;
        this.profitLabels = response.data.labels;
      })
      .catch(() => {});
    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL + "api/reports/druguse",
        {
          end: new Date().getFullYear(),
          start: new Date().getFullYear() - 1
        },
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
          }
        }
      )
      .then(response => {
        this.drugValueMonth = response.data.monthValues;
        this.drugLabelsMonth = response.data.monthLabels;
        this.drugValueYear = response.data.yearValues;
        this.drugLabelsYear = response.data.yearLabels;
        this.drugValueQuarter = response.data.quartalValues;
        this.drugLabelsQuarter = response.data.quartalLabels;
      })
      .catch(() => {});
      this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL + "api/reports/consults",
        {
          end: new Date().getFullYear(),
          start: new Date().getFullYear() - 1
        },
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
          }
        }
      )
      .then(response => {
        this.consValueMonth = response.data.monthValues;
        this.consLabelsMonth = response.data.monthLabels;
        this.consValueYear = response.data.yearValues;
        this.consLabelsYear = response.data.yearLabels;
        this.consValueQuarter = response.data.quartalValues;
        this.consLabelsQuarter = response.data.quartalLabels;
      })
      .catch(() => {});
  },
  methods: {}
};
</script>