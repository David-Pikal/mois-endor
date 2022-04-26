<template>
    
    <div class="container-title">
      <h2>MY BANK</h2>
    </div>

    <div class="container-form" v-shadow="6">
      <BankCard />
    </div>
      
</template>

<script>

import BankCard from "@/components/Home/BankCard"

export default {
  components:{ BankCard },
}
</script>


<style scoped>

.container-title {
  display: flex;
  justify-content: center;
  padding: 20px;
}

</style>