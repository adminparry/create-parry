
import { defineStore } from 'pinia';
 
export const useUserStore = defineStore('user', {
  state: () => ({
    username: 'John Doe',
    age: 30,
    permissions: []
  }),
  actions: {
    updateUsername(newUsername) {
      this.username = newUsername;
    },
    incrementAge() {
      this.age++;
    }
  }
});