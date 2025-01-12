<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import HeaderComponent from '../components/HeaderComponent.vue';
import FooterComponent from '../components/FooterComponent.vue';

const municipalities = ref([]);

// function o get municipalities
const fetchMunicipalities = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/managing-authorities/getAllAuthorities');
    municipalities.value = response.data;
  } catch (error) {
    console.error('Error fetching municipalities:', error);  }
};

// gets municipalities when page loads
onMounted(() => {
  fetchMunicipalities();
});
</script>

<template>
  <div class="municipality-page">
    <HeaderComponent />
    <h2 class="page-title">Gemeente uitslagen vergelijken</h2>
    <div id="description-container">
      <p id="description-text">
        Vergelijke alle gemeentelijke uitslagen met elkaar
      </p>
    </div>
    <div id="titel">
    </div>
    <div class="municipality-list">
      <h2>Alle Gemeenten</h2>
      <div v-if="municipalities.length > 0">
        <div v-for="municipality in municipalities" :key="municipality.id" class="municipality-item">
          <p><strong>Gemeente Identifier:</strong> {{ municipality.authorityIdentifier }}</p>
          <p><strong>Gemeente Naam:</strong> {{ municipality.authorityName }}</p>
        </div>
      </div>
      <p v-else class="no-municipalities">Geen gemeenten gevonden.</p>
    </div>
    <FooterComponent />
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
