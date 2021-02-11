<template>
  <div>
    <v-lazy :options="{
          threshold: .2
        }" transition="fade-transition">
      <v-card elevation="4" class="pa-4 mb-10" v-show="!finished">
        <v-card-title class="ml-2">{{ request.consultantName + " " + request.consultantSurname}}</v-card-title>
        <v-card-subtitle class="ml-2">{{ request.consultantEmail}}</v-card-subtitle>
        <v-divider class="ml-5 mr-5"></v-divider>
        <v-card-text class="ml-2">
          <span class="mr-2">Requested vacation from</span>
          <v-chip color="primary">{{ request.start }}</v-chip>
          <span class="ml-2 mr-2">to</span>
          <v-chip color="primary">{{ request.end }}</v-chip>
        </v-card-text>
        <v-divider class="ml-5 mr-5" v-show="rejecting"></v-divider>
        <v-form v-show="rejecting">
          <v-text-field
            class="ml-5 mr-5 mt-2 mb-2"
            v-model="response"
            :error-messages="responseErrors"
            label="Reason for rejection"
            @blur="$v.response.$touch()"
            @input="$v.response.$touch()"
          ></v-text-field>
        </v-form>
        <v-card-actions>
          <v-btn
            v-show="rejecting"
            color="secondary"
            text
            @click="stopRejecting"
            :disabled="performingAction"
          >Cancel</v-btn>
          <v-btn
            v-show="!rejecting"
            color="success"
            @click="accept"
            :disabled="performingAction"
          >Approve</v-btn>
          <v-btn
            v-show="!rejecting"
            color="error"
            @click="startRejecting"
            :disabled="performingAction"
          >Reject</v-btn>
          <v-btn
            v-show="rejecting"
            color="error"
            @click="reject"
            :disabled="performingAction || response.length == 0"
          >Confirm rejection</v-btn>
          <v-progress-circular class="ml-4" indeterminate color="primary" v-show="performingAction"></v-progress-circular>
        </v-card-actions>
      </v-card>
    </v-lazy>

    <v-snackbar v-model="snackbar" :timeout="2000" bottom class="mb-5" right>
      {{ snackbarText }}
      <template v-slot:action="{ attrs }">
        <v-btn color="primary" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required } from "vuelidate/lib/validators";

export default {
  mixins: [validationMixin],
  props: {
    request: {
      type: Object,
      required: true
    },
    endpoint: {
      type: String,
      required: true
    }
  },
  data: () => ({
    response: "",
    snackbarText: "",
    snackbar: false,
    rejecting: false,
    performingAction: false,
    finished: false
  }),
  mounted() {
    this.request.start = new Date(this.request.start).toLocaleDateString();
    this.request.end = new Date(this.request.end).toLocaleDateString();
  },
  methods: {
    accept() {
      this.performingAction = true;
      this.axios
        .post(
          this.endpoint + "/" + this.request.id + "/accept",
          {},
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
            }
          }
        )
        .then(() => {
          this.snackbarText = "Successfuly accepted vacation request.";
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
    },
    startRejecting() {
      this.rejecting = true;
    },
    stopRejecting() {
      this.rejecting = false;
    },
    reject() {
      this.$v.$touch();

      if (!this.$v.$invalid) {
        this.performingAction = true;
        this.axios
          .post(
            this.endpoint + "/" + this.request.id + "/reject",
            { response: this.response },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
              }
            }
          )
          .then(() => {
            this.snackbarText = "Successfuly rejected vacation request.";
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
  },
  validations: {
    response: {
      required
    }
  },
  computed: {
    responseErrors() {
      const errors = [];
      if (!this.$v.response.$dirty) return errors;
      !this.$v.response.required &&
        errors.push("Reason of rejection is required.");
      return errors;
    }
  }
};
</script>