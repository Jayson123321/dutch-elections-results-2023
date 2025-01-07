<script>
import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';
import '/src/assets/base.css';

export default {
  name: "Quiz",
  components: {
    FooterComponent,
    HeaderComponent,
  },
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
      showResults: false
    };
  },
  methods: {
    nextQuestion() {
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
    }
  }
};
</script>

<template>
  <div>
    <HeaderComponent />
    <div class="quiz-pagina">
      <h1>Verkiezingen 2023 Quiz</h1>

      <div v-if="!showResults">
        <div class="quiz-question">
          <h2>{{ questions[currentQuestionIndex].question }}</h2>
          <ul>
            <li v-for="(option, index) in questions[currentQuestionIndex].options" :key="index">
              <button @click="selectedAnswer = option" :class="{'selected': selectedAnswer === option}">
                {{ option }}
              </button>
            </li>
          </ul>
        </div>

        <button @click="nextQuestion" :disabled="!selectedAnswer">Volgende vraag</button>
      </div>

      <div v-if="showResults" class="quiz-results">
        <h2>Je hebt {{ correctAnswers }} van de {{ questions.length }} vragen goed beantwoord!</h2>
        <p>Bedankt voor het doen van de quiz!</p>
      </div>
    </div>
    <FooterComponent />
  </div>
</template>

<style scoped>
.quiz-pagina {
  margin-top: 100px;
  text-align: center;
  padding: 20px;
  background-color: #f0f2f5;
}

.quiz-question {
  background: #ffffff;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.quiz-question h2 {
  color: #ff6600;
}

.quiz-question ul {
  list-style-type: none;
  padding: 0;
}

.quiz-question li {
  margin: 10px 0;
}

.quiz-question button {
  background: #ff6600;
  color: white;
  padding: 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  width: 100%;
  transition: background-color 0.3s;
}

.quiz-question button:hover {
  background-color: #cc5200;
}

.quiz-question button.selected {
  background-color: #cc5200;
}

.quiz-results {
  background: #ffffff;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.1);
}

.quiz-results h2 {
  color: #4caf50;
}
</style>
