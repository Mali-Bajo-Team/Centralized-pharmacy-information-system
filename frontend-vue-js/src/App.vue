<template>
  <div id="app">
      <div>
        <md-tabs md-sync-route>
            <md-tab id="tab-home" md-label="Home" to="/login" exact>
            </md-tab>

            <md-tab id="tab-pages" md-label="Pages" to="/allUsers">
              Pages tab
            </md-tab>

            <md-tab id="tab-posts" md-label="Posts" to="/components/tabs/posts">
              Posts tab
              <p>Qui, voluptas repellat impedit ducimus earum at ad architecto consectetur perferendis aspernatur iste amet ex tempora animi, illum tenetur quae assumenda iusto.</p>
            </md-tab>

            <md-tab id="tab-favorites" md-label="Favorites" to="/components/tabs/favorites">
              Favorites tab
              <p>Maiores, dolorum. Beatae, optio tempore fuga odit aperiam velit, consequuntur magni inventore sapiente alias sequi odio qui harum dolorem sunt quasi corporis.</p>
            </md-tab>
       </md-tabs>
      </div>
    <div>
    <router-view></router-view>
  </div>
    
  </div>
</template>

<script>

import VueMaterial from 'vue-material'
import Vue from 'vue'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'

Vue.use(VueMaterial)


export default {
  name: 'App',
  
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 2px;
}
</style>
