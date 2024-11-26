<template>
  <div>
    <h1>CORS Test</h1>
    <button @click="testCORS">Test CORS</button>
  </div>
</template>

<script>
export default {
  methods: {
    async testCORS() {
      try {
        const response = await fetch('http://localhost:8080/api/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ email: 'test@example.com', password: 'password' })
        });
        const data = await response.json();
        console.log('Success:', data);
      } catch (error) {
        console.error('Error:', error);
      }
    }
  }
}
</script>

<style scoped>
/* Add any styles you need here */
</style>