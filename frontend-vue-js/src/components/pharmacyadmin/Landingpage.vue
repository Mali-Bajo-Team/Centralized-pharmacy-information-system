<template>
    <div>
        <h1>WELCOME PHARMACY ADMINISTRATOR</h1>
        <h3>Fetcing all Users from API</h3>
        <table border="1px">
            <tr>
                <td>Id</td>
                <td>Mail</td>
            </tr>
            <tr v-for="item in list" v-bind:key="item.id">
                <td>{{item.id}}</td>
                <td>{{item.email}}</td>
            </tr>
        </table>

    </div> 
</template>

<script>
import Vue from 'vue';
import axios from 'axios';
import VueAxios from 'vue-axios';

Vue.use(VueAxios,axios)

export default {
    name: "allUsers",
    data()
    {
        return {
            url: process.env.VUE_APP_APIUSERSURL,
            list: undefined
        }
    },
    mounted(){
        Vue.axios.get(this.url)
        .then((resp)=>{
           this.list = resp.data;
        })
    }
}
</script>
