<script>
import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';
import '/src/assets/base.css';

export default {
  name: "Educativepage",
  components: {
    FooterComponent,
    HeaderComponent,
  },
};
</script>

<template>
  <div>
    <HeaderComponent />
    <div class="educatieve-pagina">
      <h1>{{ $t('educativePage.title') }}</h1>
      <p>{{ $t('educativePage.description') }}</p>
      <div class="flexcontainer">
        <div class="flexbox">
          <h2>{{ $t('educativePage.electionExplanation') }}</h2>
          <p>{{ $t('educativePage.electionExplanationDesc') }}</p>
          <router-link to="/verkiezingsuitleg" class="cta-link">{{ $t('educativePage.readMore') }}</router-link>
        </div>
        <div class="flexbox">
          <h2>{{ $t('educativePage.downloadPowerPoint') }}</h2>
          <p>{{ $t('educativePage.downloadPowerPointDesc') }}</p>
          <p>{{ $t('educativePage.madeBy') }}</p>
          <a href="/educativepowerpoint.pptx" class="cta-link" download>{{ $t('educativePage.downloadPowerPoint') }}</a>
        </div>
        <div class="flexbox">
          <h2>{{ $t('educativePage.testYourKnowledge') }}</h2>
          <p>{{ $t('educativePage.testYourKnowledgeDesc') }}</p>
          <router-link to="/Quiz" class="cta-link">{{ $t('educativePage.startQuiz') }}</router-link>
        </div>
      </div>
    </div>
    <FooterComponent />
  </div>
</template>

<style scoped>
.educatieve-pagina {
  margin-top: 100px;
  text-align: center;
  align-items: center;
  padding: 20px;
  padding-bottom: 100px; /* Voeg extra padding toe aan de onderkant */
  min-height: calc(100vh - 200px); /* Zorg ervoor dat de container minimaal de hoogte van het scherm heeft */
}

.flexcontainer {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  margin-top: 50px;
}

.flexbox {
  border-radius: 12px;
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  width: 300px;
  margin: 15px;
  text-align: left;
  transition: transform 0.3s, box-shadow 0.3s;
}

.flexbox:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 20px rgba(0, 0, 0, 0.15);
}

.flexbox h2 {
  color: #ff6600; /* Oranje tekstkleur */
  font-size: 22px;
  margin-bottom: 10px;
}

.flexbox p {
  color: #6c757d; /* Grijze tekstkleur */
  font-size: 16px;
  margin-bottom: 20px;
}

.cta-link {
  display: inline-block;
  padding: 10px 15px;
  color: #fff; /* Witte tekstkleur */
  background: #ff6600; /* Oranje achtergrond */
  border-radius: 5px;
  text-decoration: none;
  font-weight: bold;
  transition: background 0.3s, transform 0.3s;
}

.cta-link:hover {
  background: #cc5200; /* Donkerdere oranje achtergrond bij hover */
  transform: translateY(-2px);
}
</style>
