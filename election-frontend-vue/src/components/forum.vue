<template>
  <div>
    <HeaderComponent />
    <div class="chat-container">
      <h1>Forum</h1>
      <p>Discussieer hier over de partijen.</p>
      <div class="forum-form">
        <div class="form-group" :class="{ 'has-error': errors.title }">
          <input v-model="newForum.title" placeholder="Titel" @blur="validateField('title')" />
          <span v-if="errors.title" class="error-message">{{ errors.title }}</span>
        </div>

        <div class="form-group" :class="{ 'has-error': errors.description }">
          <textarea v-model="newForum.description" placeholder="Beschrijving" @blur="validateField('description')"></textarea>
          <span v-if="errors.description" class="error-message">{{ errors.description }}</span>
        </div>
        <button @click="submitForum">Forum Posten</button>
        <div v-if="successMessage" class="success-message">{{ successMessage }}</div>
      </div>

      <div class="forum-list">
        <h2>Geposte Forums</h2>
        <div v-for="forum in forums" :key="forum.forumId" class="forum-item">
          <h3> <strong>{{ forum.username }}:</strong> {{ forum.title }}</h3>
          <p> {{ forum.description }}</p>

          <form @submit.prevent="submitReply(forum.forumId)">
            <div v-for="reply in forum.replies" :key="reply.replyId" class="reply-item">
              <p><strong>{{ reply.username }}:</strong> {{ reply.replyText }}</p>
            </div>
            <br>
            <textarea v-model="forum.newReply.replyText" placeholder="Beantwoord dit vraag" required></textarea>
            <button type="submit">Antwoord posten</button>
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
          id: '', // Dummy user ID
        },
      },
      forums: [],
      currentPage: 0,
      totalPages: 0,
      errors: {},
      successMessage: '',
      newReply: {
        username: '',
        replyText: ''
      }
    };
  },
  methods: {
    async fetchForums(page = 0) {
      try {
        console.log("Ophalen van forums...");
        const response = await fetch(`http://localhost:8080/api/usersforum?page=${page}&size=5`);
        if (!response.ok) {
          throw new Error(`Server error: ${response.status} - ${response.statusText}`);
        }
        const data = await response.json();
        this.forums = data.content;
        this.totalPages = data.totalPages;
        this.currentPage = data.number;

        // Fetch replies for each forum and initialize newReply for each forum
        for (let forum of this.forums) {
          const repliesResponse = await fetch(`http://localhost:8080/api/usersforum/${forum.forumId}/replies`);
          if (repliesResponse.ok) {
            forum.replies = await repliesResponse.json();
          } else {
            forum.replies = [];
          }
          forum.newReply = {replyText: ''};
        }
      } catch (error) {
        console.error('Fout bij het ophalen van forums:', error);
      }
    },

    validateField(field) {
      if (!this.newForum[field]?.trim()) {
        this.errors[field] = 'Vul dit veld in';
      } else {
        delete this.errors[field];
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

      // Extract username from JWT token
      const payload = JSON.parse(atob(token.split('.')[1]));
      const username = payload.sub;

      this.newForum.user.username = username;

      try {
        const response = await axios.post('http://localhost:8080/api/usersforum', this.newForum, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });

        const createdForum = response.data;

        this.forums.unshift({
          ...createdForum,
          replies: [],
          newReply: {replyText: ''},
        });

        this.newForum = {
          title: '',
          description: '',
          user: {id: '', username: ''},
        };

        this.successMessage = "Forum successfully posted!";
        setTimeout(() => {
          this.successMessage = '';
        }, 3000);

      } catch (error) {
        console.error('Error posting forum:', error);
        alert('An error occurred while posting the forum.');
      }
    },

    async submitReply(forumId) {
      const token = localStorage.getItem('jwtToken');

      if (!token) {
        alert('You must be logged in to post a reply.');
        return;
      }

      // Extract username from JWT token
      const payload = JSON.parse(atob(token.split('.')[1]));
      const username = payload.sub;

      const forum = this.forums.find(f => f.forumId === forumId);
      forum.newReply.username = username;

      try {
        const response = await axios.post(`http://localhost:8080/api/usersforum/${forumId}/replies`, forum.newReply, {
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });

        if (forum) {
          forum.replies.push(response.data);
        }

        forum.newReply.replyText = '';
      } catch (error) {
        console.error('Error posting reply:', error);
        alert('An error occurred while posting the reply.');
      }
    },

    goToQuestionDetails(forumId) {
      this.$router.push({name: 'forum', params: {forumId}});
    },

    async deleteForum(forumId) {
      const token = localStorage.getItem('jwtToken');

      if (!token) {
        alert("Je moet ingelogd zijn om een forum te kunnen verwijderen.")
        return;
      }

      const confirmed = confirm("Weet je zeker dat je dit forum wilt verwijderen?");
      if (confirmed) {
        try {
          await axios.delete(`http://localhost:8080/api/usersforum/${forumId}`, {
            headers: {
              'Authorization': `Bearer ${token}`  // Pass the token in the request header
            }
          });
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
    this.fetchForums(this.currentPage);
  }
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


