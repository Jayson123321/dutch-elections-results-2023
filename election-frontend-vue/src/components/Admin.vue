<script>
import axios from 'axios';
import { Chart, DoughnutController, PieController, ArcElement, Tooltip, Legend } from "chart.js";
import config from "@/config.ts";

Chart.register(DoughnutController, ArcElement, PieController, Tooltip, Legend);

export default {
  name: "Admin",
  components: {},
  data() {
    return {
      users: [],
      userCount: 0,
      searchQuery: '',
      showPopup: false,
      showUsernamePopup: false,
      showEmailPopup: false,
      showBanPopup: false,
      showUnbanPopup: false,
      selectedUserId: null,
      newUsername: '',
      newEmail: '',
      chart: null,
      banReason: '',
      unbanRequests: []
    };
  },
  computed: {
    filteredUsers() {
      if (!this.searchQuery) {
        return this.users;
      }
      const query = this.searchQuery.toLowerCase();
      return this.users.filter(user =>
          user.username.toLowerCase().includes(query) ||
          user.email.toLowerCase().includes(query)
      );
    }
  },
  mounted() {
    this.fetchUsers();
    this.fetchUserCount();
    this.fetchUnbanRequests();
  },
  methods: {
    fetchUsers() {
      axios.get(`${config.apiBaseUrl}/users/all`)
          .then(response => {
            this.users = response.data;
            this.$nextTick(() => {
              this.renderChart();
            });
          })
          .catch(error => {
            console.error('An error occurred while retrieving the user data:', error);
          });
    },
    fetchUserCount() {
      axios.get(`${config.apiBaseUrl}/users/count`)
          .then(response => {
            this.userCount = response.data;
          })
          .catch(error => {
            console.error('An error occurred while retrieving the user count:', error);
          });
    },
    fetchUnbanRequests() {
      axios.get(`${config.apiBaseUrl}/unban-requests`)
          .then(response => {
            this.unbanRequests = response.data;
          })
          .catch(error => {
            console.error('An error occurred while fetching unban requests:', error);
          });
    },
    approveUnbanRequest(requestId) {
      axios.put(`${config.apiBaseUrl}/unban-requests/${requestId}/approve`)
          .then(() => {
            alert('Unban request approved successfully.');
            this.fetchUnbanRequests();
            this.fetchUsers(); // Ververst de user data
          })
          .catch(error => {
            console.error('An error occurred while approving the unban request:', error);
          });
    },
    renderChart() {
      if (this.chart) {
        this.chart.destroy();
      }
      const ctx = this.$refs.userChart.getContext('2d');
      const userCount = this.users.filter(user => user.role !== 'banned').length;
      const bannedCount = this.users.filter(user => user.role === 'banned').length;

      const data = {
        labels: ['Active Users', 'Banned Users'],
        datasets: [{
          label: 'User Roles',
          data: [userCount, bannedCount],
          backgroundColor: ['#36A2EB', '#FF6384'],
          borderColor: ['#36A2EB', '#FF6384'],
          borderWidth: 1
        }]
      };

      this.chart = new Chart(ctx, {
        type: 'doughnut',
        data: data,
        options: {
          responsive: true,
          plugins: {
            legend: { position: 'top' },
            tooltip: {
              callbacks: {
                label: function (context) {
                  const label = context.chart.data.labels[context.dataIndex];
                  const value = context.raw;
                  return `${label}: ${value}`;
                }
              }
            }
          }
        }
      });
    },
    deleteUser(userId) {
      if (confirm('Are you sure you want to delete this user?')) {
        axios.delete(`${config.apiBaseUrl}/users/${userId}`)
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
      this.newEmail = '';
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
        const userToUpdate = this.users.find(user => user.id === this.selectedUserId);
        if (userToUpdate) {
          const updatedUser = { ...userToUpdate, username: this.newUsername };
          axios.put(`${config.apiBaseUrl}/users/${this.selectedUserId}`, updatedUser)
              .then(() => {
                alert('Username updated successfully.');
                this.fetchUsers();
                this.closeUsernamePopup();
              })
              .catch(error => {
                console.error('An error occurred while updating the username:', error);
              });
        }
      } else {
        alert('Username cannot be empty.');
      }
    },
    openEmailPopup() {
      this.showEmailPopup = true;
    },
    closeEmailPopup() {
      this.showEmailPopup = false;
      this.newEmail = '';
    },
    updateEmail() {
      if (this.newEmail.trim() !== '') {
        axios.put(`${config.apiBaseUrl}/users/${this.selectedUserId}/email`, { email: this.newEmail })
            .then(() => {
              alert('Email updated successfully.');
              this.fetchUsers();
              this.closeEmailPopup();
            })
            .catch(error => {
              console.error('An error occurred while updating the email:', error);
            });
      } else {
        alert('Email cannot be empty.');
      }
    },
    openBanPopup() {
      this.showBanPopup = true;
    },
    closeBanPopup() {
      this.showBanPopup = false;
    },
    banUser() {
      axios.put(`${config.apiBaseUrl}/users/${this.selectedUserId}/ban`)
          .then(() => {
            alert('User successfully banned.');
            this.fetchUsers();
            this.closeBanPopup();
          })
          .catch(error => {
            console.error('An error occurred while banning the user:', error);
          });
    },
    openUnbanPopup(userId) {
      this.selectedUserId = userId;
      this.showUnbanPopup = true;
    },
    closeUnbanPopup() {
      this.showUnbanPopup = false;
    },
    unbanUser() {
      axios.put(`${config.apiBaseUrl}/users/${this.selectedUserId}/unban`)
          .then(() => {
            alert('User successfully unbanned.');
            this.fetchUsers();
            this.closeUnbanPopup();
          })
          .catch(error => {
            console.error('An error occurred while unbanning the user:', error);
          });
    },
    rejectUnbanRequest(requestId) {
      axios.delete(`${config.apiBaseUrl}/unban-requests/${requestId}/reject`)
          .then(() => {
            alert('Unban request rejected successfully.');
            this.fetchUnbanRequests(); // Refresh de unban requests lijst
          })
          .catch(error => {
            console.error('An error occurred while rejecting the unban request:', error);
          });
    },
    acceptUnbanRequest(requestId) {
      axios.put(`${config.apiBaseUrl}/unban-requests/${requestId}/approve`)
          .then(() => {
            alert('Unban request approved successfully.');
            this.fetchUnbanRequests();
            this.fetchUsers();
          })
          .catch(error => {
            console.error('An error occurred while approving the unban request:', error);
          });

  }

  }



}
</script>




<template>
  <div class="admin-page">
    <HeaderComponent/>
    <div class="chart-container">
      <div class="chart-wrapper">
        <h2 class="page-title">User Analytics</h2>
        <canvas id="userChart" ref="userChart"></canvas>
      </div>
    </div>
    <div class="unban-requests-container">
      <div class="content-box">
        <h2 class="section-title">Unban Requests</h2>
        <div v-if="unbanRequests.length > 0">
          <div v-for="request in unbanRequests" :key="request.id" class="unban-request-card">
            <div class="request-header">
              <p><strong>Ticket Number:</strong> {{ request.id }}</p>

            </div>
            <div class="request-body">
              <p><strong>Username:</strong> {{ request.user.username }}</p>
              <p><strong>Message:</strong> {{ request.message }}</p>
              <div class="request-actions">
                <button @click="acceptUnbanRequest(request.id)" class="accept-button">Accept</button>
                <button @click="rejectUnbanRequest(request.id)" class="reject-button">Reject</button>
              </div>

            </div>
          </div>
        </div>
        <p v-else class="placeholder-text">No unban requests available</p>
      </div>
    </div>



    <div class="content-container">
      <h2 class="page-title">Admin Dashboard</h2>
      <div class="user-stats" v-if="userCount > 0">
        <p>Total Users: {{ userCount }}</p>
      </div>
      <div class="search-container">
        <input
            type="text"
            v-model="searchQuery"
            placeholder="Search users by name or email..."
            class="search-input"
        />
      </div>

      <div class="user-table-wrapper" v-if="users.length > 0">
        <div class="user-table-container">
          <div class="user-table-row header-row">
            <div class="user-table-cell">Username</div>
            <div class="user-table-cell">Email</div>
            <div class="user-table-cell">Role</div>
            <div class="user-table-cell actions-header">Actions</div>
          </div>
          <div v-for="user in filteredUsers" :key="user.id" class="user-table-row">
            <div class="user-table-cell">{{ user.username }}</div>
            <div class="user-table-cell">{{ user.email }}</div>
            <div
                class="user-table-cell"
                :class="{ 'banned-role': user.role === 'banned' }">
              {{ user.role }}
            </div>
            <div class="user-table-cell actions">
              <button @click="deleteUser(user.id)" class="delete-button">Delete</button>
              <button @click="openPopup(user.id)" class="manage-button">Manage</button>
              <button v-if="user.role === 'banned'" @click="openUnbanPopup(user.id)" class="unban-button">Unban</button>
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
        <button @click="openEmailPopup" class="popup-button">Change Email</button>
        <button @click="openBanPopup" class="popup-button">Ban User</button>
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

    <div v-if="showEmailPopup" class="popup-overlay">
      <div class="popup">
        <h3>Change Email</h3>
        <input v-model="newEmail" type="text" placeholder="Enter new email" class="username-input">
        <button @click="updateEmail" class="popup-button">Save Email</button>
        <button @click="closeEmailPopup" class="close-button">Cancel</button>
      </div>
    </div>

    <div v-if="showBanPopup" class="popup-overlay">
      <div class="popup">
        <h3>Ban User</h3>
        <p>Are you sure you want to ban {{ users.find(user => user.id === selectedUserId)?.username }}?</p>
        <input v-model="banReason" type="text" placeholder="Enter reason for banning" class="input-field">
        <button @click="banUser" class="popup-button">Ban User</button>
        <button @click="closeBanPopup" class="close-button">Cancel</button>
      </div>
    </div>

    <div v-if="showUnbanPopup" class="popup-overlay">
      <div class="popup">
        <h3>Unban User</h3>
        <p>Are you sure you want to unban {{ users.find(user => user.id === selectedUserId)?.username }}?</p>
        <button @click="unbanUser" class="popup-button">Yes, Unban User</button>
        <button @click="closeUnbanPopup" class="close-button">Cancel</button>
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

.chart-container {
  width: 100%;
  max-width: 800px;
  margin: 40px auto;
  display: flex;
  justify-content: center;
}


.chart-wrapper {
  background-color: #1A1A1A;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  transition: transform 0.5s ease;
}

.chart-wrapper:hover {
  transform: scale(1.05);
}

canvas {
  max-width: 100%;
  max-height: 100%;
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

.unban-button {
  background-color: #0024ff;
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
.banned-role {
  color: red;
  font-weight: bold;
}
.unban-requests-container {
  width: 100%;
  max-width: 800px;
  margin: 20px auto;
}

.content-box {
  background-color: #1A1A1A;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  transition: transform 0.5s ease;
  text-align: center;
}

.content-box:hover {
  transform: scale(1.02);
}

.section-title {
  font-size: 24px;
  color: #ffffff;
  margin-bottom: 10px;
}

.placeholder-text {
  font-size: 18px;
  color: #888;
}

.input-field {
  width: 100%;
  padding: 10px;
  margin-top: 10px;
  margin-bottom: 10px;
  border-radius: 4px;
  border: 1px solid #555;
  background-color: #2A2A2A;
  color: white;
  font-size: 16px;
}
.unban-request-card {
  background-color: #1A1A1A;
  border: 1px solid #555;
  border-radius: 8px;
  padding: 15px;
  margin: 10px 0;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  color: white;
  transition: transform 0.3s ease;
}

.unban-request-card:hover {
  transform: scale(1.02);
}

.request-header {
  font-size: 16px;
  font-weight: bold;
  color: #5bc0de;
}

.request-body {
  margin-top: 10px;
  font-size: 14px;
  color: #ddd;
}

.request-body p {
  margin: 5px 0;
}
.request-actions {
  margin-top: 10px;
  display: flex;
  gap: 10px;
}

.accept-button, .reject-button {
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.accept-button {
  background-color: #28a745;
}

.accept-button:hover {
  background-color: #218838;
}

.reject-button {
  background-color: #dc3545;
}

.reject-button:hover {
  background-color: #c82333;
}


</style>
