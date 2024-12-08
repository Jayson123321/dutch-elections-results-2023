<template>
  <div>
    <HeaderComponent/>
    <div id="description">
      <p>Deze pagina toont de verkiezingsresultaten per gemeente voor 2023. Selecteer een gemeente om de resultaten te bekijken en sorteer de stemmen op partijnaam of aantal stemmen. Alleen kandidaten die stemmen hebben ontvangen worden weergegeven.</p>
    </div>
    <canvas id="local-authorities-chart"></canvas>
    <div id="titel">
      <h1>Score per stembureau</h1>
      <h1>Verkiezingen 2023 gemeente {{ selectedAuthority?.authorityName }}</h1>
    </div>
    <span class="authority-select"><label for="authority-select">Selecteer een gemeente</label></span>
    <select id="authority-select" v-model="selectedAuthorityId" @change="showAllSelectedAuthorityVotes">
      <option value="" disabled>Selecteer een gemeente</option>
      <option class="authorityList" v-for="authority in localAuthorities" :key="authority.id" :value="authority.id">
        {{ authority.authorityName }}
      </option>
    </select>
    <button v-if="selectedReportingUnitId" @click="fetchPartyVotesByReportingUnitAndAuthorityNumber">Toon stemmen</button>
    <div v-if="votes.length > 0">
      <div id="StembureauName">
        <h3>{{ selectedReportingUnitId ? reportingUnits.find(unit => unit.id === selectedReportingUnitId)?.name : '' }}</h3>
        <table>
          <h2 id="h2uitslag">Uitslag</h2>
          <div id="sort-bar">
            <select id="sort" v-model="sortOrder" @change="showAllSelectedAuthorityVotes">
              <option value="votes">Stemmen</option>
              <option value="name">Partij naam</option>
            </select>
          </div>
          <tbody>
          <tr id="affiliationVote" v-for="(vote, index) in votes" :key="vote.id">
            <td>
              <span class="affiliation-name">{{ index + 1 }}. {{ vote.affiliation.registeredName }}</span>
            </td>
            <td id="totalStemmen"> Totaal: {{ vote.validVotes }} stemmen</td>
            <button class="show-candidates-button" @click="toggleCandidates(vote)">Toon stemmen per kandidaat</button>
            <div v-if="vote.showCandidates && candidateVotes.length > 0">
              <table>
                <tbody>
                <tr v-for="(candidateVote, index) in candidateVotes" :key="candidateVote.id">
                  <td>{{ index + 1 }}. {{ candidateVote.candidateName }}</td>
                  <td id="votes">{{ candidateVote.validVotes }} stemmen</td>
                </tr>
                </tbody>
              </table>
            </div>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="politicalComponent">
      <PoliticalNews/>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue';
import HeaderComponent from "@/components/HeaderComponent.vue";
import { Chart, PieController, ArcElement, Tooltip, Legend } from 'chart.js';
import PoliticalNews from "@/components/PoliticalNews.vue";
import config from "@/config.ts";

Chart.register(PieController, ArcElement, Tooltip, Legend);

export default defineComponent({
  components: {PoliticalNews, HeaderComponent },
  name: 'LocalAuthoritiesResults',
  data() {
    return {
      localAuthorities: [],
      selectedAuthorityId: null,
      selectedAuthority: null,
      selectedReportingUnitId: null,
      votes: [],
      candidateVotes: [],
      reportingUnits: [],
      chart: null,
      sortOrder: 'votes'
    };
  },
  mounted() {
    this.getAuthorities();
  },
  methods: {
    getAuthorities() {
      fetch('http://localhost:8080/api/managing-authorities/getAllAuthorities')
          .then(response => response.json())
          .then(data => {
            this.localAuthorities = data;
          });
    },
    async fetchAuthorityTotalVotes() {
      const selectedAuthority = this.localAuthorities.find(authority => authority.id === this.selectedAuthorityId) || null;
      if (!selectedAuthority) {
        console.log('Geen gemeente geselecteerd');
        return;
      }
      // Als sortOrder votes is, dan sorteer op Votes, anders op name
      try {
        const ChooseEndpoint = this.sortOrder === 'votes'
            ? `${config.apiBaseUrl}/result-local-authority/sortedByVotes/${selectedAuthority.authorityIdentifier}`
            : `${config.apiBaseUrl}/result-local-authority/${selectedAuthority.authorityIdentifier}`;
        const response = await fetch(ChooseEndpoint);
        if (!response.ok) {
          throw new Error('Failed to fetch authority votes');
        }
        return await response.json();
      } catch (error) {
        console.error('Error fetching authority votes:', error);
      }
    },
    async showAllSelectedAuthorityVotes() {
      this.selectedAuthority = this.localAuthorities.find(authority => authority.id === this.selectedAuthorityId) || null;
      if (this.selectedAuthority) {
        console.log(this.selectedAuthority.authorityIdentifier);
        const votes = await this.fetchAuthorityTotalVotes();
        this.votes = this.sortOrder === 'name'
            ? votes.sort((a, b) => a.affiliation.registeredName.localeCompare(b.affiliation.registeredName))
            : votes.sort((a, b) => b.validVotes - a.validVotes);
        // Add showCandidates property to each vote
        this.votes.forEach(vote => vote.showCandidates = false);
        console.log(this.votes);
        this.$nextTick(() => {
          this.renderPieChart();
        });
      } else {
        console.log('Geen gemeente geselecteerd');
      }
    },
    async fetchPartyVotesByReportingUnitAndAuthorityNumber() {
      this.votes = [];
      try {
        let reportingUnit = this.reportingUnits.find(reportingUnit => reportingUnit.id === this.selectedReportingUnitId);
        let authority = this.localAuthorities.find(authority => authority.id === this.selectedAuthorityId);
        const response = await fetch(`${config.apiBaseUrl}/managing-authorities/${reportingUnit.managingAuthorityNumber}/party-votes/${authority.authorityIdentifier}`, {
          method: 'GET'
        });
        if (!response.ok) {
          throw new Error('Failed to fetch party votes for reporting unit');
        }
        this.votes = await response.json();
        this.votes.sort((a, b) => b.validVotes - a.validVotes);
        console.log(this.votes);
        this.$nextTick(() => {
          this.renderPieChart();
        });
      } catch (error) {
        console.error('Error fetching party votes for reporting unit:', error);
      }
    },
    async showCandidates(affiliationId) {
      try {
        const response = await fetch(`${config.apiBaseUrl}/result-local-authority/party/${affiliationId}/authority/${this.selectedAuthority.authorityIdentifier}/sortedByVotes`);
        if (!response.ok) {
          throw new Error('Failed to fetch candidate votes');
        }
        this.candidateVotes = await response.json();
        console.log(this.candidateVotes);
      } catch (error) {
        console.error('Error fetching candidate votes:', error);
      }
    },
    toggleCandidates(vote) {
      vote.showCandidates = !vote.showCandidates;
      if (vote.showCandidates) {
        this.showCandidates(vote.affiliation.id);
      }
    },
    renderPieChart() {
      if (this.chart) {
        this.chart.destroy();
      }

      const chart = document.getElementById('local-authorities-chart').getContext('2d');
      const totalVotes = this.votes.reduce((sum, vote) => sum + vote.validVotes, 0);

      this.chart = new Chart(chart, {
        type: 'pie',
        data: {
          labels: this.votes.map(vote => vote.affiliation.registeredName),
          datasets: [{
            label: 'Stemmen',
            data: this.votes.map(vote => vote.validVotes),
            backgroundColor: this.votes.map(() => `rgba(${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, 0.2)`),
            borderColor: this.votes.map(() => `rgba(${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, ${Math.floor(Math.random() * 255)}, 1)`),
            borderWidth: 1
          }]
        },
        options: {
          plugins: {
            tooltip: {
              callbacks: {
                label: function(context) {
                  const value = context.raw;
                  const percentage = ((value / totalVotes) * 100).toFixed(2);
                  return `${value} stemmen (${percentage}%)`;
                }
              }
            }
          }
        }
      });
    }
  }
});
</script>

<style scoped>
#votes {
  border-bottom: 1px solid ;
}
#affiliationVote {
  border-bottom: 1px solid ;
}
.show-candidates-button {
  padding: 4px 8px;
  font-size: 0.7em;
  border: none;
  border-radius: 3px;

  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
  margin-left: 10px;
}

.show-candidates-button:hover {
  transform: scale(1.05);
}
#totalStemmen{
  font-weight: bold;
}

#description {
  margin: 20px 0;
  font-size: 1.2em;
}

#titel {
  text-align: center;
  margin: 20px 0;
}

#authority-select {
  padding: 10px;
  font-size: 1em;
  border-radius: 5px;
}

button {
  padding: 10px 20px;
  font-size: 1em;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin: 10px 0;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
}

th, td {
  padding: 10px;
  text-align: left;

}

#local-authorities-chart {
  max-width: 500px;
  max-height: 500px;
  margin: 20px auto;
  width: 100%;
  height: auto;
}

#sort-bar {
  margin: 20px 0;
  text-align: right;
}

#sort {
  padding: 10px;
  font-size: 1em;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.politicalComponent {
  margin-top: 40px;
}

@media (max-width: 600px) {
  #local-authorities-chart {
    max-width: 300px;
    max-height: 300px;
  }
}
</style>