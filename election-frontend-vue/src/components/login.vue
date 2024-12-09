<template>
  <div>
    <h1>Login</h1>
    <form @submit.prevent="login">
      <div>
        <label for="email">Email:</label>
        <input type="email" v-model="email" id="email" required />
      </div>
      <div>
        <label for="password">Password:</label>
        <input type="password" v-model="password" id="password" required />
      </div>
      <button type="submit">Login</button>
    </form>
    <p v-if="errorMessage">{{ errorMessage }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      email: '',
      password: '',
      errorMessage: ''
    };
  },
  methods: {
    async login() {
      try {
        const response = await fetch('http://localhost:8080/api/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({email: this.email, password: this.password})
        });

        if (response.ok) {
          const token = response.headers.get('Authorization').split(' ')[1];
          localStorage.setItem('jwt', token);
          console.log('Login successful:', token);
          this.errorMessage = '';
        } else {
          const data = await response.json();
          this.errorMessage = data.message;
        }
      } catch (error) {
        console.error('Error:', error);
        this.errorMessage = 'An error occurred during login.';
      }
    }
  }
}
</script>

<style scoped>
/* Add any styles you need here */
</style>