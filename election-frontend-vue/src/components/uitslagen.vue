<script>
import FooterComponent from './FooterComponent.vue'
import HeaderComponent from './HeaderComponent.vue'
export default {
  name: "uitslagen",
  components: {
    FooterComponent,
    HeaderComponent
  }
}
</script>
<template>
  <div>
    <HeaderComponent />
    <div class="uitslagen">
      <h1>Uitslagen</h1>
      <p>De uitslagen van de partijen worden hier weergegeven.</p>
    </div>
    <FooterComponent />
  </div>
</template>
<style>
.uitslagen {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  margin-top: 75px;
}

</style>