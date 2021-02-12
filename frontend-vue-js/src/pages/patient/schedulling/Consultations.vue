<template>
  <v-container>
    <v-row>
      <!--Left column-->
      <v-col xl="4" md="4" sm="12">
        <v-card>
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2"> Insert date and time of schedulling </v-toolbar-title>
          </v-toolbar>
          <!--End of toolbar of the card-->
          <v-card-text>
            <v-form >
              <v-text-field type="date" label="Insert date of reservation" v-model="dateBind">
              </v-text-field>
              <br />
              <v-menu
                ref="menu"
                v-model="menu2"
                :close-on-content-click="false"
                :nudge-right="40"
                :return-value.sync="time"
                transition="scale-transition"
                offset-y
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-text-field
                    v-model="time"
                    label="Picker in menu"
                    prepend-icon="mdi-clock-time-four-outline"
                    readonly
                    v-bind="attrs"
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-time-picker
                  v-if="menu2"
                  v-model="time"
                  full-width
                  @click:minute="$refs.menu.save(time)"
                ></v-time-picker>
              </v-menu>

              <v-btn
                width="110px"
                color="primary"
                class="ma-2 white--text mr-6"
              >
                CHOOSE
                <v-icon small right dark> mdi-checkbox-marked-circle </v-icon>
              </v-btn>
            </v-form>
          </v-card-text>
        </v-card>
        <br><br>
        <v-card mt="5">
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2">
              Sort pharmacies
            </v-toolbar-title>
          </v-toolbar>
          <br />
          <!-- Sort by price -->
          <v-row justify="center" class="pa-3">
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text mr-6"
              
            >
              Price
              <v-icon small right dark> mdi-arrow-up-bold </v-icon>
            </v-btn>
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text ml-6"
              
            >
              Price
              <v-icon small right dark> mdi-arrow-down-bold </v-icon>
            </v-btn>
            <br>
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text mr-6"
              
            >
              Rating
              <v-icon small right dark> mdi-arrow-up-bold </v-icon>
            </v-btn>
            <v-btn
              small
              width="110px"
              color="green lighten-2"
              class="ma-2 white--text ml-6"
              
            >
              Rating
              <v-icon small right dark> mdi-arrow-down-bold </v-icon>
            </v-btn>
          </v-row>
        </v-card>
      </v-col>
      <!--End of the left column-->
      <!--Right column-->
      <v-col xl="8" md="8" sm="12"> 
        <v-card
          elevation="4"
          class="pa-4 ml-10 mb-10"
        >
          <v-row>
            <v-col>
              <v-card-title>
                <h2>Name of pharmacy</h2>
              </v-card-title>

              <v-card-text>
                <v-chip color="primary">
                  <v-icon color="secondary">mdi-map-marker</v-icon>
                  Location of pharmacy
                </v-chip>
                <v-chip color="primary" class="ml-2">
                    <v-icon color="secondary">mdi-cash</v-icon>
                  Price
                </v-chip>
                <br><br>
                
               
              </v-card-text>
            </v-col>

            <v-col>
              <v-card-title>
                <v-rating
                  
                  color="accent"
                  background-color="accent "
                  half-increments
                  readonly
                ></v-rating>
              </v-card-title>
            </v-col>
          </v-row>
        </v-card>     
      </v-col>
      <!--End of the right column-->
    </v-row>
  </v-container>
</template>
<script>
export default {
  data() {
    return {
      consultations:[],
      dateBind: "",
      menu2: false,
      time: "",
    };
  },

  methods: {
      sortBy(prop) {
      this.consultations.sort((a, b) => (a[prop] < b[prop] ? -1 : 1));
    },
    sortDownBy(prop) {
      this.consultations.sort((a, b) => (a[prop] > b[prop] ? -1 : 1));
    },
  },
};
</script>