<template>
  <div>
    <HeaderComponent/>
    <h1>test</h1>
    <span class="authority-select"><label for="authority-select">Selecteer een gemeente</label></span>
    <select id="authority-select" v-model="selectedAuthorityId" @change="showAllSelectedAuthorityVotes">
      <option value="" disabled>Selecteer een gemeente</option>
      <option v-for="authority in localAuthorities" >
        {{ authority.authorityName }}
      </option>
    </select>
    <canvas id="local-authorities-chart"></canvas>
  </div>
</template>

<style>

canvas {
  max-height: 400px;
  margin-top: 2rem;
}
</style>

<script>
import {defineComponent, watch} from "vue";
import HeaderComponent from "@/components/HeaderComponent.vue";

export default defineComponent({
  components: {HeaderComponent},
  name : 'LocalAuthoritiesResults',
  data() {
    return {
      localAuthorities: [],
      selectedAuthorityId: null,
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
  },
  watch: {
    selectedAuthorityId(newVal) {
      console.log('Selected Authority ID:', newVal);
    }
  }
})
</script>