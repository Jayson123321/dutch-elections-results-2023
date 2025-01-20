<template>
  <div>
    <HeaderComponent />
    <h1 class="titel">{{ $t('totalResults.tweede_kamer_uitslagen') }}</h1>
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
      <PoliticalNews />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import HeaderComponent from "@/components/HeaderComponent.vue";
import { Chart, DoughnutController, PieController, ArcElement, Tooltip, Legend } from "chart.js";
import type { ChartConfiguration } from "chart.js";
import PoliticalNews from "@/components/PoliticalNews.vue";
import config from '@/config';
import FooterComponent from "@/components/FooterComponent.vue";

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
    FooterComponent,
    PoliticalNews,
    HeaderComponent,
  },
  data() {
    return {
      results: [] as Result[],
      chart: null as Chart<"pie", number[], string> | null,
    };
  },
  methods: {
    async fetchResults() {
      try {
        const response = await fetch(`${config.apiBaseUrl}/results`);
        if (!response.ok) {
          throw new Error('Failed to fetch results');
        }
        const data: Result[] = await response.json();
        this.results = data.sort((a, b) => b.totalVotes - a.totalVotes);
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
        '#D24737', '#74E600', '#137ABE', '#E038CC', '#9966FF', '#FF9F40', 'pink', '#137ABE', '#D24737', '#882255',
        '#2E9900', '#368779', '#FF6738', '#DB29CC', '#91902B', 'gray', '#FF29E2', '#8575FF', '#FF5B29', '#137ABE',
        '#137ABE', '#D24737', '#FF9F40', '#9966FF', '#7F8C8D', '#137ABE'
      ];

      const totalVotes = this.results.reduce((sum, result) => sum + result.totalVotes, 0);

      const config: ChartConfiguration<"pie", number[], string> = {
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
                label: (context) => {
                  const result = context.chart.data.labels?.[context.dataIndex] as string;
                  const totalVotesForResult = context.raw as number;
                  const percentage = ((totalVotesForResult / totalVotes) * 100).toFixed(2);
                  return `${result}: ${percentage}% (${totalVotesForResult}) Stemmen`;
                }
              }
            },
          }
        }
      };

      this.chart = new Chart(ctx, config);
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
  flex-direction: column;
}

.titel {
  display: flex;
  justify-content: center;
  text-align: center;
  margin-top: 100px;
}

.chartContainer {
  width: 100%;
  max-width: 600px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(250, 246, 246, 0.1);
  max-height: 600px;
  transition: transform 0.5s ease;
  margin: 20px 0;
}

.chartContainer:hover {
  transform: scale(1.05);
}

canvas {
  max-width: 100%;
  max-height: 100%;
}

table {
  width: 100%;
  max-width: 600px;
  border-collapse: collapse;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  transition: transform 0.2s ease;
  margin: 20px 0;
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
@media (min-width: 768px) {
  .totalResults {
    flex-direction: row;
    justify-content: space-between;
  }

  .chartContainer {
    width: 35%;
    margin-left: 100px;
  }

  table {
    width: 25%;
    margin-right: 150px;
  }
}
</style>