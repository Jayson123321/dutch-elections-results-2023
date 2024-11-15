<template>
  <div class="container">
    <HeaderComponent />

    <h1>Kandidaat Uitslag</h1>
    <div v-if="candidate">
      <h2>{{ candidate.name }}</h2>
      <p>Affiliatie: {{ candidate.affiliation }}</p>
    </div>

    <h3>Stemmen</h3>
    <ul v-if="votes.length">
      <li v-for="(vote, index) in votes" :key="index">
        <p>Stemmen: {{ vote.validVotes }}</p>
      </li>
    </ul>

    <div v-if="error" class="error">
      {{ error }}
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
      candidate: null,  // Laat het leeg in plaats van een object
      votes: [],
      error: null        // Voeg een error message toe
    };
  },
  async created() {
    const candidateIdentifier = this.$route.params.id; // Gebruik de identifier
    await this.fetchCandidate(candidateIdentifier);
    await this.fetchVotes(candidateIdentifier); // Pas dit aan naar de juiste identifier
  },
  methods: {
    // Haal kandidaatgegevens op
    async fetchCandidate(id) {
      try {
        const response = await fetch(`http://localhost:8080/api/candidate/${id}`);
        if (!response.ok) {
          throw new Error('HTTP error! status: ' + response.status);
        }
        this.candidate = await response.json();
      } catch (error) {
        console.error('Error fetching candidate:', error);
        this.error = "Kandidaatgegevens konden niet worden opgehaald.";
      }
    },
    // Haal stemgegevens op
    async fetchVotes(candidateIdentifier) {
      try {
        const response = await fetch(`http://localhost:8080/api/candidate/votes/${candidateIdentifier}`);
        if (!response.ok) {
          throw new Error('HTTP error! status: ' + response.status);
        }
        this.votes = await response.json();
      } catch (error) {
        console.error('Error fetching votes:', error);
        this.error = "Stemgegevens konden niet worden opgehaald.";
      }
    }
  }
}
</script>

<style scoped>
h1 {
  text-align: center;
  margin-bottom: 20px;
}
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}

h1 {
  text-align: center;
  margin-top: 100px;
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

.error {
  color: red;
  text-align: center;
  margin-top: 20px;
}
</style>
