<template>
  <div>
    <HeaderComponent />
    <h1>Partijen en Kandidaten</h1>
    <div class="party-grid">
      <div class="all-candidates box">
        <router-link to="/allekandidatenpagina">
          Alle kandidaten
        </router-link>
      </div>
      <div v-for="affiliation in affiliations" :key="affiliation.id" class="party box">
        <h2>
          <router-link :to="{ name: 'kandidatenpagina', params: { id: affiliation.id }}">
            {{ affiliation.registeredName }}
          </router-link>
        </h2>
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
/* Algemene stijlen voor de boxen */
.box {
  padding: 15px;
  border: 1px solid #ccc;
  border-radius: 8px;
  text-align: center;
  background-color: #f9f9f9;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s, box-shadow 0.2s;
}

.box:hover {
  transform: scale(1.05);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.box a {
  text-decoration: none;
  color: #000000;
  font-weight: bold;
  font-size: 16px; /* Zorg ervoor dat de tekstgrootte hetzelfde is */
}

.box a:hover {
  text-decoration: underline;
}

/* Specifieke stijlen voor de party en all-candidates boxen */
.party, .all-candidates {
  composes: box;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>