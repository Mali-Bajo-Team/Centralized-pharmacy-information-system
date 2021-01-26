<template>
    <div>
        <h1>Fetch ALL USERS with API</h1>
        <table border="1px">
            <tr>
                <td>Name</td>
                <td>Salary</td>
                <td>Age</td>
            </tr>
            <tr v-for="item in list" v-bind:key="item.id">
                <td>{{item.employee_name}}</td>
                <td>Salary</td>
                <td>Age</td>
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
        Vue.axios.get('http://dummy.restapiexample.com/api/v1/employees')
        .then((resp)=>{
            this.list = resp.data.data;
        })
    }
}
</script>
