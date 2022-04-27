<template>

    <div class="container-title">
      <h2>BANK</h2>
    </div>

    <div class="container" v-shadow="6">
      <ui-navigation-bar content-selector=".container" stacked>
        <ui-tabs
          v-model="active"
          type="textWithIcon"
          stacked
          :items="items">
        </ui-tabs>
      </ui-navigation-bar>
    </div>

    <div v-show="active == 0" class="container-form">
      <BankTransactionRow />
    </div>

    <div v-if="active == 1" class="container-form">
      <BankPaymentRow />
    </div>
</template>

<script>

import BankPaymentRow from "@/components/Bank/Payment/BankPaymentRow"
import BankTransactionRow from "@/components/Bank/Transaction/BankTransactionRow"

export default {
  components:{ BankPaymentRow, 
  BankTransactionRow 
  },
  data: function () {
    return {
      items: [
        { text: 'Transactions', icon: 'credit_card'},
        { text: 'Recurring payments', icon: 'repeat'},
      ],
      active: 0,
    }
  },

}
</script>




<style>
</style>