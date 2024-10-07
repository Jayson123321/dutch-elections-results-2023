import { createApp } from 'vue';
import App from './App.vue'; // Zorg ervoor dat dit pad correct is
import router from './router'; // Zorg ervoor dat dit pad correct is

const app = createApp(App);
app.use(router); // Gebruik de router
app.mount('#app');
