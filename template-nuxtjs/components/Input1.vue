<script setup>
const props = defineProps(["placeholder"]);
</script>
<template>
  <div class="input1-wrap">
    <slot name="perfix" />
    <input class="w-full pb-4" type="text" :placeholder="props.placeholder" />
    <slot name="suffix"></slot>
  </div>
</template>
<style lang="postcss" scoped>
.input1-wrap {
  border-bottom: 2px solid #d8d8d8;
  @apply flex flex-row justify-between;
  input {
    outline: none;
  }
}
</style>
