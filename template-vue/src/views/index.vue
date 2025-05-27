<template>
  <div>
    <nav v-for="(item, index) in routes">
      <p>
        <router-link :to="{ name: item.name }">{{ item.name }}</router-link>
      </p>
      <p v-if="item.children">
        <router-link
          v-for="(child, index) in item.children"
          :to="{ name: child.name }"
          >{{ child.name }} ->
        </router-link>
      </p>
    </nav>
    <router-view />
  </div>
</template>
<script setup>
import { routes } from "@/router/routing.config";
</script>
