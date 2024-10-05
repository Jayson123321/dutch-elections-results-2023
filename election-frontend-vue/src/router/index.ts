// src/router/index.ts
import { createRouter, createWebHistory } from 'vue-router';
import LoginPage from '../components/login.vue';
import Home from '../home.vue';
import Registration from "@/components/Registration.vue";
import PartyVotesTable from "@/components/PartyVotesTable.vue";
import ManagingAuthority from "@/components/ManagingAuthority.vue";

const routes = [
    { path: '/', component: LoginPage },
    { path: '/home', component: Home },
    { path: '/registration', component: Registration },
    { path: '/party-votes', component: PartyVotesTable },
    { path: '/managing-authorities', component: ManagingAuthority }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
