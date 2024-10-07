<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';
import FooterComponent from './FooterComponent.vue'
import HeaderComponent from './HeaderComponent.vue'
export default defineComponent({
  name: "loginPage",
  components: {
    FooterComponent,
    HeaderComponent
  },
  setup() {
    const router = useRouter();
    const username = ref('');
    const password = ref('');

    const login = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            username: username.value,
            password: password.value
          }),
        });

        if (!response.ok) {
          throw new Error('Login failed');
        }

        const data = await response.json();
        console.log(data);

        router.push('/managing-authorities');
      } catch (error) {
        console.error('Error:', error);
      }
    };

    return {
      username,
      password,
      login
    };
  }
});
</script>

<template>
  <header-component />
  <footer-component />
  <main>
    <h1>Login</h1>
    <form @submit.prevent="login">
      <label for="username">Username</label>
      <input type="text" id="username" v-model="username" required>
      <label for="password">Password</label>
      <input type="password" id="password" v-model="password" required>
      <button type="submit">Login</button>
      <p>Not have an account?</p>
      <router-link to="/registration">Register</router-link>
    </form>
  </main>
</template>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  padding: 2rem;
  height: 100%;
  border-radius: 8px;
  color: #c0ccbc;
  width: 100%;
}

label {
  margin-top: 1rem;
}

input {
  margin-top: 0.5rem;
  padding: 0.5rem;
  border-radius: 4px;
  border: 1px solid #ccc;
}

button {
  margin-top: 1.5rem;
  padding: 0.75rem;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}
</style>
