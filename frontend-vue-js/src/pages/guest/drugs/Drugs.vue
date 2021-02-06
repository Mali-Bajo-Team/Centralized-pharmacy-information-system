<template>
  <v-container>
    <v-row>
      <!-- Left column for filter & search -->
      <v-col xl="4" sm="12" md="4">
        <v-card>
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2"> Search drugs </v-toolbar-title>
          </v-toolbar>
          <!--End of toolbar of the card-->

          <v-form class="pa-2">
            <v-text-field
              v-model="searchDrugField"
              prepend-icon="mdi-magnify"
              label="Name of the drug"
            ></v-text-field>
          </v-form>
        </v-card>
      </v-col>
      <!-- End of the left column for filter & search -->

      <!-- Right column for drugs preview-->
      <v-col xl="8" sm="12" md="8">
        <v-card
          elevation="4"
          class="pa-4 ml-10 mb-10"
          v-for="drug in filteredDrugs"
          :key="drug.code"
        >
          <!-- Row for title & drug type -->
          <v-row align="center">
            <v-card-title>
              <h2>{{ drug.name }}</h2>
            </v-card-title>
            <v-spacer></v-spacer>
            <v-chip class="mr-10" color="teal" text-color="white" pill>
              <v-icon small left> mdi-pill </v-icon>

              {{ drug.typeOfDrug }}
            </v-chip>
          </v-row>
          <!-- End of the row for title & drug type -->
          <!-- Row for pharmacies and mark -->
          <v-row>
            <!-- Pharmacies where is available this drug -->
            <v-card-actions>
              <v-btn color="orange lighten-2" text> Pharmacies </v-btn>

              <v-spacer></v-spacer>

              <v-btn icon @click="drug.show = !drug.show">
                <v-icon>{{
                  drug.show ? "mdi-chevron-up" : "mdi-chevron-down"
                }}</v-icon>
              </v-btn>
            </v-card-actions>

            <!-- End of the pharmacies where is available this drug -->
            <v-spacer></v-spacer>
            <!-- Mark of drug -->
            <v-rating
              class="mr-10"
              v-model="drug.mark"
              color="accent"
              background-color="orange "
              half-increments
              readonly
            >
            </v-rating>
            <!-- End of mark of the drug -->
            <v-expand-transition>
              <div v-show="drug.show">
                <v-divider></v-divider>

                <v-card-text>
                  I'm a thing. But, like most politicians, he promised more than
                  he could deliver. You won't have time for sleeping, soldier,
                  not with all the bed making you'll be doing. Then we'll go
                  with that data file! Hey, you add a one and two zeros to that
                  or we walk! You're going to do his laundry? I've got to find a
                  way to escape.
                </v-card-text>
              </div>
            </v-expand-transition>
          </v-row>
          <!-- End of the row for type of drug and mark -->
        </v-card>
      </v-col>
      <!-- End of the right colum for drugs preview-->
    </v-row>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    drugs: [],
    searchDrugField: "",
    show: false,
  }),
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_ALL_DRUGS_ENDPOINT
      )
      .then((resp) => {
        this.drugs = resp.data;
        this.drugs = [];
        for(let drug of resp.data){
            let tempDrug = {
                name: drug.name,
                alternateDrugs: drug.alternateDrugs,
                code: drug.code,
                loyaltyPoints: drug.loyaltyPoints,
                mark: drug.mark,
                typeOfDrug: drug.typeOfDrug,
                show: false
            };
            this.drugs.push(tempDrug);
        }

      });
  },
  methods: {
    isMatchedDrug(drug) {
      if (!drug.name.toLowerCase().match(this.searchDrugField.toLowerCase()))
        return false;

      return true;
    },
  },
  computed: {
    filteredDrugs: function () {
      return this.drugs.filter((drug) => {
        return this.isMatchedDrug(drug);
      });
    },
  },
};
</script>