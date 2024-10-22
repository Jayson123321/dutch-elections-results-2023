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
      users: [],
      userCount: 0
    };
  },
  mounted() {
    this.fetchUsers();
    this.fetchUserCount();
  },
  methods: {
    fetchUsers() {
      axios.get('http://localhost:8080/api/users/all')
          .then(response => {
            this.users = response.data;
          })
          .catch(error => {
            console.error('An error occurred while retrieving the user data:', error);
          });
    },
    fetchUserCount() {
      axios.get('http://localhost:8080/api/users/count')
          .then(response => {
            console.log(response);
            this.userCount = response.data;
          })
          .catch(error => {
            console.error('An error occurred while retrieving the user count:', error);
          });
    },
    deleteUser(userId) {
      if (confirm('Are you sure you want to delete this user?')) {
        axios.delete(`http://localhost:8080/api/users/${userId}`)
            .then(() => {
              alert('User successfully deleted.');
              this.fetchUsers();
              this.fetchUserCount();
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
      <div class="count-container">
        <p>Total number of users: {{ userCount }}</p>
      </div>
      <div v-if="users.length > 0">
        <div class="table-wrapper">
          <h2 class="users-title">Users</h2>
          <table>
            <tbody>
            <tr v-for="user in users" :key="user.id">
              <td class="user-row">
                <span class="user-data">{{ user.username }}</span>
                <button @click="deleteUser(user.id)" class="delete-button">Delete user</button>
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
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap');

html, body {
  margin: 0;
  padding: 0;
  background-color: #111111;
  font-family: 'Poppins', sans-serif;
  color: white;
}

.admin-page {
  background-color: #111111;
  min-height: 100vh;
  padding: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.table-container {
  background-color: #1A1A1A;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  max-width: 600px;
  width: 100%;
}

.count-container {
  text-align: center;
  margin-bottom: 20px;
}

.users-title {
  color: white;
  font-size: 32px;
  margin-bottom: 20px;
}

.table-wrapper {
  max-height: 400px;
  overflow-y: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
}

td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #555;
  color: white;
}

.user-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.user-data {
  color: white;
  margin-right: 20px;
}

button {
  padding: 8px 12px;
  background-color: #d9534f;
  color: white;
  border: none;
  border-radius: 4px;
}

button:hover {
  background-color: #d9534f;
}

p {
  text-align: center;
  font-size: 18px;
  color: white;
}
</style>
