<template>
  <v-row class="pa-5">
    <v-spacer></v-spacer>
    <v-col sm="8" md="6" lg="6" xl="6" cols="12">
      <v-card elevation="4" class="pa-5">
        <v-card-title>Add promotion</v-card-title>

        <v-form>
          <v-text-field
            v-model="form.subject"
            :error-messages="subjectErrors"
            label="Title"
            counter="250"
            @blur="$v.form.subject.$touch()"
            @input="$v.form.subject.$touch()"
          ></v-text-field>

          <v-textarea
            rows="1"
            auto-grow
            v-model="form.content"
            :error-messages="contentErrors"
            label="Content"
            counter="800"
            @blur="$v.form.content.$touch()"
            @input="$v.form.content.$touch()"
          ></v-textarea>

          <span class="mt-8 grey-lighten2">Choose validity range:</span>
          <v-date-picker class="mt-3 mb-0" full-width v-model="form.validityRange" range :min="today"></v-date-picker>

          <v-card-actions class="pt-0">
            <v-btn color="secondary" text @click="reset">Reset form</v-btn>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="submit">Save</v-btn>
          </v-card-actions>
        </v-form>

        <v-overlay :absolute="true" :value="loading" :opacity="0.7" color="#ffffff">
          <v-progress-circular indeterminate color="secondary"></v-progress-circular>
        </v-overlay>

        <v-snackbar v-model="snackbar" :timeout="2000" bottom class="mb-5" right>
          {{ snackbarText }}
          <template v-slot:action="{ attrs }">
            <v-btn color="primary" text v-bind="attrs" @click="snackbar = false">Close</v-btn>
          </template>
        </v-snackbar>
      </v-card>
    </v-col>

    <v-spacer></v-spacer>
  </v-row>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, maxLength } from "vuelidate/lib/validators";

import {
  getMillisecondsFromStringDate,
  getTodayDateString
} from "./../../../util/dateHandler";

export default {
  mixins: [validationMixin],
  data() {
    return {
      snackbar: false,
      snackbarText: "",
      loading: false,
      form: {
        subject: "",
        content: "",
        validityRange: []
      }
    };
  },
  validations: {
    form: {
      subject: {
        required,
        maxLength: maxLength(250)
      },
      content: {
        required,
        maxLength: maxLength(800)
      }
    }
  },
  computed: {
    subjectErrors() {
      const errors = [];
      if (!this.$v.form.subject.$dirty) return errors;
      !this.$v.form.subject.required && errors.push("Subject is required.");
      !this.$v.form.subject.maxLength && errors.push("Subject is too long.");
      return errors;
    },
    contentErrors() {
      const errors = [];
      if (!this.$v.form.content.$dirty) return errors;
      !this.$v.form.content.required && errors.push("Content is required.");
      !this.$v.form.content.maxLength && errors.push("Content is too long.");
      return errors;
    },
    today() {
      return getTodayDateString();
    }
  },
  methods: {
    save() {
      let date1 = 0;
      let date2 = 0;
      if (this.form.validityRange.length == 0) {
        date1 = getMillisecondsFromStringDate(this.form.validityRange[0]);
        date2 = getMillisecondsFromStringDate(this.form.validityRange[0]);
      } else {
        date1 = getMillisecondsFromStringDate(this.form.validityRange[0]);
        date2 = getMillisecondsFromStringDate(this.form.validityRange[1]);

        if (date1 > date2) {
          var temp = date2;
          date2 = date1;
          date1 = temp;
        }
      }

      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PROMOTIONS_ENDPOINT,
          {
            title: this.form.subject,
            content: this.form.content,
            validityStart: date1,
            validityEnd: date2
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS")
            }
          }
        )
        .then(() => {
          this.reset();
          this.snackbarText = "Promotion successfully added.";
          this.loading = false;
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
          this.loading = false;
          this.snackbar = true;
        });
    },
    submit() {
      this.$v.$touch();

      if (!this.$v.$invalid) {
        if (this.form.validityRange.length == 0) {
          this.snackbarText = "Validity range is required.";
          this.snackbar = true;
          return;
        }

        this.loading = true;
        this.save();
      }
    },
    reset() {
      console.log(process.env.VUE_APP_BACKEND_URL);
      this.$v.$reset();
      this.form.subject = "";
      this.form.content = "";
      this.form.validityRange = [];
    }
  }
};
</script>