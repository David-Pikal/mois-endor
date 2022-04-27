<template>
  <div class="row-div">
  <tr class="row row-expense" v-shadow="6">
    <td class="item">{{ formatDate(result[0]) }}</td>
    <td class="item">{{ formatDate(result[1]) }}</td>
    <td class="item"> Week{{ result[2] }}</td>
    <!-- <td class="item" v-if="result[3] < 0 "> - {{ Math.abs(result[3]).toFixed(2) }} Kč</td>
    <td class="item" v-else> + {{ result[3].toFixed(2) }} Kč</td> -->
    <td class="item"> - {{ Math.abs(result[3]).toFixed(2) }} Kč</td>
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
          // this.item._id,
          this.item.firstPayment,
          this.item.lastPayment,
          this.item.interval,
          this.item.value.amount,
          // this.item.partyAccount.prefix,
          // this.item.partyAccount.accountNumber,
          // this.item.partyAccount.bankCode,
          // this.item.value.currency,
          // this.item.additionalInfo.constantSymbol,
          // this.item.additionalInfo.variableSymbol,
          // this.item.additionalInfo.specificSymbol,
          // this.item.dueDate,
          // this.item.accountId,
          // this.item.editableByUser,
          // this.item.realizationStatus,
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

<style scoped>



.item {
  text-align: center; 
  padding: 20px; 
  width: 100%;
}

.row-income {
  background: rgb(215, 255, 215);
}

.row-expense {
  background: rgb(255, 215, 215);
}

.row {
  display: flex;
  justify-content: space-around;

}

.row-div {
  margin: auto;
  width: 80%;
  padding: 10px;
}

.row-income {
  background: rgb(215, 255, 215);
}

.row-expense {
  background: rgb(255, 215, 215);
}


</style>