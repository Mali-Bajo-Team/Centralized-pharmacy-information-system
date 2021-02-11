<template>
  <v-container>
    <v-row>
      <!--Left column-->
      <v-col xl="4" sm="12" md="4">
        <v-card mt="5">
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2">
              Sort my examinations
            </v-toolbar-title>
          </v-toolbar>
        </v-card>
      </v-col>
      <!--End of left column-->

      <!--Right column-->
      <v-col xl="8" sm="12" md="8">
        <v-card>
            <v-card-subtitle>
              <h3 class="ml-3">
                Name of pharmacy,
                Name of dermatologist
                Surname of dermatologist ,
                <!--{{ convertMsToString(consultation.startDate) }}-->
              </h3>
            </v-card-subtitle>
            <v-spacer></v-spacer>
        </v-card>
        
      </v-col>
      <!--End of right column-->
    </v-row>
  </v-container>
</template>