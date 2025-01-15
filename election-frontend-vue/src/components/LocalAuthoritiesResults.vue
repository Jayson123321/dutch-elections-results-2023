<template>
  <div>
    <HeaderComponent/>
    <div id="description-container">
      <p id="description-text">{{ $t('localAuthoritiesResults.description') }}</p>
    </div>
    <canvas id="local-authorities-chart"></canvas>
    <div id="titel">
      <h1>{{ $t('localAuthoritiesResults.score_per_stembureau') }}</h1>
      <h1>{{ $t('localAuthoritiesResults.verkiezingen') }} 2023 {{ selectedAuthority?.authorityName }}</h1>
    </div>
    <span class="authority-select"><label for="authority-select">{{ $t('localAuthoritiesResults.select_authority') }}</label></span><br>
    <select id="authority-select" v-model="selectedAuthorityId" @change="showAllSelectedAuthorityVotes">
      <option value="" disabled>{{ $t('localAuthoritiesResults.select_authority') }}</option>
      <option class="authorityList" v-for="authority in localAuthorities" :key="authority.id" :value="authority.id">
        {{ authority.authorityName }}
      </option>
    </select>
    <button v-if="selectedReportingUnitId" @click="fetchPartyVotesByReportingUnitAndAuthorityNumber">{{ $t('localAuthoritiesResults.view_votes') }}</button>
    <div class="votingTable" v-if="votes.length > 0">
      <div id="StembureauName">
        <h3>{{ selectedReportingUnitId ? reportingUnits.find(unit => unit.id === selectedReportingUnitId)?.name : '' }}</h3>
        <table>
          <div id="sort-bar">
            <select id="sort" v-model="sortOrder" @change="showAllSelectedAuthorityVotes">
              <option value="votes">{{ $t('localAuthoritiesResults.votes') }}</option>
              <option value="name">{{ $t('localAuthoritiesResults.party_name') }}</option>
            </select>
          </div>
          <tbody>
          <tr id="affiliationVote" v-for="(vote, index) in votes" :key="vote.id">
            <td>
              <span class="affiliation-name">{{ index + 1 }}. {{ vote.affiliation.registeredName }}</span>
            </td>
            <td id="totalStemmen">{{ $t('localAuthoritiesResults.total_votes') }}: {{ vote.validVotes }} {{ $t('localAuthoritiesResults.votes') }}</td>
            <button class="show-candidates-button" @click="toggleCandidates(vote)">{{ $t('localAuthoritiesResults.show_votes_per_candidate') }}</button>
            <div v-if="vote.showCandidates && vote.candidateVotes.length > 0">
              <table>
                <tbody>
                <tr v-for="(candidateVote, index) in vote.candidateVotes" :key="candidateVote.id">
                  <td>{{ index + 1 }}. {{ candidateVote.candidateName }}</td>
                  <td id="votes">{{ candidateVote.validVotes }} {{ $t('localAuthoritiesResults.votes') }}</td>
                </tr>
                </tbody>
              </table>
            </div>
          </tr>
          </tbody>
        </table>
      </div>
      <div class="politicalComponent">
        <PoliticalNews/>
      </div>
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
      fetch(`${config.apiBaseUrl}/managing-authorities/getAllAuthorities`)
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
    async showCandidates(vote) {
      try {
        const response = await fetch(`${config.apiBaseUrl}/result-local-authority/party/${vote.affiliation.id}/authority/${this.selectedAuthority.authorityIdentifier}/sortedByVotes`);
        if (!response.ok) {
          throw new Error('Failed to fetch candidate votes');
        }
        vote.candidateVotes = await response.json();
        console.log(vote.candidateVotes);
      } catch (error) {
        console.error('Error fetching candidate votes:', error);
      }
    },
    toggleCandidates(vote) {
      vote.showCandidates = !vote.showCandidates;
      if (vote.showCandidates) {
        this.showCandidates(vote);
      } else {
        vote.candidateVotes = [];
      }
    },
    renderPieChart() {
      if (this.chart) {
        this.chart.destroy();
      }

      const chartElement = document.getElementById('local-authorities-chart');
      if (!chartElement) {
        console.error('Chart element not found');
        return;
      }

      const chartContext = chartElement.getContext('2d');
      if (!chartContext) {
        console.error('Chart context not found');
        return;
      }

      const totalVotes = this.votes.reduce((sum, vote) => sum + vote.validVotes, 0);

      this.chart = new Chart(chartContext, {
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
                label: function (context) {
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
.votingTable {
  float: left;
  margin-left: 15%;
}

#description-container {
  border: 1px solid #ddd;
  padding: 20px;
  margin: 20px 0;
  border-radius: 5px;
}

#description-text {
  font-size: 1.1em;
  line-height: 1.6;
  text-align: center;
}

#votes {
  border-bottom: 1px solid;
  font-weight: bold;
  font-size: medium;
}

#affiliationVote {
  border-bottom: 1px solid;
}

.show-candidates-button {
  padding: 8px 16px;
  font-size: small;
  border: 2px solid;
  border-radius: 5px;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.show-candidates-button:hover {
  transform: scale(1.05);
}

#totalStemmen {
  font-weight: bold;
}

#titel {
  text-align: center;
  margin: 20px 0;
}

#authority-select {
  padding: 10px;
  font-size: 1em;
  border-radius: 5px;
  float: left;
}
.authority-select {
  float: left;
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
  padding: 8px 12px;
  border: none;
  display: block;
  margin: 20px;
  width: 50%;
  border-color: white;
}

#local-authorities-chart {
  max-width: 600px;
  max-height: 600px;
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

@media (max-width: 768px) {
  .votingTable {
    float: none;
    margin-left: 0;
  }

  #description-container {
    padding: 10px;
    margin: 10px 0;
  }

  #titel {
    font-size: medium;
    margin: 10px 0;
  }

  #authority-select {
    width: 100%;
  }

  button {
    width: 100%;
    padding: 10px;
  }

  th, td {
    width: 100%;
    margin: 10px 0;
  }

  #local-authorities-chart {
    max-width: 300px;
    max-height: 300px;
  }

  #sort-bar {
    text-align: center;
  }

  #sort {
    width: 100%;
  }
}
</style>