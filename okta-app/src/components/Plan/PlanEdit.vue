<template>
    <div class="row-div">
    <tr class="row-values" v-shadow="6">
        <div class="row-content">
            <td class="item-cell">{{ result[0] }}</td>
            <td class="item-cell">{{ formatDate(result[1]) }}</td>
            <td class="item-cell">{{ formatDate(result[2]) }}</td>
            <td class="item-cell">{{ result[3].toFixed(2) }} Kč</td>
        </div>
        <td class="item-cell">
            <ui-fab extended @click="removeRow(result[4])">
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
  name: "PlanEdit",
  props: {
      item: { type: Object, required: true }
  },
  computed:{
    result(){
      return [
        this.item.title,
          this.item.startDate,
          this.item.endDate,
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
              url: "/plan/project/delete",
              params: { id: idItem },
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