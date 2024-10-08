// src/router/index.ts
import { createRouter, createWebHistory } from 'vue-router';
import LoginPage from '../components/login.vue';
import Home from '../home.vue';
import home from '../components/home.vue';
import Registration from "@/components/Registration.vue";
import PartyVotesTable from "@/components/PartyVotesTable.vue";
import ManagingAuthority from "@/components/ManagingAuthority.vue";
import Partijenpagina from "@/components/partijenpagina.vue";
import Forum from "@/components/forum.vue";
import Uitslagen from "@/components/uitslagen.vue";

const routes = [
    { path: '/', component: home },
    { path: '/home', component: Home },
    { path: '/registration', component: Registration },
    { path: '/party-votes', component: PartyVotesTable },
    { path: '/managing-authorities', component: ManagingAuthority },
    { path: '/partijenpagina', component: Partijenpagina},
    { path: '/forum', component: Forum },
    { path: '/uitslagen', component: Uitslagen },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
