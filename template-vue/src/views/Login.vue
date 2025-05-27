<template>
  <div class="login">
    <div class="login__container">
      <div class="login__container__title">
        <h1>Sign in</h1>
      </div>
      <div class="login__container__form">
        <form @submit.prevent="login">
          <div class="login__container__form__input">
            <input type="text" placeholder="Username" v-model="username" />
          </div>

          <div class="login__container__form__input">
            <input type="password" placeholder="Password" v-model="password" />
          </div>

          <div class="login__container__form__button">
            <button type="submit">Sign in</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { getInfo } from "@/api/user.js";
import { useRouter } from "vue-router";
import { useUserPermission } from "@/stores/permission.js";

const router = useRouter();
const userPermission = useUserPermission();

const username = ref("");
const password = ref("");

const login = async () => {
  const ret = await getInfo({
    username: username.value,
    password: password.value,
  });
  const admin = "admin";
  if (admin == username.value && admin == password.value) {
    console.log(ret);
    userPermission.setPermissions(ret.data);
    router.push({ name: "home" });
  } else {
    alert("用户名或密码错误");
  }
};
</script>
