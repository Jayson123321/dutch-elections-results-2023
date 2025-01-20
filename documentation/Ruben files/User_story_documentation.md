
# Introduction
This document provides a high-level overview of the application, its purpose, and key features. It sets the context for the reader and explains what the document covers. The following sections will detail the code written for this project, including user stories, unit tests, and implementation details.

This document is intended for developers, testers, and other stakeholders involved in the project. It provides a comprehensive overview of the application's functionality and implementation details.

The application is a web-based platform that allows users to view information about political candidates, parties, and elections. It provides educational resources, such as quizzes and PowerPoint presentations, to help users learn about the election process in the Netherlands. The application also includes features such as a search bar and pagination to improve user experience and accessibility.

# User Stories
As a user i want to navigate through the all candidates page per 30 candidates to improve loading times and save energy.
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-p-se-ti/semester-1/wiipuujaamee42/-/issues/55

This user story was made to improve loading times and save energy, by only loading 30 candidates at a time. This was done by adding a pagination system to the all candidates page. The page now shows 30 candidates at a time and the user can navigate through the pages by clicking on the next and previous buttons. The user can also see how many pages there are in total and which page they are currently on.

In the data section, there is an array with candidates and parties. There is a variable for the current page and the number of items per page. In the computed section, a calculation is made for the current page and the number of items per page. The total number of pages is also calculated. In the mounted section, the data is fetched. In the methods section, the data is fetched and a function is created for the next and previous pages.
```vue

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

As a user I want a page that will explain how elections work in the Netherlands and how this page will show you how to analyse the results.
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-p-se-ti/semester-1/wiipuujaamee42/-/issues/61

This user story is part of an epic, this epic is made for ethics and a social point of view. This user story was made to create a page that explains how elections work in the Netherlands and how users can analyze the results. The page contains information about the election process in the Netherlands, how political parties win seats, and how the results are analyzed. The page also shows users how to analyze the results of the elections and how to interpret the data. This user story contributes to society by educating people about the elections and how politics work in the Netherlands. This is an ethical user story because it educates people about the elections and how politics work in the Netherlands.

```vue
<template>
  <div>
    <HeaderComponent />
    <div class="educatieve-pagina">
      <h1>Educatieve Ondersteuning</h1>
      <p>Leer meer over verkiezingen, politieke processen en hoe je stem invloed heeft.</p>
      <div class="flexcontainer">
        <div class="flexbox">
          <h2>Uitleg over verkiezingen</h2>
          <p>Lees meer over hoe verkiezingen werken en hoe politieke partijen hun zetels winnen.</p>
          <router-link to="/verkiezingsuitleg" class="cta-link">Lees meer</router-link>
        </div>
        <div class="flexbox">
          <h2>Download PowerPoint</h2>
          <p>Download een PowerPoint-presentatie over verkiezingen.</p>
          <p>Powerpoint gemaakt door Seneca.</p>
          <a href="/educativepowerpoint.pptx" class="cta-link" download>Download PowerPoint</a>
        </div>
        <div class="flexbox">
          <h2>Test je kennis</h2>
          <p>Doe een quiz en ontdek hoeveel je weet over het Nederlandse politieke systeem.</p>
          <router-link to="/Quiz" class="cta-link">Start quiz</router-link>
        </div>
      </div>
    </div>
    <FooterComponent />
  </div>
</template>
```

As a user I want to be able to test my knowledge on the elections by making a quiz, so people and children can learn how politics work in the Netherlands.
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-p-se-ti/semester-1/wiipuujaamee42/-/issues/62

This user story was made to create a quiz that will test the user's knowledge on the elections. The quiz consists of 5 questions and the user can select an answer for each question. After answering all questions, the user can see how many questions they answered correctly and which questions they answered correctly and incorrectly. The user can also see the correct answer for each question.
This user story contributes to society by educating people and children about the elections and how politics work in the Netherlands.
This is an ethical user story because it educates people and children about the elections and how politics work in the Netherlands.

```vue

```java
data() {
    return {
      questions: [
        {
          question: "Op welke kandidaat heeft Nederland het meeste gestemd in de verkiezingen van 2023?",
          options: ["Frans Timmermans", "Geert Wilders", "Dylan Yesilguz", "Pieter Omtzigt"],
          answer: "Dylan Yesilguz"
        },
        {
          question: "Wat was het belangrijkste thema tijdens de verkiezingen van 2023?",
          options: ["Klimaatverandering", "Onderwijs", "Migratie en integratie", "Gezondheidszorg"],
          answer: "Klimaatverandering"
        },
        {
          question: "Welke partij behaalde de meeste zetels in de Tweede Kamer tijdens de verkiezingen van 2023?",
          options: ["D66", "VVD", "FvD", "PVV"],
          answer: "VVD"
        },
        {
          question: "Wie was de leider van de partij GroenLinks tijdens de verkiezingen van 2023?",
          options: ["Jesse Klaver", "Tofik Dibi", "Lilian Marijnissen", "Frans Timmermans"],
          answer: "Jesse Klaver"
        },
        {
          question: "Wat was het doel van de campagne van de partij DENK?",
          options: ["Streven naar meer open grenzen", "Verbeteren van de zorgsector", "Bevorderen van integratie en inclusie", "Verlaging van de belastingen"],
          answer: "Bevorderen van integratie en inclusie"
        }
      ],
      currentQuestionIndex: 0,
      selectedAnswer: '',
      correctAnswers: 0,
      showResults: false,
      answersGiven: []
    };
  },
  methods: {
    nextQuestion() {
      this.answersGiven.push({
        question: this.questions[this.currentQuestionIndex].question,
        selectedAnswer: this.selectedAnswer,
        correctAnswer: this.questions[this.currentQuestionIndex].answer
      });

      if (this.selectedAnswer === this.questions[this.currentQuestionIndex].answer) {
        this.correctAnswers++;
      }

      if (this.currentQuestionIndex < this.questions.length - 1) {
        this.currentQuestionIndex++;
        this.selectedAnswer = '';  // reset selected answer for next question
      } else {
        this.showResults = true;
      }
    },
    isLastQuestion() {
      return this.currentQuestionIndex === this.questions.length - 1;
    },
    getAnswerClass(option) {
      if (this.showResults) {
        return {
          correct: option === this.questions[this.currentQuestionIndex].answer,
          incorrect: option === this.selectedAnswer && option !== this.questions[this.currentQuestionIndex].answer
        };
      }
      return {};
    }
  }
};
</script>

```
As a user I want to be able to teach using this website so I want a page for eductational purposes where I can download a powerpoint presentation.
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-p-se-ti/semester-1/wiipuujaamee42/-/issues/63

This user story was made to create a page where the user can download a PowerPoint presentation about the elections. The user can click on the download button to download the PowerPoint presentation. The PowerPoint presentation was created by Seneca and contains information about the elections and how politics work in the Netherlands. The user can use this PowerPoint presentation to teach others about the elections and how politics work in the Netherlands.
This user story is ethical because it provides educational resources to users and allows them to teach others about the elections and how politics work in the Netherlands.

```vue
 <div class="flexbox">
          <h2>Download PowerPoint</h2>
          <p>Download een PowerPoint-presentatie over verkiezingen.</p>
          <p>Powerpoint gemaakt door Seneca.</p>
          <a href="/educativepowerpoint.pptx" class="cta-link" download>Download PowerPoint</a>

```


## Uitleg over Verkiezingen
- Beschrijving: Een pagina die uitlegt hoe verkiezingen in Nederland werken en hoe gebruikers de resultaten kunnen analyseren.
- Ethische Impact: Door gebruikers te informeren over het verkiezingsproces, bevordert deze user story transparantie en begrip van democratische processen. Dit draagt bij aan een beter geïnformeerde bevolking die bewustere keuzes kan maken tijdens verkiezingen.
  Kennisquiz over Verkiezingen
- Beschrijving: Een quiz die de kennis van gebruikers over verkiezingen test, met feedback over correcte en incorrecte antwoorden. Het is bewezen dat een quiz help bij sneller en efficienter leren(Brian Robert Cook and Andrea Babon 2016.)
- Ethische Impact: Deze user story stimuleert educatie en betrokkenheid bij het politieke systeem. Door gebruikers, inclusief kinderen, te betrekken bij een interactieve leerervaring, wordt hun interesse in politiek en democratie vergroot. Dit kan leiden tot een meer betrokken en geïnformeerde burgerij.
  Downloadbare Educatieve PowerPoint
- Beschrijving: Een pagina waar gebruikers een PowerPoint-presentatie over verkiezingen kunnen downloaden voor educatieve doeleinden.
- Ethische Impact: Het beschikbaar stellen van educatieve middelen zoals een PowerPoint-presentatie ondersteunt leraren, ouders en andere opvoeders bij het onderwijzen over verkiezingen. Dit bevordert de verspreiding van kennis en draagt bij aan de educatie van toekomstige generaties.

### Conclusie
- Deze drie user stories dragen gezamenlijk bij aan een beter geïnformeerde en betrokken samenleving. Door transparantie te bevorderen, educatie te stimuleren en educatieve middelen beschikbaar te stellen, ondersteunen deze functionaliteiten de democratische waarden en dragen ze bij aan een ethisch verantwoorde maatschappij. Het project helpt gebruikers niet alleen om beter geïnformeerde kiezers te worden, maar ook om anderen te onderwijzen over het belang van verkiezingen en politieke betrokkenheid.

### Bronnen:
- Active learning through online quizzes: better learning and less (busy) work (Brian Robert Cook and Andrea Babon 2016.)


As a user I want to be able to search for all candidates by using a search bar, I also want the search bar to be able to search on municipality's and political party's.
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-p-se-ti/semester-1/wiipuujaamee42/-/issues/66

This user story was made to create a search bar on the all candidates page. The user can enter a search query in the search bar and the page will filter the candidates based on the search query. The search bar can search for candidates by name, municipality, and political party. The search bar is case-insensitive and will show the candidates that match the search query. 

```vue
    <input type="text" v-model="searchTerm" placeholder="Zoek kandidaten..." class="search-bar" />
```
```java
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
```