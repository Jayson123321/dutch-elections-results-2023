<template>
  <HeaderComponent />
  <div>
    <h1>Your Forums</h1>
    <div v-if="forums.length === 0">No forums to display.</div>
    <div v-else>
      <div v-for="forum in forums" :key="forum.forumId" class="forum-card">
        <h3>{{ forum.title }}</h3>
        <p>{{ forum.description }}</p>
        <button @click="deleteForum(forum.forumId)">Delete Forum</button>

        <div v-if="forum.replies.length > 0" class="replies">
          <h4>Replies:</h4>
          <ul>
            <li v-for="reply in forum.replies" :key="reply.id">
              {{ reply.replyText }}
            </li>
          </ul>

        </div>
      </div>
    </div>
    <button @click="loadMore">Load More</button>
  </div>
  <FooterComponent />
</template>

<script>
import axios from 'axios';
import HeaderComponent from "@/components/HeaderComponent.vue";
import FooterComponent from "@/components/FooterComponent.vue";

export default {
  components: {FooterComponent, HeaderComponent},
  data() {
    return {
      forums: [],
      currentPage: 0,
    };
  },
  mounted() {
    this.fetchUserForums();
  },
  methods: {
    async fetchUserForums() {
      const token = localStorage.getItem('jwtToken');
      if (!token) {
        this.$router.push('/login');
        return;
      }

      try {
        const response = await axios.get('http://localhost:8080/api/usersforum/api/user', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          params: { page: this.currentPage },
        });

        this.forums = response.data.content;
      } catch (error) {
        console.error("Error fetching user forums:", error);
      }
    },
    async deleteForum(forumId) {
      const token = localStorage.getItem('jwtToken');
      if (!token) {
        this.$router.push('/login');
        return;
      }

      const confirmed = confirm("Weet je zeker dat je dit forum wilt verwijderen?");
      if (confirmed) {
        try {
          await axios.delete(`http://localhost:8080/api/usersforum/${forumId}`, {
            headers: {
              Authorization: `Bearer ${token}`
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
    loadMore() {
      this.currentPage++;
      this.fetchUserForums();
    },
  },
};
</script>

<style scoped>
.forum-card {
  border: 1px solid #ddd;
  padding: 10px;
  margin-bottom: 10px;
}

button {
  margin-top: 20px;
}
</style>
