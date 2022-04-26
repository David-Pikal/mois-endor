<template>
  <div class="row-div">
    <tr class="row" v-shadow="6">
      <td class="item">{{ result[0] }}</td>
      <td class="item">{{ formatDate(result[1]) }}</td>
      <td class="item">{{ formatDate(result[2]) }}</td>
      <td class="item">{{ result[3].toFixed(2) }} Kč</td>
      <td class="item"> - {{ getValuePerMonth(result[3], result[1], result[2]).toFixed(2) }} Kč</td>
    </tr>
  </div>
</template>

<script>

import dayjs from 'dayjs';

export default {
  name: "PlanItem",
  props: {
    item: {
      type: Object,
      required: true
    },
    
  },
  computed:{
    result(){
      return [
        this.item.title,
          this.item.startDate,
          this.item.endDate,
          this.item.value,
          // this.item.id,
          // this.item.userID,
        ]
    }
  },
  methods: {
    formatDate(dateString) {
      const date = dayjs(dateString);
      return date.format('DD. MM. YYYY');
    },
    getValuePerMonth(value, dateStart, dateEnd) {
      const start = new Date(dateStart);
      const end = new Date(dateEnd);
      const years = end.getFullYear() - start.getFullYear()
      const months = (12 * years) + (end.getMonth() - start.getMonth() + 1)
      return value / months
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