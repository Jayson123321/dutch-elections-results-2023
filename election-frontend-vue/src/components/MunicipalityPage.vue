<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
import HeaderComponent from '../components/HeaderComponent.vue';
import FooterComponent from '../components/FooterComponent.vue';

const municipalities = ref([]);
const selectedMunicipality1 = ref(null);
const selectedMunicipality2 = ref(null);
const selectedParty = ref(null);
const partyVotes = ref([]);
const parties = ref([]);


// function o get municipalities
const fetchMunicipalities = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/managing-authorities/getAllAuthorities');
    municipalities.value = response.data;
  } catch (error) {
    console.error('Error fetching municipalities:', error);
  }
};

// get the parties
const fetchParties = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/parties');
    parties.value = response.data;
  } catch (error) {
    console.error('Error fetching parties:', error);
  }
};


// function to get parties and votes
const fetchPartyVotes = async () => {
  if (!selectedMunicipality1.value || !selectedMunicipality2.value || !selectedParty.value) {
    console.error('Selecteer beide gemeentes en een partij.');
    return;
  }

  try {
    const response = await axios.get(`http://localhost:8080/api/votes?municipality1=${selectedMunicipality1.value}&municipality2=${selectedMunicipality2.value}&party=${selectedParty.value}`);
    partyVotes.value = response.data;
  } catch (error) {
    console.error('Error fetching party votes:', error);
  }
};

// gets municipalities when page loads
onMounted(() => {
  fetchMunicipalities();
  fetchParties();
});
</script>


<template>
  <div class="municipality-page">
    <HeaderComponent />
    <h2 class="page-title">Gemeente uitslagen vergelijken</h2>
    <div id="description-container">
      <p id="description-text">
        Vergelijk de gemeentelijke uitslagen met elkaar door twee gemeenten en een partij te selecteren.
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
      <div class="party-selection">
        <h3>Selecteer een partij</h3>
        <select v-model="selectedParty">
          <option value="" disabled>Selecteer een partij</option>
          <option v-for="party in parties" :key="party.id" :value="party.id">
            {{ party.name }}
          </option>
        </select>
      </div>
    </div>
    <button class="compare-button" @click="fetchPartyVotes">Vergelijk stemmen</button>
    <div class="party-votes" v-if="partyVotes.length > 0">
      <h3>Resultaten voor de partij</h3>
      <div v-for="vote in partyVotes" :key="vote.municipalityId" class="vote-item">
        <p><strong>Gemeente:</strong> {{ vote.municipalityName }}</p>
        <p><strong>Stemmen:</strong> {{ vote.votes }}</p>
      </div>
    </div>
    <p v-else-if="partyVotes.length === 0 && selectedMunicipality1 && selectedMunicipality2 && selectedParty">
      Geen stemmen gevonden voor de geselecteerde partij in de geselecteerde gemeenten.
    </p>
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

