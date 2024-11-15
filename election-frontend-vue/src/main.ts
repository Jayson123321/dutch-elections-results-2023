import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import '@fortawesome/fontawesome-free/css/all.css';
import '@fortawesome/fontawesome-free/js/all.js';
import { createVuetify } from 'vuetify';
import 'vuetify/dist/vuetify.min.css';
import * as components from 'vuetify/components';
import * as directives from 'vuetify/directives';

const vuetify = createVuetify({
    components,
    directives,
});

const app = createApp(App);
app.use(router);
app.use(vuetify);
app.mount('#app');
