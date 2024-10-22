<template>
  <div>
    <HeaderComponent/>
    <div id="titel">
      <h1>Score per stembureau</h1>
      <h1>Verkiezingen 2023 gemeente {{ selectedAuthority?.authorityName }}</h1>
    </div>

    <span class="authority-select"><label for="authority-select">Selecteer een gemeente</label></span>
    <select id="authority-select" v-model="selectedAuthorityId" @change="showAllSelectedAuthorityVotes">
      <option value="" disabled>Selecteer een gemeente</option>
      <option v-for="authority in authorities" :key="authority.id" :value="authority.id">
        {{ authority.authorityName }}
      </option>
    </select>

    <div v-if="partyVotes.length > 0">
      <div id="StembureauName">
        <h3>{{ selectedAuthority?.authorityName }}</h3>
        <table>
          <tbody>
          <tr v-for="vote in partyVotes" :key="vote.id" class="party-vote-box">
            <td><span class="affiliation-name">{{ vote.affiliation.registeredName }}</span></td>
            <td>{{ vote.validVotes }} stemmen</td>
          </tr>
          </tbody>
        </table>
      </div>
      <PieChart :chartData="chartData" :options="chartOptions"/>
    </div>
  </div>
</template>


<style>
#titel {
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

tbody tr {
  background-color: white;
}

#StembureauName {
  display: flex;
  justify-content: flex-end;
}

.party-vote-box {
  border: 1px solid black;
  padding: 10px;
  margin: 5px;
  background-color:white;
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

<script>
import { defineComponent } from 'vue';
import { Pie } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement } from 'chart.js';
import FooterComponent from "@/components/FooterComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";

ChartJS.register(Title, Tooltip, Legend, ArcElement);

export default defineComponent({
  name: 'LocalAuthoritiesResults',
  components: { FooterComponent, HeaderComponent, PieChart: Pie },
  data() {
    return {
      authorities: [],
      selectedAuthorityId: null,
      selectedAuthority: null,
      partyVotes: [],
      chartData: {
        labels: [],
        datasets: [{
          data: [],
          backgroundColor: []
        }]
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false
      }
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
          this.updateChartData();
        } catch (error) {
          console.error('Error fetching party votes:', error);
        }
      }
    },
    updateChartData() {
      const labels = this.partyVotes.map(vote => vote.affiliation.registeredName);
      const data = this.partyVotes.map(vote => vote.validVotes);
      const backgroundColor = this.partyVotes.map(() => `#${Math.floor(Math.random()*16777215).toString(16)}`);

      this.chartData = {
        labels,
        datasets: [{
          data,
          backgroundColor
        }]
      };
    }
  }
});
</script>