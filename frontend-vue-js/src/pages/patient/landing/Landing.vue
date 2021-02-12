<template>
  <v-container>
    <h3>Patient</h3>
    <v-form>
      <v-text-field type="date" label="Unesi" v-model="dateBind">
      </v-text-field>
          <v-col
    >
      <v-menu
        ref="menu"
        v-model="menu2"
        :close-on-content-click="false"
        :nudge-right="40"
        :return-value.sync="time"
        transition="scale-transition"
        offset-y
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
            v-model="time"
            label="Picker in menu"
            prepend-icon="mdi-clock-time-four-outline"
            readonly
            v-bind="attrs"
            v-on="on"
          ></v-text-field>
        </template>
        <v-time-picker
          v-if="menu2"
          v-model="time"
          full-width
          @click:minute="$refs.menu.save(time)"
        ></v-time-picker>
      </v-menu>
    </v-col>
    </v-form>


  </v-container>
</template>

<script>
export default {
  data() {
    return {
      dateBind: "",
      menu2: false,
      time: ""
    };
  },

  methods: {},
};
</script>