<script setup>
import { useMainStore } from "~~/stores/useMainStore";

const state = useMainStore();
</script>
<template>
  <Transition name="slide-fade">
    <p v-if="state.visible">hello</p>
  </Transition>
</template>
<style>
/*
  进入和离开动画可以使用不同
  持续时间和速度曲线。
*/
.slide-fade-enter-active {
  transition: all 0.8s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.3s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateY(20px);
  opacity: 0;
}
</style>
