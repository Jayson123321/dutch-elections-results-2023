<template>
  <div>
    <HeaderComponent/>
    <div id="description-container">
      <p id="description-text">{{ $t('managingAuthority.description') }}</p>
    </div>
    <div id="titel">
      <h1>{{ $t('managingAuthority.score_per_stembureau') }}</h1>
      <h1>{{ $t('managingAuthority.verkiezingen') }} 2023 {{ selectedAuthority?.authorityName }}</h1>
    </div>
    <canvas id="partyVotesChart"></canvas>
    <div class="filter">
      <h2 id="h2filter">{{ $t('managingAuthority.filter') }}</h2>
      <span class="authority-select"><label for="authority-select">{{ $t('managingAuthority.select_authority') }}</label></span>
      <select id="authority-select" v-model="selectedAuthorityId" @change="showAllSelectedAuthorityVotes">
        <option value="" disabled>{{ $t('managingAuthority.select_authority') }}</option>
        <option v-for="authority in authorities" :key="authority.id" :value="authority.id">
          {{ authority.authorityName }}
        </option>
      </select>
      <span class="reportingUnit-select"><label for="reportingUnit-select">{{ $t('managingAuthority.select_reporting_unit') }}</label></span>
      <div class="autocomplete-container">
        <v-autocomplete
            v-model="selectedReportingUnitId"
            :items="reportingUnits"
            item-title="name"
            item-value="id"
            :placeholder="$t('managingAuthority.search_reporting_unit')"
            persistent-placeholder
            clearable
            transition="scale-transition"
        ></v-autocomplete>
      </div>
      <button v-if="selectedReportingUnitId" @click="fetchPartyVotesByReportingUnitAndAuthorityNumber">{{ $t('managingAuthority.view_votes') }}</button>
    </div>
    <div v-if="partyVotes.length > 0">
      <div id="StembureauName">
        <h3>{{ selectedReportingUnitId ? reportingUnits.find(unit => unit.id === selectedReportingUnitId)?.name : '' }}</h3>
        <table>
          <tbody>
          <tr class="affiliationVote" v-for="(vote, index) in partyVotes" :key="vote.id">
            <td class="affiliation-name">{{ index + 1 }}. {{ vote.affiliation.registeredName }}</td>
            <td id="totalStemmen">{{ $t('managingAuthority.total_votes') }}: {{ vote.validVotes }} {{ $t('managingAuthority.votes') }}</td>
            <td><button class="show-candidates-button" @click="toggleCandidates(vote)">{{ $t('managingAuthority.show_votes_per_candidate') }}</button></td>
            <div v-if="vote.showCandidates">
              <table>
                <tbody>
                <tr v-for="(candidateVote, index) in vote.candidateVotes" :key="index">
                  <td>{{ candidateVote.candidateName }}</td>
                  <td id="votes">{{ candidateVote.validVotes }} {{ $t('managingAuthority.votes') }}</td>
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

<style scoped>
#input-0 {
  background-color: transparent;
  border: none;
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

table {
  border-collapse: collapse;
}

.affiliationVote {
  border-bottom: 1px solid;
}

#votes {
  border-bottom: 1px solid;
  font-weight: bold;
  font-size: medium;
}

#titel {
  text-align: center;
  margin-top: 5%;
  font-size: larger;
}

#h2filter {
  border-bottom: 1px solid;
  width: 30%;
}

label {
  margin-right: 10px;
  display: block;
  margin-top: 20px;
}

select {
  margin-bottom: 20px;
  padding: 5px;
  display: block;
  margin-top: 10px;
}

th, td {
  padding: 8px 12px;
  border: none;
  display: block;
  margin: 20px;
  border-color: white;
}

table {
  border: white;
}

#StembureauName {
  margin-left: 30%;
  font-size: 1.2em;
  font-weight: bold;
  border: none;
}

.affiliation-name {
  font-size: x-large;
}

#authority-select {
  padding: 10px;
  font-size: 1em;
  border-radius: 5px;
}

.authority-select {
  font-weight: bold;
}

.reportingUnit-select {
  font-weight: bold;
}

canvas {
  max-height: 10%;
}

.autocomplete-container {
  width: auto;
  max-width: 35%;
}

.show-candidates-button {
  padding: 8px 16px;
  font-size: small;
  border: 2px solid;
  border-radius: 5px;
  cursor: pointer;
  transition: transform 0.2s ease;
  margin-left: 10px;
}

@media (max-width: 768px) {
  #description-container {
    padding: 10px;
    margin: 10px 0;
  }

  #titel {
    font-size: medium;
    margin-top: 10%;
  }

  #h2filter {
    width: 100%;
  }

  label, select {
    width: 100%;
    margin-top: 10px;
  }

  th, td {
    width: 100%;
    margin: 10px 0;
  }

  #StembureauName {
    margin-left: 0;
    text-align: center;
  }

  .autocomplete-container {
    max-width: 100%;
  }
}
</style>

<script>
import { defineComponent } from 'vue';
import FooterComponent from "@/components/FooterComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";
import PoliticalNews from "@/components/PoliticalNews.vue";
import { Chart, BarController, BarElement, CategoryScale, LinearScale, Title, Tooltip, Legend } from 'chart.js';
import config from "@/config.ts";
import i18n from "@/i181n.js";


Chart.register(BarController, BarElement, CategoryScale, LinearScale, Title, Tooltip, Legend);

export default defineComponent({
  name: 'ManagingAuthorities',
  data() {
    return {
      authorities: [],
      selectedAuthorityId: null,
      selectedAuthority: null,
      selectedReportingUnitId: null,
      partyVotes: [],
      reportingUnits: [],
      stemBureau: [],
      chart: null,
      candidateVotes: []
    };
  },

  mounted() {
    this.fetchAuthorities();
  },
  methods: {
    async fetchAuthorities() {
      try {
        const response = await fetch(`${config.apiBaseUrl}/managing-authorities/getAllAuthorities`);
        if (!response.ok) {
          throw new Error('Failed to fetch authorities');
        }
        this.authorities = await response.json();
      } catch (error) {
        console.error('Error fetching authorities:', error);
      }
    },
    async showAllSelectedAuthorityVotes() {
      this.selectedAuthority = this.authorities.find(authority => authority.id === this.selectedAuthorityId) || null;

      if (this.selectedAuthority) {
        try {
          const response = await fetch(`${config.apiBaseUrl}/managing-authorities/${this.selectedAuthority.authorityIdentifier}`);
          if (!response.ok) {
            throw new Error('Failed to fetch party votes');
          }
          this.partyVotes = await response.json();
          this.renderChart();
        } catch (error) {
          console.error('Error fetching party votes:', error);
        }

        this.fetchReportingUnit();
      }
    },
    async fetchReportingUnit() {
      if (this.selectedAuthority) {
        try {
          const response = await fetch(`${config.apiBaseUrl}/managing-authorities/${this.selectedAuthority.authorityIdentifier}/reporting-units`);
          if (!response.ok) {
            throw new Error('Failed to fetch reporting units');
          }
          this.reportingUnits = await response.json();
          console.log(this.reportingUnits);
        } catch (error) {
          console.error('Error fetching reporting units:', error);
        }
      }
    },
    async fetchPartyVotesByReportingUnitAndAuthorityNumber() {
      this.partyVotes = [];
      try {
        let reportingUnit = this.reportingUnits.find(reportingUnit => reportingUnit.id === this.selectedReportingUnitId);
        let authority = this.authorities.find(authority => authority.id === this.selectedAuthorityId);
        const response = await fetch(`${config.apiBaseUrl}/managing-authorities/${reportingUnit.managingAuthorityNumber}/party-votes/${authority.authorityIdentifier}`, {
          method: 'GET'
        });
        if (!response.ok) {
          throw new Error('Failed to fetch party votes for reporting unit');
        }
        this.partyVotes = await response.json();
        this.partyVotes.forEach(vote => vote.showCandidates = false);
        this.partyVotes.sort((a, b) => b.validVotes - a.validVotes);
        console.log(this.partyVotes);
        this.$nextTick(() => {
          this.renderChart();
        });
      } catch (error) {
        console.error('Error fetching party votes for reporting unit:', error);
      }
    },
    async fetchVotesByCandidate(vote) {
      try {
        const response = await fetch(`${config.apiBaseUrl}/candidate-reporting-unit-votes/reporting-unit/${this.selectedAuthority.authorityIdentifier}/affiliation/${vote.affiliation.id}/managingAuthorityNumber/${this.partyVotes[0].managingAuthorityNumber}`);
        if (!response.ok) {
          throw new Error('Failed to fetch votes by candidate');
        }
        vote.candidateVotes = await response.json();
        console.log(vote.candidateVotes);
      } catch (error) {
        console.error('Error fetching votes by candidate:', error);
      }
    },
    toggleCandidates(vote) {
      vote.showCandidates = !vote.showCandidates;
      if (vote.showCandidates) {
        this.fetchVotesByCandidate(vote);
      } else {
        vote.candidateVotes = [];
      }
    },
    renderChart() {
      if (this.chart) {
        this.chart.destroy();
      }

      const chart = document.getElementById('partyVotesChart').getContext('2d');
      const totalVotes = this.partyVotes.reduce((sum, vote) => sum + vote.validVotes, 0);

      this.chart = new Chart(chart, {
        type: 'bar',
        data: {
          labels: this.partyVotes.map(vote => vote.affiliation.registeredName),
          datasets: [{
            label: 'Stemmen',
            data: this.partyVotes.map(vote => vote.validVotes),
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1
          }]
        },
        options: {
          scales: {
            y: {
              beginAtZero: true
            }
          },
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
        },
      });
    }
  },
  components: {PoliticalNews, FooterComponent, HeaderComponent}
});
</script>