<template>
  <div class="about-us">
    <h1>Over Ons</h1>
    <p>Welkom bij onze website! Wij zijn een team van gepassioneerde professionals die zich inzetten voor het delen van de TK2023 resultaten.</p>
    <h2>Onze Missie</h2>
    <p>Onze missie is om transparantie en toegankelijkheid te bevorderen door de resultaten van de Tweede-kamer 2023 verkiezingen te delen.</p>
    <h2>Meer Informatie</h2>
    <p>Voor meer informatie kunt u contact met ons opnemen via info@democrazy.com.</p>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: "About-us"
});
</script>

<style scoped>
.about-us {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.about-us p {
  font-size: 1.2em;
  line-height: 1.6;
}

.about-us ul {
  list-style-type: none;
  padding: 0;
}

.about-us ul li {
  font-size: 1.1em;
  margin: 10px 0;
}
</style>