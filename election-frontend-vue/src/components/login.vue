<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: "UserLogin",
  setup() {
    const router = useRouter();
    const email = ref('');
    const password = ref('');

    const login = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/auth/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            email: email.value,
            password: password.value
          }),
        });

        if (!response.ok) {
          throw new Error('Login failed');
        }

        const data = await response.json();
        console.log(data);

        // Store the JWT token in local storage
        localStorage.setItem('jwtToken', data.token);

        router.push('/dashboard');
      } catch (error) {
        console.error('Error:', error);
      }
    };

    return {
      email,
      password,
      login
    };
  }
});
</script>

<template>
  <main class="form-container">
    <div class="form-box">
      <h1>User Login</h1>
      <form @submit.prevent="login" class="form">
        <label for="email">Email</label>
        <input type="text" id="email" v-model="email" required>
        <label for="password">Password</label>
        <input type="password" id="password" v-model="password" required>
        <button type="submit">Login</button>
      </form>
    </div>
  </main>
</template>

<style scoped>
.form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f0f0f0;
}

.form-box {
  background-color: #ffffff;
  padding: 2rem;
  border-radius: 0.5rem;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.form label {
  font-weight: bold;
  color: #333333;
}

.form input {
  padding: 0.5rem;
  border-radius: 0.25rem;
  border: 1px solid #cccccc;
}

.form button {
  padding: 0.5rem 1rem;
  border-radius: 0.25rem;
  border: none;
  background-color: #333333;
  color: white;
  cursor: pointer;
}
</style>