<template>
  <div class="row-div" v-if="filterDate(result[1])">

    <tr v-if="result[0] == 0" class="row row-income" v-shadow="6">
      <td class="item">{{ formatDate(result[1]) }}</td>
      <td class="item" v-if="result[0] == 0"> + {{ result[2].toFixed(2) }} Kč</td>
    </tr>

    <tr v-if="result[0] == 1" class="row row-expense" v-shadow="6">
      <td class="item">{{ formatDate(result[1]) }}</td>
      <td class="item" v-if="result[0] == 1"> - {{ result[2].toFixed(2) }} Kč</td>
    </tr>
  </div>
</template>

<script>

import dayjs from 'dayjs';
import moment from 'moment';

export default {
  name: "IncomeExpenseItem",
  props: {
    dateStart: String,
    dateEnd: String,
    item: {
      type: Object,
      required: true
    },
    
  },
  computed:{
    result(){
      return [
          this.item.incomeExpenseType,
          this.item.date,
          this.item.value,
        ]
    }
  },
  methods: {
    formatDate(dateString) {
      const date = dayjs(dateString);
      return date.format('DD. MM. YYYY');
    },

    filterDate(dateString) {
      const date = new Date(dateString);
      const from = new Date(this.dateStart);
      const to = new Date(this.dateEnd);
      return moment(date).isBetween(from, to, 'days', '[]');
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

</style>