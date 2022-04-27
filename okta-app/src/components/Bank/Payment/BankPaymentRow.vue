<template>
    <div class="table-wide">
        
        <div class="row-div-date">
            <ui-rangepicker v-model="date" outlined :labels="['Start Date', 'End Date']">
                <template #separator>-</template>
            </ui-rangepicker>
            <ui-button class="row-div-button" @click="filterTransfers">Filter</ui-button>
        </div>
    
        <table class="table-wide">
            <div class="row-div">
                <tr class="row-header" v-shadow="6">
                    <td><h3>Start</h3></td>
                    <td><h3>Last</h3></td>
                    <td><h3>Interval</h3></td>
                    <td><h3>Value</h3></td>
                </tr>
            </div>
            <BankPaymentItem v-for="(item, index) in sortMe(myData)" :key="index" :item="item"/>
        </table>
    </div>

</template>

<script>


import BankPaymentItem from "./BankPaymentItem"
import apiClient from "@/api/apiClient"
import dayjs from 'dayjs'


export default {
    name: "BankPaymentRow",
    components:{ BankPaymentItem },
    data(){
        return { 
            myData: [],
            date: []
        }
    },
    created(){
        const currentDay = new Date()
        const daysAgo = new Date(currentDay.getTime())
        daysAgo.setDate(currentDay.getDate() - 7)
        this.date[0] = dayjs(daysAgo).format('YYYY-MM-DD')
        this.date[1] = dayjs(currentDay).format('YYYY-MM-DD')
        this.filterTransfers()
    },

    methods:{

        async filterTransfers() {
            const dateStart = new Date(this.date[0]).toISOString()
            const dateEnd = new Date(this.date[1]).toISOString()
            this.getData({ dateFrom:dateStart, dateTo:dateEnd })
        },

        async getData({ dateFrom, dateTo }) {
            const token = this.$auth.getAccessToken()
            const response = await new apiClient().getMyApi(
                {
                    accessToken:token,
                    url: "/bank/payment", 
                    params: { dateFrom: dateFrom, dateTo: dateTo }, 
                }
            )
            this.myData = response
        },

        sortMe: function() {
            return this.myData.sort((t1,t2) => t1.firstPayment < t2.firstPayment);
        },
    },
}


</script>



<style>
</style>