<template>
  <HeaderComponent />
  <div>
    <h1>{{ $t('userForums.title') }}</h1>
    <div v-if="forums.length === 0">{{ $t('userForums.noForums') }}</div>
    <div v-else>
      <div v-for="forum in forums" :key="forum.forumId" class="forum-card">
        <h3>{{ forum.title }}</h3>
        <p>{{ forum.description }}</p>
        <button @click="deleteForum(forum.forumId)">{{ $t('userForums.deleteButton') }}</button>

        <div v-if="forum.replies.length > 0" class="replies">
          <h4>{{ $t('userForums.repliesTitle') }}</h4>
          <ul>
            <li v-for="reply in forum.replies" :key="reply.id">
              {{ reply.replyText }}
            </li>
          </ul>
        </div>
      </div>
    </div>
    <button class="loadmore" @click="loadMore">{{ $t('userForums.loadMoreButton') }}</button>
  </div>
</template>

<script>
import HeaderComponent from "@/components/HeaderComponent.vue";
import FooterComponent from "@/components/FooterComponent.vue";
import config from "@/config.ts";


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
        const token = localStorage.getItem("jwtToken");
        if (!token) {
          this.$router.push("/login");
          return;
        }

        try {
          const response = await fetch(`${config.apiBaseUrl}/usersforum/api/user?page=${this.currentPage}`, {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
          if (!response.ok) {
            throw new Error("Failed to fetch forums");
          }
          const data = await response.json();
          this.forums = data.content;
        } catch (error) {
          console.error("Error fetching user forums:", error);
          if (error.response?.status === 401) {
            this.$router.push("/login");
          }
        }
      },
      async deleteForum(forumId) {
        const token = localStorage.getItem("jwtToken");
        if (!token) {
          this.$router.push("/login");
          return;
        }

        const confirmed = confirm(this.$t("Weet je zeker dat je dit forum wilt verwijderen?"));
        if (confirmed) {
          try {
            const response = await fetch(`${config.apiBaseUrl}/usersforum/${forumId}`, {
              method: "DELETE",
              headers: {
                Authorization: `Bearer ${token}`,
              },
            });
            if (!response.ok) {
              throw new Error("Failed to delete forum");
            }
            this.forums = this.forums.filter((forum) => forum.forumId !== forumId);
            alert(this.$t("Forum succesvol verwijderd."));
          } catch (error) {
            console.error("Error deleting forum:", error);
            alert(this.$t("Er is een fout opgetreden bij het verwijderen van het forum."));
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
   border-radius: 8px;
   padding: 15px;
   margin-bottom: 15px;
   transition: box-shadow 0.3s ease-in-out;
   background-color: white;
 }

.forum-card:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.forum-card h3 {
  margin: 0;
  font-size: 1.5em;
  color: #333;
}

.forum-card p {
  font-size: 1em;
  color: #666;
  margin: 10px 0;
}

button {
  padding: 10px 15px;
  font-size: 1em;
  background-color: #AD0000;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.replies {
  margin-top: 15px;
  padding: 10px;
  border-top: 1px solid #ddd;
}

.replies h4 {
  margin-bottom: 10px;
  font-size: 1.2em;
  color: #444;
}

.replies ul {
  list-style-type: none;
  padding: 0;
}

.replies li {
  padding: 5px 0;
  font-size: 1em;
  color: #555;
}

.loadmore {
  background-color: #117CEE;
}

</style>
