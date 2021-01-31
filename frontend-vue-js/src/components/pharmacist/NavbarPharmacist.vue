<template>
 <div>
    <md-tabs md-sync-route>
      <md-tab id="tab-home" md-label="Home" to="/components/pharmacist/Landingpage.vue" exact></md-tab>
      <md-tab id="tab-profile" md-label="Pages" to="/components/pharmacist/Profile.vue"></md-tab>
      <md-tab id="tab-posts" md-label="Posts" to="/components/tabs/posts"></md-tab>
      <md-tab id="tab-favorites" md-label="Favorites" to="/components/tabs/favorites"></md-tab>
      <md-tab id="tab-disabled" md-label="Disabled" md-disabled></md-tab>
    </md-tabs>
  </div>
</template>

<script>
export default {
  name: "NavbarPharmacist",
  data() {
    return {};
  }
};
</script>


