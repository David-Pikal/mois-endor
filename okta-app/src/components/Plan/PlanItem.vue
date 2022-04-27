<template>
  <div class="row-div">
    <tr class="row-values" v-shadow="6">
      <td class="item-cell">{{ result[0] }}</td>
      <td class="item-cell">{{ formatDate(result[1]) }}</td>
      <td class="item-cell">{{ formatDate(result[2]) }}</td>
      <td class="item-cell">{{ result[3].toFixed(2) }} Kč</td>
      <td class="item-cell"> - {{ getValuePerMonth(result[3], result[1], result[2]).toFixed(2) }} Kč</td>
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

<style>
</style>