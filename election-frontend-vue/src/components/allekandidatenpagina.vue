<template>
  <HeaderComponent />

  <div class="container">
    <h1>{{ $t('alleKandidaten.title') }}</h1>
    <input type="text" v-model="searchTerm" :placeholder="$t('alleKandidaten.searchPlaceholder')" class="search-bar" />
    <ul class="candidate-list">
      <li v-for="candidate in paginatedCandidates" :key="candidate.id" class="candidate-item">
        <router-link :to="{ name: 'kandidatenuitslag', params: { id: candidate.id } }">
          <div class="candidate-card">
            <h2>{{ candidate.candidateName }}</h2>
            <p>{{ $t('alleKandidaten.gender') }}: {{ candidate.gender }}</p>
            <p>{{ $t('alleKandidaten.address') }}: {{ candidate.qualifyingAddress }}</p>
            <p>{{ $t('alleKandidaten.party') }}: {{ candidate.affiliation ? candidate.affiliation.registeredName : $t('alleKandidaten.unknown') }}</p>
          </div>
        </router-link>
      </li>
    </ul>
    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 1">{{ $t('alleKandidaten.prev') }}</button>
      <button @click="nextPage" :disabled="currentPage === totalPages">{{ $t('alleKandidaten.next') }}</button>
    </div>
  </div>
</template>

<script>
import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';
import config from "@/config.ts";

export default {
  name: "allekandidatenpagina",
  components: {
    FooterComponent,
    HeaderComponent
  },
  data() {
    return {
      candidates: [],
      affiliations: [],
      currentPage: 1,
      itemsPerPage: 30,
      searchTerm: ''
    };
  },
  computed: {
    filteredCandidates() {
      const searchTermLower = this.searchTerm.toLowerCase();
      return this.candidates.filter(candidate => {
        return candidate.candidateName.toLowerCase().includes(searchTermLower) ||
            candidate.qualifyingAddress.toLowerCase().includes(searchTermLower) ||
            (candidate.affiliation && candidate.affiliation.registeredName.toLowerCase().includes(searchTermLower));
      });
    },
    paginatedCandidates() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.filteredCandidates.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.filteredCandidates.length / this.itemsPerPage);
    }
  },
  mounted() {
    this.fetchData();
  },
  methods: {
    async fetchData() {
      try {
        const [candidatesResponse, affiliationsResponse] = await Promise.all([
          fetch(`${config.apiBaseUrl}/candidate/all`),
          fetch(`${config.apiBaseUrl}/affiliations`),
          fetch(`${config.apiBaseUrl}/candidate/search?searchTerm=${this.searchTerm}`)
        ]);

        if (!candidatesResponse.ok || !affiliationsResponse.ok) {
          throw new Error('HTTP error! status: ' + candidatesResponse.status + ' ' + affiliationsResponse.status);
        }

        const candidates = await candidatesResponse.json();
        const affiliations = await affiliationsResponse.json();

        this.candidates = candidates.map(candidate => {
          const affiliation = affiliations.find(aff => aff.affiliationId === candidate.affiliationId);
          return {...candidate, affiliation};
        });
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
      }
    }
  }
}
</script>

<style scoped>
h1 {
  text-align: center;
}

.container {
  max-width: 1200px;
  padding: 20px;
  font-family: Arial, sans-serif;
  margin: 50px auto 100px auto;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.search-bar {
  width: 100%;
  padding: 12px 20px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 25px;
  font-size: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.search-bar:focus {
  border-color: #007bff;
  box-shadow: 0 4px 8px rgba(0, 123, 255, 0.2);
  outline: none;
}

.search-bar::placeholder {
  color: #999;
  font-style: italic;
}

.candidate-list {
  list-style: none;
  padding: 0;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  justify-content: center;
}

.candidate-item {
  margin: 10px;
}

.candidate-card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  padding: 20px;
  transition: transform 0.2s, box-shadow 0.2s;
}

.candidate-card:hover {
  transform: scale(1.05);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
}

.candidate-card h2 {
  margin-top: 0;
  color: #000000;
}

.candidate-card p {
  margin: 5px 0;
  color: #555;
}

.pagination {
  margin-top: 20px;
  display: flex;
  gap: 10px;
}

.pagination button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
}

.pagination button:disabled {
  background-color: #cccccc;
  cursor: not-allowed;
}

.pagination button:hover:not(:disabled) {
  background-color: #0056b3;
}

@media (max-width: 768px) {
  .candidate-list {
    grid-template-columns: repeat(2, 1fr);
  }

  .candidate-card {
    padding: 15px;
  }

  .pagination {
    flex-direction: column;
    align-items: center;
  }

  .pagination button {
    width: 100%;
    margin-bottom: 10px;
  }
}

@media (max-width: 480px) {
  .candidate-list {
    grid-template-columns: 1fr;
  }

  .candidate-card {
    padding: 10px;
  }
}
</style>