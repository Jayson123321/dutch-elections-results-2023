<template>
  <div class="container">
    <HeaderComponent />
    <h1>Kandidaten voor Partij {{ affiliationId }}</h1>
    <ul class="candidate-list">
      <li v-for="candidate in candidates" :key="candidate.id" class="candidate-item">
        <div class="candidate-card">
          <h2>{{ candidate.candidateName }}</h2>
          <p>Gender: {{ candidate.gender }}</p>
          <p>Address: {{ candidate.qualifyingAddress }}</p>
        </div>
      </li>
    </ul>
    <FooterComponent />
  </div>
</template>
<script>
import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';
import config from "@/config.ts";

export default {
  name: "KandidatenPagina",
  components: {
    FooterComponent,
    HeaderComponent
  },
  data() {
    return {
      affiliationId: this.$route.params.id,
      candidates: []
    };
  },
  mounted() {
    this.fetchCandidates();
  },
  methods: {
    async fetchCandidates() {
      try {
        console.log(`Fetching candidates for affiliationId: ${this.affiliationId}`);
        const response = await fetch(`${config.apiBaseUrl}/candidate?affiliationId=${this.affiliationId}`);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        this.candidates = await response.json();
      } catch (error) {
        console.error('Error fetching candidates:', error);
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
  margin-bottom: 20px;
}

.candidate-list {
  list-style: none;
  padding: 0;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

.candidate-item {
  margin: 10px;
}

.candidate-card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  width: 300px;
  transition: transform 0.2s;
}

.candidate-card:hover {
  transform: scale(1.05);
}

.candidate-card h2 {
  margin-top: 0;
  color: #007BFF;
}

.candidate-card p {
  margin: 5px 0;
  color: #555;
}
</style>
