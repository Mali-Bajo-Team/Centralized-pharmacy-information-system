import allUsers from './components/allUsers.vue';
import index from './components/Index.vue';
import login from './components/Login.vue';

export default [
    { path: '/', component: index},
    { path: '/allUsers', component: allUsers},
    { path: '/login', component: login},
]