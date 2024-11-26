<template>
  <div>
    <HeaderComponent />
    <div class="chat-container">
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

       Lijst van forums
      <div class="forum-list">
        <h2>Geposte Forums</h2>
        <div v-for="forum in forums" :key="forum.forumId" class="forum-item">
          <h3>{{ forum.title }}</h3>
          <p>{{ forum.description }}</p>
<!--          <p>{{ forum.user.username }}</p>-->
        </div>
      </div>
    </div>
    <FooterComponent />
  </div>
</template>

<script>
import HeaderComponent from './HeaderComponent.vue';
import FooterComponent from './FooterComponent.vue';

export default {
  name: "Forum",
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
          id: 1, // Dummy user ID
        },
      },
      forums: [], // Lijst van bestaande forums
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
