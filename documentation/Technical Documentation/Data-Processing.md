
## Data Processing

### Overview
In this application, data is processed from XML files and stored in an SQL database. The data is then accessed and manipulated through various layers, including repositories, services, and controllers. This document explains the data flow, validation, and transformation steps involved in the process.

### Data Flow

1. **XML Data Processing**:
    - **XML Processor**: The application uses an XML processor to read and parse XML files. The processor extracts relevant data and transforms it into entities that can be stored in the SQL database.
    - **Data Validation**: During the extraction process, the data is validated to ensure it meets the required format and constraints. Any invalid data is logged and skipped.

2. **Storing Data in SQL Database**:
    - **Entities**: The extracted data is mapped to entity classes, which represent the database tables. These entities are annotated with JPA annotations to define the table structure and relationships.
    - **Repositories**: The application uses Spring Data JPA repositories to handle CRUD operations on the entities. Repositories provide an abstraction layer over the database, allowing for easy data access and manipulation.

3. **Accessing Data**:
    - **Services**: Service classes contain the business logic of the application. They interact with the repositories to perform operations such as fetching, updating, and deleting data. Services also handle any additional data validation and transformation required before the data is sent to the controllers.
    - **Controllers**: Controllers are responsible for handling HTTP requests and returning responses. They use the services to perform the necessary operations and return the data to the client in the desired format (e.g., JSON).

### Components

#### XML Processor
The XML processor reads and parses XML files, validates the data, and transforms it into entities.

```java
import org.springframework.stereotype.Component;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class XmlProcessor {

    public List<Affiliation> processXmlFile(String filePath) {
        List<Affiliation> affiliations = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));
            NodeList nodeList = document.getElementsByTagName("affiliation");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Affiliation affiliation = new Affiliation();
                affiliations.add(affiliation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return affiliations;
    }
}
```

#### Entities
Entities represent the database tables and are used to store the processed data.

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

#### Repositories
Repositories provide an abstraction layer for CRUD operations on the entities.

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {
}
```

#### Services
Services contain the business logic and interact with the repositories.

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AffiliationService {

    @Autowired
    private AffiliationRepository affiliationRepository;

    public List<Affiliation> getAllAffiliations() {
        return affiliationRepository.findAll();
    }

    public void saveAffiliations(List<Affiliation> affiliations) {
        affiliationRepository.saveAll(affiliations);
    }
}
```

#### Controllers
Controllers handle HTTP requests and use the services to perform operations.

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/affiliations")
public class AffiliationController {

    @Autowired
    private AffiliationService affiliationService;

    @GetMapping
    public List<Affiliation> getAllAffiliations() {
        return affiliationService.getAllAffiliations();
    }
}
```

### Summary
The application processes data from XML files, validates and transforms it into entities, and stores it in an SQL database. The data is accessed and manipulated through repositories, services, and controllers, ensuring a clean separation of concerns and maintainable code structure.
