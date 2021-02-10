<template>
    <v-app-bar
      fixed    
      shrink-on-scroll
      prominent
      app      
    >
      <template v-slot:img="{ props }">
        <v-img
          v-bind="props"
          gradient="to top right, rgba(255,255,230,.95), rgba(15,45,45,.7)"          
            src="@/assets/pills.jpg"
        ></v-img>
      </template>

      <template v-slot:extension>
        <v-tabs align-with-title hide-slider>
          <template v-for="item in items">
            <v-tab v-if="item.to" :key="item.title" :to="item.to">{{item.title}}</v-tab>
            <v-menu  v-else :key="item.title" bottom offset-y="true" left>
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                text
                class="align-self-center pl-5 pr-5 rounded-0"
                v-bind="attrs"
                v-on="on"
              >
                {{item.title}}
                <v-icon right>
                  mdi-menu-down
                </v-icon>
              </v-btn>
            </template>

            <v-list>
              <v-list-item
                v-for="subitem in item.children"
                :key="subitem"
                :to="subitem.to"
              >
                {{ subitem.title }}
              </v-list-item>
            </v-list>
          </v-menu>
          </template>          
          <v-tab v-if="logout" @click="doLogout">Log out</v-tab>
        </v-tabs>
      </template>
    </v-app-bar>
</template>

<script>
import { removeToken } from './../util/token'

export default {
    props: {
        items: {
            type: Array,
            default: function () {
                return []
            }
        },
        logout: {
          type: Boolean,
          default: true
        }
    },
    methods:{
      doLogout: function() {
        removeToken()
        this.$router.push('/')
      }
    }
}
</script>