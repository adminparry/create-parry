<script setup>
const { data: count } = await useFetch("/api/hello");
</script>
<template>
  <h1>{{ count }}</h1>
</template>
