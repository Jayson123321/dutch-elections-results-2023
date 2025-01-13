<script lang="ts">
import { defineComponent, ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

export default defineComponent({
  name: "LoginComponent",
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
          const errorText = await response.text();
          alert(`Login failed: ${errorText}`);
          throw new Error(`Login failed: ${errorText}`);
        }

        const data = await response.json();
        console.log(data);

        localStorage.setItem('jwtToken', data.token);
        await router.push('/');
      } catch (error) {
        console.error('Error:', error);
      }
    };

    onMounted(() => {
      const isDarkMode = document.documentElement.getAttribute('data-theme') === 'dark';
      if (isDarkMode) {
        document.body.classList.add('dark-mode');
      } else {
        document.body.classList.remove('dark-mode');
      }
    });

    return {
      email,
      password,
      login
    };
  }
});
</script>

<template>
  <div>
    <router-link to="/" class="home-link">Home</router-link>
      <div class="form-box">
        <h1>Login</h1>
        <form @submit.prevent="login" class="form">
          <label for="email">Email</label>
          <input type="email" id="email" v-model="email" required>
          <label for="password">Password</label>
          <input type="password" id="password" v-model="password" required>
          <button type="submit">Login</button>
        </form>
        <p>Don't have an account? <router-link to="/registration">Register here</router-link></p>
      </div>
  </div>
</template>

<style scoped>
.home-link {
  position: absolute;
  top: 10px;
  left: 10px;
  color: var(--link-color);
  text-decoration: underline;
  cursor: pointer;
}

.form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: var(--background-color);
}

.form-box {
  background-color: var(--box-background-color);
  padding: 2rem;
  border-radius: 0.5rem;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  width: 100%;
  position: relative;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.form label {
  font-weight: bold;
  color: var(--text-color);
}

.form input {
  padding: 0.5rem;
  border-radius: 0.25rem;
  border: 1px solid var(--input-border-color);
}

.form button {
  padding: 0.5rem 1rem;
  border-radius: 0.25rem;
  border: none;
  background-color: var(--button-background-color);
  color: var(--button-text-color);
  cursor: pointer;
  transition: background-color 0.3s;
}

.form button:hover {
  background-color: var(--button-hover-background-color);
}

p {
  margin-top: 1rem;
  text-align: center;
}

router-link {
  color: var(--link-color);
  text-decoration: underline;
  cursor: pointer;
}
</style>