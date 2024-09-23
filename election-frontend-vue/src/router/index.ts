import { createRouter, createWebHistory } from 'vue-router';
import loginPage from '../components/login.vue';
import home from '../home.vue';
const routes = [
    { path: '/', component: loginPage },
    { path: '/home', component: home },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
