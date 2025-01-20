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
        home: {
            party: 'Party',
            party_description: 'Discover the political parties in the Netherlands and their candidates.',
            results: 'Results',
            results_description: 'View the results of the 2023 elections in the Netherlands.',
            forum: 'Forum',
            forum_description: 'Ask questions and discuss topics with other users.',
        },
        header: {
            home: 'Home',
            results: 'Results',
            parties: 'Parties',
            login: 'Login',
            search: 'Search...',
            toggle_language: 'Toggle Language',
            educativePage: 'Educative Support',
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
        managingAuthority: {
            description: 'Select an authority, then a reporting unit. You can view votes per candidate. Only candidates who received votes are displayed.',
            score_per_stembureau: 'Score per polling station',
            verkiezingen: 'Elections',
            filter: 'Filter',
            select_authority: 'Select an authority',
            select_reporting_unit: 'Select a reporting unit',
            search_reporting_unit: 'Search a reporting unit',
            view_votes: 'View votes',
            total_votes: 'Total votes',
            votes: 'Votes',
            show_votes_per_candidate: 'Show votes per candidate',
        },
        localAuthoritiesResults: {
            description: 'Select an authority. You can view votes per candidate. Only candidates who received votes are displayed.',
            score_per_stembureau: 'Score per polling station',
            verkiezingen: 'Elections',
            filter: 'Filter',
            select_authority: 'Select an authority',
            select_reporting_unit: 'Select a reporting unit',
            search_reporting_unit: 'Search a reporting unit',
            view_votes: 'View votes',
            total_votes: 'Total votes',
            votes: 'Votes',
            show_votes_per_candidate: 'Show votes per candidate',
            party_name: 'Party Name',
            results: 'Results',
        },
        alleKandidaten: {
            title: 'All Candidates',
            searchPlaceholder: 'Search candidates...',
            gender: 'Gender',
            address: 'Address',
            party: 'Party',
            unknown: 'Unknown',
            prev: 'Previous',
            next: 'Next'
        },
        userForums: {
            title: 'Your Forums',
            noForums: 'No forums to display.',
            deleteButton: 'Delete Forum',
            repliesTitle: 'Replies:',
            loadMoreButton: 'Load More'
        },
        partijenPagina: {
            title: 'Parties and Candidates',
            allCandidates: 'All candidates'
        },
        kandidaatUitslag: {
            title: 'Candidate Result',
            name: 'Name',
            totalVotes: 'Total votes',
            selectAuthority: 'Select an authority',
            resultsFor: 'Results for',
            votesForAuthority: 'Votes for authority',
            noVotesForAuthority: 'No votes found for this authority.',
            selectReportingUnit: 'Select a reporting unit',
            resultsForReportingUnit: 'Results for reporting unit',
            votesForReportingUnit: 'Votes for reporting unit',
            noVotesForReportingUnit: 'No votes found for this reporting unit.'
        },
        forum: {
            title: 'Forum',
            titlePlaceholder: 'Title',
            description: 'Discuss here about the parties',
            descriptionPlaceholder: 'Description',
            submitButton: 'Submit Forum',
            postedForums: 'Posted Forums',
            replyPlaceholder: 'Reply to this question',
            submitReplyButton: 'Submit Reply',
            deleteButton: 'Delete Post',
            prevPage: 'Previous',
            nextPage: 'Next',
            page: 'Page',
            of: 'of'
        },
        educativePage: {
            title: 'Educational Support',
            description: 'Learn more about elections, political processes, and how your vote matters.',
            electionExplanation: 'Explanation about elections',
            electionExplanationDesc: 'Read more about how elections work and how political parties win seats.',
            readMore: 'Read more',
            downloadPowerPoint: 'Download PowerPoint',
            downloadPowerPointDesc: 'Download a PowerPoint presentation about elections.',
            madeBy: 'PowerPoint made by Seneca.',
            testYourKnowledge: 'Test your knowledge',
            testYourKnowledgeDesc: 'Take a quiz and find out how much you know about the Dutch political system.',
            startQuiz: 'Start quiz'
        },
        politicalNews: {
            title: 'Stay updated with the latest political news!',
            source: 'Source: Nu.nl',
            posted: 'Posted:'
        },
        quiz: {
            title: 'Elections 2023 Quiz',
            nextButton: 'Next question',
            resultsMessage: 'You answered {{ correctAnswers }} out of {{ questionsLength }} questions correctly!',
            thankYouMessage: 'Thank you for taking the quiz!',
            questions: [
                {
                    question: "Which candidate received the most votes in the 2023 elections in the Netherlands?",
                    options: ["Frans Timmermans", "Geert Wilders", "Dylan Yesilguz", "Pieter Omtzigt"],
                    answer: "Dylan Yesilguz"
                },
                {
                    question: "What was the main theme during the 2023 elections?",
                    options: ["Climate change", "Education", "Migration and integration", "Healthcare"],
                    answer: "Climate change"
                },
                {
                    question: "Which party won the most seats in the House of Representatives during the 2023 elections?",
                    options: ["D66", "VVD", "FvD", "PVV"],
                    answer: "VVD"
                },
                {
                    question: "Who was the leader of the GroenLinks party during the 2023 elections?",
                    options: ["Jesse Klaver", "Tofik Dibi", "Lilian Marijnissen", "Frans Timmermans"],
                    answer: "Frans Timmermans"
                },
                {
                    question: "What was the goal of the DENK party's campaign?",
                    options: ["Striving for more open borders", "Improving the healthcare sector", "Promoting integration and inclusion", "Lowering taxes"],
                    answer: "Promoting integration and inclusion"
                }

            ]
        }
    },
    nl: {
        home: {
            party: 'Partij',
            party_description: 'Ontdek de politieke partijen in Nederland en hun kandidaten.',
            results: 'Resultaten',
            results_description: 'Bekijk de resultaten van de verkiezingen van 2023 in Nederland.',
            forum: 'Forum',
            forum_description: 'Stel vragen en bespreek onderwerpen met andere gebruikers.',
        },
        common: {
            home: 'Home',
            results: 'Resultaten',
            parties: 'Partijen',
            login: 'Inloggen',
            search: 'Zoeken...',
            toggle_button: 'NL'
        },
        userForums: {
            title: 'Jouw Forums',
            noForums: 'Geen forums om weer te geven.',
            deleteButton: 'Verwijder Forum',
            repliesTitle: 'Antwoorden:',
            loadMoreButton: 'Meer laden'
        },
        header: {
            home: 'Home',
            results: 'Resultaten',
            parties: 'Partijen',
            login: 'Inloggen',
            search: 'Zoeken...',
            toggle_language: 'Taal Wisselen',
            educativePage: 'Educatieve Ondersteuning',
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
        managingAuthority: {
            description: 'Selecteer een gemeente, vervolgens een stembureau. U kunt stemmen per kandidaat bekijken. Alleen kandidaten die stemmen hebben ontvangen worden weergegeven.',
            score_per_stembureau: 'Score per stembureau',
            verkiezingen: 'Verkiezingen',
            filter: 'Filter',
            select_authority: 'Selecteer een gemeente',
            select_reporting_unit: 'Selecteer een stembureau',
            search_reporting_unit: 'Zoek een stembreau',
            view_votes: 'Bekijk de stemmen',
            total_votes: 'Totaal aantal stemmen',
            votes: 'Stemmen',
            show_votes_per_candidate: 'Toon stemmen per kandidaat',
        },
        localAuthoritiesResults: {
            description: 'Selecteer een gemeente. U kunt stemmen per kandidaat bekijken. Alleen kandidaten die stemmen hebben ontvangen worden weergegeven.',
            score_per_stembureau: 'Score per stembureau',
            verkiezingen: 'Verkiezingen',
            filter: 'Filter',
            select_authority: 'Selecteer een gemeente',
            select_reporting_unit: 'Selecteer een stembureau',
            search_reporting_unit: 'Zoek een stembreau',
            view_votes: 'Bekijk de stemmen',
            total_votes: 'Totaal aantal stemmen',
            votes: 'Stemmen',
            show_votes_per_candidate: 'Toon stemmen per kandidaat',
            party_name: 'Partij Naam',
            results: 'resultaten',
        },
        alleKandidaten: {
            title: 'Alle Kandidaten',
            searchPlaceholder: 'Zoek kandidaten...',
            gender: 'Geslacht',
            address: 'Adres',
            party: 'Partij',
            unknown: 'Onbekend',
            prev: 'Vorige',
            next: 'Volgende'
        },
        partijenPagina: {
            title: 'Partijen en Kandidaten',
            allCandidates: 'Alle kandidaten'
        },
        kandidaatUitslag: {
            title: 'Resultaat Kandidaat',
            name: 'Naam',
            totalVotes: 'Totale stemmen',
            selectAuthority: 'Kies een gemeente',
            resultsFor: 'Resultaten voor',
            votesForAuthority: 'Stemmen gemeente',
            noVotesForAuthority: 'Geen stemmen gevonden voor deze gemeente.',
            selectReportingUnit: 'Kies een rapportage-eenheid',
            resultsForReportingUnit: 'Resultaten voor rapportage-eenheid',
            votesForReportingUnit: 'Stemmen rapportage-eenheid',
            noVotesForReportingUnit: 'Geen stemmen gevonden voor deze rapportage-eenheid.'
        },
        forum: {
            title: 'Forum',
            titlePlaceholder: 'Titel',
            description: 'Discussieer hier over de partijen',
            descriptionPlaceholder: 'Beschrijving',
            submitButton: 'Forum Posten',
            postedForums: 'Geposte Forums',
            replyPlaceholder: 'Beantwoord dit vraag',
            submitReplyButton: 'Antwoord Posten',
            deleteButton: 'Verwijder post',
            prevPage: 'Vorige',
            nextPage: 'Volgende',
            page: 'Pagina',
            of: 'van'
        },
        educativePage: {
            title: 'Educatieve Ondersteuning',
            description: 'Leer meer over verkiezingen, politieke processen en hoe je stem invloed heeft.',
            electionExplanation: 'Uitleg over verkiezingen',
            electionExplanationDesc: 'Lees meer over hoe verkiezingen werken en hoe politieke partijen hun zetels winnen.',
            readMore: 'Lees meer',
            downloadPowerPoint: 'Download PowerPoint',
            downloadPowerPointDesc: 'Download een PowerPoint-presentatie over verkiezingen.',
            madeBy: 'PowerPoint gemaakt door Seneca.',
            testYourKnowledge: 'Test je kennis',
            testYourKnowledgeDesc: 'Doe een quiz en ontdek hoeveel je weet over het Nederlandse politieke systeem.',
            startQuiz: 'Start quiz',
            educativePage: 'Educatieve Ondersteuning',
        },
        politicalNews: {
            title: 'Blijf op de hoogte van het laatste politieke nieuws!',
            source: 'Bron: Nu.nl',
            posted: 'Geplaatst:'
        },
        quiz: {
            title: 'Verkiezingen 2023 Quiz',
            nextButton: 'Volgende vraag',
            resultsMessage: 'Je hebt {{ correctAnswers }} van de {{ questionsLength }} vragen goed beantwoord!',
            thankYouMessage: 'Bedankt voor het doen van de quiz!',
            questions: [
                {
                    question: "Op welke kandidaat heeft Nederland het meeste gestemd in de verkiezingen van 2023?",
                    options: ["Frans Timmermans", "Geert Wilders", "Dylan Yesilguz", "Pieter Omtzigt"],
                    answer: "Dylan Yesilguz"
                },
                {
                    question: "Wat was het belangrijkste thema tijdens de verkiezingen van 2023?",
                    options: ["Klimaatverandering", "Onderwijs", "Migratie en integratie", "Gezondheidszorg"],
                    answer: "Klimaatverandering"
                },
                {
                    question: "Welke partij behaalde de meeste zetels in de Tweede Kamer tijdens de verkiezingen van 2023?",
                    options: ["D66", "VVD", "FvD", "PVV"],
                    answer: "VVD"
                },
                {
                    question: "Wie was de leider van de partij GroenLinks tijdens de verkiezingen van 2023?",
                    options: ["Jesse Klaver", "Tofik Dibi", "Lilian Marijnissen", "Frans Timmermans"],
                    answer: "Frans Timmermans"
                },
                {
                    question: "Wat was het doel van de campagne van de partij DENK?",
                    options: ["Streven naar meer open grenzen", "Verbeteren van de zorgsector", "Bevorderen van integratie en inclusie", "Verlaging van de belastingen"],
                    answer: "Bevorderen van integratie en inclusie"
                }
            ]
        }
    }
};

const i18n = createI18n({
    locale: 'en',
    fallbackLocale: 'en',
    messages,
});

export default i18n;