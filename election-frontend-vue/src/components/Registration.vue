<script lang="ts">
import {defineComponent} from 'vue'
import { useRouter } from 'vue-router';

export default defineComponent({
  name: "Registration",
  data() {
    return {
      username: '',
      password: '',
      email: ''
    }
  },
  methods: {
    async register() {
      try {
        const response = await fetch('http://localhost:8080/api/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            username: this.username,
            password: this.password,
            email: this.email
          }),
        });

        if (!response.ok) {
          throw new Error('Registration failed');
        }

        const data = await response.json();
        console.log(data);

        this.$router.push('/login'); // Use this.$router to navigate
      } catch (error) {
        console.error('Error:', error);
      }
    }
  }
})
</script>

<template>
  <main>
    <h1>Registration</h1>
    <form @submit.prevent="register">
      <label for="username">Username</label>
      <input type="text" id="username" v-model="username" required>
      <label for="password">Password</label>
      <input type="password" id="password" v-model="password" required>
      <label for="email">Email</label>
      <input type="email" id="email" v-model="email" required>
      <button type="submit">Register</button>
    </form>
  </main>
</template>

<style scoped>
  main {
    display: flex;
    flex-direction: column;
    place-items: center;
    gap: 1rem;
  }

  form {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }

  label {
    font-weight: bold;
  }

  input {
    padding: 0.5rem;
    border-radius: 0.25rem;
    border: 1px solid #ccc;
  }

  button {
    padding: 0.5rem 1rem;
    border-radius: 0.25rem;
    border: none;
    background-color: #007bff;
    color: white;
    cursor: pointer;
  }
</style>