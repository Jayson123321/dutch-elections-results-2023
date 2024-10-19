<template>
  <div>
    <HeaderComponent />
    <h1>Partijen en Kandidaten</h1>
    <div class="party-grid">
      <div v-for="affiliation in affiliations" :key="affiliation.id" class="party">
        <h2>
          <router-link :to="{ name: 'kandidatenpagina', params: { id: affiliation.id }}">
            {{ affiliation.registeredName }}
          </router-link>
        </h2>
      </div>
      <div>
        <router-link to="/allekandidatenpagina">
          Alle kandidaten
        </router-link>
      </div>
    </div>
    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';

export default {
  name: "partijenpagina",
  components: {
    FooterComponent,
    HeaderComponent
  },
  data() {
    return {
      affiliations: [],
      showCandidates: {},
      selectedCandidates: {}
    };
  },
  mounted() {
    this.fetchAffiliations();
  },
  methods: {
    async fetchAffiliations() {
      try {
        const response = await fetch('http://localhost:8080/api/affiliations');
        this.affiliations = await response.json();
      } catch (error) {
        console.error('Error fetching affiliations:', error);
      }
    },
  }
}
</script>

<style>
.party-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 20px;
}
.party {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  text-align: center;
}
.party a {
  text-decoration: none;
  color: #000;
}
.party a:hover {
  text-decoration: underline;
}
</style>