import './assets/main.css';
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import Registration from "@/components/Registration.vue";

createApp(App)
    .use(router) // Gebruik de router
    .mount('#app');
