# Data Processing
Explain how the data is processed and how data flows through the application. Describe the architecture, components, and technologies used for data processing.

This is done for the better part with actual  examples of code in the project that show clearly how we work in this project.
## Repositories
Repositories are used to interact with the database. They provide methods for CRUD operations and custom queries.

### Example
```java
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findByNameContaining(String name);
}
```

## Services
Services contain the business logic of the application. They use repositories to fetch and manipulate data.

### Example
```java
@Service
public class CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    public List<Candidate> searchCandidates(String searchTerm) {
        return candidateRepository.findByNameContaining(searchTerm);
    }
}
```

## Controllers
Controllers handle HTTP requests and responses. They use services to process data and return results to the client.

### Example
```java
@RestController
@RequestMapping("/api/candidate")
public class CandidateController {
    @Autowired
    private CandidateService candidateService;

    @GetMapping("/search")
    public List<Candidate> searchCandidates(@RequestParam String searchTerm) {
        return candidateService.searchCandidates(searchTerm);
    }
}
```

## Parsers
Parsers are used to transform data from one format to another. They are typically used in services or controllers.

### Example
```java
public class CandidateParser {
    public static CandidateDTO toDTO(Candidate candidate) {
        CandidateDTO dto = new CandidateDTO();
        dto.setId(candidate.getId());
        dto.setName(candidate.getName());
        // other fields
        return dto;
    }
}
```

## Backend
The backend is built using Spring Boot. It handles data processing, business logic, and database interactions.

## Frontend
The frontend is built using Vue.js. It interacts with the backend via API calls and displays data to the user.

### Example
```vue
<template>
  <div>
    <input v-model="searchTerm" @input="fetchData" placeholder="Search candidates..." />
    <ul>
      <li v-for="candidate in candidates" :key="candidate.id">{{ candidate.name }}</li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchTerm: '',
      candidates: []
    };
  },
  methods: {
    async fetchData() {
      const response = await fetch(`http://localhost:8080/api/candidate/search?searchTerm=${this.searchTerm}`);
      this.candidates = await response.json();
    }
  }
};
</script>
```

# API Documentation
Instructions about effectively using and integrating with your API. Lists the application's API endpoints with details about their functionality, request/response formats, parameters, and examples of how to use them.

## Endpoints

### `GET /api/candidate/search`
Search for candidates by name.

#### Parameters
- `searchTerm` (String): The term to search for.

#### Response
- `200 OK`: A list of candidates matching the search term.

#### Example
```bash
curl -X GET "http://localhost:8080/api/candidate/search?searchTerm=John"
```

### `GET /api/candidate/{id}`
Get candidate details by ID.

#### Parameters
- `id` (Long): The ID of the candidate.

#### Response
- `200 OK`: The candidate details.

#### Example
```bash
curl -X GET "http://localhost:8080/api/candidate/1"
```
## Extra info
- In this project we parsed all the data into the database via parsers, used services to interact with the database and controllers to handle the HTTP requests and responses. In the frontend we receive the data and put the logic in the script in the frontend.
We only found out this was not the best way to make the project work on the 13th of Januari so we had no time to switch the entire project up. But the logic in the frontend looks almost identical as it would have looked in the services.
