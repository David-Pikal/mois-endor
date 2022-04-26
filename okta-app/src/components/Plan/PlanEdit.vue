<template>
    <div class="row-div">
    <tr class="row" v-shadow="6">
        <div class="row-content">
            <td class="item">{{ result[0] }}</td>
            <td class="item">{{ formatDate(result[1]) }}</td>
            <td class="item">{{ formatDate(result[2]) }}</td>
            <td class="item">{{ result[3].toFixed(2) }} Kč</td>
        </div>
        <td class="item">
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
          // this.item.userID,
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

<style scoped>


.item {
 padding: 20px; 
}

.row {
  display: flex;
  justify-content: space-between;
}

.row-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.row-div {
  margin: auto;
  width: 100%;
  padding: 10px;
}

</style>