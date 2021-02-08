<template>
  <v-container>
    <v-row>
      <v-col>
        <v-card
          elevation="4"
          class="pa-4 ml-16 mr-16 mb-10"
          v-for="complaint in allComplaints"
          :key="complaint.id"
        >
          <!-- Row for title & make response dialog-->
          <v-row align="center">
            <v-card-title>
              <h4 class="ml-3">
                Complaint from {{ complaint.creator.name }}
                {{ complaint.creator.surname }}
              </h4>
            </v-card-title>
            <v-spacer></v-spacer>

            <!-- Make a response dialog -->
            <v-dialog
              v-model="complaint.showMakeResponseDialog"
              width="500"
              :retain-focus="false"
            >
              <template #activator="{ on: dialog }">
                <v-tooltip bottom>
                  <template #activator="{ on: tooltip }">
                    <v-btn
                      v-on="{ ...tooltip, ...dialog }"
                      elevation="0"
                      left
                      class="mr-6"
                      fab
                      dark
                      x-small
                      color="primary"
                    >
                      <v-icon dark> mdi-forum </v-icon>
                    </v-btn>
                  </template>
                  <span> Make response </span>
                </v-tooltip>
              </template>
              <v-card>
                <!--Toolbar of the card-->
                <v-toolbar color="primary" dark dense flat>
                  <v-toolbar-title class="body-2">
                    <h3>Make response</h3>
                  </v-toolbar-title>
                </v-toolbar>
                <!-- End of the toolbar of the card -->
                <!-- Form for making a response -->
                <v-form class="ma-5">
                  <v-textarea
                    outlined
                    v-model="complaint.response"
                    label="Response"
                  ></v-textarea>
                </v-form>
                <!-- End of the form for making a response -->
                <v-card-actions class="pb-4">
                  <v-spacer> </v-spacer>
                  <v-btn
                    color="success"
                    dark
                    depressed
                    @click="confirmResponse(complaint)"
                  >
                    <v-icon dark left> mdi-checkbox-marked-circle </v-icon>
                    Confirm
                  </v-btn>
                  <v-btn
                    color="blue"
                    dark
                    depressed
                    @click="
                      complaint.showMakeResponseDialog = !complaint.showMakeResponseDialog
                    "
                    ><v-icon dark left> mdi-minus-circle </v-icon>
                    Close
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
            <!-- End of the make response dialog -->
          </v-row>
          <!-- End of the row for title & make response dialog-->
          <!-- Row for complaint details -->
          <v-row>
            <v-card-actions>
              <v-btn class="ml-3" color="orange lighten-2" text>
                Details
              </v-btn>

              <v-spacer></v-spacer>

              <v-btn
                icon
                @click="
                  complaint.showExtensionDetails = !complaint.showExtensionDetails
                "
              >
                <v-icon>{{
                  complaint.showExtensionDetails
                    ? "mdi-chevron-up"
                    : "mdi-chevron-down"
                }}</v-icon>
              </v-btn>
            </v-card-actions>
            <v-expand-transition>
              <div v-show="complaint.showExtensionDetails">
                <v-divider></v-divider>
                <br />
                <v-card class="pl-2 mr-4 mb-5 ml-8" elevation="4">
                  <v-card-text>
                    I have complaint on 
                  </v-card-text>
                </v-card>
              </div>
            </v-expand-transition>
          </v-row>
          <!-- End of the row for complaint details -->
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { getStringDateFromMilliseconds } from "./../../../util/dateHandler";

export default {
  data: () => ({
    allComplaints: [],
  }),
  methods: {
    convertMsToString(ms) {
      return getStringDateFromMilliseconds(ms);
    },
    confirmResponse(complaint) {
      alert(complaint);
    },
  },
  mounted() {
    this.axios

      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_COMPLAINTS_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        this.allComplaints = [];
        let counter = 1;
        for (let complaint of resp.data) {
          let tempObj = {
            id: counter,
            showMakeResponseDialog: false,
            showExtensionDetails: false,
            content: complaint.content,
            response: complaint.response,
            creationTimestamp: complaint.creationTimestamp,
            creator: {
              id: complaint.creator.id,
              name: complaint.creator.name,
              surname: complaint.creator.surname,
              alergies: complaint.creator.alergies,
            },
            
          };

        if(complaint.pharmacy != null){
            tempObj.pharmacy={
              id: complaint.pharmacy.id,
              name: complaint.pharmacy.name,
              location: complaint.pharmacy.location,
              rating: complaint.pharmacy.rating,
            }
        }
         if(complaint.consultant != null){
            tempObj.consultant= {
              email: complaint.consultant.email,
              name: complaint.consultant.name,
              lastName: complaint.consultant.lastName,
              phoneNumber: complaint.consultant.phoneNumber,
              location: complaint.consultant.location,
              city: complaint.consultant.city,
              country: complaint.consultant.country,
            }
        }

          this.allComplaints.push(tempObj);
          counter = counter + 1;
        }
      });
  },
};
</script>