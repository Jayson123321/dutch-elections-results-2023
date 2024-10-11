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
      <h2>John Doe</h2>
      <h1>Admin</h1>
      <h3>Analytics</h3>
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