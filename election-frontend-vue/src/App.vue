<script setup lang="ts">
import { useI18n } from 'vue-i18n';
import FooterComponent from "@/components/FooterComponent.vue";

const { locale, t } = useI18n();

const toggleLocale = () => {
  locale.value = locale.value === 'en' ? 'nl' : 'en';
};
</script>

<template>
  <div>
    <button class="professional-button" @click="toggleLocale">{{ t('common.toggle_button') }}</button>
    <router-view></router-view>
  </div>
<footer-component />
</template>

<style scoped>
header {
  line-height: 1.5;
}
.professional-button {
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 5px 10px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  float: right;
}

.professional-button:hover {
  background-color: #0056b3;
}
.logo {
  display: block;
  margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

}
</style>