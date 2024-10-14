<script>
import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';
import axios from 'axios';

export default {
  name: "Admin",
  components: {
    FooterComponent,
    HeaderComponent
  },
  data() {
    return {
      users: []
    };
  },
  mounted() {
    axios.get('/api/users/all')
        .then(response => {
          this.users = response.data.map(user => ({ username: user.username }));
        })
        .catch(error => {
          console.error('Er is een fout opgetreden bij het ophalen van de gebruikersgegevens:', error);
        });
  }
}
</script>
<template>
  <div>
    <HeaderComponent />
    <div class="admin-page">
      <h1>Admin Dashboard</h1>
      <p>Welkom op de adminpagina.</p>
      <div v-if="users.length > 0">
        <h2>Users:</h2>
        <ul>
          <li v-for="user in users" :key="user.username">
            {{ user.username }}
          </li>
        </ul>
      </div>
      <div v-else>
        <p>Geen users gevonden.</p>
      </div>
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