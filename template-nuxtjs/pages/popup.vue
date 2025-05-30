<script setup>
const visible = useState("visible", () => false);
</script>
<template>
  <div class="contaner mt-20">
    <button @click="visible = !visible">popup</button>

    <Snakebar :visible="visible">
      <div class="w-full">
        <div class="share mt-10">
          <div>
            Download and get rewards instantly! I’ve just got my cash out! Come
            and join my team with a click:
          </div>
          <div class="text-sky-500">
            <a class="" href="https://www.google.com" target="_blank">
              https://www.google.com
            </a>
          </div>
          <div class="flex justify-end text-sky-500">Copy</div>
        </div>
        <div class="mt-5 flex flex-col justify-center items-center relative">
          <div class="line"></div>

          <div class="pl-4 pr-4 bg-white or">or</div>
        </div>
        <div class="flex flex-row justify-around mt-5">
          <div class="column">
            <img width="50" src="@/assets/svg/contacts.svg" alt="" />
            <div>facebook</div>
          </div>
          <div class="column">
            <img width="50" src="@/assets/svg/contacts.svg" alt="" />
            <div>facebook</div>
          </div>
          <div class="column">
            <img width="50" src="@/assets/svg/contacts.svg" alt="" />
            <div>facebook</div>
          </div>
        </div>
      </div>
    </Snakebar>
  </div>
</template>
