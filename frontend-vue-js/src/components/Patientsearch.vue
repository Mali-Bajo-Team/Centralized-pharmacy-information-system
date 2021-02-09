<template>
  <v-app>
    <v-row>
      <v-col md="2"> </v-col>
      <v-col>
        <h1 class="ml-n primary--text">Search patients</h1>
        <v-container fluid>
          <v-data-iterator
            :items="items"
            :items-per-page.sync="itemsPerPage"
            :page.sync="page"
            :search="search"
            :sort-by="sortBy.toLowerCase()"
            :sort-desc="sortDesc"
            hide-default-footer
          >
            <template v-slot:header>
              <v-toolbar dark  class="mb-1 primary">
                <v-text-field
                  v-model="search"
                  clearable
                  flat
                  solo-inverted
                  hide-details
                  prepend-inner-icon="mdi-magnify"
                  label="Search by name and surname"
                ></v-text-field>
                <template v-if="$vuetify.breakpoint.mdAndUp">
                  <v-spacer></v-spacer>
                  <v-spacer></v-spacer>
                  <v-btn-toggle v-model="sortDesc" mandatory>
                    <v-btn large depressed color="primary" :value="false">
                      <v-icon>mdi-arrow-up</v-icon>
                    </v-btn>
                    <v-btn large depressed color="primary" :value="true">
                      <v-icon>mdi-arrow-down</v-icon>
                    </v-btn>
                  </v-btn-toggle>
                </template>
              </v-toolbar>
            </template>

            <template v-slot:default="props">
              <v-row>
                <v-col
                  v-for="item in props.items"
                  :key="item.name"
                  cols="12"
                  sm="6"
                  md="4"
                  lg="3"
                >
                  <v-card>
                    <v-btn
                      color="primary"
                      @click="showExaminationDialog(item)"
                      class="ml-3 mt-5 elevation-10"
                    >
                      REPORT
                    </v-btn>
                    <v-card-title class="subheading font-weight-bold">
                      {{ item.id + ". " + item.name + " " + item.surname }}
                    </v-card-title>

                    <v-divider></v-divider>

                    <v-list dense>
                      <v-list-item
                        v-for="(key, index) in filteredKeys"
                        :key="index"
                      >
                        <v-list-item-content
                          :class="{
                            'blue--text': sortBy === key,
                          }"
                        >
                          {{ key }}:
                        </v-list-item-content>
                        <v-list-item-content
                          class="align-end"
                          :class="{
                            'blue--text': sortBy === key,
                          }"
                        >
                          {{ item[key.toLowerCase()] }}
                        </v-list-item-content>
                      </v-list-item>
                    </v-list>
                  </v-card>
                </v-col>
              </v-row>
            </template>

            <template v-slot:footer>
              <v-row class="mt-2" align="center" justify="center">
                <span class="grey--text">Items per page</span>
                <v-menu offset-y>
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn
                      dark
                      text
                      color="primary"
                      class="ml-2"
                      v-bind="attrs"
                      v-on="on"
                    >
                      {{ itemsPerPage }}
                      <v-icon>mdi-chevron-down</v-icon>
                    </v-btn>
                  </template>
                  <v-list>
                    <v-list-item
                      v-for="(number, index) in itemsPerPageArray"
                      :key="index"
                      @click="updateItemsPerPage(number)"
                    >
                      <v-list-item-title>{{ number }}</v-list-item-title>
                    </v-list-item>
                  </v-list>
                </v-menu>

                <v-spacer></v-spacer>

                <span class="mr-4 grey--text">
                  Page {{ page }} of {{ numberOfPages }}
                </span>
                <v-btn
                  fab
                  dark
                  color=" primary"
                  class="mr-1"
                  @click="formerPage"
                >
                  <v-icon>mdi-chevron-left</v-icon>
                </v-btn>
                <v-btn
                  fab
                  dark
                  color="primary"
                  class="ml-1"
                  @click="nextPage"
                >
                  <v-icon>mdi-chevron-right</v-icon>
                </v-btn>
              </v-row>
            </template>
          </v-data-iterator>
        </v-container>
      </v-col>

      <v-col md="2"> </v-col>
    </v-row>

    <!-- Start QUESTION DIALOG -->
    <v-dialog v-model="questionDialog" persistent max-width="600px">
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
          <v-btn color="primary" text @click="questionDialog = false">
            The patient did not show up
          </v-btn>
          <v-btn
            color="primary"
            text
            @click="
              questionDialog = false;
              reportDialog = true;
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
                <v-card color="grey lighten-1" class="mb-12" height="200px">
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
                Prescribe medicine
              </v-stepper-step>

              <v-stepper-content step="2">
                <v-card
                  color="grey lighten-1"
                  class="mb-12"
                  height="200px"
                ></v-card>
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

              <v-stepper-step :complete="e6 > 3" step="3">
                Schedule an additional examination
              </v-stepper-step>

              <v-stepper-content step="3">
                <v-card
                  color="grey lighten-1"
                  class="mb-12"
                  height="200px"
                ></v-card>
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

              <v-stepper-step step="4"> Submit </v-stepper-step>
              <v-stepper-content step="4">
                <v-card
                  color="grey lighten-1"
                  class="mb-12"
                  height="200px"
                ></v-card>
                <v-btn
                  color="primary"
                  @click="
                    e6 = 1;
                    reportDialog = false;
                  "
                >
                  Submit
                </v-btn>
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
            </v-stepper>
          </v-container>
        </v-card-text>
      </v-card>
    </v-dialog>
    <!-- End REPORT DIALOG-->
  </v-app>
</template>

<script>
export default {
  data() {
    return {
      show3: false,
      patients: [],
      itemsPerPageArray: [4, 8, 12, 16, 20],
      search: "",
      filter: {},
      sortDesc: false,
      page: 1,
      itemsPerPage: 12,
      sortBy: "name",
      keys: ["Name"],
      items: [],
      e6: 1,
      report: "",
      name: "",
      questionDialog: false,
      reportDialog: false,
    };
  },
  created() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_ALL_PATIENTS_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        this.items = resp.data;
      });
  },
  computed: {
    numberOfPages() {
      return Math.ceil(this.items.length / this.itemsPerPage);
    },
    filteredKeys() {
      return this.keys.filter((key) => key !== "Name");
    },
  },
  methods: {
    showExaminationDialog(item) {
      this.name = item.name + " " + item.surname;
      this.questionDialog = true;
      console.log(item.name);
    },
    nextPage() {
      if (this.page + 1 <= this.numberOfPages) this.page += 1;
    },
    formerPage() {
      if (this.page - 1 >= 1) this.page -= 1;
    },
    updateItemsPerPage(number) {
      this.itemsPerPage = number;
    },
  },
};
</script>

<style scoped>
</style>