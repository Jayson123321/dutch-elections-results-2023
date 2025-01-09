Display of the documentation and explanations for user story's.

As a user i want to navigate through the all candidates page per 30 candidates to improve loading times and save energy.
https://gitlab.fdmci.hva.nl/semester-3-hbo-ict/onderwijs/student-projecten/2024-2025/out-p-se-ti/semester-1/wiipuujaamee42/-/issues/55

This user story was made to improve loading times and save energy, by only loading 30 candidates at a time. This was done by adding a pagination system to the all candidates page. The page now shows 30 candidates at a time and the user can navigate through the pages by clicking on the next and previous buttons. The user can also see how many pages there are in total and which page they are currently on.


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

