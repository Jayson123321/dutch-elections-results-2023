import { createRouter, createWebHistory } from 'vue-router'
import Home from '../components/Home.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    },
    { path: '/footer',
      name: 'footer',
      component: () => import('../components/FooterComponent.vue')
    },
    { path: '/header',
      name: 'header',
      component: () => import('../components/HeaderComponent.vue')},
    { path: '/partijenpagina',
      name: 'partijenpagina',
      component: () => import('../components/partijenpagina.vue')},
    { path: '/forum',
      name: 'forum',
      component: () => import('../components/forum.vue')},
    { path: '/uitslagen',
      name: 'uitslagen',
      component: () => import('../components/uitslagen.vue')},
  ]
})

export default router
