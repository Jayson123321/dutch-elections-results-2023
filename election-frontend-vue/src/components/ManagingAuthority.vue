<template>
  <div>
    <HeaderComponent/>
    <div id="titel">
      <h1>Score per stembureau</h1>
      <h1>Verkiezingen 2023 gemeente {{ selectedAuthority?.authorityName }}</h1>
    </div>
    <canvas id="partyVotesChart"></canvas>

    <span class="authority-select"><label for="authority-select">Selecteer een gemeente</label></span>
    <select id="authority-select" v-model="selectedAuthorityId" @change="showAllSelectedAuthorityVotes">
      <option value="" disabled>Selecteer een gemeente</option>
      <option v-for="authority in authorities" :key="authority.id" :value="authority.id">
        {{ authority.authorityName }}
      </option>
    </select>
    <span class="reportingUnit-select"><label for="reportingUnit-select">Selecteer een stembureau</label></span>
    <v-autocomplete
        v-model="selectedReportingUnitId"
        :items="reportingUnits"
        item-title="name"
        item-value="id"
        placeholder="Zoek een stembureau"
        persistent-placeholder
        clearable
    ></v-autocomplete>
    <button @click="fetchPartyVotesByReportingUnitAndAuthorityNumber">Toon stemmen</button>
    <div v-if="partyVotes.length > 0">
      <div id="StembureauName">
        <h3>{{ selectedReportingUnitId ? reportingUnits.find(unit => unit.id === selectedReportingUnitId)?.name : '' }}</h3>
        <table>
          <tbody>
          <tr v-for="(vote, index) in partyVotes" :key="vote.id">
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
  width: 50%;
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
  color: #cccccc;
}

.affiliation-name {
  font-size: x-large;
  color: white;
}

#authority-select {
  border-radius: 15px 15px 0 0;
}

#reportingUnit-select {
  border-radius: 15px 15px 0 0;
  width: 20%;
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
</style>
<script>
import { defineComponent } from 'vue';
import FooterComponent from "@/components/FooterComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";
import { Chart, BarController, BarElement, CategoryScale, LinearScale, Title, Tooltip, Legend } from 'chart.js';

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
      chart: null
    };
  },

  mounted() {
    this.fetchAuthorities();
  },
  methods: {
    async fetchAuthorities() {
      try {
        const response = await fetch('http://localhost:8080/api/managing-authorities/getAllAuthorities');
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
          const response = await fetch(`http://localhost:8080/api/managing-authorities/${this.selectedAuthority.authorityIdentifier}`);
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
          const response = await fetch(`http://localhost:8080/api/managing-authorities/${this.selectedAuthority.authorityIdentifier}/reporting-units`);
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
        const response = await fetch(`http://localhost:8080/api/managing-authorities/${reportingUnit.managingAuthorityNumber}/party-votes/${authority.authorityIdentifier}`, {
          method: 'GET'
        });
        if (!response.ok) {
          throw new Error('Failed to fetch party votes for reporting unit');
        }
        this.partyVotes = await response.json();
        this.partyVotes.sort((a, b) => b.validVotes - a.validVotes);
        console.log(this.partyVotes);
        this.$nextTick(() => {
          this.renderChart();
        });
      } catch (error) {
        console.error('Error fetching party votes for reporting unit:', error);
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
  },
  components: { FooterComponent, HeaderComponent }
});
</script>