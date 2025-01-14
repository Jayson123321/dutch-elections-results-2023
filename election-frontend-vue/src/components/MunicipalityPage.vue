<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import HeaderComponent from '../components/HeaderComponent.vue';
import FooterComponent from '../components/FooterComponent.vue';

const municipalities = ref([]);
const selectedMunicipality1 = ref(null);
const selectedMunicipality2 = ref(null);
const municipalityResults = ref([]);

// function to get municipalities
const fetchMunicipalities = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/managing-authorities/getAllAuthorities');
    municipalities.value = response.data;
  } catch (error) {
    console.error('Error fetching municipalities:', error);
  }
};

// function to compare municipalities
const compareMunicipalities = async () => {
  if (!selectedMunicipality1.value || !selectedMunicipality2.value) {
    console.error('Selecteer beide gemeenten.');
    return;
  }

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
        Vergelijk de gemeentelijke uitslagen met elkaar door twee gemeenten te selecteren.
      </p>
    </div>
    <div id="selectors-container">
      <div class="municipality-selection">
        <h3>Selecteer de eerste gemeente</h3>
        <select v-model="selectedMunicipality1">
          <option value="" disabled>Selecteer een gemeente</option>
          <option v-for="municipality in municipalities" :key="municipality.id" :value="municipality.id">
            {{ municipality.authorityName }}
          </option>
        </select>
      </div>
      <div class="municipality-selection">
        <h3>Selecteer de tweede gemeente</h3>
        <select v-model="selectedMunicipality2">
          <option value="" disabled>Selecteer een gemeente</option>
          <option v-for="municipality in municipalities" :key="municipality.id" :value="municipality.id">
            {{ municipality.authorityName }}
          </option>
        </select>
      </div>
    </div>
    <button class="compare-button">Vergelijk Gemeenten</button>
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

#description-container {
  border: 1px solid #ddd;
  padding: 20px;
  margin: 20px 0;
  border-radius: 5px;
  background-color: #222;
}

#description-text {
  font-size: 1.1em;
  line-height: 1.6;
}

#titel {
  text-align: center;
  margin: 20px 0;
}

.municipality-list {
  margin: 20px 0;
  text-align: left;
  padding: 0 20px;
}
.municipality-item {
  background-color: #222;
  padding: 15px;
  border-radius: 8px;
  margin-bottom: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
}

.no-municipalities {
  color: #888;
  font-size: 1.2em;
}
</style>

