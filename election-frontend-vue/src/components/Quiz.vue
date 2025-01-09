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
          answer: "Frans Timmermans"
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
              <button
                  @click="selectedAnswer = option"
                  :class="{'selected': selectedAnswer === option, 'highlighted': selectedAnswer && selectedAnswer !== option}"
                  :disabled="showResults && selectedAnswer !== option">
                {{ option }}
              </button>
            </li>
          </ul>
        </div>

        <button @click="nextQuestion" :disabled="!selectedAnswer" class="next-button">Volgende vraag</button>
      </div>

      <div v-if="showResults" class="quiz-results">
        <h2>Je hebt {{ correctAnswers }} van de {{ questions.length }} vragen goed beantwoord!</h2>
        <div v-for="(answer, index) in answersGiven" :key="index" class="result-item">
          <p><strong>Vraag {{ index + 1 }}:</strong> {{ answer.question }}</p>
          <p><strong>Jouw antwoord:</strong> <span :class="{'correct': answer.selectedAnswer === answer.correctAnswer, 'incorrect': answer.selectedAnswer !== answer.correctAnswer}">{{ answer.selectedAnswer }}</span></p>
          <p><strong>Correct antwoord:</strong> {{ answer.correctAnswer }}</p>
        </div>
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
  min-height: calc(100vh - 200px);
}

.quiz-question {
  background: #ffffff;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
  margin-bottom: 30px;
  transition: transform 0.3s, box-shadow 0.3s;
}

.quiz-question:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15);
}

.quiz-question h2 {
  color: #ff6600;
  font-size: 24px;
  margin-bottom: 20px;
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
  padding: 15px;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  width: 100%;
  font-size: 16px;
  transition: background-color 0.3s, transform 0.3s;
}

.quiz-question button:hover {
  background-color: #cc5200;
  transform: translateY(-2px);
}

.quiz-question button.selected {
  background-color: #cc5200;
}

.quiz-results {
  background: #ffffff;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
}

.quiz-results:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 30px rgba(0, 0, 0, 0.15);
}

.quiz-results h2 {
  color: #4caf50;
  font-size: 24px;
  margin-bottom: 20px;
}

.result-item {
  margin-bottom: 20px;
}

.correct {
  color: green;
  font-weight: bold;
}

.incorrect {
  color: red;
  font-weight: bold;
}

.highlighted {
  background-color: #f8d7da;
}
</style>
