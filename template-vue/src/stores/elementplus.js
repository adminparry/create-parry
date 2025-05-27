import { defineStore } from "pinia";
import { useI18n } from 'vue-i18n'


async function loadElementLocale(lang) {
  switch (lang) {
    case "zh":
      return (await import("element-plus/dist/locale/zh-cn.mjs")).default;
    case "en":
      return (await import("element-plus/dist/locale/en.mjs")).default;
    default:
      return (await import("element-plus/dist/locale/en.mjs")).default;
  }
}

const changeLang = async (lang) => {
  const locale = await loadElementLocale(lang);
  const i18n = useI18n();
  i18n.global.locale.value = lang;
  ElementPlus.locale(locale);
};


export const useElementPlusConfig = defineStore("elementplus", {
  state: () => ({
    locale: changeLang(localStorage.getItem("lang") || "en"),
    size: "default",
  }),
  actions: {
    setLocale(locale) {
      this.locale = locale;
      localStorage.setItem("lang", locale);
      element.setLocale(locale);
    },
    setSize(size) {
      this.size = size;
      elementPlusConfig.setSize(size);
    },
  },
});
