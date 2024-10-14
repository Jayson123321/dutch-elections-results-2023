<template>
  <div>
    <HeaderComponent/>
    <div id="title">
      <h1>Results per Local Authority</h1>
      <h1>Election 2023 for {{ selectedAuthority?.authorityName }}</h1>
    </div>
    <canvas id="localAuthorityResultsChart"></canvas>

    <span class="authority-select"><label for="authority-select">Select a Local Authority</label></span>
    <select id="authority-select" v-model="selectedAuthorityId" @change="showDetails">
      <option value="" disabled>Select an authority</option>
      <option v-for="authority in authorities" :key="authority.id" :value="authority.id">
        {{ authority.authorityName }}
      </option>
    </select>

    <div v-if="results.length > 0">
      <div id="AuthorityName">
        <h3>{{ selectedAuthorityId ? authorities.find(unit => unit.id === selectedAuthorityId)?.name : '' }}</h3>
        <table>
          <tbody>
          <tr v-for="result in results" :key="result.id">
            <td><span class="affiliation-name">{{ result.affiliation.registeredName }}</span></td>
            <td>{{ result.validVotes }} votes</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue';
import FooterComponent from "@/components/FooterComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";
import { Chart, BarController, BarElement, CategoryScale, LinearScale, Title, Tooltip, Legend } from 'chart.js';

Chart.register(BarController, BarElement, CategoryScale, LinearScale, Title, Tooltip, Legend);

export default defineComponent({
  name: 'LocalAuthorityResults',
  data() {
    return {
      authorities: [],
      selectedAuthorityId: null,
      selectedAuthority: null,
      results: [],
      chart: null
    };
  },

  mounted() {
    this.fetchAuthorities();
  },
  methods: {
    async fetchAuthorities() {
      try {
        const response = await fetch('http://localhost:8080/api/local-authorities');
        if (!response.ok) {
          throw new Error('Failed to fetch authorities');
        }
        this.authorities = await response.json();
      } catch (error) {
        console.error('Error fetching authorities:', error);
      }
    },
    async showDetails() {
      this.selectedAuthority = this.authorities.find(auth => auth.id === this.selectedAuthorityId) || null;

      if (this.selectedAuthority) {
        try {
          const response = await fetch(`http://localhost:8080/api/local-authorities/${this.selectedAuthority.authorityIdentifier}/results`);
          if (!response.ok) {
            throw new Error('Failed to fetch results');
          }
          this.results = await response.json();
          this.renderChart();
        } catch (error) {
          console.error('Error fetching results:', error);
        }
      }
    },
    renderChart() {
      // Destroy the previous chart if it exists
      if (this.chart) {
        this.chart.destroy();
      }

      const chart = document.getElementById('localAuthorityResultsChart').getContext('2d');
      this.chart = new Chart(chart, {
        type: 'bar',
        data: {
          labels: this.results.map(result => result.affiliation.registeredName),
          datasets: [{
            label: 'Votes',
            data: this.results.map(result => result.validVotes),
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
          }
        }
      });
    }
  },
  components: { FooterComponent, HeaderComponent }
});
</script>

<style>
#title {
  text-align: center;
  margin-top: 5%;
  font-size: larger;
  font-family: sans-serif;
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

table {
  border-collapse: collapse;
}

th, td {
  padding: 8px 12px;
  display: block;
  margin: 20px;
  border: none;
}

thead {
  background-color: #000000;
  color: #ffffff;
}

tbody tr:nth-child(even) {
  background-color: #b1afaf;
}

#AuthorityName {
  margin-left: 30%;
  font-size: 1.2em;
  font-weight: bold;
  color: #000000;
  font-family: sans-serif;
}

.affiliation-name {
  font-size: x-large;
  font-family: sans-serif;
}

#authority-select {
  border-radius: 15px 15px 0 0;
  font-family: sans-serif;
}

.authority-select {
  font-family: sans-serif;
  font-weight: bold;
}

canvas {
  max-height: 10%;
}
</style>