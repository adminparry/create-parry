<template>
  <div>
    <div ref="chart" style="width: 100%; height: 400px"></div>
  </div>
</template>

<script setup>
import { ref } from "vue";

defineProps({
  msg: String,
});
</script>
