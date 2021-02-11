<template>
  <v-app style="background: rgba(0,0,0,0);">        
        <v-main>
            <v-container fluid>
                
<v-row class="pa-5 pt-10">
    <v-spacer></v-spacer>
    <v-col sm="8" md="6" lg="4" xl="4" cols="12">
        <v-card
        elevation="4"
        class="pa-5"
        >

        <v-card-title>Change password to proceed</v-card-title>
        <v-form>
        <v-text-field
            v-model="form.oldPassword"
            :append-icon="form.showOldPassword ? 'mdi-eye' : 'mdi-eye-off'"
            :errorMessages="oldPasswordErrors"
            :type="form.showOldPassword ? 'text' : 'password'"
            label="Current password"
            @click:append="form.showOldPassword = !form.showOldPassword"            
            @blur="$v.form.oldPassword.$touch()"
            @input="$v.form.oldPassword.$touch()"
          ></v-text-field>   

          <v-text-field
            v-model="form.newPassword"
            :append-icon="form.showNewPassword ? 'mdi-eye' : 'mdi-eye-off'"
            :errorMessages="newPasswordErrors"
            :type="form.showNewPassword ? 'text' : 'password'"
            label="New password"
            @click:append="form.showNewPassword = !form.showNewPassword"            
            @blur="$v.form.newPassword.$touch()"
            @input="$v.form.newPassword.$touch()"
          ></v-text-field>   

          <v-text-field
            v-model="form.repeatedNewPassword"
            :append-icon="form.showRepeatedNewPassword ? 'mdi-eye' : 'mdi-eye-off'"
            :errorMessages="repeatedNewPasswordErrors"
            :type="form.showRepeatedNewPassword ? 'text' : 'password'"
            label="Confirm new password"
            @click:append="form.showRepeatedNewPassword = !form.showRepeatedNewPassword"            
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
          color='#ffffff'
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
    </v-col>
    
    <v-spacer></v-spacer>
    </v-row>

            </v-container>
        </v-main>
    </v-app>    
</template>

<script>
import { validationMixin } from "vuelidate";
import {
  required,
  minLength,
  sameAs
} from "vuelidate/lib/validators";

import { saveToken, getToken, getParsedToken } from "./../../util/token"

export default {
  mixins: [validationMixin],
  data() {
    return {
      snackbar: false,
      snackbarText: '',
      loading: false,
      form: {
        oldPassword: "",
        showOldPassword: false,
        newPassword: "",
        showNewPassword: false,
        repeatedNewPassword: "",
        showRepeatedNewPassword: false
      },
    };
  },
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
        sameAsNewPassword: sameAs('newPassword')
      }
    },
  },
  computed: {
      oldPasswordErrors() {
        const errors = []
        if (!this.$v.form.oldPassword.$dirty) return errors
        !this.$v.form.oldPassword.required && errors.push('Current password is required.')        
        !this.$v.form.oldPassword.minLength && errors.push('Current password is too short.')
        return errors
      },
      newPasswordErrors() {
        const errors = []
        if (!this.$v.form.newPassword.$dirty) return errors
        !this.$v.form.newPassword.required && errors.push('New password is required.')        
        !this.$v.form.newPassword.minLength && errors.push('New password is too short.')
        return errors
      },
      repeatedNewPasswordErrors() {
        const errors = []
        if (!this.$v.form.repeatedNewPassword.$dirty) return errors
        !this.$v.form.repeatedNewPassword.required && errors.push('Repeated new password is required.')        
        !this.$v.form.repeatedNewPassword.minLength && errors.push('Repeated new password is too short.')     
        !this.$v.form.repeatedNewPassword.sameAsNewPassword && errors.push('Passwords must match.')
        return errors
      }
  },
  methods: {
    auth() {
        this.axios
        .put(process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_CHANGE_PASSWORD_ENDPOINT, 
            {
            oldPassword: this.form.oldPassword,
            newPassword: this.form.newPassword,
            }, 
            {
                headers: {
                    Authorization: "Bearer " + getToken()
                }
            })
        .then(() => {            
            setTimeout(() => this.axios
            .post(process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_LOGIN_ENDPOINT, {
            password: this.form.newPassword,
            email: getParsedToken().sub,
            })
            .then(response => {
            let token = response.data.accessToken;
            saveToken(token)

            if(this.$route.params.nextUrl != null){
                this.$router.push(this.$route.params.nextUrl)
            }
            else {
                this.$router.push('/')
            }
            })
            .catch(error => {
            if (error.response && error.response.data)
                this.snackbarText = error.response.data
            else
                this.snackbarText = error.message
            this.loading = false
            this.snackbar = true
            }), 1000);          
        })
        .catch(error => {
          if (error.response && error.response.data)
            this.snackbarText = error.response.data
          else
            this.snackbarText = error.message
          this.loading = false
          this.snackbar = true
        });
    },
    submit() {
      this.$v.$touch();

      if (!this.$v.$invalid) {
        this.loading = true
        this.auth();
      }
    },
    reset() {
        console.log(process.env.VUE_APP_BACKEND_URL)
        this.$v.$reset()
        this.form.oldPassword = ''
        this.form.newPassword = ''
        this.form.repeatedNewPassword = ''
    }
  },
};
</script>