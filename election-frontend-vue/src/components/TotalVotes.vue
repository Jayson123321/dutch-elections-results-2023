<template>
  <div>
    <HeaderComponent />
    <h1 class="titel">Tweede kamer uitslagen</h1>
    <div class="totalResults">
      <div class="chartContainer">
            <canvas id="electionResults" ref="electionResults"></canvas>
      </div>
      <table>
        <tbody>
        <tr v-for="result in results" :key="result.id">
          <td>{{ result.affiliationName }}</td>
          <td>{{ result.totalVotes }}</td>
          <td>{{ result.percentage ? result.percentage.toFixed(2) : 0 }}%</td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="politicalComponent">
      <PoliticalNews/>
    </div>
    <FooterComponent />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import HeaderComponent from "@/components/HeaderComponent.vue";
import { Chart, DoughnutController, PieController, ArcElement, Tooltip, Legend } from "chart.js";
import PoliticalNews from "@/components/PoliticalNews.vue";

Chart.register(DoughnutController, ArcElement, PieController, Tooltip, Legend);

interface Result {
  id: number;
  affiliationName: string;
  totalVotes: number;
  percentage?: number;
}

export default defineComponent({
  name: "totalResults",
  components: {
    PoliticalNews,
    HeaderComponent,
  },
  data() {
    return {
      results: [] as Result[],
      chart: null as Chart<'pie', any[], any> | null,
    };
  },
  methods: {
    async fetchResults() {
      try {
        const response = await fetch('https://wiipuujaamee42-backend.onrender.com/api/results');
        if (!response.ok) {
          throw new Error('Failed to fetch results');
        }
        this.results = await response.json();
        this.results.sort((a, b) => b.totalVotes - a.totalVotes);
        this.$nextTick(() => {
          this.renderChart();
        });
      } catch (error) {
        console.error('Error fetching results:', error);
      }
    },
    renderChart() {
      if (this.chart) {
        this.chart.destroy();
      }

      const ctx = (this.$refs.electionResults as HTMLCanvasElement).getContext('2d');
      if (!ctx) {
        console.error('Failed to get canvas context');
        return;
      }

      const colors = [
        '#C0392B', '#74E600', '#36A2EB', '#99198C', '#9966FF', '#FF9F40', 'pink', '#661100', '#117733', '#882255',
        '#332288', '#44AA99', '#FF3D00', '#3D1F0A', '#454416', 'gray', 'red', '#black', '#A62800', '#9B59B6',
        '#F1C40F', '#f83964', '#FF9F40', '#ff6691', '#7F8C8D', '#163BF5'
      ];

      const totalVotes = this.results.reduce((sum, result) => sum + result.totalVotes, 0);

      this.chart = new Chart(ctx, {
        type: 'pie',
        data: {
          labels: this.results.map(result => result.affiliationName),
          datasets: [{
            label: 'Valid Votes',
            data: this.results.map(result => result.totalVotes),
            backgroundColor: colors,
            borderColor: colors.map(color => color.replace('0.2', '1')),
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
          plugins: {
            legend: {
              position: 'top',
            },
            tooltip: {
              callbacks: {
                label: function(context) {
                  const result = context.chart.data.labels ? context.chart.data.labels[context.dataIndex] : '';
                  const totalVotesForResult = context.raw as number;
                  const percentage = ((totalVotesForResult / totalVotes) * 100).toFixed(2);
                  return `${result}: ${percentage}% (${totalVotesForResult}) Stemmen`;
                }
              }
            },
          }
        }
      });
    }
  },
  mounted() {
    this.fetchResults();
  }
});
</script>

<style scoped>
.totalResults {
  margin-top: 100px;
  display: flex;
  justify-content: space-between;
}

.titel {
  display: flex;
  justify-content: center;
  text-align: center;
  margin-top: 100px;
}

.chartContainer {
  width: 35%;
  float: right;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  max-height: 600px;
  transition: transform 0.5s ease;
  margin-left: 100px;
}

.chartContainer:hover {
  transform: scale(1.05);
}

canvas {
  max-width: 100%;
  max-height: 100%;
}

table {
  width:25%;
  border-collapse: collapse;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  transition: transform 0.2s ease;
  margin-right: 150px;
}

.table:hover {
  transform: scale(1.05);
}

th, td {
  padding: 8px;
  border: none;
}

th {
  background-color: #f2f2f2;
  text-align: left;
}

tr:hover {
  background-color: #7e7d7d;
  transform: scale(1.05);
  transition: transform 0.2s ease;
}
</style>