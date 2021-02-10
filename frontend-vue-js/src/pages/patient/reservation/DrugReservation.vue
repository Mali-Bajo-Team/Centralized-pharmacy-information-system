<template>
  <v-container>
    <v-card width="600px" class="mx-auto">
      <v-card-title>Reservation 1</v-card-title>
      <v-row>
           <v-col xl="8" md="8" sm="12">
             <v-card-text>
               Drug Brufen, pharmacy Jankovic, amount 15, deadline
             </v-card-text>
             <v-spacer></v-spacer>
           </v-col>
           <v-col>
             <v-card-actions class="pb-4">
                  <v-spacer> </v-spacer>
                  <v-btn
                    elevation="0"
                      left
                      top
                      class="ma-5"
                      fab
                      dark
                      small
                      color="red lighten-2"
                     
                  > <v-icon dark>mdi-delete</v-icon></v-btn>
              </v-card-actions>
           </v-col>
      </v-row>
    </v-card>
  </v-container>

</template>

<script>
export default {
  data: ()=> ({
    
  }),
}
</script>