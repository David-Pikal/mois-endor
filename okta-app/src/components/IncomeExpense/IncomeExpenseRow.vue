<template>
    <div class="table">
        <div class="row-div-date">
            <ui-rangepicker v-model="date" outlined :labels="['Start Date', 'End Date']">
                <template #separator>-</template>
            </ui-rangepicker>
        </div>

        <table v-if="!edit" class="table">
            <div class="row-div">
                <tr class="row" v-shadow="6">
                    <td><h2>Date</h2></td>
                    <td><h2>Value</h2></td>
                </tr>
            </div>
            <IncomeExpenseItem v-for="(item, index) in sortMe()" :key="index" :item="item" :dateStart="date[0]" :dateEnd="date[1]" />
        </table>
        <table v-else class="table">
            <IncomeExpenseEdit v-for="(item, index) in sortMe()" :key="index" :item="item" :dateStart="date[0]" :dateEnd="date[1]" />
        </table>
    </div>
</template>


<script>

import IncomeExpenseItem from "./IncomeExpenseItem"
import IncomeExpenseEdit from "./IncomeExpenseEdit"
import apiClient from "@/api/apiClient"
import dayjs from 'dayjs'


export default {
    name: "IncomeExpenseRow",
    props: { edit:Boolean },
    components:{ IncomeExpenseEdit, IncomeExpenseItem },
    data(){
        return{
            myData: [],
            date: []
        }
    },

    async mounted(){
        const currentDay = new Date()
        const daysAgo = new Date(currentDay.getTime())
        daysAgo.setDate(currentDay.getDate() - 7)
        this.date[0] = dayjs(daysAgo).format('YYYY-MM-DD')
        this.date[1] = dayjs(currentDay).format('YYYY-MM-DD')
        this.getData()
    },

    methods:{
        async getData(){
            const token = this.$auth.getAccessToken()
            const response = await new apiClient().getMyApi({accessToken:token})
            this.myData = response
        },

        sortMe: function() {
            return this.myData.sort((t1,t2) => t1.date < t2.date);
        },

    }, 
}


</script>



<style scoped>

.table {
  width: 100%;
}

.row-div-date {
  margin: auto;
  width: 50%;
  padding: 10px;
}

.row-div-button {
  margin: auto;
  width: 100%;
}

.row-div {
  margin: auto;
  width: 80%;
  padding: 10px;
}

.row {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}

.row-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.item-butt {
 padding: 20px; 
}

</style>