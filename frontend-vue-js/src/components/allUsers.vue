<template>
    <div>
        <h1>Fetch ALL USERS with API</h1>
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
        return {list:undefined}
    },
    mounted(){
        Vue.axios.get('http://localhost:8081/api/users')
        .then((resp)=>{
           this.list = resp.data;
        })
    }
}
</script>
