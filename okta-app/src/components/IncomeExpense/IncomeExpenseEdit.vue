<template>
    <div class="row-div" v-if="filterDate(result[1])">
    <tr class="row-values" v-shadow="6">
        <div class="row-content">
            <td class="item-cell">{{ formatDate(result[1]) }}</td>
            <td class="item-cell" v-if="result[0] == 0"> + {{ result[2].toFixed(2) }} Kč</td>
            <td class="item-cell" v-if="result[0] == 1"> - {{ result[2].toFixed(2) }} Kč</td>
        </div>
        <td class="item-cell">
            <ui-fab extended @click="removeRow(result[3])">
                <span>Delete</span>
                <template #after="{ iconClass }">
                    <ui-icon :class="iconClass">delete</ui-icon>
                </template>
            </ui-fab>
        </td>
    </tr>
  </div>
</template>

<script>

import apiClient from "@/api/apiClient"
import dayjs from 'dayjs';
import moment from 'moment';

export default {
  name: "IncomeExpenseEdit",
  props: {
      dateStart: String,
      dateEnd: String,
      item: { type: Object, required: true }
  },
  computed:{
    result(){
      return [
          this.item.incomeExpenseType,
          this.item.date,
          this.item.value,
          this.item.id,
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
      return moment(date).isBetween(this.dateStart, this.dateEnd, 'days', '[]');
    },

    async removeRow(idItem){
        const token = this.$auth.getAccessToken()
        await new apiClient().getMyApi({
            accessToken:token,
            method: "DELETE", 
            url: "/IncomeExpenses/" + idItem
            }
        )
        await new apiClient().getMyApi({accessToken:token})
        this.$parent.$parent.componentKey += 1
    }
  }
}
</script>

<style>
</style>