<template>
  <div class="row-div">
    <tr class="row" v-shadow="6">
      <td class="item">{{ result[0] }}</td>
      <td class="item">{{ formatDate(result[1]) }}</td>
      <td class="item">{{ formatDate(result[2]) }}</td>
      <td class="item">{{ result[2].toFixed(2) }} Kč</td>
    </tr>
  </div>
</template>

<script>

import dayjs from 'dayjs';

export default {
  name: "PlanItem",
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