<template>
  <div>
    <NavbarPharmacist></NavbarPharmacist>

    <h1 id="pharmacistprofile">Pharmacist profile</h1>

    <div class="separator">
    <md-avatar id="avatar" class="md-avatar-icon md-primary">P</md-avatar>

    <div id="containerprofiledata"> 
      <p class="profiledata profiledataName">{{name}}</p>
      <p class="profiledata">{{surname}}</p>
      <p class="profiledata">{{phoneNumber}}</p>
      <p class="profiledata">{{location}}</p>
       <md-button id="editbutton" class="md-fab md-plain">
        <md-icon id="editicon">edit</md-icon>
      </md-button>
    </div>
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
      name: "",
      surname: "",
      phoneNumber: "",
      location: ""
    };
  },
  mounted() {
    // Vue.axios.get(this.url).then((resp) => {
    //   this.list = resp.data;
    // });
  
      //Use data from local storage not from API req  
      if (localStorage.name) {
        this.name = localStorage.name;
        this.surname = localStorage.surname;
        this.phoneNumber = localStorage.phoneNumber;
        this.location = localStorage.location;
      }//else send req to APi
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
.profiledata{
  color: white;
  font-size: 30px;
}
.profiledataName{
  padding-top: 20px;
}
#containerprofiledata{
  width: 400px;
  height: 300px;
  margin-left: 12%;
  background-color: #448aff;
  border-radius: 5%;
}
#editbutton{
  background-color: white;
}
#editicon {
  color: #448aff;
}
#pharmacistprofile{
  color: #448aff;
}
</style>
