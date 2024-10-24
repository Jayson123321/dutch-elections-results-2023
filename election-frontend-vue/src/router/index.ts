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
import ChooseResults from "@/components/ChooseResults.vue";
import kandidatenpagina from "@/components/kandidatenpagina.vue";
import allekandidatenpagina from "@/components/allekandidatenpagina.vue";
import TotalResults from "@/components/TotalVotes.vue";
import Admin from '../components/Admin.vue';
import kandidatenuitslag from "@/components/kandidatenuitslag.vue";
import LocalAuthoritiesResults from "@/components/LocalAuthoritiesResults.vue";
const routes = [
    { path: '/', component: home },
    { path: '/home', component: Home },
    { path: '/registration', component: Registration },
    { path: '/party-votes', component: PartyVotesTable },
    { path: '/managing-authorities', component: ManagingAuthority },
    { path: '/partijenpagina', component: Partijenpagina},
    { path: '/partijen/:id', name: 'kandidatenpagina', component: kandidatenpagina, props: true},
    { path: '/forum', component: Forum },
    { path: '/uitslagen', component: Uitslagen },
    { path: '/choose-results', component: ChooseResults },
    { path: '/totalResults', component: TotalResults },
    { path: '/allekandidatenpagina', component: allekandidatenpagina },
    { path: '/admin', component: Admin },
    { path: '/kandidatenuitslag/:id', name: 'kandidatenuitslag', component: kandidatenuitslag, props: true },
    { path: '/localAuthorityResults', component: LocalAuthoritiesResults }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
