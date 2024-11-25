<template>
  <div class="container">
    <HeaderComponent />

    <h1>Resultaat Kandidaat</h1>

    <!-- Kandidateninformatie weergeven -->
    <div v-if="candidate">
      <h2>Naam: {{ candidate.candidateName }}</h2>
    </div>

    <!-- Stemmenoverzicht -->
    <h3>Stemmen</h3>
    <ul v-if="votes.length">
      <li v-for="(vote, index) in votes" :key="index">
        <p>Aantal stemmen: {{ vote.validVotes }}</p>
      </li>
    </ul>


    <!-- Foutmelding indien aanwezig -->
    <div v-if="error" class="error">
      {{ error }}
    </div>

    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';
import config from "@/config.ts";

export default {
  name: "KandidaatResultaat",
  components: {
    FooterComponent,
    HeaderComponent
  },
  props: ['id'], // Accepteert id als prop
  data() {
    return {
      candidate: null,
      votes: [],
      error: null
    };
  },
  async created() {
    if (this.id) {
      try {
        await this.findCandidateVotesById();
        console.log("Candidate data:", this.candidate);
        await this.fetchVotes();
        console.log("Votes data:", this.votes);
      } catch (error) {
        this.error = "Er is een probleem opgetreden bij het ophalen van de gegevens.";
      }
    } else {
      this.error = "Geen kandidaat-ID opgegeven in de route.";
    }
  },

  methods: {
    async findCandidateVotesById() {
      try {
        const response = await fetch(`${config.apiBaseUrl}/candidate/${this.id}`);
        if (!response.ok) throw new Error(`HTTP-fout! Status: ${response.status}`);
        this.candidate = await response.json();
      } catch (error) {
        console.error("Fout bij het ophalen van de kandidaat:", error);
      }
    },

    async fetchVotes() {
      try {
        const response = await fetch(`${config.apiBaseUrl}/candidate-votes/votes/${this.id}`);
        if (!response.ok) throw new Error(`HTTP-fout! Status: ${response.status}`);
        const data = await response.json();

        // Controleer of 'data' een array is
        this.votes = Array.isArray(data) ? data : [data];
        console.log("Gekregen stemmen:", this.votes); // controleer de output

      } catch (error) {
        console.error("Fout bij het ophalen van stemmen:", error);
      }
    }

  }
};
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
  margin-top: 100px;
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

.error {
  color: red;
  text-align: center;
  margin-top: 20px;
}
</style>
