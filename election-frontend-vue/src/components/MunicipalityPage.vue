<script setup lang="ts">
import { ref, onMounted, type Ref } from 'vue';
import axios from 'axios';
import HeaderComponent from '../components/HeaderComponent.vue';
import FooterComponent from '../components/FooterComponent.vue';
import config from "@/config.ts";

interface Municipality {
  id: number;
  authorityName: string;
  authorityIdentifier: string;
}

interface Vote {
  id: number;
  affiliation: {
    registeredName: string;
  };
  validVotes: number;
}

const municipalities = ref<Municipality[]>([]);
const selectedMunicipality1Id = ref<number | null>(null);
const selectedMunicipality2Id = ref<number | null>(null);
const municipality1Votes = ref<Vote[]>([]);
const municipality2Votes = ref<Vote[]>([]);

// Haal alle gemeenten op
const fetchMunicipalities = async () => {
  try {
    const response = await axios.get(`${config.apiBaseUrl}/managing-authorities/getAllAuthorities`);
    municipalities.value = response.data;
  } catch (error) {
    console.error('Error fetching municipalities:', error);
  }
};

// Haal de stemresultaten op voor een specifieke gemeente
const fetchVotesForMunicipality = async (municipalityId: number, targetVotesRef: Ref<Vote[]>) => {
  const selectedMunicipality = municipalities.value.find(
      (municipality) => municipality.id === municipalityId
  );

  if (selectedMunicipality) {
    try {
      const endpoint = `${config.apiBaseUrl}/result-local-authority/${selectedMunicipality.authorityIdentifier}`;
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
    <div class="description-container">
      <p class="description-text">
        {{ $t('municipalityPage.description') }}
      </p>
    </div>
    <div class="selectors-container">
      <div class="municipality-selection">
        <label for="municipality-select-1">{{ $t('municipalityPage.selectMunicipality') }}:</label>
        <select id="municipality-select-1" v-model="selectedMunicipality1Id" @change="fetchBothMunicipalityVotes">
          <option value="" disabled>{{ $t('municipalityPage.selectMunicipalityPlaceholder') }}</option>
          <option v-for="municipality in municipalities" :key="municipality.id" :value="municipality.id">
            {{ municipality.authorityName }}
          </option>
        </select>
      </div>
      <div class="municipality-selection">
        <label for="municipality-select-2">{{ $t('municipalityPage.selectMunicipality') }}:</label>
        <select id="municipality-select-2" v-model="selectedMunicipality2Id" @change="fetchBothMunicipalityVotes">
          <option value="" disabled>{{ $t('municipalityPage.selectMunicipalityPlaceholder') }}</option>
          <option v-for="municipality in municipalities" :key="municipality.id" :value="municipality.id">
            {{ municipality.authorityName }}
          </option>
        </select>
      </div>
    </div>
    <div class="results-container">
      <div class="results" v-if="municipality1Votes.length > 0">
        <h3>{{ $t('municipalityPage.municipality1') }}</h3>
        <ul class="results-table">
          <li v-for="vote in municipality1Votes" :key="vote.id">
            <span>{{ $t('municipalityPage.party') }}: {{ vote.affiliation.registeredName }}</span>
            <span>{{ $t('municipalityPage.votes') }}: {{ vote.validVotes }}</span>
          </li>
        </ul>
      </div>
      <div class="results" v-if="municipality2Votes.length > 0">
        <h3>{{ $t('municipalityPage.municipality2') }}</h3>
        <ul class="results-table">
          <li v-for="vote in municipality2Votes" :key="vote.id">
            <span>{{ $t('municipalityPage.party') }}: {{ vote.affiliation.registeredName }}</span>
            <span>{{ $t('municipalityPage.votes') }}: {{ vote.validVotes }}</span>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped>
.municipality-page {
  font-family: Arial, sans-serif;
  padding: 20px;
  margin: 0 auto;
  color: #333;
}

.description-container {
  margin-bottom: 20px;
  text-align: center;
}

.selectors-container {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.municipality-selection {
  flex: 1;
  display: flex;
  flex-direction: column;
}

label {
  font-weight: bold;
  margin-bottom: 5px;
}

select {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.results-container {
  display: flex;
  justify-content: space-between;
  gap: 20px;
}

.results {
  flex: 1;
  background-color: #f9f9f9;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.results-table {
  max-width: 400px;
  margin: 0 auto;
  list-style: none;
  padding: 0;
}

h3 {
  margin-bottom: 10px;
  font-size: 1.2rem;
  color: #555;
  text-align: center;
}

ul {
  padding: 0;
}

li {
  display: flex;
  justify-content: space-between;
  padding: 8px 0;
  border-bottom: 1px solid #eee;
}

li:last-child {
  border-bottom: none;
}

span {
  font-size: 1rem;
}
</style>