<template>
  <div>
    <HeaderComponent/>
    <h1>test</h1>
    <span class="authority-select"><label for="authority-select">Selecteer een gemeente</label></span>
    <select id="authority-select" v-model="selectedAuthorityId" @change="showAllSelectedAuthorityVotes">
      <option value="" disabled>Selecteer een gemeente</option>
      <option v-for="authority in localAuthorities" :key="authority.id" :value="authority.id">
        {{ authority.authorityName }}
      </option>
    </select>
    <canvas id="local-authorities-chart"></canvas>
    <table v-if="votes.length">
      <thead>
      <tr>
        <th>Affiliation ID</th>
        <th>Authority ID</th>
        <th>Valid Votes</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="vote in votes" :key="vote.id">
        <td>{{ vote.affiliationId }}</td>
        <td>{{ vote.authorityId }}</td>
        <td>{{ vote.validVotes }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<style>
canvas {
  max-height: 400px;
  margin-top: 2rem;
}
table {
  width: 50%;
  border-collapse: collapse;
  margin-top: 2rem;
}
th, td {
  padding: 8px;
}
th {
  color: coral;
}
</style>

<script>
import {defineComponent} from "vue";
import HeaderComponent from "@/components/HeaderComponent.vue";

export default defineComponent({
  components: {HeaderComponent},
  name : 'LocalAuthoritiesResults',
  data() {
    return {
      localAuthorities: [],
      selectedAuthorityId: null,
      votes: [],
      chart: null
    }
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
            this.createChart();
          })
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
      const selectedAuthority = this.localAuthorities.find(authority => authority.id === this.selectedAuthorityId) || null;
      if (selectedAuthority) {
        console.log(selectedAuthority.authorityIdentifier);
        const votes = await this.fetchAuthorityTotalVotes();
        this.votes = votes.sort((a, b) => b.validVotes - a.validVotes);
        console.log(this.votes);
      } else {
        console.log('Geen gemeente geselecteerd');
      }
    },

    createChart() {
      const ctx = document.getElementById('local-authorities-chart').getContext('2d');
      this.chart = new Chart(ctx, {
        type: 'bar',
        data: {
          labels: this.localAuthorities.map(authority => authority.name),
          datasets: [{
            label: 'Votes',
            data: this.localAuthorities.map(authority => authority.votes),
            backgroundColor: this.localAuthorities.map(() => '#' + (Math.random() * 0xFFFFFF << 0).toString(16))
          }]
        },
        options: {
          scales: {
            y: {
              beginAtZero: true
            }
          }
        }
      })
    }
  }
})
</script>