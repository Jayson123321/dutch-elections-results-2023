<script>
import {defineComponent} from 'vue';
import FooterComponent from "@/components/FooterComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";

export default defineComponent({
  name: 'ManagingAuthorities',
  data() {
    return {
      authorities: [],
      selectedAuthorityId: null,
      partyVotes: [],
      selectedAuthority: null,
    };
  },
  mounted() {
    this.fetchAuthorities();
  },
  methods: {
    async fetchAuthorities() {
      try {
        const response = await fetch('http://localhost:8080/api/managing-authorities');
        if (!response.ok) {
          throw new Error('Failed to fetch authorities');
        }
        this.authorities = await response.json();
      } catch (error) {
        console.error('Error fetching authorities:', error);
      }
    },
    async showDetails() {
      this.selectedAuthority = this.authorities.find(auth => auth.id === this.selectedAuthorityId) || null;

      if (this.selectedAuthority) {
        try {
          const response = await fetch(`http://localhost:8080/api/managing-authorities/${this.selectedAuthority.authorityIdentifier}/party-votes`);
          if (!response.ok) {
            throw new Error('Failed to fetch party votes');
          }
          this.partyVotes = await response.json();
        } catch (error) {
          console.error('Error fetching party votes:', error);
        }
      }
    }
  },
  components: {FooterComponent, HeaderComponent},
FooterComponent
});
</script>
<template>
  <div>
    <h1>Gemeentelijke verkiezingen per stembureau</h1>
    <HeaderComponent />
    <label for="authority-select">Selecteer een gemeente</label>
    <select id="authority-select" v-model="selectedAuthorityId" @change="showDetails">
      <option value="" disabled>Select an authority</option>
      <option v-for="authority in authorities" :key="authority.id" :value="authority.id">
        {{ authority.authorityName }}
      </option>
    </select>

    <div v-if="partyVotes.length > 0">
      <h2>Uitslag gemeente {{ selectedAuthority?.authorityName }}</h2>
      <table>
        <thead>
        <tr>
          <th>Reporting Unit Identifier</th>
          <th>Valid Votes</th>
          <th>Affiliation ID</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="vote in partyVotes" :key="vote.id">
          <td>{{ vote.reportingUnitIdentifier }}</td>
          <td>{{ vote.validVotes }}</td>
          <td>{{ vote.affiliationId }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<style>
h1 {
  font-size: 2em;
  margin-bottom: 20px;
}

label {
  margin-right: 10px;
}

select {
  margin-bottom: 20px;
  padding: 5px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  padding: 8px 12px;
  border: 1px solid #ddd;
}

thead {
  background-color: #000000;
  color: #ffffff;
}

tbody tr:nth-child(even) {
  background-color: #b1afaf;
}
</style>
