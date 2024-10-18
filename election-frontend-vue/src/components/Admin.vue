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
    axios.get('http://localhost:8080/api/users/all', { responseType: 'json' }) // Pas de poort aan naar jouw backend server
        .then(response => {
          this.users = response.data;
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
      <div v-if="users.length > 0">
        <table >
          <tr>
            <th>Usernames</th>
          </tr>
          <tr v-for="user in users" :key="user">
            <td>{{ user }}</td>
          </tr>
        </table>
      </div>
      <div v-else>
        <p>Geen users gevonden.</p>
      </div>
    </div>
    <FooterComponent />
  </div>
</template>


