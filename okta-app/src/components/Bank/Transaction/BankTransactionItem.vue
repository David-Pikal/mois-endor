<template>
  <div v-if="result[2] == 'INCOMING'"  class="row-div">
    <tr class="row row-income" v-shadow="6">
      <td class="item">{{ formatDate(result[3]) }}</td>
      <td class="item">+ {{ result[1].toFixed(2) }} Kč</td>
    </tr>
  </div>
  <div v-else class="row-div">
    <tr class="row row-expense" v-shadow="6">
      <td class="item">{{ formatDate(result[3]) }}</td>
      <td class="item"> - {{ result[1].toFixed(2) }} Kč</td>
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
          // this.item._id,
          // this.item.value.currency,
          // this.item.partyAccount.prefix,
          // this.item.partyAccount.accountNumber,
          // this.item.partyAccount.bankCode,
          // this.item.partyDescription,
          // this.item.transactionType,
          // this.item.bookingDate,
          // this.item.userDescription,
          // this.item.payerMessage,
          // this.item.payeeMessage,
          // this.item.categoryId,
          // this.item.transactionFee,
          // this.item.transactionFeeCanceled,
          // this.item.additionalInfoDomestic.constantSymbol,
          // this.item.additionalInfoDomestic.variableSymbol,
          // this.item.additionalInfoDomestic.specificSymbol,
          // this.item.additionalInfoForeign.originalValue.amount,
          // this.item.additionalInfoForeign.originalValue.currency,
          // this.item.additionalInfoForeign.exchangeRate,
          // this.item.additionalInfoCard.mcc,
          // this.item.additionalInfoCard.merchantName,
          // this.item.additionalInfoCard.cardNumber,
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
 padding: 20px; 
}


.row-income {
  background: rgb(215, 255, 215);
}

.row-expense {
  background: rgb(255, 215, 215);
}

.row {
  display: flex;
  justify-content: space-between;

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