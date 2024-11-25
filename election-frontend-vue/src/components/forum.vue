

<!--<script>-->
<!--import FooterComponent from './FooterComponent.vue'-->
<!--import HeaderComponent from './HeaderComponent.vue'-->

<!--export default {-->
<!--  name: "forum",-->
<!--  components: {-->
<!--    FooterComponent,-->
<!--    HeaderComponent-->
<!--  },-->
<!--  data() {-->
<!--    return {-->
<!--      newForum: {-->
<!--        title: '',-->
<!--        description: '',-->
<!--        user: {-->
<!--          id: 1-->
<!--        }-->
<!--      },-->
<!--      forums: []-->
<!--      // newMessage: '',-->
<!--      // messages: []-->
<!--    };-->
<!--  },-->
<!--  methods: {-->
<!--    async fetchForums() {-->
<!--      try {-->
<!--        const response = await fetch('http://localhost:8080/api/usersforum', {-->
<!--          method: 'POST',-->
<!--          headers: {-->
<!--            'Content-Type': 'application/json'-->
<!--          },-->
<!--          body: JSON.stringify(this.newForum)-->
<!--        });-->
<!--        if (!response.ok) {-->
<!--          throw new Error('Fout bij sturen van de form');-->
<!--        }-->
<!--        const createdForum = await response.json();-->
<!--        console.log('Forum succesvol toegevoegd:', createdForum);-->
<!--        -->
<!--        this.forums.push(createdForum); // Voeg het nieuwe forum toe aan de lijst-->
<!--        this.newForum.title = '';-->
<!--        this.newForum.description = '';-->
<!--        // sendMessage() {-->
<!--        //   if (this.newMessage.trim() !== '') {-->
<!--        //     this.messages.push({ id: Date.now(), text: this.newMessage });-->
<!--        //     this.newMessage = '';-->
<!--      } catch (error) {-->
<!--        console.error('Fout:', error);-->
<!--    }-->
<!--  }-->
<!--},}-->
<!--</script>-->
<!--<template>-->
<!--  <div>-->
<!--    <HeaderComponent />-->
<!--    <div class="chat-container">-->
<!--      <h1>Forum</h1>-->
<!--      <p>Discussieer hier over de partijen.</p>-->
<!--      <div class="chat-box">-->
<!--        <div class="messages">-->
<!--          <div v-for="message in messages" :key="message.id" class="message">-->
<!--            {{ message.text }}-->
<!--          </div>-->
<!--        </div>-->
<!--        <div class="input-container">-->
<!--          <input v-model="newMessage" @keyup.enter="sendMessage" placeholder="Type your message here..." />-->
<!--          <button @click="sendMessage">Send</button>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->

<!--    &lt;!&ndash; Forum formulier &ndash;&gt;-->
<!--    <div class="forum-form">-->
<!--      <input-->
<!--          v-model="newForum.title"-->
<!--          placeholder="Titel"-->
<!--          required-->
<!--      />-->
<!--      <textarea-->
<!--          v-model="newForum.description"-->
<!--          placeholder="Beschrijving"-->
<!--          required-->
<!--      ></textarea>-->
<!--      <button @click="submitForum">Forum Posten</button>-->
<!--    </div>-->
<!--    <FooterComponent />-->
<!--  </div>-->
<!--</template>-->
<!--<style>-->
<!--.chat-container {-->
<!--  display: flex;-->
<!--  flex-direction: column;-->
<!--  align-items: center;-->
<!--  padding: 20px;-->
<!--}-->

<!--.chat-box {-->
<!--  width: 80%;-->
<!--  max-width: 800px;-->
<!--  border: 1px solid #ddd;-->
<!--  border-radius: 8px;-->
<!--  padding: 20px;-->
<!--  background-color: #f9f9f9;-->
<!--}-->

<!--.messages {-->
<!--  max-height: 400px;-->
<!--  overflow-y: auto;-->
<!--  margin-bottom: 20px;-->
<!--}-->

<!--.message {-->
<!--  background-color: #e0e0e0;-->
<!--  padding: 10px;-->
<!--  border-radius: 5px;-->
<!--  margin-bottom: 10px;-->
<!--}-->

<!--.input-container {-->
<!--  display: flex;-->
<!--  align-items: center;-->
<!--}-->

<!--input {-->
<!--  flex: 1;-->
<!--  padding: 10px;-->
<!--  border: 1px solid #ddd;-->
<!--  border-radius: 5px;-->
<!--  margin-right: 10px;-->
<!--}-->

<!--button {-->
<!--  padding: 10px 20px;-->
<!--  border: none;-->
<!--  border-radius: 5px;-->
<!--  background-color: #007bff;-->
<!--  color: white;-->
<!--  cursor: pointer;-->
<!--}-->

<!--button:hover {-->
<!--  background-color: #0056b3;-->
<!--}-->
<!--</style>-->

//test
<!--<template>-->
<!--  <div>-->
<!--    <HeaderComponent />-->
<!--    <div class="chat-container">-->
<!--      <h1>Forum</h1>-->
<!--      <p>Discussieer hier over de partijen.</p>-->

<!--      &lt;!&ndash; Forum formulier &ndash;&gt;-->
<!--      <div class="forum-form">-->
<!--        <input-->
<!--            v-model="newForum.title"-->
<!--            placeholder="Titel"-->
<!--            required-->
<!--        />-->
<!--        <textarea-->
<!--            v-model="newForum.description"-->
<!--            placeholder="Beschrijving"-->
<!--            required-->
<!--        ></textarea>-->
<!--        <button @click="submitForum">Forum Posten</button>-->
<!--      </div>-->

<!--      &lt;!&ndash; Lijst van forums &ndash;&gt;-->
<!--      <div class="forum-list">-->
<!--        <h2>Geposte Forums</h2>-->
<!--        <div v-for="forum in forums" :key="forum.forumId" class="forum-item">-->
<!--          <h3>{{ forum.title }}</h3>-->
<!--          <p>{{ forum.description }}</p>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
<!--    <FooterComponent />-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import HeaderComponent from './HeaderComponent.vue';-->
<!--import FooterComponent from './FooterComponent.vue';-->

<!--export default {-->
<!--  name: "Forum",-->
<!--  components: {-->
<!--    HeaderComponent,-->
<!--    FooterComponent,-->
<!--  },-->
<!--  data() {-->
<!--    return {-->
<!--      newForum: {-->
<!--        title: '',-->
<!--        description: '',-->
<!--        user: {-->
<!--          id: 1, // Dummy user ID, pas dit aan voor je gebruik-->
<!--        },-->
<!--      },-->
<!--      forums: [],-->
<!--    };-->
<!--  },-->
<!--  methods: {-->
<!--    async fetchForums() {-->
<!--      try {-->
<!--        const response = await fetch('http://localhost:8080/api/usersforum');-->
<!--        if (!response.ok) {-->
<!--          throw new Error('Fout bij ophalen van forums: ' + response.statusText);-->
<!--        }-->
<!--        this.forums = await response.json();-->
<!--      } catch (error) {-->
<!--        console.error('Fout bij het ophalen van forums:', error);-->
<!--      }-->
<!--    },-->
<!--    async submitForum() {-->
<!--      try {-->
<!--        console.log('Versturen van forum:', this.newForum); // Debugging-->

<!--        const response = await fetch('http://localhost:8080/api/usersforum', {-->
<!--          method: 'POST',-->
<!--          headers: {-->
<!--            'Content-Type': 'application/json',-->
<!--          },-->
<!--          body: JSON.stringify(this.newForum),-->
<!--        });-->

<!--        if (!response.ok) {-->
<!--          const errorText = await response.text();-->
<!--          throw new Error(-->
<!--              `Fout bij het versturen van forum: ${response.statusText} - ${errorText}`-->
<!--          );-->
<!--        }-->

<!--        const createdForum = await response.json();-->
<!--        console.log('Forum succesvol toegevoegd:', createdForum); // Debugging-->

<!--        this.forums.push(createdForum); // Voeg het nieuwe forum toe aan de lijst-->
<!--        this.newForum.title = '';-->
<!--        this.newForum.description = ''; // Reset formulier-->
<!--      } catch (error) {-->
<!--        console.error('Fout bij het versturen van forum:', error);-->
<!--        alert('Er is een fout opgetreden bij het versturen van het forum.');-->
<!--      }-->
<!--    },-->
<!--    mounted() {-->
<!--      this.fetchForums(); // Haal bestaande forums op bij het laden van de pagina-->
<!--    },-->
<!--  }-->
<!--};-->
<!--</script>-->


<template>
  <div>
    <HeaderComponent />
    <div v-if="forumId" class="forum-detail-container">
      <ForumDetail :id="forumId" />
    </div>
    <div v-else class="chat-container">
      <h1>Forum</h1>
      <p>Discussieer hier over de partijen.</p>

      <!-- Forum formulier -->
      <div class="forum-form">
        <input
            v-model="newForum.title"
            placeholder="Titel"
            required
        />
        <textarea
            v-model="newForum.description"
            placeholder="Beschrijving"
            required
        ></textarea>
        <button @click="submitForum">Forum Posten</button>
      </div>

      <!-- Lijst van forums -->
      <div class="forum-list">
        <h2>Geposte Forums</h2>
        <div v-for="forum in forums" :key="forum.forumId" class="forum-item" @click="goToForum(forum.forumId)">
          <h3>{{ forum.title }}</h3>
          <p>{{ forum.description }}</p>
        </div>
      </div>
    </div>
    <FooterComponent />
  </div>
</template>

<script>
import HeaderComponent from './HeaderComponent.vue';
import FooterComponent from './FooterComponent.vue';
import ForumDetail from './ForumDetail.vue';

export default {
  name: "Forum",
  components: {
    HeaderComponent,
    FooterComponent,
    ForumDetail,
  },
  props: {
    id: {
      type: String,
      default: null,
    },
  },
  data() {
    return {
      newForum: {
        title: '',
        description: '',
        user: {
          id: 1, // Dummy user ID
        },
      },
      forums: [], // Lijst van bestaande forums
    };
  },
  computed: {
    forumId() {
      return this.id;
    },
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
      } catch (error) {
        console.error('Fout bij het ophalen van forums:', error);
      }
    },
    async submitForum() {
      try {
        console.log('Versturen van forum:', this.newForum);

        const response = await fetch('http://localhost:8080/api/usersforum', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(this.newForum),
        });

        if (!response.ok) {
          const errorText = await response.text();
          throw new Error(
              `Fout bij het versturen van forum: ${response.statusText} - ${errorText}`
          );
        }

        const createdForum = await response.json();
        console.log('Forum succesvol toegevoegd:', createdForum);

        // Voeg het nieuwe forum toe aan de lijst
        this.forums.push(createdForum);

        // Reset het formulier
        this.newForum.title = '';
        this.newForum.description = '';
      } catch (error) {
        console.error('Fout bij het versturen van forum:', error);
        alert('Er is een fout opgetreden bij het versturen van het forum.');
      }
    },
    goToForum(forumId) {
      this.$router.push({ path: `/forum/${forumId}` });
    }
  },
  mounted() {
    // Haal bestaande forums op wanneer de component wordt geladen
    this.fetchForums();
  },
};
</script>


<style>

.chat-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.forum-form {
  width: 100%;
  max-width: 600px;
  margin-bottom: 20px;
}

.forum-form input,
.forum-form textarea {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.forum-form button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
}

.forum-form button:hover {
  background-color: #0056b3;
}

.forum-list {
  width: 100%;
  max-width: 600px;
}

.forum-item {
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 10px;
  margin-bottom: 10px;
  background-color: #f9f9f9;
}
</style>
