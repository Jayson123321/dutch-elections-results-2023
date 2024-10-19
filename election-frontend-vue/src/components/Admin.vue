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
    this.fetchUsers();
  },
  methods: {
    fetchUsers() {
      axios.get('http://localhost:8080/api/users/all', { responseType: 'json' })
          .then(response => {
            this.users = response.data;
          })
          .catch(error => {
            console.error('Er is een fout opgetreden bij het ophalen van de gebruikersgegevens:', error);
          });
    },
    deleteUser(userId) {
      if (confirm('Weet je zeker dat je deze gebruiker wilt verwijderen?')) {
        axios.delete(`http://localhost:8080/api/users/${userId}`)
            .then(() => {
              alert('Gebruiker succesvol verwijderd.');
              this.fetchUsers();
            })
            .catch(error => {
              console.error('Er is een fout opgetreden bij het verwijderen van de gebruiker:', error);
            });
      }
    }
  }
}
</script>

<template>
  <div>
    <HeaderComponent />
    <div class="admin-page">
      <div v-if="users.length > 0">
        <table>
          <thead>
          <tr>
            <th>Gebruikersnaam</th>
            <th>Acties</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="user in users" :key="user.id">
            <td>{{ user.username }}</td>
            <td>
              <button @click="deleteUser(user.id)">Verwijder</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <div v-else>
        <p>Geen users gevonden.</p>
      </div>
    </div>
    <FooterComponent />
  </div>
</template>

<style scoped>
.admin-page {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

th, td {
  text-align: left;
  padding: 12px;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f4f4f4;
  font-weight: bold;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

tbody tr:hover {
  background-color: #f9f9f9;
}

button {
  background-color: #d9534f;
  color: white;
  border: none;
  padding: 8px 12px;
  cursor: pointer;
  border-radius: 4px;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #c9302c;
}

p {
  text-align: center;
  font-size: 18px;
  color: #888;
}
</style>
