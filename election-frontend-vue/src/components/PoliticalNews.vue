<template>
  <div>
    <h2>Laatste politieke nieuws</h2>
    <ul v-if="news.length">
      <li v-for="(article, index) in news" :key="index" :href="article.link">
        <a :href="article.link" target="_blank">{{ article.title }}</a>
      </li>
    </ul>
    <p v-else>Geen nieuws beschikbaar</p>
  </div>
</template>
<style>
ul {
  list-style-type: none;
  padding: 0;
}
li {
  margin-bottom: 1rem;
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
    // Interval om elke 5 minuten (300000 ms) nieuws op te halen
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
    },
  },
};

</script>
