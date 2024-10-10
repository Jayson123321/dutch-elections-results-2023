<template>
  <div>
    <h1>Gemeentelijke verkiezingen per stembureau</h1>
    <HeaderComponent/>

    <label for="authority-select">Selecteer een gemeente</label>
    <select id="authority-select" v-model="selectedAuthorityId" @change="showDetails">
      <option value="" disabled>Select an authority</option>
      <option v-for="authority in authorities" :key="authority.id" :value="authority.id">
        {{ authority.authorityName }}
      </option>
    </select>

    <label for="reportingUnit-select">Selecteer een stembureau</label>
    <select id="reportingUnit-select" v-model="selectedReportingUnitId"
            @change="() => fetchPartyVotesByReportingUnitAndAuthorityNumber(selectedReportingUnitId, selectedAuthority.authorityIdentifier)">
      <option value="" disabled>Selecteer eem Stembureau</option>
      <option v-for="reportingUnit in reportingUnits" :key="reportingUnit.id" :value="reportingUnit.id">
        {{ reportingUnit.name }}
      </option>
    </select>


    <div v-if="partyVotes.length > 0">
      <div id="uitslagGemeente">
      <h2>Uitslag gemeente {{ selectedAuthority?.authorityName }}</h2>
      </div>
      <div id="StembureauName">
      <h3>{{ selectedReportingUnitId ? reportingUnits.find(unit => unit.id === selectedReportingUnitId)?.name : '' }}</h3>
      </div>
      <table>
        <tbody>
        <tr v-for="vote in partyVotes" :key="vote.id">
          <td>{{ vote.affiliationId }}</td>
          <td>{{ vote.validVotes }} stemmen</td>
        </tr>
        </tbody>
      </table>

    </div>
  </div>
</template>

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
      selectedAuthority: null,
      selectedReportingUnitId: null,
      partyVotes: [],
      reportingUnits: [],
      stemBureau: []
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

        this.fetchReportingUnit();
      }
    },
    async fetchReportingUnit() {
      if (this.selectedAuthority) {
        try {
          const response = await fetch(`http://localhost:8080/api/managing-authorities/${this.selectedAuthority.authorityIdentifier}/reporting-units`);
          if (!response.ok) {
            throw new Error('Failed to fetch reporting units');
          }
          this.reportingUnits = await response.json();
          console.log(this.reportingUnits);
        } catch (error) {
          console.error('Error fetching reporting units:', error);
        }
      }
    },
    async fetchPartyVotesByReportingUnitAndAuthorityNumber() {
      this.partyVotes = []; // Reset partyVotes before fetching new data
      try {
        let reportingUnit = this.reportingUnits.find(reportingUnit => reportingUnit.id === this.selectedReportingUnitId);
        let authority = this.authorities.find(authority => authority.id === this.selectedAuthorityId);
        const response = await fetch(`http://localhost:8080/api/managing-authorities/${reportingUnit.managingAuthorityNumber}/party-votes/${authority.authorityIdentifier}`, {
          method: 'GET'
        });
        if (!response.ok) {
          throw new Error('Failed to fetch party votes for reporting unit');
        }
        this.partyVotes = await response.json();
        // Sort the partyVotes by validVotes in descending order
        this.partyVotes.sort((a, b) => b.validVotes - a.validVotes);
        console.log(this.partyVotes);
      } catch (error) {
        console.error('Error fetching party votes for reporting unit:', error);
      }
    },
  },
  components: {FooterComponent, HeaderComponent}
});
</script>

<style>
h1 {
  font-size: 2em;
  margin-bottom: 20px;
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
  width: 50%;
  border-collapse: collapse;
  margin-left: 30%;
}

th, td {
  padding: 8px 12px;
  display: block;
  margin: 20px ;
  border: none;
}

thead {
  background-color: #000000;
  color: #ffffff;
}

tbody tr:nth-child(even) {
  background-color: #b1afaf;
}
#uitslagGemeente {
  margin-left: 30%;
  font-size: 1.5em;
  font-weight: bold;
  color: #000000;
}
#StembureauName {
  margin-left: 30%;
  font-size: 1.2em;
  font-weight: bold;
  color: #000000;
}
</style>