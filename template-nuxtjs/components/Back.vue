<script setup>
const back = () => {
    window.history.go(-1)
}
</script>
<template>
    <div class="fixed left-10 top-10">
        <img @click="back" class="pointer" src="@/assets/svg/back.svg" alt="">
    </div>
</template>
<style>
.pointer {
    cursor: pointer;
}
</style>