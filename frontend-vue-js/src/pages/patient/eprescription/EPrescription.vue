<template>
<v-container>
  <div>
    <p class="decode-result">
      Last result: <b>{{ result }}</b>
    </p>

    <qrcode-capture @decode="onDecode" />
  </div>
</v-container>
</template>

<script>
import { QrcodeCapture } from "vue-qrcode-reader";

export default {
  components: { QrcodeCapture },
  data() {
    return {
      result: "",
    };
  },

  methods: {
    onDecode(result) {
      this.result = result;
    },
  },
};
</script>