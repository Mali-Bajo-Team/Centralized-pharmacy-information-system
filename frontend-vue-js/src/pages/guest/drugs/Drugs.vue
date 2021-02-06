<template>
    <v-container>
        <v-row>
            <!-- Left column for filter & search -->
            <v-col>
                <p> Left </p>
            </v-col>
            <!-- End of the left column for filter & search -->

            <!-- Right column for drugs preview-->
            <v-col>
                <p> Right </p>
            </v-col>
            <!-- End of the right colum for drugs preview-->
        </v-row>
    </v-container>
</template>

<script>

export default {
  data: () => ({
    drugs: [],
  }),
  mounted() {
    this.axios
      .get(process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_ALL_DRUGS_ENDPOINT,)
      .then((resp) => {
        this.drugs = resp.data;
      });
  },


};
</script>