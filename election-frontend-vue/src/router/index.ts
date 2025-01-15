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
import PoliticalNews from "@/components/PoliticalNews.vue";
import HeatMap from "@/components/HeatMap.vue";
import Educativepage from "@/components/Educativepage.vue";
import Quiz from "@/components/Quiz.vue";
import verkiezingsuitleg from "@/components/verkiezingsuitleg.vue";
import MunicipalityPage from "@/components/MunicipalityPage.vue";
import userForums from "@/components/userForums.vue";
import AboutUs from "@/components/About-us.vue";

const routes = [
    { path: '/', component: home },
    { path: '/home', component: Home },
    { path: '/registration', component: Registration },
    { path: '/party-votes', component: PartyVotesTable },
    { path: '/managing-authorities', component: ManagingAuthority },
    { path: '/partijenpagina', component: Partijenpagina},
    { path: '/partijen/:id', name: 'kandidatenpagina', component: kandidatenpagina, props: true},
    { path: '/forum', component: Forum },
    { path: '/forum/:forumId', name: 'forum', component: Forum },
    { path: '/uitslagen', component: Uitslagen },
    { path: '/choose-results', component: ChooseResults },
    { path: '/totalResults', component: TotalResults },
    { path: '/allekandidatenpagina', component: allekandidatenpagina },
    { path: '/admin', component: Admin },
    { path: '/kandidatenuitslag/:id', name: 'kandidatenuitslag', component: kandidatenuitslag, props: true },
    { path: '/localAuthorityResults', component: LocalAuthoritiesResults },
    { path: '/politicalNews', component: PoliticalNews },
    { path : '/login', component: LoginPage },
    { path: '/Heatmap', component: HeatMap },
    { path: '/Educativepage', component: Educativepage },
    { path: '/Quiz', component: Quiz },
    { path: '/verkiezingsuitleg', component: verkiezingsuitleg},
    { path: '/MunicipalityPage', component: MunicipalityPage },
    { path: '/userForums', component: userForums },
    { path: '/About-us', component: AboutUs}
    // { path: '', component: :}

];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
