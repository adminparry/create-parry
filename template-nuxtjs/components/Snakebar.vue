<script setup>
const props = defineProps({
  visible: Boolean,
});

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
    <div class="snake-mask" v-show="visible">
      <transition name="slide-fade">
        <div class="snake-dialog" v-show="visible">
          <div class="flex justify-end">
            <img
              src="@/assets/svg/close.svg"
              alt=""
              @click="visible = !visible"
            />
          </div>

          <slot />
        </div>
      </transition>
    </div>
  </transition>
</template>
<style>
.snake-mask {
  background: rgba(0, 0, 0, 0.5);
  @apply fixed tblr  flex items-end  justify-center;
}
.snake-dialog {
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
  background-color: white;
  @apply bg-white p-5 w-full;
}
</style>
