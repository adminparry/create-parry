<script setup>
import { useMainStore } from "@/stores/useMainStore";

const state = useMainStore();
</script>
<template>
  <div class="container">
    <div class="mt-20 flex flex-row">
      <div>{{ state.name }}</div>
      <div>{{ state.counter }}</div>
    </div>
    <button
      class="w-20 border-spacing-16 border-red-500 border-2 mr-10"
      @click="state.counter++"
    >
      +
    </button>
    <button class="border-2 w-20" @click="state.counter--">-</button>
  </div>
</template>
