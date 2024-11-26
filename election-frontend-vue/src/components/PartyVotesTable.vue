<template>
  <div>
    <h1>Party Votes</h1>
    <table v-if="partyVotes.length">
      <thead>
      <tr>
        <th>Reporting Unit Identifier</th>
        <th>Reporting Unit ID</th>
        <th>Affiliation ID</th>
        <th>Valid Votes</th>
        <th>Postal Code</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="vote in partyVotes" :key="vote.id">
        <td>{{ vote.reportingUnitIdentifier }}</td>
        <td>{{ vote.reportingUnitId }}</td>
        <td>{{ vote.affiliationId }}</td>
        <td>{{ vote.validVotes }}</td>
        <td>{{ vote.postalCode }}</td>
      </tr>
      </tbody>
    </table>
    <p v-else>No data available.</p>
    <p v-if="loading">Loading...</p>
    <p v-if="error">{{ error }}</p>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import config from "@/config";

interface Vote {
  id: number;
  reportingUnitId: string;
  affiliationId: string;
  validVotes: number;
  postalCode: string;
  reportingUnitIdentifier: string;
}

export default defineComponent({
  name: 'PartyVotesTable',
  data() {
    return {
      partyVotes: [] as Vote[],
      loading: true,
      error: null as string | null,
    };
  },
  mounted() {
    this.fetchPartyVotes();
  },
  methods: {
    async fetchPartyVotes() {
      this.loading = true;
      try {
        const response = await fetch(`${config.apiBaseUrl}/party-votes`);
        if (!response.ok) {
          throw new Error('Failed to fetch data');
        }
        const data: Vote[] = await response.json();
        console.log(data);
        this.partyVotes = data;
      } catch (err) {
        if (err instanceof Error) {
          this.error = 'Error fetching data: ' + err.message;
        } else {
          this.error = 'Unknown error occurred';
        }
      } finally {
        this.loading = false;
      }
    },
},
});
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  padding: 10px;
  text-align: left;
  border: 1px solid #000000;
}

th {
  background-color: #f2f2f2;
}
</style>
