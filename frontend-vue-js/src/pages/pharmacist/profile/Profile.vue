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

          <v-dialog v-model="dialog" transition="dialog-bottom-transition" max-width="600">
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
                        <v-btn depressed class="mr-4 mb-5" @click="dialog = false"> Cancel </v-btn>
                        <v-btn depressed class="mr-4 mb-5"   @click="changePersonalData" color="primary">
                          Submit
                        </v-btn>
                      </v-card>
                    </v-tab-item>
                    <v-tab-item>
                      <v-card flat>
                        <v-text-field
                          :append-icon="show3 ? 'mdi-eye' : 'mdi-eye-off'"
                          :rules="[rules.required, rules.min]"
                          :type="show3 ? 'text' : 'password'"
                          name="input-10-2"
                          label="Not visible"
                          hint="At least 8 characters"
                          value="wqfasds"
                          class="input-group--focused"
                          @click:append="show3 = !show3"
                        ></v-text-field>
                        <v-text-field
                          :append-icon="show3 ? 'mdi-eye' : 'mdi-eye-off'"
                          :rules="[rules.required, rules.min]"
                          :type="show3 ? 'text' : 'password'"
                          name="input-10-2"
                          label="Not visible"
                          hint="At least 8 characters"
                          value="wqfasds"
                          class="input-group--focused"
                          @click:append="show3 = !show3"
                        ></v-text-field>
                        <v-btn depressed class="mr-4 mb-5"> Cancel </v-btn>
                        <v-btn
                          depressed
                          class="mr-4 mb-5"
                          color="primary"
                        
                        >
                          Submit
                        </v-btn>
                      </v-card>
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

export default {
  components: {
    calendar,
  },
  data: () => ({
    rules: {
      required: (value) => !!value || "Required.",
      min: (v) => v.length >= 8 || "Min 8 characters",
      emailMatch: () => `The email and password you entered don't match`,
    },
     dialog: false,
    show3: false,
    password: "Password",
    pharmacist: [],
  }),
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
          { name: this.pharmacist.name,
            surname: this.pharmacist.lastName,
            city: 'Novi Sad',
            address:  this.pharmacist.location,
            country: 'Serbia',
            phone: this.pharmacist.phoneNumber
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((resp) => {
          this.pharmacist = resp.data;
          this.dialog= false;
          this.$router.go();
        });
    },
  }
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

