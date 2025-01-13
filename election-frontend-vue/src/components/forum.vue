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
          <p><strong>Posted by: {{ forum.username }}</strong></p>

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
          <button class="delete-button" @click="deleteForum(forum.forumId)">Verwijder post</button>
        </div>
      </div>
      <div class="pagination">
        <button @click="goToPage(currentPage - 1)" :disabled="currentPage <= 0">Vorige</button>
        <span>Pagina {{ currentPage + 1 }} van {{ totalPages }}</span>
        <button @click="goToPage(currentPage + 1)" :disabled="currentPage >= totalPages - 1">Volgende</button>
      </div>
    </div>
    <FooterComponent />
  </div>
</template>

<script>
import axios from 'axios';
import HeaderComponent from './HeaderComponent.vue';
import FooterComponent from './FooterComponent.vue';

export default {
  name: 'Forum',
  components: {
    HeaderComponent,
    FooterComponent
  },
  data() {
    return {
      newForum: {
        title: '',
        description: '',
        user: { id: '' },
      },
      forums: [],
      errors: {},
      successMessage: '',
      currentPage: 0,
      totalPages: 0,
    };
  },
  methods: {
    validateField(field) {
      if (!this.newForum[field]) {
        this.$set(this.errors, field, 'This field is required.');
      } else {
        this.$delete(this.errors, field);
      }
    },
    async fetchForums(page) {
      try {
        const response = await axios.get(`http://localhost:8080/api/usersforum?page=${page}`);
        this.forums = response.data.content;
        this.totalPages = response.data.totalPages;
      } catch (error) {
        console.error('Error fetching forums:', error);
      }
    },
    async submitForum() {
      this.validateField('title');
      this.validateField('description');
      if (Object.keys(this.errors).length > 0) return;

      const token = localStorage.getItem('jwtToken');

      if (!token) {
        alert('You must be logged in to post a forum.');
        return;
      }

      try {
        const response = await axios.post('http://localhost:8080/api/usersforum', this.newForum, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });

        if (response.data && response.data.forum && response.data.username) {
          const createdForum = response.data.forum;
          createdForum.username = response.data.username; // Add the username to the created forum

          this.forums.unshift({
            ...createdForum,
            replies: [],
            newReply: { replyText: '' },
          });

          this.newForum = {
            title: '',
            description: '',
            user: { id: '' },
          };

          this.successMessage = 'Forum posted successfully!';
          setTimeout(() => {
            this.successMessage = '';
          }, 3000);
        } else {
          throw new Error('Invalid response structure');
        }

      } catch (error) {
        console.error('Error posting forum:', error);
        alert('An error occurred while posting the forum.');
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
    },
    async deleteForum(forumId) {
      const confirmed = confirm("Weet je zeker dat je dit forum wilt verwijderen?");
      if (confirmed) {
        try {
          await axios.delete(`http://localhost:8080/api/usersforum/${forumId}`);
          this.forums = this.forums.filter(forum => forum.forumId !== forumId);
          alert('Forum succesvol verwijderd.');
        } catch (error) {
          console.error('Fout bij het verwijderen van het forum:', error);
          alert('Er is een fout opgetreden bij het verwijderen van het forum.');
        }
      }
    },
    goToPage(page) {
      if (page >= 0 && page < this.totalPages) {
        this.fetchForums(page);
      }
    }
  },
  mounted() {
    const token = localStorage.getItem('jwtToken');
    const username = localStorage.getItem('username');
    const password = localStorage.getItem('password');

    if (token && username && password) {
      console.log('User is logged in:', { token, username, password });
      this.fetchForums(this.currentPage);
    } else {
      console.log('User is not logged in.');
      this.$router.push({ name: 'login' });
    }
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
  --link-color: #007bff;
  --border-color: #cccccc;
}

.chat-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  //background-color: var(--background-color);
  font-family: Arial, sans-serif;
  //color: var(--text-color);
  min-height: 100vh;
}

.forum-form {
  width: 100%;
  max-width: 800px;
  margin-bottom: 20px;
  background-color: var(--card-background-color);
  padding: 20px;
  border: 2px solid #007bff;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.forum-form input,
.forum-form textarea {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 5px;
  font-size: 16px;
  background-color: var(--input-background-color);
  color: var(--text-color);
}

.forum-form button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  //color: var(--button-text-color);
  cursor: pointer;
  font-size: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.forum-list {
  width: 100%;
  max-width: 800px;
  margin-top: 20px;
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

.forum-item .delete-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: #ffffff;
  cursor: pointer;
  font-size: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  display: block;
  margin: 10px auto;
}
.forum-item button:hover {
  background-color: #0056b3;
}

.pagination button{
  color:var(--button-text-color) ;
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  background-color: #7a7a7a;
  color: var(--button-text-color);
  cursor: pointer;
  font-size: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);

}
</style>


