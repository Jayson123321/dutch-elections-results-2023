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
            toggle_button: 'En'
        },
        header: {
            home: 'Home',
            results: 'Results',
            parties: 'Parties',
            login: 'Login',
            search: 'Search...',
            toggle_language: 'Toggle Language',
        },
        totalResults: {
            tweede_kamer_uitslagen: 'House of Representatives Results',
        },
        chooseResults: {
            welcome_message: 'Welcome to Demo-Crazy',
            description_message: 'The place where you can find everything about political parties in the Netherlands.',
            local_authority_results: 'Local Authority Results',
            discover_local_votes: 'Discover what your municipality has voted!',
            local_results_per_polling_station: 'Local results per polling station',
            view_results_per_polling_station: 'View the results per polling station',
            total_results: 'Total results',
            view_total_results: 'View the total results',
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
        totalResults: {
            tweede_kamer_uitslagen: 'Tweede kamer uitslagen',
        },
        chooseResults: {
            welcome_message: 'Welkom bij Demo-Crazy',
            description_message: 'De plek waar je alles kunt vinden over de politieke partijen in Nederland.',
            local_authority_results: 'Gemeentelijke Uitslagen',
            discover_local_votes: 'Ontdek wat jouw gemeente heeft gestemd!',
            local_results_per_polling_station: 'Gemeentelijke uitslag per stembureau',
            view_results_per_polling_station: 'Bekijk de uitslag per stembureau',
            total_results: 'Totaal uitslag',
            view_total_results: 'Bekijk de totaal uitslag',
        },
    }
};

const i18n = createI18n({
    locale: 'en',
    fallbackLocale: 'en',
    messages,
});

export default i18n;