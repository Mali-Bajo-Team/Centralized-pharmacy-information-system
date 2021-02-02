<template>
  <div>
    <NavbarPharmacist></NavbarPharmacist>

    <h1 id="welcomepharmacist">Welcome pharmacist</h1>

    <div class="separator">
      <p>{{list}}</p>
    </div>
   
  </div>
</template>

<script>
import Vue from "vue";
import axios from "axios";
import VueAxios from "vue-axios";

Vue.use(VueAxios, axios);

export default {
  name: "PharmacistLanding",
  data() {
    return {
      url: 'http://localhost:8081/api/consulltations',
      list: undefined,
    };
  },
  mounted() {
    Vue.axios
      .get(this.url, {
        headers: {
          Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
        },
      })
      .then((resp) => {
        this.list = resp.data;
      });
  },
};
</script>

<style>
#welcomepharmacist{
  color: #448aff;
  font-size: 40px;
}
</style>
