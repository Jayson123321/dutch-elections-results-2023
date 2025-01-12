<script setup lang="ts">
import HeaderComponent from '../components/HeaderComponent.vue';
import FooterComponent from '../components/FooterComponent.vue';
</script>

<template>
  <div class="municipality-page">
    <HeaderComponent />
    <h2 class="page-title">Municipalities</h2>
    <FooterComponent />
  </div>
</template>

<style scoped>
.municipality-page {
  padding: 20px;
  background-color: #111;
  color: #fff;
  min-height: 100vh;
  text-align: center;
}
.page-title {
  font-size: 2em;
  margin-bottom: 20px;
}
</style>
