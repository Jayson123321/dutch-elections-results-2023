<script lang="ts">
import {defineComponent} from 'vue'
import { useRouter } from 'vue-router';

export default defineComponent({
  name: "Registration"
})
</script>

<template>
  <main>
    <h1>Registration</h1>
    <form>
      <label for="username">Username</label>
      <input type="text" id="username" required>
      <label for="password">Password</label>
      <input type="password" id="password" required>
      <label for="email">Email</label>
      <input type="email" id="emaixl" required>
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