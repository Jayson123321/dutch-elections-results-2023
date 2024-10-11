<script>
import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';
export default {
  name: "Admin",
  components: {
    FooterComponent,
    HeaderComponent
  }
}
</script>
<template>
  <div>
    <HeaderComponent />
    <div class="admin-page">
      <h1>Admin Dashboard</h1>
      <p>Welkom op de adminpagina.</p>
    </div>
    <FooterComponent />
  </div>
</template>
<style>
.admin-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  margin-top: 75px;
}
</style>