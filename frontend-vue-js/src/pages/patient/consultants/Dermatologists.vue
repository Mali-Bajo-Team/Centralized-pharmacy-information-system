<template>
  <consultants :endpoint="endpoint"></consultants>
</template>
<script>
import consultants from "./../../../components/consultantSearch/Container";

export default {
  components: {
    consultants
  },
  data: () => ({
    endpoint:
      process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_DERMATOLOGIST_ENDPOINT
  })
};
</script>