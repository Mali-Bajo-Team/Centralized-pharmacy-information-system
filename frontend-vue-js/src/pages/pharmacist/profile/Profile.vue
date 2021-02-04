<template>
  <v-app>
    <v-row>
      <v-col md="3" class="ml-10 mt-15">
        <v-card elevation="3" class="pa-6 primary">
          <p class="font-weight-medium">Name : {{ pharmacist.name }}</p>
          <p class="font-weight-medium">Lastname : {{ pharmacist.lastName }}</p>
          <p class="font-weight-medium">
            Phone number : {{ pharmacist.phoneNumber }}
          </p>
          <p class="font-weight-medium">Location : {{ pharmacist.location }}</p>

          <v-dialog
            v-model="dialog"
            transition="dialog-bottom-transition"
            max-width="600"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                class="rounded-circle"
                fab
                dark
                large
                color="secondary"
                v-bind="attrs"
                v-on="on"
              >
                <v-icon dark> mdi-pencil </v-icon>
              </v-btn>
            </template>
            <template>
              <v-card>
                <v-card>
                  <v-toolbar flat color="primary" dark>
                    <v-toolbar-title>Edit</v-toolbar-title>
                  </v-toolbar>
                  <v-tabs vertical>
                    <v-tab>
                      <v-icon left> mdi-account </v-icon>
                      Personal info
                    </v-tab>
                    <v-tab>
                      <v-icon left> mdi-lock </v-icon>
                      Password
                    </v-tab>
                    <v-tab>
                      <v-icon left> mdi-access-point </v-icon>
                      Option 3
                    </v-tab>

                    <v-tab-item>
                      <v-card flat>
                        <v-text-field
                          v-model="pharmacist.name"
                          label="First Name"
                          filled
                        ></v-text-field>
                        <v-text-field
                          v-model="pharmacist.lastName"
                          label="Last name"
                          filled
                        ></v-text-field>
                        <v-text-field
                          v-model="pharmacist.phoneNumber"
                          label="Phone number"
                          filled
                        ></v-text-field>
                        <v-text-field
                          v-model="pharmacist.location"
                          label="Location"
                          filled
                        ></v-text-field>
                        <v-btn
                          depressed
                          class="mr-4 mb-5"
                          @click="dialog = false"
                        >
                          Cancel
                        </v-btn>
                        <v-btn
                          depressed
                          class="mr-4 mb-5"
                          @click="changePersonalData"
                          color="primary"
                        >
                          Submit
                        </v-btn>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item>
                      <v-container fluid>
                        <v-card elevation="4" class="pa-5">
                          <v-card-title
                            >Change password to proceed</v-card-title
                          >
                          <v-form>
                            <v-text-field
                              v-model="form.oldPassword"
                              :append-icon="
                                form.showOldPassword ? 'mdi-eye' : 'mdi-eye-off'
                              "
                              :errorMessages="oldPasswordErrors"
                              :type="form.showOldPassword ? 'text' : 'password'"
                              label="Current password"
                              @click:append="
                                form.showOldPassword = !form.showOldPassword
                              "
                              @blur="$v.form.oldPassword.$touch()"
                              @input="$v.form.oldPassword.$touch()"
                            ></v-text-field>

                            <v-text-field
                              v-model="form.newPassword"
                              :append-icon="
                                form.showNewPassword ? 'mdi-eye' : 'mdi-eye-off'
                              "
                              :errorMessages="newPasswordErrors"
                              :type="form.showNewPassword ? 'text' : 'password'"
                              label="New password"
                              @click:append="
                                form.showNewPassword = !form.showNewPassword
                              "
                              @blur="$v.form.newPassword.$touch()"
                              @input="$v.form.newPassword.$touch()"
                            ></v-text-field>

                            <v-text-field
                              v-model="form.repeatedNewPassword"
                              :append-icon="
                                form.showRepeatedNewPassword
                                  ? 'mdi-eye'
                                  : 'mdi-eye-off'
                              "
                              :errorMessages="repeatedNewPasswordErrors"
                              :type="
                                form.showRepeatedNewPassword
                                  ? 'text'
                                  : 'password'
                              "
                              label="Confirm new password"
                              @click:append="
                                form.showRepeatedNewPassword = !form.showRepeatedNewPassword
                              "
                              @blur="$v.form.repeatedNewPassword.$touch()"
                              @input="$v.form.repeatedNewPassword.$touch()"
                            ></v-text-field>

                            <v-card-actions class="pt-5">
                              <v-btn color="secondary" text @click="reset">
                                Reset form
                              </v-btn>
                              <v-spacer></v-spacer>
                              <v-btn color="primary" @click="submit">
                                Change password
                              </v-btn>
                            </v-card-actions>
                          </v-form>

                          <v-overlay
                            :absolute="true"
                            :value="loading"
                            :opacity="0.7"
                            color="#ffffff"
                          >
                            <v-progress-circular
                              indeterminate
                              color="secondary"
                            ></v-progress-circular>
                          </v-overlay>

                          <v-snackbar
                            v-model="snackbar"
                            :timeout="2000"
                            bottom
                            class="mb-5"
                            right
                          >
                            {{ snackbarText }}

                            <template v-slot:action="{ attrs }">
                              <v-btn
                                color="primary"
                                text
                                v-bind="attrs"
                                @click="snackbar = false"
                              >
                                Close
                              </v-btn>
                            </template>
                          </v-snackbar>
                        </v-card>
                      </v-container>
                    </v-tab-item>
                    <v-tab-item>
                      <v-card flat> </v-card>
                    </v-tab-item>
                  </v-tabs>
                </v-card>
              </v-card>
            </template>
          </v-dialog>
        </v-card>
      </v-col>
      <v-col md="6">
        <div class="calendar pa-8">
          <calendar></calendar>
        </div>
      </v-col>
    </v-row>
  </v-app>
</template>

<script>
import calendar from "../../../components/Calendar";
import { validationMixin } from "vuelidate";
import { required, minLength, sameAs } from "vuelidate/lib/validators";
import { saveToken, getToken, getParsedToken } from "./../../../util/token";
export default {
  mixins: [validationMixin],
  components: {
    calendar,
  },
  data: () => ({
    rules: {
      required: (value) => !!value || "Required.",
      min: (v) => v.length >= 8 || "Min 8 characters",
      emailMatch: () => `The email and password you entered don't match`,
    },
    snackbar: false,
    snackbarText: "",
    loading: false,
    form: {
      oldPassword: "",
      showOldPassword: false,
      newPassword: "",
      showNewPassword: false,
      repeatedNewPassword: "",
      showRepeatedNewPassword: false,
    },
    dialog: false,
    show3: false,
    password: "Password",
    pharmacist: [],
  }),
  validations: {
    form: {
      oldPassword: {
        required,
        minLength: minLength(6),
      },
      newPassword: {
        required,
        minLength: minLength(6),
      },
      repeatedNewPassword: {
        required,
        minLength: minLength(6),
        sameAsNewPassword: sameAs("newPassword"),
      },
    },
  },
  computed: {
    oldPasswordErrors() {
      const errors = [];
      if (!this.$v.form.oldPassword.$dirty) return errors;
      !this.$v.form.oldPassword.required &&
        errors.push("Current password is required.");
      !this.$v.form.oldPassword.minLength &&
        errors.push("Current password is too short.");
      return errors;
    },
    newPasswordErrors() {
      const errors = [];
      if (!this.$v.form.newPassword.$dirty) return errors;
      !this.$v.form.newPassword.required &&
        errors.push("New password is required.");
      !this.$v.form.newPassword.minLength &&
        errors.push("New password is too short.");
      return errors;
    },
    repeatedNewPasswordErrors() {
      const errors = [];
      if (!this.$v.form.repeatedNewPassword.$dirty) return errors;
      !this.$v.form.repeatedNewPassword.required &&
        errors.push("Repeated new password is required.");
      !this.$v.form.repeatedNewPassword.minLength &&
        errors.push("Repeated new password is too short.");
      !this.$v.form.repeatedNewPassword.sameAsNewPassword &&
        errors.push("Passwords must match.");
      return errors;
    },
  },
  created() {
    var token = parseJwt(localStorage.getItem("JWT-CPIS"));
    var email = token.sub;

    this.axios
      .post(
        process.env.VUE_APP_BACKEND_URL +
          process.env.VUE_APP_LOGEDPHARMACIST_ENDPOINT,
        { email: email },
        {
          headers: {
            Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
          },
        }
      )
      .then((resp) => {
        this.pharmacist = resp.data;
      });
  },
  methods: {
    changePersonalData() {
      this.axios
        .put(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PROFILE_ENDPOINT,
          {
            name: this.pharmacist.name,
            surname: this.pharmacist.lastName,
            city: "Novi Sad",
            address: this.pharmacist.location,
            country: "Serbia",
            phone: this.pharmacist.phoneNumber,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((resp) => {
          this.pharmacist = resp.data;
          this.dialog = false;
          this.$router.go();
        });
    },
    auth() {
      this.axios
        .put(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_CHANGE_PASSWORD_ENDPOINT,
          {
            oldPassword: this.form.oldPassword,
            newPassword: this.form.newPassword,
          },
          {
            headers: {
              Authorization: "Bearer " + getToken(),
            },
          }
        )
        .then(() => {
          setTimeout(
            () =>
              this.axios
                .post(
                  process.env.VUE_APP_BACKEND_URL +
                    process.env.VUE_APP_LOGIN_ENDPOINT,
                  {
                    password: this.form.newPassword,
                    email: getParsedToken().sub,
                  }
                )
                .then((response) => {
                  let token = response.data.accessToken;
                  saveToken(token);

                  this.$router.go();
                })
                .catch((error) => {
                  if (error.response && error.response.data)
                    this.snackbarText = error.response.data;
                  else this.snackbarText = error.message;
                  this.loading = false;
                  this.snackbar = true;
                }),
            1000
          );
        })
        .catch((error) => {
          if (error.response && error.response.data)
            this.snackbarText = error.response.data;
          else this.snackbarText = error.message;
          this.loading = false;
          this.snackbar = true;
        });
    },
    submit() {
      this.$v.$touch();

      if (!this.$v.$invalid) {
        this.loading = true;
        this.auth();
      }
    },
    reset() {
      console.log(process.env.VUE_APP_BACKEND_URL);
      this.$v.$reset();
      this.form.oldPassword = "";
      this.form.newPassword = "";
      this.form.repeatedNewPassword = "";
    },
  },
};

function parseJwt(token) {
  var base64Payload = token.split(".")[1];
  var payload = Buffer.from(base64Payload, "base64");
  return JSON.parse(payload.toString());
}
</script>

<style scoped>
p {
  color: white;
}
</style>

