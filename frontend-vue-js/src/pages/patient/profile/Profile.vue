<template >
  <v-card width="600px" class="mx-auto">
    <v-card-title> Ana Maksimovic </v-card-title>
    <v-row>
      <v-col xl="8" md="8" sm="12">
        <v-card-text>
          <!--Category-->
          <v-chip color="primary"> kategorija </v-chip>
          <br /><br />
          <!--Username-->
          <v-icon color="primary">mdi-account</v-icon>
          anaperisic@gmail.com
          <br /><br />

          <!--Adress-->
          <v-icon color="primary"> mdi-map-marker </v-icon>
          Dr Sime Milosevica 10
          <br /><br />

          <!--Phone nubmer-->
          <v-icon color="primary"> mdi-phone-classic </v-icon>
          050220033

          <br /><br />
          <v-divider></v-divider>
          <br />

          <!--Points-->
          <v-icon color="primary">mdi-star</v-icon>
          15
          <br /><br />

          <!--Category-->
          <v-icon color="primary">mdi-check</v-icon>
        </v-card-text>
      </v-col>

      <v-col xl="4" md="4" sm="12">
        <v-dialog  width="500">
          <template v-slot:activator="{ on, attrs }">
            <v-btn fab dark large color="primary" v-bind="attrs" v-on="on">
              <v-icon dark> mdi-pencil </v-icon>
            </v-btn>
          </template>

          <v-card>
            <v-toolbar color="primary" dark dense flat>
              <v-toolbar-title class="body-2">
                Change your personal information
              </v-toolbar-title>
            </v-toolbar>
            <v-card-text>
              <v-form>
                <v-text-field label="Change your name"></v-text-field>
                <v-text-field label="Change your surname"></v-text-field>
                <v-text-field label="Change your address"></v-text-field>
                <v-text-field label="Change your phone number"></v-text-field>
                <v-text-field label="Add drugs which cause you allergic reactions"></v-text-field>
              </v-form>
            </v-card-text>

            <v-divider></v-divider>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="primary" text @click="dialog = false">
                Save changes
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>
  </v-card>
</template>