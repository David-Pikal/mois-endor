<template>
    <div class="table">
        <div class="row-div-date">
            <ui-rangepicker v-model="date" outlined :labels="['Start Date', 'End Date']">
                <template #separator>-</template>
            </ui-rangepicker>
        <ui-button class="row-div-button" @click="filterTransfers">Filter</ui-button>
        </div>
    
        <table class="table">
            <div class="row-div">
                <tr class="row" v-shadow="6">
                    <td><h3>Date</h3></td>
                    <td><h3>Value</h3></td>
                </tr>
            </div>
            <BankTransactionItem v-for="(item, index) in myData" :key="index" :item="item"/>
        </table>
    </div>

</template>

<script>

import BankTransactionItem from "./BankTransactionItem"
import apiClient from "@/api/apiClient"
import dayjs from 'dayjs'

export default {
    name: "BankTransactionRow",
    components:{ BankTransactionItem },
    data(){
        return { 
            myData: [],
            date: []
        }
    },
    
    async created(){
        const currentDay = new Date()
        const daysAgo = new Date(currentDay.getTime())
        daysAgo.setDate(currentDay.getDate() - 7)
        this.date[0] = dayjs(daysAgo).format('YYYY-MM-DD')
        this.date[1] = dayjs(currentDay).format('YYYY-MM-DD')
        this.filterTransfers()
    },

    methods:{
        async getData({ dateFrom, dateTo }) {
            const token = this.$auth.getAccessToken()
            const response = await new apiClient().getMyApi(
                {
                    accessToken:token,
                    url: "/bank/transaction", 
                    params: { dateFrom: dateFrom, dateTo: dateTo }, 
                }
            )
            this.myData = response
        },

        async filterTransfers() {
            const dateStart = new Date(this.date[0]).toISOString()
            const dateEnd = new Date(this.date[1]).toISOString()
            this.getData({ dateFrom:dateStart, dateTo:dateEnd })
        },

        sortMe: function() {
            return this.myData.sort((t1,t2) => t1.valueDate < t2.valueDate);
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

</style>