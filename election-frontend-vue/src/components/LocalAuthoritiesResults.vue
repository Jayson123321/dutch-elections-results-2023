<template>
  <div>
    <HeaderComponent/>
    <div id="titel">
      <h1>Score per stembureau</h1>
      <h1>Verkiezingen 2023 gemeente {{ selectedAuthority?.authorityName }}</h1>
    </div>
    <canvas id="local-authorities-chart"></canvas>
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
          <tbody>
          <tr v-for="(vote, index) in votes" :key="vote.id">
            <td><span class="affiliation-name">{{ index + 1 }}. {{ vote.affiliation.registeredName }}</span></td>
            <td>{{ vote.validVotes }} stemmen</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style>
#titel {
  text-align: center;
  margin-top: 5%;
  font-size: larger;
}

label {
  margin-right: 10px;
  display: block;
  margin-top: 20px;
  color: var(--color-text);
}

select {
  margin-bottom: 20px;
  padding: 5px;
  display: block;
  margin-top: 10px;
  background-color: var(--color-background-soft);
  color: var(--color-text);
}

th, td {
  padding: 8px 12px;
  border: none;
  display: block;
  margin: 20px;
  width: 50%;
  border-color: var(--color-border);
  color: var(--color-text);
}

table {
  border: var(--color-border);
}

#StembureauName {
  margin-left: 30%;
  font-size: 1.2em;
  font-weight: bold;
  border: none;
  color: var(--color-text);
}

.affiliation-name {
  font-size: x-large;
  color: var(--color-text);
}

#authority-select {
  border-radius: 15px 15px 0 0;
  border: var(--color-border);
  background-color: var(--color-background-soft);
  color: var(--color-text);
}

#reportingUnit-select {
  border-radius: 15px 15px 0 0;
  width: 20%;
  background-color: var(--color-background-soft);
  color: var(--color-text);
}

.authority-select {
  font-weight: bold;
  color: var(--color-text);
  border-color: var(--color-border-hover);
}

.reportingUnit-select {
  font-weight: bold;
  color: var(--color-text);
}

canvas {
  max-height: 10%;
}
</style>

<script>
import { defineComponent } from 'vue';
import HeaderComponent from "@/components/HeaderComponent.vue";
import { Chart, PieController, ArcElement, Tooltip, Legend } from 'chart.js';

Chart.register(PieController, ArcElement, Tooltip, Legend);

export default defineComponent({
  components: { HeaderComponent },
  name: 'LocalAuthoritiesResults',
  data() {
    return {
      localAuthorities: [],
      selectedAuthorityId: null,
      selectedAuthority: null,
      selectedReportingUnitId: null,
      votes: [],
      reportingUnits: [],
      chart: null
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
      try {
        const response = await fetch(`http://localhost:8080/api/result-local-authority/${selectedAuthority.authorityIdentifier}`);
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
        this.votes = votes.sort((a, b) => b.validVotes - a.validVotes);
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
        const response = await fetch(`http://localhost:8080/api/managing-authorities/${reportingUnit.managingAuthorityNumber}/party-votes/${authority.authorityIdentifier}`, {
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