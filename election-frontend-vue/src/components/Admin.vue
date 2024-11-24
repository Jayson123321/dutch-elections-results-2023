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
      userCount: 0,
      showPopup: false,
      showUsernamePopup: false,
      selectedUserId: null,
      newUsername: ''
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
    },
    openPopup(userId) {
      this.selectedUserId = userId;
      this.showPopup = true;
    },
    closePopup() {
      this.showPopup = false;
      this.selectedUserId = null;
      this.newUsername = '';
    },
    openUsernamePopup() {
      this.showUsernamePopup = true;
    },
    closeUsernamePopup() {
      this.showUsernamePopup = false;
      this.newUsername = '';
    },
    updateUsername() {
      if (this.newUsername.trim() !== '') {
        axios.put(`http://localhost:8080/api/users/${this.selectedUserId}`, {username: this.newUsername})
            .then(() => {
              alert('Username updated successfully.');
              this.fetchUsers();
              this.closeUsernamePopup();
            })
            .catch(error => {
              console.error('An error occurred while updating the username:', error);
            });
      } else {
        alert('Username cannot be empty.');
      }
    }
  }
}
</script>

<template>
  <div class="admin-page">
    <HeaderComponent/>
    <div class="content-container">
      <h2 class="page-title">Admin Dashboard</h2>
      <div class="user-stats">
        <p>Total Users: {{ userCount }}</p>
      </div>
      <div class="user-table-wrapper" v-if="users.length > 0">
        <div class="user-table-container">
          <div class="user-table-row header-row">
            <div class="user-table-cell">Username</div>
            <div class="user-table-cell">Email</div>
            <div class="user-table-cell actions-header">Actions</div>
          </div>
          <div v-for="user in users" :key="user.id" class="user-table-row">
            <div class="user-table-cell">{{ user.username }}</div>
            <div class="user-table-cell">{{ user.email }}</div>
            <div class="user-table-cell actions">
              <button @click="deleteUser(user.id)" class="delete-button">Delete</button>
              <button @click="openPopup(user.id)" class="manage-button">Manage</button>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="no-users-message">
        <p>No users found</p>
      </div>
    </div>
    <FooterComponent/>

    <div v-if="showPopup" class="popup-overlay">
      <div class="popup">
        <h3>Manage User Actions</h3>
        <button @click="openUsernamePopup" class="popup-button">Change Username</button>
        <button @click="closePopup" class="popup-button">Change Email</button>
        <button class="popup-button">Action 3</button>
        <button class="popup-button">Action 4</button>
        <button class="popup-button">Action 5</button>
        <button @click="closePopup" class="close-button">Close</button>
      </div>
    </div>

    <div v-if="showUsernamePopup" class="popup-overlay">
      <div class="popup">
        <h3>Change Username</h3>
        <input v-model="newUsername" type="text" placeholder="Enter new username" class="username-input">
        <button @click="updateUsername" class="popup-button">Save Username</button>
        <button @click="closeUsernamePopup" class="close-button">Cancel</button>
      </div>
    </div>
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
  padding: 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.content-container {
  width: 100%;
  max-width: 800px;
  background-color: #1A1A1A;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.page-title {
  text-align: center;
  font-size: 36px;
  margin-bottom: 20px;
  color: #ffffff;
}

.user-stats {
  margin-bottom: 20px;
  text-align: center;
  font-size: 20px;
}

.user-table-wrapper {
  overflow-x: auto;
}

.user-table-container {
  display: flex;
  flex-direction: column;
}

.user-table-row {
  display: flex;
  padding: 15px;
  border-bottom: 1px solid #555;
}

.header-row {
  font-weight: bold;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.user-table-cell {
  flex: 1;
  color: white;
  padding: 5px 10px;
}

.actions-header {
  text-align: right;
}

.actions {
  display: flex;
  gap: 10px;
  justify-content: flex-end;
}

.no-users-message {
  text-align: center;
  font-size: 18px;
  color: #888;
}

button {
  padding: 10px 15px;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.delete-button {
  background-color: #d9534f;
}

.delete-button:hover {
  background-color: #c9302c;
}

.manage-button {
  background-color: #5bc0de;
}

.manage-button:hover {
  background-color: #31b0d5;
}

.popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
}

.popup {
  background: #1A1A1A;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  text-align: center;
}

.close-button {
  padding: 10px 20px;
  background-color: #d9534f;
  color: white;
  border: none;
  border-radius: 4px;
  margin-top: 20px;
}

.close-button:hover {
  background-color: #c9302c;
}

.username-input {
  padding: 10px;
  margin: 10px;
  border-radius: 4px;
  border: none;
  width: calc(100% - 40px);
}

.popup-button {
  padding: 10px 20px;
  margin: 10px;
  background-color: #5bc0de;
  color: white;
  border: none;
  border-radius: 4px;
}

.popup-button:hover {
  background-color: #31b0d5;
}
</style>
