<template>
    
    <div class="container-title">
      <h2>HOME</h2>
    </div>

    <div class="container-form" v-shadow="6">
      <BankCard/>
    </div>
      
</template>

<script>

import BankCard from "@/components/Home/BankCard"

export default {
  components:{ BankCard },
}
</script>


<style>
</style>