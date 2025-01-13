<template>
  <div class="container">
    <HeaderComponent />
    <h1>{{ $t('partijenPagina.title') }}</h1>
    <div class="party-grid">
      <div class="all-candidates box">
        <router-link to="/allekandidatenpagina">
          {{ $t('partijenPagina.allCandidates') }}
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
/* Algemene stijlen voor de container */
.container {
  max-width: 1200px;
  padding: 20px;
  font-family: Arial, sans-serif;
  margin: 50px auto 100px auto;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center; /* Zorgt ervoor dat de inhoud in het midden staat */
}

/* Stijlen voor de lijst */
.party-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr); /* Aangepast voor 2 boxen */
  gap: 20px;
  justify-content: center;
}

/* Algemene stijlen voor de boxen */
.box {
  padding: 15px;
  border: 1px solid #ccc;
  border-radius: 8px;
  text-align: center;
  background-color: #f9f9f9;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s, box-shadow 0.2s;
  max-width: 300px;
  align-items: center;
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
  max-width: 600px;
}
</style>