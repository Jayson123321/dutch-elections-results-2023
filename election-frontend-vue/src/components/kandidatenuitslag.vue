<template>
  <div>
    <HeaderComponent />
    <div class="container">
      <h1>{{ $t('kandidaatUitslag.title') }}</h1>

      <div v-if="candidate">
        <h2>{{ $t('kandidaatUitslag.name') }}: {{ candidate.candidateName }}</h2>
        <p>{{ $t('kandidaatUitslag.totalVotes') }}: {{ candidateVotes }}</p>
      </div>
      <div>
        <label for="authority-select">{{ $t('kandidaatUitslag.selectAuthority') }}:</label>
        <select id="authority-select" v-model="selectedAuthority" @change="filterVotesByAuthority">
          <option value="" disabled>{{ $t('kandidaatUitslag.selectAuthority') }}</option>
          <option v-for="vote in votesPerAuthority" :key="vote.authorityName" :value="vote.authorityName">
            {{ vote.authorityName }}
          </option>
        </select>
      </div>

      <div v-if="selectedAuthority">
        <h3>{{ $t('kandidaatUitslag.resultsFor') }} {{ selectedAuthority }}</h3>
        <table v-if="filteredVotes.length > 0">
          <tr v-for="vote in filteredVotes" :key="vote.id">
            <td>{{ $t('kandidaatUitslag.name') }}: {{ candidate.candidateName }}</td>
            <td>{{ $t('kandidaatUitslag.votesForAuthority') }}: {{ vote.validVotes }}</td>
          </tr>
        </table>
        <p v-else>{{ $t('kandidaatUitslag.noVotesForAuthority') }}</p>
      </div>

      <div v-if="selectedAuthority">
        <label for="reporting-unit-select">{{ $t('kandidaatUitslag.selectReportingUnit') }}:</label>
        <select id="reporting-unit-select" v-model="selectedReportingUnit" @change="fetchVotesForReportingUnit(selectedReportingUnit)">
          <option value="" disabled>{{ $t('kandidaatUitslag.selectReportingUnit') }}</option>
          <option v-for="unit in reportingUnits" :key="unit.reportingUnitId" :value="unit.reportingUnitId">
            {{ unit.reportingUnitName }}
          </option>
        </select>
      </div>

      <div v-if="selectedReportingUnit">
        <h3>{{ $t('kandidaatUitslag.resultsForReportingUnit') }} {{ selectedReportingUnit }}</h3>
        <table v-if="filteredReportingUnitVotes.length > 0">
          <tr v-for="vote in filteredReportingUnitVotes" :key="vote.id">
            <td>{{ $t('kandidaatUitslag.name') }}: {{ candidate.candidateName }}</td>
            <td>{{ $t('kandidaatUitslag.votesForReportingUnit') }}: {{ vote.validVotes }}</td>
          </tr>
        </table>
        <p v-else>{{ $t('kandidaatUitslag.noVotesForReportingUnit') }}</p>
      </div>

      <div v-if="error" class="error">{{ error }}</div>
    </div>
  </div>
</template>

<script>
import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';
import { Chart, registerables } from 'chart.js';
import config from "@/config.ts";

Chart.register(...registerables);

export default {
  name: "KandidaatResultaat",
  components: {
    FooterComponent,
    HeaderComponent,
  },
  props: ['id'],
  data() {
    return {
      candidate: null,
      candidateVotes: 0,
      votesPerAuthority: [],
      selectedAuthority: null,
      filteredVotes: [],
      reportingUnits: [],
      selectedReportingUnit: null,
      filteredReportingUnitVotes: [],
      error: null,
      cache: {},
    };
  },
  async created() {
    if (this.id) {
      try {
        await this.fetchCandidateData();
        await this.findCandidateVotesById();
        await this.fetchCandidateVotesByAuthority();
      } catch (error) {
        console.error("Fout bij initialisatie:", error);
        this.error = "Er is een probleem opgetreden bij het ophalen van de gegevens.";
      }
    } else {
      this.error = "Geen kandidaat-ID opgegeven in de route.";
    }
  },
  methods: {
    async fetchVotesForReportingUnit(reportingUnitId) {
      if (!reportingUnitId) {
        console.warn("Geen rapportage-eenheid geselecteerd.");
        return;
      }
      try {
        const response = await fetch(
            `${config.apiBaseUrl}/candidate-reporting-unit-votes/reporting-unit/${reportingUnitId}/candidate/${this.candidate.id}/affiliation/${this.candidate.affiliationId}`
        );
        if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);
        const data = await response.json();
        this.filteredReportingUnitVotes = data;
      } catch (error) {
        console.error("Error fetching votes for reporting unit:", error);
      }
    },
    async fetchReportingUnitsByMunicipality(municipalityName) {
      if (this.cache[municipalityName]) {
        this.reportingUnits = this.cache[municipalityName];
        return;
      }
      try {
        const response = await fetch(`${config.apiBaseUrl}/candidate-reporting-unit-votes/municipality/${municipalityName}`);
        if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);
        const data = await response.json();
        const uniqueReportingUnits = Array.from(new Set(data.map(unit => unit.reportingUnitId)))
            .map(id => data.find(unit => unit.reportingUnitId === id));
        this.reportingUnits = uniqueReportingUnits;
        this.cache[municipalityName] = uniqueReportingUnits; // Cache de resultaten
      } catch (error) {
        console.error("Error fetching reporting units:", error);
        this.error = "Failed to fetch reporting units.";
      }
    },
    filterVotesByAuthority() {
      if (this.selectedAuthority) {
        this.filteredVotes = this.votesPerAuthority.filter(vote =>
            vote.authorityName === this.selectedAuthority
        );
        this.fetchReportingUnitsByMunicipality(this.selectedAuthority);
      } else {
        this.filteredVotes = [];
        this.reportingUnits = [];
      }
    },
    async fetchCandidateVotesByAuthority() {
      try {
        const response = await fetch(`${config.apiBaseUrl}/candidate-authority-votes/candidate/${this.id}`);
        if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);
        const data = await response.json();
        this.votesPerAuthority = data;
      } catch (error) {
        console.error("Error fetching candidate votes per authority:", error);
        this.error = "Failed to fetch candidate votes per authority.";
      }
    },
    filterVotesByReportingUnit() {
      if (!this.selectedReportingUnit || !this.candidate) {
        console.warn("Geen rapportage-eenheid of kandidaat beschikbaar.");
        this.filteredReportingUnitVotes = [];
        return;
      }
      this.filteredReportingUnitVotes = this.reportingUnits.filter(
          (unit) =>
              unit.reportingUnitId === this.selectedReportingUnit &&
              unit.candidate?.id === this.candidate.id
      );
    },
    async fetchFilteredVotes(candidateId, reportingUnitId) {
      try {
        const response = await fetch(
            `${config.apiBaseUrl}/candidate-reporting-unit-votes/candidate/${candidateId}/reporting-unit/${reportingUnitId}`
        );
        const data = await response.json();
        this.filteredReportingUnitVotes = data;
      } catch (error) {
        console.error("Error fetching filtered votes:", error);
      }
    },
    async fetchCandidateData() {
      try {
        const response = await fetch(`${config.apiBaseUrl}/candidate-votes/votes/${this.id}`);
        if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);
        const data = await response.json();
        this.candidateVotes = data.validVotes;
      } catch (error) {
        console.error("Error fetching candidate data:", error);
        this.error = "Failed to fetch candidate data.";
      }
    },
    async findCandidateVotesById() {
      try {
        const response = await fetch(`${config.apiBaseUrl}/candidate/${this.id}`);
        if (!response.ok) throw new Error(`HTTP-fout! Status: ${response.status}`);
        this.candidate = await response.json();
      } catch (error) {
        console.error("Fout bij het ophalen van de kandidaat:", error);
      }
    },
  },
};
</script>

<style scoped>
:root {
  --background-color-light: #f9f9f9;
  --text-color-light: #333;
  --background-color-dark: #333;
  --text-color-dark: #f9f9f9;
  --element-background-color-light: #fff;
  --element-background-color-dark: #444;
  --border-color-light: #ddd;
  --border-color-dark: #555;
}

[data-theme="light"] {
  --background-color: var(--background-color-light);
  --text-color: var(--text-color-light);
  --element-background-color: var(--element-background-color-light);
  --border-color: var(--border-color-light);
}

[data-theme="dark"] {
  --background-color: var(--background-color-dark);
  --text-color: var(--text-color-dark);
  --element-background-color: var(--element-background-color-dark);
  --border-color: var(--border-color-dark);
}

[data-theme="light"] select {
  background-color: var(--element-background-color-light);
  color: black;
}

[data-theme="dark"] select {
  background-color: black;
  color: white;
}

body {
  background-color: var(--background-color);
  color: var(--text-color);
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
  background-color: var(--element-background-color);
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: grid;
  grid-template-rows: auto 1fr auto;
  gap: 20px;
}

h1 {
  text-align: center;
  color: inherit;
  margin-top: 50px;
  margin-bottom: 20px;
  font-size: 2.5em;
}

h2, h3 {
  color: inherit;
}

ul {
  list-style: none;
  padding: 0;
}

li {
  background: var(--element-background-color);
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin: 10px 0;
}

label {
  display: block;
  margin-bottom: 10px;
  font-weight: bold;
  color: inherit;
}

select {
  width: 100%;
  padding: 10px;
  border-radius: 8px;
  border: 1px solid var(--border-color);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
  background-color: var(--element-background-color);
  color: inherit;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid var(--border-color);
  color: inherit;
}

th {
  background-color: var(--element-background-color);
}

tr:hover {
  background-color: var(--element-background-color);
}

.error {
  color: red;
  text-align: center;
  margin-top: 20px;
  font-weight: bold;
}

.header, .footer {
  grid-column: 1 / -1;
}

.content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.candidate-info, .votes-overview, .authority-selection, .filtered-results {
  background: var(--element-background-color);
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.candidate-info h2, .votes-overview h3, .authority-selection h3, .filtered-results h3 {
  margin-top: 0;
  color: inherit;
}
</style>