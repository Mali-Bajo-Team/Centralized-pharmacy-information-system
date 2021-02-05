<template>
  <v-container>
    <h3>Loyalty Program</h3>
  </v-container>
</template>

<script>
export default {
  data: () => ({
      loyaltyProgram: null,
  }),
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_LOYALTY_PROGRAM,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        this.loyaltyProgram = resp.data;
      });
  },
};
</script>