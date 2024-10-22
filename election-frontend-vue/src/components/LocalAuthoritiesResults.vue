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
  margin-top: 6rem;
  font-size: 1.5rem;
  font-weight: bold;
  color: #333;
}

label {
  margin-right: 10px;
  display: block;
  margin-top: 9rem;
  font-weight: bold;
  color: #555;
}

select {
  margin-bottom: 1.5rem;
  padding: 0.5rem;
  display: block;
  margin-top: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-family: 'Arial', sans-serif;
  font-size: 1rem;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;
}

th, td {
  padding: 0.75rem;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

thead {
  background-color: #f4f4f4;
  color: #333;
}

tbody tr:nth-child(even) {
  background-color: #f9f9f9;
}

tbody tr:hover {
  background-color: #f1f1f1;
}

#StembureauName {
  margin-top: 2rem;
  font-size: 1.25rem;
  font-weight: bold;
  color: #333;
}

.party-vote-box {
  border: 1px solid #ddd;
  padding: 0.75rem;
  margin: 0.5rem 0;
  background-color: #fff;
  border-radius: 5px;
}

.affiliation-name {
  font-size: 1rem;
  font-family: 'Arial', sans-serif;
  font-weight: bold;
  color: #555;
}

#authority-select {
  border-radius: 5px;
  font-family: 'Arial', sans-serif;
  font-size: 1rem;
}

.authority-select {
  font-family: 'Arial', sans-serif;
  font-weight: bold;
  color: #555;
}

canvas {
  max-height: 400px;
  margin-top: 2rem;
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
  watch: {
  selectedAuthorityId() {
  console.log('selectedAuthorityId changed:', this.selectedAuthorityId);
  this.showAllSelectedAuthorityVotes();
}
},
  methods: {
  async fetchAuthorities() {
  try {
  const response = await fetch('http://localhost:8080/api/managing-authorities/getAllAuthorities');
  if (!response.ok) {
  throw new Error('Failed to fetch authorities');
}
  this.authorities = await response.json();
  console.log('Fetched authorities:', this.authorities);
} catch (error) {
  console.error('Error fetching authorities:', error);
}
},
  async showAllSelectedAuthorityVotes() {
  console.log('Fetching votes for authority ID:', this.selectedAuthorityId);
  this.selectedAuthority = this.authorities.find(authority => authority.id === this.selectedAuthorityId) || null;
  console.log('Selected authority:', this.selectedAuthority);

  // Reset partyVotes and chartData
  this.partyVotes = [];
  this.chartData = {
  labels: [],
  datasets: [{
  data: [],
  backgroundColor: []
}]
};

  if (this.selectedAuthority) {
  try {
  const response = await fetch(`http://localhost:8080/api/managing-authorities/${this.selectedAuthority.authorityIdentifier}`);
  if (!response.ok) {
  throw new Error('Failed to fetch party votes');
}
  const allPartyVotes = await response.json();
  this.partyVotes = allPartyVotes.slice(0, 25); // Limit to first 25 results
  console.log('Fetched party votes:', this.partyVotes);
  this.updateChartData();
} catch (error) {
  console.error('Error fetching party votes:', error);
}
}
},
  updateChartData() {
  if (!this.chartData) {
  this.chartData = {
  labels: [],
  datasets: [{
  data: [],
  backgroundColor: []
}]
};
}

  const labels = this.partyVotes.map(vote => vote.affiliation.registeredName);
  const data = this.partyVotes.map(vote => vote.validVotes);
  const backgroundColor = this.partyVotes.map(() => `#${Math.floor(Math.random() * 16777215).toString(16)}`);

  this.chartData.labels = labels;
  this.chartData.datasets[0].data = data;
  this.chartData.datasets[0].backgroundColor = backgroundColor;

  console.log('Updated chart data:', this.chartData);
}
}
});
</script>