<template>
  <div class="container">
    <HeaderComponent />
    <h1>Uitslagen voor {{ candidate.candidateName }}</h1>
    <div v-if="results.length">
      <ul>
        <li v-for="result in results" :key="result.id">
          <p>Partij: {{ result.partyName }}</p>
          <p>Stemmen: {{ result.votes }}</p>
        </li>
      </ul>
    </div>
    <div v-else>
      <p>Geen uitslagen gevonden voor deze kandidaat.</p>
    </div>
    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';

export default {
  name: "kandidatenuitslag",
  components: {
    FooterComponent,
    HeaderComponent
  },
  data() {
    return {
      candidate: {},
      results: []
    };
  },
  async created() {
    const candidateId = this.$route.params.id;
    await this.fetchCandidate(candidateId);
    await this.fetchResults(candidateId);
  },
  methods: {
    async fetchCandidate(id) {
      try {
        const response = await fetch(`http://localhost:8080/api/candidate/${id}`);
        if (!response.ok) {
          throw new Error('HTTP error! status: ' + response.status);
        }
        this.candidate = await response.json();
      } catch (error) {
        console.error('Error fetching candidate:', error);
      }
    },
    async fetchResults(candidateId) {
      try {
        const response = await fetch(`http://localhost:8080/api/results/candidate/${candidateId}`);
        if (!response.ok) {
          throw new Error('HTTP error! status: ' + response.status);
        }
        this.results = await response.json();
      } catch (error) {
        console.error('Error fetching results:', error);
      }
    }
  }
}
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}

h1 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

ul {
  list-style: none;
  padding: 0;
}

li {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin: 10px 0;
}
</style>