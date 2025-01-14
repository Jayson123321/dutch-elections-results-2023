# API Documentation

This documentation describes the application's API endpoints, including details about functionality, request/response formats, parameters, and examples.

## Contents
- [Admin API](#admin-api)
- [Affiliations API](#affiliations-api)
- [Authentication API](#authentication-api)
- [Candidate Authority Votes API](#candidate-authority-votes-api)
- [Candidate API](#candidate-api)
- [Candidate Votes API](#candidate-votes-api)
- [Managing Authorities API](#managing-authorities-api)
- [Municipalities API](#municipalities-api)
- [News API](#news-api)
- [Party Votes API](#party-votes-api)
- [Result Local Authority API](#result-local-authority-api)
- [Total Votes API](#total-votes-api)
- [Unban Requests API](#unban-requests-api)
- [User API](#user-api)

---

### Admin API
**Base URL:** `/api/admin`

- **GET `/test`**
    - **Description:** Test the Admin API endpoint.
    - **Response:**
      ```json
      "Admin test API!"
      ```

- **GET `/all`**
    - **Description:** Retrieve a list of all admins.
    - **Response:** List of `Admin` objects.

---

### Affiliations API
**Base URL:** `/api/affiliations`

- **GET `/`**
    - **Description:** Retrieve a list of all affiliations.
    - **Response:** List of `Affiliation` objects.

---

### Authentication API
**Base URL:** `/api/auth`

- **POST `/register`**
    - **Description:** Register a new user.
    - **Request:**
      ```json
      {
        "username": "string",
        "password": "string"
      }
      ```
    - **Response:**
      ```json
      "User registered successfully!"
      ```

- **POST `/login`**
    - **Description:** Log in a user.
    - **Request:**
      ```json
      {
        "username": "string",
        "password": "string"
      }
      ```
    - **Response:**
      ```json
      {
        "token": "string",
        "userid": "string",
        "username": "string"
      }
      ```

---

### Candidate Authority Votes API
**Base URL:** `/api/candidate-authority-votes`

- **GET `/candidate/{candidateId}`**
    - **Description:** Retrieve votes for a specific candidate.
    - **Path Parameter:**
        - `candidateId`: ID of the candidate.
    - **Response:** List of `CandidateAuthorityVotes` objects.

- **GET `/{candidateId}/total-valid-votes`**
    - **Description:** Calculate the total number of valid votes for a candidate.
    - **Path Parameter:**
        - `candidateId`: ID of the candidate.
    - **Response:** Integer (total number of votes).

---

### Candidate API
**Base URL:** `/api/candidate`

- **GET `/`**
    - **Description:** Retrieve candidates by affiliation ID.
    - **Query Parameter:**
        - `affiliationId`: ID of the affiliation.
    - **Response:** List of `Candidate` objects.

- **GET `/all`**
    - **Description:** Retrieve all candidates.
    - **Response:** List of `Candidate` objects.

- **GET `/{id}`**
    - **Description:** Retrieve details of a specific candidate.
    - **Path Parameter:**
        - `id`: ID of the candidate.
    - **Response:** `Candidate` object.

---

### Candidate Votes API
**Base URL:** `/api/candidate-votes`

- **POST `/save`**
    - **Description:** Save vote information for a candidate.
    - **Request:**
      ```json
      {
        "candidateId": "string",
        "votes": 0
      }
      ```
    - **Response:** Saved `CandidateVotes` object.

- **GET `/votes/{id}`**
    - **Description:** Retrieve vote information by ID.
    - **Path Parameter:**
        - `id`: ID of the vote.
    - **Response:** `CandidateVotes` object.

---

### Managing Authorities API
**Base URL:** `/api/managing-authorities`

- **GET `/getAllAuthorities`**
    - **Description:** Retrieve all managing authorities.
    - **Response:** List of `ManagingAuthority` objects.

- **GET `/{authorityIdentifier}`**
    - **Description:** Retrieve party votes by reporting unit ID.
    - **Path Parameter:**
        - `authorityIdentifier`: ID of the authority.
    - **Response:** List of `PartyVotes` objects.

---

### Municipalities API
**Base URL:** `/api/municipalities`

- **GET `/`**
    - **Description:** Retrieve all municipalities.
    - **Response:** List of `Municipality` objects.

---

### News API
**Base URL:** `/api/political-news`

- **GET `/`**
    - **Description:** Retrieve political news articles.
    - **Response:** List of `Article` objects.

---

### Party Votes API
**Base URL:** `/api/party-votes`

- **GET `/reporting-unit/{reportingUnitId}`**
    - **Description:** Retrieve party votes by reporting unit ID.
    - **Path Parameter:**
        - `reportingUnitId`: ID of the reporting unit.
    - **Response:** List of `PartyVotes` objects.

- **GET `/reporting-unit/{reportingUnitId}/sortedByVotes`**
    - **Description:** Retrieve party votes by reporting unit ID sorted by valid votes in descending order.
    - **Path Parameter:**
        - `reportingUnitId`: ID of the reporting unit.
    - **Response:** List of `PartyVotes` objects.

- **GET `/reporting-unit/{reportingUnitId}/sortedByName`**
    - **Description:** Retrieve party votes by reporting unit ID sorted by affiliation name.
    - **Path Parameter:**
        - `reportingUnitId`: ID of the reporting unit.
    - **Response:** List of `PartyVotes` objects.

---

### Result Local Authority API
**Base URL:** `/api/result-local-authority`

- **GET `/{authorityId}`**
    - **Description:** Retrieve total votes for a specific authority.
    - **Path Parameter:**
        - `authorityId`: ID of the authority.
    - **Response:** List of `AuthorityTotalVote` objects.

- **GET `/sortedByVotes/{authorityId}`**
    - **Description:** Retrieve total votes for a specific authority sorted by valid votes in descending order.
    - **Path Parameter:**
        - `authorityId`: ID of the authority.
    - **Response:** List of `AuthorityTotalVote` objects.

- **GET `/party/{affiliationId}/authority/{authorityId}`**
    - **Description:** Retrieve votes by affiliation and authority ID.
    - **Path Parameters:**
        - `affiliationId`: ID of the affiliation.
        - `authorityId`: ID of the authority.
    - **Response:** List of `CandidateAuthorityVotes` objects.

- **GET `/party/{affiliationId}/authority/{authorityId}/sortedByVotes`**
    - **Description:** Retrieve votes by affiliation and authority ID sorted by valid votes in descending order.
    - **Path Parameters:**
        - `affiliationId`: ID of the affiliation.
        - `authorityId`: ID of the authority.
    - **Response:** List of `CandidateAuthorityVotes` objects.

---

### Total Votes API
**Base URL:** `/api/results`

- **GET `/sortedByName`**
    - **Description:** Retrieve total votes sorted by affiliation name alphabetically.
    - **Response:** List of `TotalVote` objects.

- **GET `/sortedByVotes`**
    - **Description:** Retrieve total votes sorted by vote count in descending order.
    - **Response:** List of `TotalVote` objects.

- **GET `/`**
    - **Description:** Retrieve total votes along with their percentages.
    - **Response:** List of `TotalVoteWithPercentageDTO` objects.

---

### Unban Requests API
**Base URL:** `/api/unban-requests`

- **GET `/`**
    - **Description:** Retrieve all unban requests.
    - **Response:** List of `UnbanRequest` objects.

- **PUT `/{id}/approve`**
    - **Description:** Approve an unban request and unban the corresponding user.
    - **Path Parameter:**
        - `id`: ID of the unban request.
    - **Response:** None.

- **DELETE `/{id}/reject`**
    - **Description:** Reject an unban request.
    - **Path Parameter:**
        - `id`: ID of the unban request.
    - **Response:** None.

---

### User API
**Base URL:** `/api/users`

- **GET `/all`**
    - **Description:** Retrieve all users.
    - **Response:** List of `User` objects.

- **DELETE `/{id}`**
    - **Description:** Delete a user by ID.
    - **Path Parameter:**
        - `id`: ID of the user.
    - **Response:** None.

- **GET `/count`**
    - **Description:** Retrieve the total number of users.
    - **Response:** Integer (number of users).

- **PUT `/{id}`**
    - **Description:** Update a user's details.
    - **Path Parameter:**
        - `id`: ID of the user.
    - **Request:** `User` object with updated details.
    - **Response:** None.

- **PUT `/{id}/email`**
    - **Description:** Update a user's email.
    - **Path Parameter:**
        - `id`: ID of the user.
    - **Request:** `User` object with updated email.
    - **Response:** None.

- **PUT `/{id}/ban`**
    - **Description:** Ban a user.
    - **Path Parameter:**
        - `id`: ID of the user.
    - **Response:** None.

- **PUT `/{id}/unban`**
    - **Description:** Unban a user.
    - **Path Parameter:**
        - `id`: ID of the user.
    - **Response:** None.
