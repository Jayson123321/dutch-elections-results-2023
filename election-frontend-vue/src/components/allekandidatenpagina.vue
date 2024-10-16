<template>
  <div class="container">
    <HeaderComponent />
    <h1>Alle Kandidaten</h1>
    <ul class="candidate-list">
      <li v-for="candidate in candidates" :key="candidate.id" class="candidate-item">
        <router-link :to="{ name: 'kandidatenuitslag', params: { id: candidate.id } }">
          <div class="candidate-card">
            <h2>{{ candidate.candidateName }}</h2>
            <p>Gender: {{ candidate.gender }}</p>
            <p>Adres: {{ candidate.qualifyingAddress }}</p>
            <p>Partij: {{ candidate.affiliation ? candidate.affiliation.registeredName : 'Onbekend' }}</p>
          </div>
        </router-link>
      </li>
    </ul>
    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';

export default {
  name: "allekandidatenpagina",
  components: {
    FooterComponent,
    HeaderComponent
  },
  data() {
    return {
      candidates: [],
      affiliations: []
    };
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    async fetchData() {
      try {
        const [candidatesResponse, affiliationsResponse] = await Promise.all([
          fetch(`http://localhost:8080/api/candidate/all`),
          fetch(`http://localhost:8080/api/affiliations`)
        ]);

        if (!candidatesResponse.ok || !affiliationsResponse.ok) {
          throw new Error('HTTP error! status: ' + candidatesResponse.status + ' ' + affiliationsResponse.status);
        }

        const candidates = await candidatesResponse.json();
        const affiliations = await affiliationsResponse.json();

        this.candidates = candidates.map(candidate => {
          const affiliation = affiliations.find(aff => aff.affiliationId === candidate.affiliationId);
          return { ...candidate, affiliation };
        });
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    }
  }
}
</script>

<style scoped>
.container {
  max-width: 1200px;
  padding: 20px;
  font-family: Arial, sans-serif;
  margin-bottom: 100px;
  margin-top: 50px;
}

h1 {
  text-align: center;
  color: #333;
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