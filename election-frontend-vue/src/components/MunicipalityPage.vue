<script setup lang="ts">
import HeaderComponent from '../components/HeaderComponent.vue';
import FooterComponent from '../components/FooterComponent.vue';

export default {
  name: 'MunicipalityPage',
  components: {
    HeaderComponent,
    FooterComponent,
  },
};
</script>

<template>
  <div class="municipality-page">
    <HeaderComponent />
    <h2 class="page-title">Municipalities</h2>
    <FooterComponent />
  </div>
</template>

<style scoped>

</style>