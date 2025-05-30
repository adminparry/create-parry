<script setup>
import { useMainStore } from "~~/stores/useMainStore";
const store = useMainStore();
store.message = "Network Request";
store.$subscribe(() => {
  const body = document.body || document.documentElement.body;
  if (store.visible) {
    body.classList.add("overflow-hidden");
    setTimeout(() => {
      store.visible = false;
    }, 1000);
  } else {
    body.classList.remove("overflow-hidden");
  }
});
</script>
<template>
  <transition name="fade">
    <div class="msg" v-show="store.visible">
      <div class="style">
        {{ store.message }}
      </div>
    </div>
  </transition>
</template>
<style>
.msg {
  @apply fixed tblr row;
}
.msg .style {
  @apply px-8 py-3 rounded-xl bg-black text-white row;
}
</style>
