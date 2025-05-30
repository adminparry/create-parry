<template>
  <div class="mt-60">
    <div class="login p-5">
      <div class="mb-6 text-xl">Log In</div>
      <div class="mb-10">Email</div>
      <div class="mb-10">
        <Input1 placeholder="Email Address" />
      </div>
      <Input1 placeholder="Password">
        <template #suffix>
          <img src="@/assets/svg/icon.svg" alt="" />
        </template>
      </Input1>
      <div class="flex justify-end text-amber-500 mt-4">
        <a target="_blank" href="https://www.google.com">Forgot Password?</a>
      </div>
    </div>
    <!-- <Animate /> -->
  </div>
</template>
<style>
.share {
  background-color: #fffbee;
  @apply rounded-xl border-2 p-5;
}
.line {
  z-index: 1;
  height: 1px;
  @apply w-40 bg-black top-1/2 absolute;
}
.or {
  z-index: 2;
}
.login {
  @apply rounded-xl;
}
</style>
