<script setup>
import { useMainStore } from "~~/stores/useMainStore";
const store = useMainStore();
</script>
<template>
  <div class="mt-20">
    <ToolTip />
    <button
      class="border-2 border-sky-50"
      @click="store.visible = !store.visible"
    >
      button
    </button>
  </div>
</template>
