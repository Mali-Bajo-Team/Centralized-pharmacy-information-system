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
        <v-tabs align-with-title>
          <v-tab v-for="item in items" :key="item.title" :to="item.to">{{item.title}}</v-tab>
        </v-tabs>
      </template>
    </v-app-bar>
</template>

<script>
export default {
    props: {
        items: {
            type: Array,
            default: function () {
                return []
            }
        }
    }
}
</script>