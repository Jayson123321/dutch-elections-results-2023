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
    <ul>
      <li>
        <p>Kandidaatstemmen: {{ candidateVotes }}</p>
      </li>

    </ul>

    <!-- Cirkeldiagram -->
    <canvas id="votesChart" width="400" height="400"></canvas>

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
import { Chart, registerables } from 'chart.js';
import config from "@/config.ts";

Chart.register(...registerables);

export default {
  name: "KandidaatResultaat",
  components: {
    FooterComponent,
    HeaderComponent,
  },
  props: ['id'], // Accepteert kandidaat-ID als prop
  data() {
    return {
      candidate: null,
      candidateVotes: 0,
      totalVotes: 20865452, // Static total votes
      error: null,
    };
  },
  async created() {
    if (this.id) {
      try {
        await this.findCandidateVotesById();
        console.log("Candidate data:", this.candidate);
        await this.fetchCandidateData();
        this.createChart();
      } catch (error) {
        this.error = "Er is een probleem opgetreden bij het ophalen van de gegevens.";
      }
    } else {
      this.error = "Geen kandidaat-ID opgegeven in de route.";
    }
  },
  methods: {
    async fetchCandidateData() {
      try {
        // Fetch candidate details and votes
        const response = await fetch(`${config.apiBaseUrl}/candidate-votes/votes/${this.id}`);
        if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);
        const data = await response.json();

        // this.candidate = data; // Assuming the response contains candidate details
        this.candidateVotes = data.validVotes;
      } catch (error) {
        console.error("Error fetching candidate data:", error);
        this.error = "Failed to fetch candidate data.";
      }
    },
    async findCandidateVotesById() {
      try {
        const response = await fetch(`${config.apiBaseUrl}/candidate/${this.id}`);
        if (!response.ok) throw new Error(`HTTP-fout! Status: ${response.status}`);
        this.candidate = await response.json();
      } catch (error) {
        console.error("Fout bij het ophalen van de kandidaat:", error);
      }
    },
    createChart() {
      const ctx = document.getElementById('votesChart').getContext('2d');
      const otherVotes = this.totalVotes - this.candidateVotes;

      new Chart(ctx, {
        type: 'pie',
        data: {
          labels: ['Kandidaatstemmen', 'Overige stemmen'],
          datasets: [
            {
              data: [this.candidateVotes, otherVotes],
              backgroundColor: ['#36A2EB', '#FF6384'],
            },
          ],
        },
        options: {
          responsive: true,
          plugins: {
            legend: {
              position: 'top',
            },
            title: {
              display: true,
              text: 'Verdeling van geldige stemmen',
            },
          },
        },
      });
    },
  },
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
