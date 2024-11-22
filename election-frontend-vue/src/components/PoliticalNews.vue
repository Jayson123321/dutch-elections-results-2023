<template>
  <div class="news-container">
    <h2>Blijf op de hoogte van het laatste politieke nieuws!</h2>
    <h4>(Bron: Nu.nl)</h4>
    <ul>
      <li v-for="(article, index) in news" :key="index" class="news-item">
        <a :href="article.link" target="_blank">
          <div class="news-content">
            <img :src="article.imageUrl" alt="News image" class="news-image" v-if="article.imageUrl" />
            <div class="news-details">
              <h3 class="news-title">{{ article.title }}</h3>
              <p class="news-date">Geplaatst: {{ formatDate(article.pubDate) }}</p>
            </div>
          </div>
        </a>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.news-container {
  max-width: 100%;
  margin: 0 auto;
  padding: 20px;
  border-radius: 15px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

h2 {
  font-size: 2.5rem;
  text-align: center;
  margin-bottom: 15px;
}
a {
  text-decoration: none;
}
h4 {
  margin-bottom: 25px;
  font-size: 1rem;
  font-weight: 400;
  text-align: center;
}

ul {
  display: flex;
  flex-direction: column;
  list-style-type: none;
  padding: 0;
}

.news-item {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin: 10px 0;
  border-radius: 10px;
  overflow: hidden;
  transition: transform 0.3s, box-shadow 0.3s;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.news-item:hover {
  transform: scale(1.02);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.news-content {
  display: flex;
  flex-direction: row;
  align-items: center;
  width: 100%;
}

.news-image {
  width: 150px;
  height: 100px;
  object-fit: cover;
  border-radius: 10px 0 0 10px;
}

.news-details {
  padding: 10px;
  flex: 1;
  text-align: left;
}

.news-title {
  font-size: 1.25rem;
  font-weight: bold;
  margin: 0;
}

.news-date {
  font-size: 0.875rem;
  color: #777;
  margin-top: 5px;
}
</style>

<script>
import axios from 'axios';

export default {
  name: "PoliticalNews",
  data() {
    return {
      news: [],
    };
  },
  mounted() {
    this.fetchNews();
    setInterval(this.fetchNews, 300000);
  },
  methods: {
    async fetchNews() {
      try {
        const response = await axios.get("https://wiipuujaamee42-backend.onrender.com/api/political-news");
        this.news = response.data;
      } catch (error) {
        console.error("Error fetching news:", error);
      }
    },
    formatDate(dateString) {
      const options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
      };
      return new Date(dateString).toLocaleString(options);
    },
  },
};
</script>