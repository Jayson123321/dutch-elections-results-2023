<template>
  <div class="container">
    <HeaderComponent />

    <h1>Resultaat Kandidaat</h1>

    <!-- Kandidateninformatie weergeven -->
    <div v-if="candidate">
      <h2>Naam: {{ candidate.candidateName }}</h2>
    </div>

    <!-- Stemmenoverzicht -->
    <h3>Stemmen</h3>
    <ul>
      <li>
        <p>Kandidaatstemmen: {{ candidateVotes }}</p>
      </li>

    </ul>
    <div>
      <!-- Gemeente selectie -->
      <label for="authority-select">Kies een gemeente:</label>
      <select id="authority-select" v-model="selectedAuthority" @change="filterVotesByAuthority">
        <option value="" disabled>Kies een gemeente</option>
        <option v-for="vote in votesPerAuthority" :key="vote.authorityName" :value="vote.authorityName">
          {{ vote.authorityName }}
        </option>

      </select>



      <!-- Gefilterde resultaten -->
      <h3 v-if="selectedAuthority">Resultaten voor {{ selectedAuthority }}</h3>
      <table v-if="filteredVotes.length > 0">
        <thead>
        <tr>
          <th>Kandidaat</th>
          <th>Stemmen</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="vote in filteredVotes" :key="vote.id">
          <td>{{ candidate.candidateName }}</td>
          <td>{{ vote.validVotes }}</td>
        </tr>
        </tbody>
      </table>
      <p v-else>Geen stemmen gevonden voor deze gemeente.</p>
    </div>



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
  props: ['id'], // Accepteert kandidaat-ID als prop
  data() {
    return {
      candidate: null,
      candidateVotes: 0,
      votesPerAuthority: [], // Alle stemmen per gemeente
      selectedAuthority: null, // Geselecteerde gemeente
      filteredVotes: [], // Gefilterde stemmen voor de geselecteerde gemeente
      totalVotes: 10475203, // Static total votes
      error: null,
    };
  }


  ,
  async created() {
    if (this.id) {
      try {
        await this.findCandidateVotesById();
        await this.fetchCandidateData(); // Landelijke stemmen
        await this.fetchCandidateVotesByAuthority(); // Stemmen per gemeente
        this.filterVotesByAuthority(); // Filter stemmen per gemeente
      } catch (error) {
        this.error = "Er is een probleem opgetreden bij het ophalen van de gegevens.";
      }
    } else {
      this.error = "Geen kandidaat-ID opgegeven in de route.";
    }
  }
  ,
  methods: {
    async fetchCandidateVotesByAuthority() {
      try {
        const response = await fetch(`http://localhost:8080/api/candidate-authority-votes/candidate/${this.id}`);
        if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);

        const data = await response.json();
        console.log("Received votes per authority:", data); // Controleer de data hier
        this.votesPerAuthority = data;
      } catch (error) {
        console.error("Error fetching candidate votes per authority:", error);
        this.error = "Failed to fetch candidate votes per authority.";
      }
    }
    ,

    filterVotesByAuthority() {
      if (this.selectedAuthority) {
        this.filteredVotes = this.votesPerAuthority.filter(vote =>
            vote.authorityName === this.selectedAuthority
        );
      } else {
        this.filteredVotes = [];
      }
    }

    ,

    async fetchCandidateData() {
      try {
        // Fetch candidate details and national-level votes
        const response = await fetch(`http://localhost:8080/api/candidate-votes/votes/${this.id}`);
        if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);

        const data = await response.json();
        this.candidateVotes = data.validVotes; // Totale stemmen op nationaal niveau
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
  }
  ,
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