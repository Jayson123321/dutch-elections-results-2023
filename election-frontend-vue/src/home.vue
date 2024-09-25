<script lang="ts">
import {defineComponent} from 'vue'
import Registration from "@/components/Registration.vue";

export default defineComponent({
  name: "home",
  components: {Registration}
})
</script>

<template>
  <main>
    <h1>Home</h1>
    <p>test</p>
    <Registration/>
  </main>
</template>

<style scoped>

</style>