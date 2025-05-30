<script setup>
import Test from "@/components/others/Test.vue";
const visible = useState("visible", () => false);
</script>
<template>
  <div class="">
    <button @click="visible = !visible"><Test /></button>

    <Popup :visible="visible">
      <p class="text-center">Edit your name</p>
      <p>
        <input class="text-center" type="text" value="Michael_123" />
      </p>
      <div class="flex justify-center">
        <Button status="disable">Mint</Button>
      </div>
    </Popup>
  </div>
</template>
