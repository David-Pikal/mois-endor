<template>
  <div class="row-div">
  <tr class="row-values row-expense" v-shadow="6">
    <td class="item-wide-center-cell">{{ formatDate(result[0]) }}</td>
    <td class="item-wide-center-cell">{{ formatDate(result[1]) }}</td>
    <td class="item-wide-center-cell"> Week{{ result[2] }}</td>
    <td class="item-wide-center-cell"> - {{ Math.abs(result[3]).toFixed(2) }} Kč</td>
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
          this.item.firstPayment,
          this.item.lastPayment,
          this.item.interval,
          this.item.value.amount,
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