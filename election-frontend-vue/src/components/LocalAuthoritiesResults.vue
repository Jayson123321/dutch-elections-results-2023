<template>
  <div>
    <HeaderComponent/>
    <div id="titel">
      <h1>Score per stembureau</h1>
      <h1>Verkiezingen 2023 gemeente {{ selectedAuthority?.authorityName }}</h1>
    </div>

    <span class="authority-select"><label for="authority-select">Selecteer een gemeente</label></span>
    <select id="authority-select" v-model="selectedAuthorityId" @change="showAllSelectedAuthorityVotes">
      <option value="" disabled>Selecteer een gemeente</option>
      <option v-for="authority in authorities" :key="authority.id" :value="authority.id">
        {{ authority.authorityName }}
      </option>
    </select>

    <div v-if="partyVotes.length > 0">
      <div id="StembureauName">
        <h3>{{ selectedAuthority?.authorityName }}</h3>
        <table>
          <tbody>
          <tr v-for="vote in partyVotes" :key="vote.id">
            <td><span class="affiliation-name">{{ vote.affiliation.registeredName }}</span></td>
            <td>{{ vote.validVotes }} stemmen</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style>
#titel {
  text-align: center;
  margin-top: 5%;
  font-size: larger;
  font-family: sans-serif;
}

label {
  margin-right: 10px;
  display: block;
  margin-top: 20px;
}

select {
  margin-bottom: 20px;
  padding: 5px;
  display: block;
  margin-top: 10px;
}

table {
  border-collapse: collapse;
}

th, td {
  padding: 8px 12px;
  display: block;
  margin: 20px;
  border: none;
}

thead {
  background-color: #000000;
  color: #ffffff;
}

tbody tr:nth-child(even) {
  background-color: #b1afaf;
}

#StembureauName {
  margin-left: 30%;
  font-size: 1.2em;
  font-weight: bold;
  color: #000000;
  font-family: sans-serif;
}

.affiliation-name {
  font-size: x-large;
  font-family: sans-serif;
}

#authority-select {
  border-radius: 15px 15px 0 0;
  font-family: sans-serif;
}

#reportingUnit-select {
  border-radius: 15px 15px 0 0;
  font-family: sans-serif;
  width: 20%;
}

.authority-select {
  font-family: sans-serif;
  font-weight: bold;
}

.reportingUnit-select {
  font-family: sans-serif;
  font-weight: bold;

}

canvas {
  max-height: 10%;

}
</style>

<script>
import { defineComponent } from 'vue';
import FooterComponent from "@/components/FooterComponent.vue";
import HeaderComponent from "@/components/HeaderComponent.vue";

export default defineComponent({
  name: 'ManagingAuthorities',
  data() {
    return {
      authorities: [],
      selectedAuthorityId: null,
      selectedAuthority: null,
      partyVotes: []
    };
  },

  mounted() {
    this.fetchAuthorities();
  },
  methods: {
    async fetchAuthorities() {
      try {
        const response = await fetch('http://localhost:8080/api/managing-authorities/getAllAuthorities');
        if (!response.ok) {
          throw new Error('Failed to fetch authorities');
        }
        this.authorities = await response.json();
      } catch (error) {
        console.error('Error fetching authorities:', error);
      }
    },
    async showAllSelectedAuthorityVotes() {
      this.selectedAuthority = this.authorities.find(authority => authority.id === this.selectedAuthorityId) || null;

      if (this.selectedAuthority) {
        try {
          const response = await fetch(`http://localhost:8080/api/managing-authorities/${this.selectedAuthority.authorityIdentifier}`);
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
  components: { FooterComponent, HeaderComponent }
});
</script>