Display of the documentation and explanations for user story's.

As a user i want to navigate through the all candidates page per 30 candidates to improve loading times and save energy.
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-p-se-ti/semester-1/wiipuujaamee42/-/issues/55

This user story was made to improve loading times and save energy, by only loading 30 candidates at a time. This was done by adding a pagination system to the all candidates page. The page now shows 30 candidates at a time and the user can navigate through the pages by clicking on the next and previous buttons. The user can also see how many pages there are in total and which page they are currently on.
This is made for ethics.

```java
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
    };
  },
  computed: {
    paginatedCandidates() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.candidates.slice(start, end);
    },
    totalPages() {
      return Math.ceil(this.candidates.length / this.itemsPerPage);
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
          fetch(`${config.apiBaseUrl}/affiliations`)
        ]);

        if (!candidatesResponse.ok || !affiliationsResponse.ok) {
          throw new Error('HTTP error! status: ' + candidatesResponse.status + ' ' + affiliationsResponse.status);
        }

        const candidates = await candidatesResponse.json();
        const affiliations = await affiliationsResponse.json();

        this.candidates = candidates.map(candidate => {
          const affiliation = affiliations.find(aff => aff.affiliationId === candidate.affiliationId);
          return { ...candidate, affiliation };
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
```
