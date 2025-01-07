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
      <h1>Educatieve Ondersteuning</h1>
      <p>Leer meer over verkiezingen, politieke processen en hoe je stem invloed heeft.</p>
      <div class="flexcontainer">
        <div class="flexbox">
          <h2>Uitleg over verkiezingen</h2>
          <p>Lees meer over hoe verkiezingen werken en hoe politieke partijen hun zetels winnen.</p>
          <router-link to="/verkiezingsuitleg" class="cta-link">Lees meer</router-link>
        </div>
        <div class="flexbox">
          <h2>Download PowerPoint</h2>
          <p>Download een PowerPoint-presentatie over verkiezingen.</p>
          <p>Powerpoint gemaakt door Seneca.</p>
          <a href="/educativepowerpoint.pptx" class="cta-link" download>Download PowerPoint</a>
        </div>
        <div class="flexbox">
          <h2>Test je kennis</h2>
          <p>Doe een quiz en ontdek hoeveel je weet over het Nederlandse politieke systeem.</p>
          <router-link to="/quiz" class="cta-link">Start quiz</router-link>
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
  height: calc(100vh - 200px);
}

.flexcontainer {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  margin-top: 50px;
}

.flexbox {
  background: #f8f9fa; /* Lichtgrijze achtergrond */
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 20px;
  width: 300px;
  margin: 15px;
  text-align: left;
}

.flexbox h2 {
  color: #007bff; /* Blauwe tekstkleur */
  font-size: 20px;
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
  background: #007bff; /* Blauwe achtergrond */
  border-radius: 5px;
  text-decoration: none;
  font-weight: bold;
  transition: background 0.3s;
}

.cta-link:hover {
  background: #0056b3; /* Donkerdere blauwe achtergrond bij hover */
}
</style>
