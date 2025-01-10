<template>
  <div>
    <HeaderComponent />
    <div class="homepage">
      <h1>{{ $t('chooseResults.welcome_message') }}</h1>
      <p>{{ $t('chooseResults.description_message') }}</p>
      <div class="flexcontainer">
        <router-link to="/localAuthorityResults" class="flexbox flexbox1">
          <h2>{{ $t('chooseResults.local_authority_results') }}</h2>
          <p>{{ $t('chooseResults.discover_local_votes') }}</p>
        </router-link>
        <router-link to="/managing-authorities" class="flexbox flexbox2">
          <h2>{{ $t('chooseResults.local_results_per_polling_station') }}</h2>
          <p>{{ $t('chooseResults.view_results_per_polling_station') }}</p>
        </router-link>
        <router-link to="/totalResults" class="flexbox flexbox3">
          <h2>{{ $t('chooseResults.total_results') }}</h2>
          <p>{{ $t('chooseResults.view_total_results') }}</p>
        </router-link>
      </div>
    </div>
    <FooterComponent />
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import HeaderComponent from "@/components/HeaderComponent.vue";
import FooterComponent from "@/components/FooterComponent.vue";

export default defineComponent({
  name: "choose-results",
  components: { FooterComponent, HeaderComponent }
});
</script>

<style scoped>

</style>