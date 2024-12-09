<template>
  <div>
    <HeaderComponent />
    <div class="chat-container">
      <h1>Forum</h1>
      <p>Discussieer hier over de partijen.</p>
      <div class="forum-form">
        <div class="form-group" :class="{ 'has-error': errors.title }">
          <input
              v-model="newForum.title"
              placeholder="Titel"
              @blur="validateField('title')"
          />
          <span v-if="errors.title" class="error-message">{{ errors.title }}</span>
        </div>

        <div class="form-group" :class="{ 'has-error': errors.description }">
          <textarea
              v-model="newForum.description"
              placeholder="Beschrijving"
              @blur="validateField('description')"
          ></textarea>
          <span v-if="errors.description" class="error-message">{{ errors.description }}</span>
        </div>
        <button @click="submitForum">Forum Posten</button>
        <div v-if="successMessage" class="success-message">
          {{ successMessage }}
        </div>
      </div>

      <div class="forum-list">
        <h2>Geposte Forums</h2>
        <div v-for="forum in forums" :key="forum.forumId" class="forum-item">
          <h3 @click="goToQuestionDetails(forum.forumId)">{{ forum.title }}</h3>
          <p>{{ forum.description }}</p>

          <form @submit.prevent="submitReply(forum.forumId)">
            <div v-for="reply in forum.replies" :key="reply.replyId" class="reply-item">
              <p><strong>{{ reply.username }}:</strong> {{ reply.replyText }}</p>
            </div>
            <br>
  <textarea
      v-model="forum.newReply.replyText"
      placeholder="Beantwoord dit vraag"
      required
  ></textarea>
            <button type="submit">Antwoord Posten</button>
          </form>

        </div>
      </div>
    </div>
    <FooterComponent />
  </div>
</template>

<script>
import HeaderComponent from './HeaderComponent.vue';
import FooterComponent from './FooterComponent.vue';
import axios from 'axios';

export default {
  name: "ForumComponent",
  components: {
    HeaderComponent,
    FooterComponent,
  },
  data() {
    return {
      newForum: {
        title: '',
        description: '',
        user: {
          id: '1', // Dummy user ID
        },
      },
      forums: [],
      errors: {}, // Lijst van bestaande forums
      successMessage: '',
      newReply: {
        username: '',
        replyText: ''
      }
    };
  },
  methods: {
    async fetchForums() {
      try {
        console.log("Ophalen van forums...");
        const response = await fetch('http://localhost:8080/api/usersforum');
        if (!response.ok) {
          throw new Error('Fout bij ophalen van forums: ' + response.statusText);
        }
        this.forums = await response.json();
        console.log('Forums opgehaald:', this.forums); // Debugging

        // Fetch replies for each forum and initialize newReply for each forum
        for (let forum of this.forums) {
          const repliesResponse = await fetch(`http://localhost:8080/api/usersforum/${forum.forumId}/replies`);
          if (repliesResponse.ok) {
            forum.replies = await repliesResponse.json();
          } else {
            forum.replies = [];
          }
          forum.newReply = { replyText: '' }; // Initialize newReply for each forum
        }
      } catch (error) {
        console.error('Fout bij het ophalen van forums:', error);
      }
    },

    // Valideer velden
    validateField(field) {
      if (!this.newForum[field]?.trim()) {
        this.errors[field] = 'Vul dit veld in';
      } else {
        delete this.errors[field];
      }
    },

    async submitForum() {
      // Valideer de velden
      this.validateField('title');
      this.validateField('description');
      if (Object.keys(this.errors).length > 0) return;

      try {
        const response = await axios.post('http://localhost:8080/api/usersforum', this.newForum);
        const createdForum = response.data;

        // Voeg direct het nieuwe forum toe aan de lijst
        this.forums.unshift({
          ...createdForum,
          replies: [], // Initialiseer lege replies voor het nieuwe forum
          newReply: { replyText: '' }, // Voeg een lege newReply toe voor consistentie
        });

        // Reset formulier
       this.newForum = {
        title: '',
       description: '',
        user: { id: '1' },
      };

        // Succesmelding instellen
        this.successMessage = "Forum succesvol geplaatst!";
        setTimeout(() => {
        this.successMessage = '';
       }, 3000);

      } catch (error) {
        console.error('Fout bij het toevoegen van een forum:', error);
        alert('Er is een fout opgetreden bij het plaatsen van het forum.');
      }
    },

    async submitReply(forumId) {
      try {
        const forum = this.forums.find(f => f.forumId === forumId);
        const response = await axios.post(`http://localhost:8080/api/usersforum/${forumId}/replies`, forum.newReply);
        console.log('Reply succesvol toegevoegd:', response.data);

        // Voeg de nieuwe reply toe aan de juiste forum
        if (forum) {
          forum.replies.push(response.data);
        }

        // Reset het reply formulier
        forum.newReply.replyText = '';
      } catch (error) {
        console.error('Fout bij het versturen van reply:', error);
        alert('Er is een fout opgetreden bij het versturen van de reply.');
      }
    },
    goToQuestionDetails(forumId) {
      this.$router.push({ name: 'forum', params: { forumId } });
    }
  },
  mounted() {
    // Haal bestaande forums op wanneer de component wordt geladen

    this.fetchForums();
  },
};
</script>

<style>

.success-message {
  color: green;
  font-weight: bold;
  margin: 10px 0;
  text-align: center;
}

.has-error input,
.has-error textarea {
  border: 2px solid red;
}

.error-message {
  color: red;
  font-size: 14px;
  margin: 5px 0;
}
.error-message {
  color: red;
  font-size: 14px;
  margin: 5px 0;
}
:root {
  --background-color: #f0f0f0;
  --text-color: #333333;
  --card-background-color: #ffffff;
  --input-background-color: #ffffff;
  --button-text-color: #ffffff;
  --reply-background-color: #f9f9f9;
  --link-color: #ff4500;
  --border-color: #cccccc;
}

.chat-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background-color: var(--background-color);
  font-family: Arial, sans-serif;
  color: var(--text-color);
}

.forum-form {
  width: 100%;
  max-width: 800px;
  margin-bottom: 20px;
  background-color: var(--card-background-color);
  padding: 20px;
  border: 2px solid #ff4500;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.forum-form input,
.forum-form textarea {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  //border: 1px solid var(--border-color);
  border-radius: 5px;
  font-size: 16px;
  background-color: var(--input-background-color);
  color: var(--text-color);
}

.forum-form button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #ff4500; /* Reddit-like button color */
  color: var(--button-text-color);
  cursor: pointer;
  font-size: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.forum-list {
  width: 100%;
  max-width: 800px;
}

.forum-item {
  background-color: var(--card-background-color);
  padding: 20px;
  margin-bottom: 20px;
  border: 1px solid var(--border-color);
  border-radius: 5px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.forum-item h3 {
  margin: 0 0 10px;
  font-size: 23px;
  cursor: pointer;
  color: var(--link-color);
}

.forum-item h3:hover {
  text-decoration: underline;
}

.forum-item p {
  margin: 0 0 10px;
  color: var(--text-color);
}

.reply-item {
  background-color: var(--reply-background-color);
  padding: 10px;
  margin-top: 10px;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.reply-item p {
  margin: 0;
  color: var(--text-color);
}

.reply-item strong {
  color: var(--link-color);
}

form {
  margin-top: 20px;
}

form textarea {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid var(--border-color);
  border-radius: 5px;
  font-size: 16px;
  background-color: var(--input-background-color);
  color: var(--text-color);
}

form button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #7a7a7a;
  color: var(--button-text-color);
  cursor: pointer;
  font-size: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}
</style>


