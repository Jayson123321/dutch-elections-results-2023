<template>
  <div class="container">
    <HeaderComponent />

    <h1>Resultaat Kandidaat</h1>

    <div v-if="candidate">
      <h2>Naam: {{ candidate.candidateName }}</h2>
      <p>Totale stemmen: {{ candidateVotes }}</p>
    </div>

    <div>
      <label for="authority-select">Kies een gemeente:</label>
      <select id="authority-select" v-model="selectedAuthority" @change="filterVotesByAuthority">
        <option value="" disabled>Kies een gemeente</option>
        <option v-for="vote in votesPerAuthority" :key="vote.authorityName" :value="vote.authorityName">
          {{ vote.authorityName }}
        </option>
      </select>
    </div>

    <div v-if="selectedAuthority">
      <h3>Resultaten voor {{ selectedAuthority }}</h3>
      <table v-if="filteredVotes.length > 0">
        <tr v-for="vote in filteredVotes" :key="vote.id">
          <td>Naam: {{ candidate.candidateName }}</td>
          <td>Stemmen gemeente: {{ vote.validVotes }}</td>
        </tr>
      </table>
      <p v-else>Geen stemmen gevonden voor deze gemeente.</p>
    </div>

    <div v-if="selectedAuthority">
      <label for="reporting-unit-select">Kies een rapportage-eenheid:</label>
      <select id="reporting-unit-select" v-model="selectedReportingUnit" @change="fetchVotesForReportingUnit(selectedReportingUnit)">
        <option value="" disabled>Kies een rapportage-eenheid</option>
        <option v-for="unit in reportingUnits" :key="unit.reportingUnitId" :value="unit.reportingUnitId">
          {{ unit.reportingUnitName }}
        </option>
      </select>
    </div>

    <div v-if="selectedReportingUnit">
      <h3>Resultaten voor rapportage-eenheid {{ selectedReportingUnit }}</h3>
      <table v-if="filteredReportingUnitVotes.length > 0">
        <tr v-for="vote in filteredReportingUnitVotes" :key="vote.id">
          <td>Naam: {{ candidate.candidateName }}</td>
          <td>Stemmen rapportage-eenheid: {{ vote.validVotes }}</td>
        </tr>
      </table>
      <p v-else>Geen stemmen gevonden voor deze rapportage-eenheid.</p>
    </div>

    <div v-if="error" class="error">{{ error }}</div>
    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';
import { Chart, registerables } from 'chart.js';

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
            `http://localhost:8080/api/candidate-reporting-unit-votes/reporting-unit/${reportingUnitId}/candidate/${this.candidate.id}/affiliation/${this.candidate.affiliationId}`
        );
        if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);
        const data = await response.json();
        this.filteredReportingUnitVotes = data;
      } catch (error) {
        console.error("Error fetching votes for reporting unit:", error);
      }
    }
    ,

    async fetchReportingUnitsByMunicipality(municipalityName) {
      try {
        const response = await fetch(`http://localhost:8080/api/candidate-reporting-unit-votes/municipality/${municipalityName}`);
        if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);
        const data = await response.json();
        console.log("Fetched reporting units:", data); // Log the fetched data

        // Remove duplicates
        const uniqueReportingUnits = Array.from(new Set(data.map(unit => unit.reportingUnitId)))
            .map(id => {
              return data.find(unit => unit.reportingUnitId === id);
            });

        this.reportingUnits = uniqueReportingUnits;
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
        const response = await fetch(`http://localhost:8080/api/candidate-authority-votes/candidate/${this.id}`);
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
    }
    ,

    async fetchFilteredVotes(candidateId, reportingUnitId) {
      try {
        const response = await fetch(
            `http://localhost:8080/api/candidate-reporting-unit-votes/candidate/${candidateId}/reporting-unit/${reportingUnitId}`
        );
        const data = await response.json();
        this.filteredReportingUnitVotes = data;
      } catch (error) {
        console.error("Error fetching filtered votes:", error);
      }
    },


    async fetchCandidateData() {
      try {
        const response = await fetch(`http://localhost:8080/api/candidate-votes/votes/${this.id}`);
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
        const response = await fetch(`http://localhost:8080/api/candidate/${this.id}`);
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
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: grid;
  grid-template-rows: auto 1fr auto;
  gap: 20px;
}

h1 {
  text-align: center;
  color: #333;
  margin-top: 50px;
  margin-bottom: 20px;
  font-size: 2.5em;
}

h2, h3 {
  color: #555;
}

ul {
  list-style: none;
  padding: 0;
}

li {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin: 10px 0;
}

label {
  display: block;
  margin-bottom: 10px;
  font-weight: bold;
}

select {
  width: 100%;
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #ccc;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f2f2f2;
}

tr:hover {
  background-color: #f1f1f1;
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
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.candidate-info h2, .votes-overview h3, .authority-selection h3, .filtered-results h3 {
  margin-top: 0;
}
</style>