<script>
import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';

export default {
  name: "kandidatenuitslag",
  components: {
    FooterComponent,
    HeaderComponent
  },
  data() {
    return {
      candidate: {},
      votes: []
    };
  },
  async created() {
    const candidateId = this.$route.params.id;
    await this.fetchCandidate(candidateId);
    await this.fetchVotes(candidateId);
  },
  methods: {
    async fetchCandidate(id) {
      try {
        const response = await fetch(`http://localhost:8080/api/candidate/${id}`);
        if (!response.ok) {
          throw new Error('HTTP error! status: ' + response.status);
        }
        this.candidate = await response.json();
      } catch (error) {
        console.error('Error fetching candidate:', error);
      }
    },
    async fetchVotes(candidateId) {
      try {
        const response = await fetch(`http://localhost:8080/api/candidate/all/${candidateId}`);
        if (!response.ok) {
          throw new Error('HTTP error! status: ' + response.status);
        }
        this.votes = await response.json();
      } catch (error) {
        console.error('Error fetching votes:', error);
      }
    }
  }
}
</script>

<style scoped>
h1 {
  text-align: center;
  margin-bottom: 20px;
}
.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}

h1 {
  text-align: center;
  color: #333;
  margin-top: 100px;
}

ul {
  list-style: none;
  padding: 0;
}

li {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  margin: 10px 0;
}
</style>