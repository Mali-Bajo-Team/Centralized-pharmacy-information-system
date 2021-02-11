<template>
  <div>
    <v-card elevation="4" class="pa-4 mb-10" v-show="!finished">
      <v-card-title class="ml-2">
        {{ consultant.name + " " + consultant.surname}}
        <v-spacer></v-spacer>
        <v-rating
          class="mr-10"
          v-model="consultant.rating"
          color="accent"
          background-color="orange "
          half-increments
          readonly
        ></v-rating>
      </v-card-title>
      <v-card-text class="ml-0">
        <v-chip
          class="ml-0 mr-4"
          v-for="pharmacy in consultant.pharmacies"
          :key="pharmacy.id"
        >{{ pharmacy.name }}</v-chip>
      </v-card-text>
      <v-card-actions v-if="admin">
        <v-btn color="error" @click="fire" :disabled="performingAction">Fire</v-btn>
        <v-progress-circular class="ml-4" indeterminate color="primary" v-show="performingAction"></v-progress-circular>
      </v-card-actions>
    </v-card>

    <v-snackbar v-model="snackbar" :timeout="2000" bottom class="mb-5" right>
      {{ snackbarText }}
      <template v-slot:action="{ attrs }">
        <v-btn color="primary" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
export default {
  props: {
    consultant: {
      type: Object,
      required: true
    },
    admin: {
      type: Boolean,
      default: false
    },
    endpoint: {
      type: String,
      default: ""
    }
  },
  data: () => ({
    finished: false,
    performingAction: false,
    snackbarText: "",
    snackbar: false
  }),
  methods: {
    fire: function() {
      if (!this.admin) return;
      this.performingAction = true;
      this.axios
        .delete(this.endpoint + "/" + this.consultant.id, {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
          }
        })
        .then(() => {
          this.snackbarText = "Employee successfully fired.";
          this.finished = true;
          this.snackbar = true;
        })
        .catch(error => {
          if (
            error.response &&
            error.response.data &&
            error.response.data.message
          )
            this.snackbarText = error.response.data.message;
          else if (error.message) this.snackbarText = error.message;
          else this.snackbarText = "An unknown error has occured.";
          this.performingAction = false;
          this.snackbar = true;
        });
    }
  }
};
</script>