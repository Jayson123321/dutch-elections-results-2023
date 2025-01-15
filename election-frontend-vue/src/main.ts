import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { createVuetify } from 'vuetify';
import i18n from './i181n.js';
import translateDirective from './translateDirective.js';

const vuetify = createVuetify({
});

const app = createApp(App);
app.use(router);
app.use(vuetify);
app.use(i18n);
app.directive('translate', translateDirective);
app.mount('#app');