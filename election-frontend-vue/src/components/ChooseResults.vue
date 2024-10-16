<script lang="ts">
import {defineComponent} from 'vue'
import HeaderComponent from "@/components/HeaderComponent.vue";
import FooterComponent from "@/components/FooterComponent.vue";

export default defineComponent({
  name: "choose-results" ,
  components: {FooterComponent, HeaderComponent}
})
</script>

<template>
  <div>
    <HeaderComponent />
    <div class="homepage">
      <h1>Welkom bij Demo-Crazy</h1>
      <p>De plek waar je alles kunt vinden over de politieke partijen in Nederland.</p>
      <div class="flexcontainer">
        <router-link to="/localAuthorityResults" class="flexbox flexbox1">
          <h2>Gemeentelijke Uitslagen</h2>
          <p>Ontdek wat jou gemeente heeft gestemd!</p>
        </router-link>
        <router-link to="/managing-authorities" class="flexbox flexbox2">
          <h2>Gemeentelijke uitslag per stembureau</h2>
          <p>Bekijk de uitslag per stembureau</p>
        </router-link>
        <router-link to="/totalResults" class="flexbox flexbox3">
          <h2>Totaal uitslag</h2>
          <p>Bekijk de totaal uitslag</p>
        </router-link>
      </div>
    </div>
    <FooterComponent />
  </div>
</template>

<style scoped>

</style>