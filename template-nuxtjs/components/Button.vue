<script setup>
const props = defineProps({
  status: String,
});
</script>
<template>
  <div :class="props.status">
    <slot />
  </div>
</template>
<style>
.btn {
  color: #f8ca5a;
  width: 160px;
  height: 44px;
  @apply bg-black rounded-xl flex items-center justify-center;
}
.loading {
  color: #f8ca5a;
  width: 160px;
  height: 44px;
  @apply bg-white rounded-xl flex items-center justify-center;
}
.disable {
  width: 160px;
  height: 44px;
  background-color: #d7d6d5;
  cursor: not-allowed;
  @apply text-white rounded-xl flex items-center justify-center;
}
</style>
