import { defineStore } from 'pinia'
// main is the name of the store. It is unique across your application
// and will appear in devtools
export const useMainStore = defineStore('demo', {
  // a function that returns a fresh state
  state: () => ({
    counter: 0,
    name: 'Eduardo',
    message: "",
    messageStatus: false,
    visible: false
  }),

  persist: {
    paths:['name']
  },
})