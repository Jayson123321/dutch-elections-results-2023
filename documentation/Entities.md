### `AuthorityTotalVote`

**Beschrijving**: Deze entiteit vertegenwoordigt de totale geldige stemmen per autoriteit. Het is belangrijk om deze entiteit te maken omdat het een cruciaal onderdeel is van de gegevensstructuur van de applicatie. Door de attributen en hun relaties duidelijk te beschrijven, kunnen andere ontwikkelaars de structuur en het doel van deze entiteit beter begrijpen.

**Waarom deze entiteit maken**:
- **Gegevensbeheer**:  Gestructureerde manier om de totale geldige stemmen per autoriteit te beheren.
- **Relaties**: Het legt de relatie vast tussen een autoriteit en de stemmen die aan een bepaalde partij zijn toegekend.
- 
**Attributen**:
- `id`: Unieke identifier voor de autoriteit totale stemmen.
- `affiliation`: De partij of groepering waartoe de stemmen behoren.
- `authorityId`: De identifier van de autoriteit.
- `validVotes`: Het aantal geldige stemmen.

**Code**:
```java
package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "authority_total_votes")
public class AuthorityTotalVote {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "affiliation_id", referencedColumnName = "affiliation_id")
    private Affiliation affiliation;

    @Lob
    @Column(name = "authority_id")
    private String authorityId;

    @Column(name = "valid_votes")
    private Integer validVotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public Integer getValidVotes() {
        return validVotes;
    }

    public void setValidVotes(Integer validVotes) {
        this.validVotes = validVotes;
    }
}
```
### `Affiliation`

**Beschrijving**: Deze entiteit vertegenwoordigt een partij of groepering waaraan stemmen zijn toegekend. Het is belangrijk om deze entiteit te maken omdat het een cruciaal onderdeel is van de gegevensstructuur van de applicatie. Door de attributen en hun relaties duidelijk te beschrijven, kunnen andere ontwikkelaars de structuur en het doel van deze entiteit beter begrijpen.

**Waarom deze entiteit maken**:
- **Gegevensbeheer**: Gestructureerde manier om partijen of groeperingen te beheren.
- **Relaties**: Het legt de relatie vast tussen een partij en de kandidaten die aan deze partij zijn verbonden.

**Attributen**:
- `id`: Unieke identifier voor de partij.
- `affiliationId`: Unieke identifier voor de partij.
- `registeredName`: De geregistreerde naam van de partij.
- `candidates`: Lijst van kandidaten die aan deze partij zijn verbonden.

**Code**:
```java
package com.election.backendjava.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "affiliation")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Affiliation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "affiliation_id", unique = true, nullable = false)
    private String affiliationId;

    @Column(name = "registered_name", nullable = false)
    private String registeredName;

    @OneToMany(mappedBy = "affiliation")
    private List<Candidate> candidates;

    public Affiliation() {}

    public Affiliation(String affiliationId, String registeredName) {
        this.affiliationId = affiliationId;
        this.registeredName = registeredName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(String affiliationId) {
        this.affiliationId = affiliationId;
    }

    public String getRegisteredName() {
        return registeredName;
    }

    public void setRegisteredName(String registeredName) {
        this.registeredName = registeredName;
    }
}
```

### `Candidate`

**Beschrijving**: Deze entiteit vertegenwoordigt een kandidaat die deelneemt aan de verkiezingen.

**Attributen**:
- `id`: Unieke identifier voor de kandidaat.
- `name`: Naam van de kandidaat.
- `affiliation`: De partij of groepering waartoe de kandidaat behoort.

**Code**:
```java
package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "candidate")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "affiliation_id", referencedColumnName = "affiliation_id")
    private Affiliation affiliation;

    public Candidate() {}

    public Candidate(String name, Affiliation affiliation) {
        this.name = name;
        this.affiliation = affiliation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }
}
```

### `CandidateVotes`

**Beschrijving**: Deze entiteit vertegenwoordigt de stemmen die een kandidaat heeft ontvangen.

**Attributen**:
- `id`: Unieke identifier voor de kandidaatstemmen.
- `candidate`: De kandidaat die de stemmen heeft ontvangen.
- `votes`: Het aantal stemmen dat de kandidaat heeft ontvangen.

**Code**:
```java
package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "candidate_votes")
public class CandidateVotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private Candidate candidate;

    @Column(name = "votes", nullable = false)
    private Integer votes;

    public CandidateVotes() {}

    public CandidateVotes(Candidate candidate, Integer votes) {
        this.candidate = candidate;
        this.votes = votes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
```

### `ManagingAuthority`

**Beschrijving**: Deze entiteit vertegenwoordigt een autoriteit die verantwoordelijk is voor het beheer van de verkiezingen.

**Attributen**:
- `id`: Unieke identifier voor de autoriteit.
- `name`: Naam van de autoriteit.

**Code**:
```java
package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "managing_authority")
public class ManagingAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public ManagingAuthority() {}

    public ManagingAuthority(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

### `PartyVotes`

**Beschrijving**: Deze entiteit vertegenwoordigt de stemmen die een partij heeft ontvangen.

**Attributen**:
- `id`: Unieke identifier voor de partijstemmen.
- `affiliation`: De partij die de stemmen heeft ontvangen.
- `votes`: Het aantal stemmen dat de partij heeft ontvangen.

**Code**:
```java
package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "party_votes")
public class PartyVotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "affiliation_id", referencedColumnName = "affiliation_id")
    private Affiliation affiliation;

    @Column(name = "votes", nullable = false)
    private Integer votes;

    public PartyVotes() {}

    public PartyVotes(Affiliation affiliation, Integer votes) {
        this.affiliation = affiliation;
        this.votes = votes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
```

### `TotalVote`

**Beschrijving**: Deze entiteit vertegenwoordigt het totale aantal stemmen dat is uitgebracht.

**Attributen**:
- `id`: Unieke identifier voor de totale stemmen.
- `totalVotes`: Het totale aantal stemmen.

**Code**:
```java
package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "total_vote")
public class TotalVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_votes", nullable = false)
    private Integer totalVotes;

    public TotalVote() {}

    public TotalVote(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }
}
```

### `User`

**Beschrijving**: Deze entiteit vertegenwoordigt een gebruiker van de applicatie.

**Attributen**:
- `id`: Unieke identifier voor de gebruiker.
- `username`: Gebruikersnaam van de gebruiker.
- `password`: Wachtwoord van de gebruiker.

**Code**:
```java
package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```