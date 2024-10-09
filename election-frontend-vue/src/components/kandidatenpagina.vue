<template>
  <div>
    <HeaderComponent />
    <h1>Kandidaten voor Partij {{ affiliationId }}</h1>
    <ul>
      <li v-for="candidate in candidates" :key="candidate.id">
        {{ candidate.candidateName }}
      </li>
    </ul>
    <FooterComponent />
  </div>
</template>

<script>
import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';

export default {
  name: "kandidatenpagina",
  components: {
    FooterComponent,
    HeaderComponent
  },
  data() {
    return {
      affiliationId: this.$route.params.id,
      candidates: []
    };
  },
  mounted() {
    this.fetchCandidates();
  },
  methods: {
    async fetchCandidates() {
      try {
        const response = await fetch(`http://localhost:8080/api/affiliations/${this.affiliationId}/candidates`);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        this.candidates = await response.json();
      } catch (error) {
        console.error('Error fetching candidates:', error);
      }
    }
  }
}
</script>

<style>
body {
  margin-top: 70px;
}
</style>