<template>
  <div>
    <h1>{{ question.title }}</h1>
    <p>{{ question.description }}</p>

    <h2>Replies</h2>
    <ul>
      <li v-for="reply in replies" :key="reply.replyId">
        <p>{{ reply.replyText }}</p>
        <small>By {{ reply.username }} on {{ reply.createdAt }}</small>
      </li>
    </ul>

    <h2>Add a Reply</h2>
    <form @submit.prevent="submitReply">
      <div>
        <label for="replyText">Reply:</label>
        <textarea v-model="newReply.replyText" required></textarea>
      </div>
      <button type="submit">Submit</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      question: {},
      replies: [],
      newReply: {
        username: null,
        replyText: ''
      }
    };
  },
  created() {
    const forumId = this.$route.params.forumId;
    this.fetchQuestion(forumId);
    this.fetchReplies(forumId);
  },
  methods: {
    fetchQuestion(forumId) {
      axios.get(`/api/usersforum/${forumId}`)
          .then(response => {
            this.question = response.data;
          });
    },
    fetchReplies(forumId) {
      axios.get(`/api/usersforum/${forumId}/replies`)
          .then(response => {
            this.replies = response.data;
          });
    },
    submitReply() {
      const forumId = this.$route.params.forumId;
      axios.post(`/api/usersforum/${forumId}/replies`, this.newReply)
          .then(response => {
            this.replies.push(response.data);
            this.newReply.replyText = '';
          });
    }
  }
};
</script>