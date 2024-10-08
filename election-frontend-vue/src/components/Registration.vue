<script lang="ts">
import { defineComponent, ref } from 'vue'; // Import ref
import { useRouter } from 'vue-router';

export default defineComponent({
  name: "Registration",
  setup() {
    const router = useRouter();

    // Gebruik ref om reactieve variabelen te maken
    const username = ref('');
    const password = ref('');
    const email = ref('');

    const register = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            username: username.value,
            password: password.value,
            email: email.value
          }),
        });

        if (!response.ok) {
          throw new Error('Registration failed');
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
      email,
      register
    };
  }
});
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
