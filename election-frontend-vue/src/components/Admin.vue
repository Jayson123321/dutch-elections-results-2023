<script>
/*import FooterComponent from './FooterComponent.vue';
import HeaderComponent from './HeaderComponent.vue';*/
import axios from 'axios';

export default {
  name: "Admin",
  components: {
    /*FooterComponent,
    HeaderComponent*/
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
            console.error('An error occurred while retrieving the user data:', error);
          });
    },
    deleteUser(userId) {
      if (confirm('Are you sure you want to delete this user?')) {
        axios.delete(`http://localhost:8080/api/users/${userId}`)
            .then(() => {
              alert('User successfully deleted.');
              this.fetchUsers();
            })
            .catch(error => {
              console.error('An error occurred while deleting the user:', error);
            });
      }
    }
  }
}
</script>

<template>
  <div class="admin-page">
    <HeaderComponent/>
    <div class="table-container">
      <div v-if="users.length > 0">
        <div class="table-wrapper">
          <table>
            <tbody>
            <tr v-for="user in users" :key="user.id">
              <td>{{ user.username }}</td>
              <td>
                <button @click="deleteUser(user.id)">Delete user</button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div v-else>
        <p>No users found</p>
      </div>
    </div>
    <FooterComponent/>
  </div>
</template>

<style scoped>
html, body {
  margin: 0;
  padding: 0;
  background-color: #111;
  height: 100%;
  overflow-x: hidden;
}

.admin-page {
  background-color: #111;
  min-height: 100vh;
  padding: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.table-container {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  width: 100%;
}

.table-wrapper {
  max-height: 400px; /* hight table */
  overflow-y: auto; /* Scroll */
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f4f4f4;
  font-weight: bold;
}

tr:hover {
  background-color: #f9f9f9;
}

button {
  padding: 8px 12px;
  background-color: #d9534f;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
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
