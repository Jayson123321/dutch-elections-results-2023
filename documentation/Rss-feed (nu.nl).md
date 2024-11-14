# RSS Feed Documentatie (nu.nl)

## Overzicht
Dit document biedt een overzicht van de `PoliticalNews` component in het `election-frontend-vue` project. De component haalt het laatste politieke nieuws op van een API en toont dit.

## Component: `PoliticalNews.vue`

### Template
De PoliticalNews template is de structuur van de nieuwsweergave, inclusief een container voor de nieuwsitems, koppen en een lijst van artikelen.

### Script
Het script-gedeelte bevat:
- Het importeren van `axios` voor het maken van HTTP-verzoeken.
- Het definiëren van de naam en gegevens-eigenschappen van de component.
- Het ophalen van nieuwsgegevens van de API bij het laden van de component en op regelmatige intervallen.
- Methoden voor het ophalen van nieuws en het formatteren van datums.

### Code Voorbeeld

```vue
<template>
  <div class="news-container">
    <h2>De laatste politieke nieuws</h2>
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
  border-radius: 10px;
  border: 1px solid #8c8c8c;
  border-bottom: none;
  border-left: none;
  border-right: none;
  box-shadow: 0 4px 8px rgba(255, 255, 255, 0.1);
}

h2 {
  font-size: 2rem;
  text-align: center;
  margin-bottom: 10px;
}

h4 {
  margin-bottom: 20px;
  font-size: 0.875rem;
  font-weight: 400;
  color: #888;
  text-align: center;
}

ul {
  display: flex;
  flex-wrap: wrap;
  list-style-type: none;
  padding: 0;
}

.news-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  background: white;
  margin: 5px;
  border-radius: 10px;
  overflow: hidden;
  transition: transform 0.3s, box-shadow 0.3s;
  box-shadow: 0 2px 4px rgba(154, 154, 154, 0.1);
  width: calc(20% - 10px);
}

@media (max-width: 1200px) {
  .news-item {
    width: calc(25% - 10px);
  }
}

@media (max-width: 992px) {
  .news-item {
    width: calc(33.333% - 10px);
  }
}

@media (max-width: 768px) {
  .news-item {
    width: calc(50% - 10px);
  }
}

@media (max-width: 576px) {
  .news-item {
    width: calc(100% - 10px);
  }
}

.news-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

.news-image {
  width: 100%;
  height: auto;
  object-fit: cover;
  border-radius: 10px 10px 0 0;
}

.news-details {
  padding: 5px;
  flex: 1;
  text-align: center;
}

.news-title {
  font-size: 1rem;
  font-weight: bold;
  color: #333;
  margin: 0;
  text-decoration: none;
}

.news-date {
  font-size: 0.75rem;
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
        const response = await axios.get("http://localhost:8080/api/political-news");
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
```
API Endpoint
URL: http://localhost:8080/api/political-news
Methode: GET
Respons: JSON array van nieuwsartikelen
Notities
De component haalt elke 5 minuten nieuws op.
De datum wordt geformatteerd met de formatDate methode.
Extra Componenten
Er is een Controller, Article (wat eigenlijk geen entiteit is maar toch in die map zit) en een Processor gemaakt.