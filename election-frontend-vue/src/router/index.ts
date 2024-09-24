import { createRouter, createWebHistory } from 'vue-router';
import loginPage from '../components/login.vue';
import home from '../home.vue';
import Registration from "@/components/Registration.vue";
const routes = [
    { path: '/', component: loginPage },
    { path: '/home', component: home },
    { path: '/Registration', component: Registration },
];
//test
const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
