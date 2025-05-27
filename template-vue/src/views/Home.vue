<template>
  <div>
    <p v-permission="'menu:r'">{{ username }}</p>
    <p>{{ userStore.age }}</p>
    <button @click="updateName">Change Name</button>
    <button @click="increaseAge">Increase Age</button>
    <router-view></router-view>
  </div>
</template>

<script setup>
import { useUserStore } from "@/stores/userStore";
import { useUserPermission } from "@/stores/permission";
const userStore = useUserStore();
const permissionStore = useUserPermission();

console.log(permissionStore.permissions)
const { incrementAge, updateUsername, username } = userStore;

function updateName() {
  updateUsername("Jane Doe");
}
function increaseAge() {
  incrementAge();
}
</script>
