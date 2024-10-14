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
  name: "KandidatenPagina",
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
        console.log(`Fetching candidates for affiliationId: ${this.affiliationId}`);
        const response = await fetch(`http://localhost:8080/api/candidate?affiliationId=${this.affiliationId}`);
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

<style scoped>
body {
  margin-top: 70px;
}
</style>
