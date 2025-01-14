<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import HeaderComponent from '../components/HeaderComponent.vue';
import FooterComponent from '../components/FooterComponent.vue';

const municipalities = ref([]);
const selectedMunicipality1Id = ref(null);
const selectedMunicipality2Id = ref(null);
const municipality1Votes = ref([]);
const municipality2Votes = ref([]);

// Haal alle gemeenten op
const fetchMunicipalities = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/managing-authorities/getAllAuthorities');
    municipalities.value = response.data;
  } catch (error) {
    console.error('Error fetching municipalities:', error);
  }
};

// Haal de stemresultaten op voor een specifieke gemeente
const fetchVotesForMunicipality = async (municipalityId, targetVotesRef) => {
  const selectedMunicipality = municipalities.value.find(
      (municipality) => municipality.id === municipalityId
  );

  if (selectedMunicipality) {
    try {
      const endpoint = `http://localhost:8080/api/result-local-authority/${selectedMunicipality.authorityIdentifier}`;
      const response = await axios.get(endpoint);
      targetVotesRef.value = response.data;
    } catch (error) {
      console.error('Error fetching municipality votes:', error);
    }
  }
};

// Haal beide stemresultaten op
const fetchBothMunicipalityVotes = () => {
  if (selectedMunicipality1Id.value) {
    fetchVotesForMunicipality(selectedMunicipality1Id.value, municipality1Votes);
  }
  if (selectedMunicipality2Id.value) {
    fetchVotesForMunicipality(selectedMunicipality2Id.value, municipality2Votes);
  }
};

// Mount de initiële data
onMounted(() => {
  fetchMunicipalities();
});
</script>

<template>
  <div class="municipality-page">
    <HeaderComponent />
    <div id="description-container">
      <p id="description-text">
        Selecteer twee gemeenten om de resultaten te vergelijken.
      </p>
    </div>
    <div id="selectors-container">
      <div class="municipality-selection">
        <label for="authority-select-1">Selecteer de eerste gemeente</label>
        <select id="authority-select-1" v-model="selectedMunicipality1Id" @change="fetchBothMunicipalityVotes">
          <option value="" disabled>Selecteer een gemeente</option>
          <option v-for="municipality in municipalities" :key="municipality.id" :value="municipality.id">
            {{ municipality.authorityName }}
          </option>
        </select>
      </div>
      <div class="municipality-selection">
        <label for="authority-select-2">Selecteer de tweede gemeente</label>
        <select id="authority-select-2" v-model="selectedMunicipality2Id" @change="fetchBothMunicipalityVotes">
          <option value="" disabled>Selecteer een gemeente</option>
          <option v-for="municipality in municipalities" :key="municipality.id" :value="municipality.id">
            {{ municipality.authorityName }}
          </option>
        </select>
      </div>
    </div>
    <div id="results-container">
      <div class="results" v-if="municipality1Votes.length > 0">
        <h3>Stemresultaten voor Gemeente 1</h3>
        <table>
          <thead>
          <tr>
            <th>Partij</th>
            <th>Stemmen</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="vote in municipality1Votes" :key="vote.id">
            <td>{{ vote.affiliation.registeredName }}</td>
            <td>{{ vote.validVotes }}</td>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="results" v-if="municipality2Votes.length > 0">
        <h3>Stemresultaten voor Gemeente 2</h3>
        <table>
          <thead>
          <tr>
            <th>Partij</th>
            <th>Stemmen</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="vote in municipality2Votes" :key="vote.id">
            <td>{{ vote.affiliation.registeredName }}</td>
            <td>{{ vote.validVotes }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <FooterComponent />
  </div>
</template>







<style scoped>
.municipality-page {
  font-family: Arial, sans-serif;
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

#description-container {
  margin-bottom: 20px;
  text-align: center;
}

#selectors-container {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.municipality-selection {
  flex: 1;
  margin: 0 10px;
}

#sort-bar {
  text-align: center;
  margin-bottom: 20px;
}

#results-container {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}

.results {
  flex: 1;
}

table {
  width: 100%;
  border-collapse: collapse;
}

table th,
table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

table th {
  background-color: #f4f4f4;
  font-weight: bold;
}

h3 {
  text-align: center;
  margin-bottom: 10px;
}

</style>

