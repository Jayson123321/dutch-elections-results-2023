<template>
  <div class="news-container">
    <h2>De laatste politieke nieuws</h2>
    <h4>(Bron: Nu.nl)</h4>
    <ul>
      <li v-for="(article, index) in news" :key="index">
        <a :href="article.link" target="_blank">{{ article.title }}</a>
      </li>
    </ul>
  </div>
</template>

<style scoped>
h4 {
  margin-bottom: 10px;
  font-size: 0.875rem;
  font-weight: 400;
  color: #888;
  text-align: center;
}
.news-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(151, 151, 151, 0.1);
}

h2 {
  font-size: 2rem;
  text-align: center;
  margin-bottom: 20px;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin-bottom: 15px;
  padding: 15px;
  border-radius: 8px;
  transition: transform 0.3s, background-color 0.3s, box-shadow 0.3s;
  box-shadow: 0 2px 4px rgba(154, 154, 154, 0.1);
}

li:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(143, 143, 143, 0.2);
}

a {
  text-decoration: none;
  font-weight: bold;
}

a:hover {
  text-decoration: underline;
}
</style>

<script>
import axios from 'axios';

export default {
  name: "PoliticalNews",
  data() {
    return {
      news: [],
      showPoliticalNews: false
    };
  },
  mounted() {
    this.fetchNews();
    setInterval(this.fetchNews, 300000);
  },
  methods: {
    async fetchNews() {
      try {
        const response = await axios.get("http://localhost:8080/api/political-news");
        this.news = response.data;
      } catch (error) {
        console.error("Error fetching news:", error);
      }
    }
  }
};
</script>