<template>
  <v-app>
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
  data: () => ({
    e6: 1,
    report: "",
    dragEvent: null,

    name: "",
    patientId: null,
    questionDialog: false,
    reportDialog: false,
    consultants: [],
    response: null,
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
    showExaminationDialog(event) {
      this.name = event.event.name;
      this.patientId = event.event.patientId;
      this.questionDialog = true;
      console.log(event);
    },
    getEvents() {
      var token = parseJwt(localStorage.getItem("JWT-CPIS"));
      var email = token.sub;

      this.consultants = [];
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
                " " +
                this.consultants[i].patientSurname,
              start: this.consultants[i].startDate,
              end: this.consultants[i].endDate,
              color: "primary",
              timed: 1,
              patientId: this.consultants[i].patientId,
            });
          }
          this.events = events;
        });
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


