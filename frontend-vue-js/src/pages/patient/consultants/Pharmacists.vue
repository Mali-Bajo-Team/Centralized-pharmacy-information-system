<template>
  <v-row>
    <v-spacer></v-spacer>
    <v-col sm="12" md="12" lg="9" xl="9" cols="12">
      <consultants :endpoint="endpoint"></consultants>
    </v-col>
    <v-spacer></v-spacer>
  </v-row>
</template>
<script>
import consultants from "./../../../components/consultantSearch/Container";

export default {
  components: {
    consultants
  },
  data: () => ({
    endpoint:
      process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_PHARMACIST_ENDPOINT
  })
};
</script>