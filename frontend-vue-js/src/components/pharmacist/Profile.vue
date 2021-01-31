<template>
  <div>
    <NavbarPharmacist></NavbarPharmacist>

    <h1>WELCOME PHARMACIST</h1>
    <h3>Fetcing all Users from API</h3>
    <div class="separator">

      <md-avatar id="avatar" class="md-avatar-icon md-primary">P</md-avatar>
    <h1>{{name}}</h1>
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
      url: process.env.VUE_APP_APIUSERSURL,
      list: undefined,
      name: ""
    };
  },
  mounted() {
    // Vue.axios.get(this.url).then((resp) => {
    //   this.list = resp.data;
       
  
    // });
        localStorage.name = "Mark";

      if (localStorage.name) {
        this.name = localStorage.name;
      }
  },
};
</script>

<style>
#avatar {
  width: 80px;
  height: 80px;
  margin-top: 5%;
  margin-right: 80%;
}
</style>
