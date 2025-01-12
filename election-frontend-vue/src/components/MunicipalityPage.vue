<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import HeaderComponent from '../components/HeaderComponent.vue';
import FooterComponent from '../components/FooterComponent.vue';

const municipalities = ref([]);

// function o get municipalities
const fetchMunicipalities = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/municipalities');
    municipalities.value = response.data;
  } catch (error) {
    console.error('Error fetching municipalities:', error);
  }
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
