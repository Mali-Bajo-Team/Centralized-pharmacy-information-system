<template>
  <v-container>
    <v-row>
      <!-- Left column for qr-code-upload, filter & search -->
      <v-col xl="4" sm="12" md="4">
        <!-- Upload QR code card -->
        <v-card>
          <!--Toolbar of the card-->
          <v-toolbar color="primary" dark dense flat>
            <v-toolbar-title class="body-2"> Search drugs </v-toolbar-title>
          </v-toolbar>
          <!--End of the toolbar of the card-->

          <v-card-subtitle class="ma-1"> Upload your QR code</v-card-subtitle>
          <v-form class="pa-5">
            <qrcode-capture @decode="onDecode" />
          </v-form>
        </v-card>
        <!--End of the upload of the QR code card -->
      </v-col>
      <!-- Left column for qr-code-upload, filter & search -->

      <!-- Right column for pharmacies -->
      <v-col xl="8" sm="12" md="8"> right side </v-col>
      <!-- End of the right column for the pharmacies -->
    </v-row>
  </v-container>
</template>

<script>
import { QrcodeCapture } from "vue-qrcode-reader";

export default {
  components: { QrcodeCapture },
  data() {
    return {
      readedQr: {},
      pharmaciesWithRequiredDrugsAmount: [],
    };
  },

  methods: {
    onDecode(readedQr) {
      this.readedQr = JSON.parse(readedQr);
      console.log(this.readedQr);
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL +
            process.env.VUE_APP_PHARMACIES_REQUIRED_DRUGS,
          this.readedQr,
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("JWT-CPIS"),
            },
          }
        )
        .then((resp) => {
          this.pharmaciesWithRequiredDrugsAmount = resp.data;
        })
        .catch((error) => {
          alert("Error: " + error);
        });
    },
  },
};
</script>