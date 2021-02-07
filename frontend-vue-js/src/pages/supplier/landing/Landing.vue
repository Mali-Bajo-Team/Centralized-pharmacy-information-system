<template>
  <v-container>
    <h3>Supplier</h3>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    supplierOffers: [],
  }),
  methods: {},
  mounted() {
    this.axios
      .get(
        process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_PROCUREMENT_ENDPOINT,
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        alert("success");
        this.supplierOffers = resp.data[0];
      });
  },
};
</script>