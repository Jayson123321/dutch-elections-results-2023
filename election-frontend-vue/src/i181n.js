// src/i18n.js
import { createI18n } from 'vue-i18n';

const messages = {
    en: {
        common: {
            home: 'Home',
            results: 'Results',
            parties: 'Parties',
            login: 'Login',
            search: 'Search...',
            toggle_button: 'ENG'
        },
        header: {
            home: 'Home',
            results: 'Results',
            parties: 'Parties',
            login: 'Login',
            search: 'Search...',
            toggle_language: 'Toggle Language',
        },
    },
    nl: {
        common: {
            home: 'Home',
            results: 'Resultaten',
            parties: 'Partijen',
            login: 'Inloggen',
            search: 'Zoeken...',
            toggle_button: 'NL'
        },
        header: {
            home: 'Home',
            results: 'Resultaten',
            parties: 'Partijen',
            login: 'Inloggen',
            search: 'Zoeken...',
            toggle_language: 'Taal Wisselen',
        },
    }
};

const i18n = createI18n({
    locale: 'en',
    fallbackLocale: 'en',
    messages,
});

export default i18n;