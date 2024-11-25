<template>
  <div>
    <HeaderComponent />
    <div class="forum-detail-container">
      <h1>{{ forum.title }}</h1>
      <p>{{ forum.description }}</p>

      <!-- Reply form -->
      <div class="reply-form">
        <textarea v-model="newReply" placeholder="Type your reply here..." required></textarea>
        <button @click="submitReply">Beantwoord</button>
      </div>

      <!-- List of replies -->
      <div class="replies-list">
        <h2>Replies</h2>
        <div v-for="reply in replies" :key="reply.id" class="reply-item">
          <p>{{ reply.text }}</p>
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
  name: "ForumDetail",
  components: {
    HeaderComponent,
    FooterComponent,
  },
  data() {
    return {
      forum: {},
      newReply: '',
      replies: [],
    };
  },
  methods: {
    async fetchForum() {
      try {
        const response = await fetch(`http://localhost:8080/api/usersforum/${this.$route.params.id}`);
        if (!response.ok) {
          throw new Error('Error fetching forum: ' + response.statusText);
        }
        this.forum = await response.json();
      } catch (error) {
        console.error('Error fetching forum:', error);
      }
    },
    async fetchReplies() {
      try {
        const response = await fetch(`http://localhost:8080/api/usersforum/${this.$route.params.id}/replies`);
        if (!response.ok) {
          throw new Error('Error fetching replies: ' + response.statusText);
        }
        this.replies = await response.json();
      } catch (error) {
        console.error('Error fetching replies:', error);
      }
    },
    async submitReply() {
      try {
        const response = await fetch(`http://localhost:8080/api/usersforum/${this.$route.params.id}/replies`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ text: this.newReply }),
        });

        if (!response.ok) {
          throw new Error('Error submitting reply: ' + response.statusText);
        }

        const createdReply = await response.json();
        this.replies.push(createdReply);
        this.newReply = '';
      } catch (error) {
        console.error('Error submitting reply:', error);
      }
    },
  },
  mounted() {
    this.fetchForum();
    this.fetchReplies();
  },
};
</script>

<style>
.forum-detail-container {
  padding: 20px;
}

.reply-form {
  margin-top: 20px;
}

.reply-form textarea {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.reply-form button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
}

.reply-form button:hover {
  background-color: #0056b3;
}

.replies-list {
  margin-top: 20px;
}

.reply-item {
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 10px;
  margin-bottom: 10px;
  background-color: #f9f9f9;
}
</style>