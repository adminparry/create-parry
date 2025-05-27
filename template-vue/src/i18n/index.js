import { createI18n } from "vue-i18n";
import zh from "./zh";
import en from "./en";


const i18n = createI18n({
  legacy: false,

  messages: {
    en: {
      message: en,
    },
    zh: {
      message: zh,
    },
  },
});

export default i18n;
