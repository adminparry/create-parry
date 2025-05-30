<script setup>
const props = defineProps(["visible"]);

const visible = useState("visible", () => props.visible);
watch(visible, (visible, o) => {
  const body = document.body || document.documentElement.body;
  if (visible) {
    body.classList.add("overflow-hidden");
  } else {
    body.classList.remove("overflow-hidden");
  }
});
</script>
<template>
  <transition name="fade">
    <div class="mask" v-show="visible">
      <transition name="slide-fade">
        <div class="dialog" v-show="visible">
          <div class="flex justify-end">
            <img
              src="@/assets/svg/close.svg"
              alt=""
              @click="visible = !visible"
            />
          </div>
          <div class="p-5">
            <slot />
          </div>
        </div>
      </transition>
    </div>
  </transition>
</template>
<style>
.mask {
  background: rgba(0, 0, 0, 0.5);
  @apply fixed left-0 top-0 right-0 bottom-0  flex items-center  justify-center;
}
.dialog {
  @apply bg-white p-5 rounded-xl opacity-100;
}
.popup {
  width: 222px;
  height: 52px;
  @apply flex flex-row bg-black  text-gray-50 justify-center items-center rounded-xl;
}
</style>
