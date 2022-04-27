<template>
  <div v-if="result[2] == 'INCOMING'"  class="row-div">
    <tr class="row-values row-income" v-shadow="6">
      <td class="item-cell">{{ formatDate(result[3]) }}</td>
      <td class="item-cell">+ {{ result[0].toFixed(2) }} Kč</td>
    </tr>
  </div>
  <div v-else class="row-div">
    <tr class="row-values row-expense" v-shadow="6">
      <td class="item-cell">{{ formatDate(result[3]) }}</td>
      <td class="item-cell"> - {{ result[1].toFixed(2) }} Kč</td>
    </tr>
  </div>

</template>

<script>

import dayjs from 'dayjs';

export default {
  name: "BankPaymentItem",
  props: {
    item: {
      type: Object,
      required: true
    }
  },
  computed:{
    result(){
      return [
          this.item.value.amount,
          this.item.accountId,
          this.item.direction,
          this.item.valueDate,
        ]
    }
  },
  methods: {
    formatDate(dateString) {
      const date = dayjs(dateString);
      return date.format('DD. MM. YYYY');
    },
  }
}
</script>


<style>
</style>