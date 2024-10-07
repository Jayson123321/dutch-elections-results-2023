<script>
import FooterComponent from './FooterComponent.vue'
import HeaderComponent from './HeaderComponent.vue'
export default {
  name: "partijenpagina",
  components: {
    FooterComponent,
    HeaderComponent
  }
}
</script>
<template>
  <div>
    <HeaderComponent />
<div class="partijenpagina">
  <h1>Partijen</h1>
  <p>De partijen die meedoen aan de verkiezingen.</p>
</div>
    <FooterComponent />
  </div>
</template>
<style>
.partijenpagina {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  margin-top: 75px; /* Adjust the value as needed */
}
</style>