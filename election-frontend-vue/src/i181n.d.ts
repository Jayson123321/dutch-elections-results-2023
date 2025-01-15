import { App } from 'vue';
import { I18n } from 'vue-i18n';

declare const i18n: I18n & {
    install(app: App): void;
};

export default i18n;