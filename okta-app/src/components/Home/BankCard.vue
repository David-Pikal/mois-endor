<template>
    <div class="table">
        <div class="hadline">
            <h2>Account balance</h2>
            <h2>{{ currentBalance }} Kč</h2>
        </div>
        <!-- <p>{{ evolutionValues }}</p> -->
        <ui-list-divider/>
        <div class="ccsMyChart">
            <MyChart :someData="evolutionValues" :someLabels="evolutionFormatedDates"/>
        </div>
    </div>
    
</template>
<script>

import MyChart from "./MyChart"
import apiClient from "@/api/apiClient"
import dayjs from 'dayjs'

export default {
    name: "BankCard",
    components:{ MyChart },
    data(){
        return { 
            currentBalance: 0,
            date: [],
            distance: "weeks",
            distanceNum: 7,
            scale: 5,
            evolutionDates: [],
            evolutionFormatedDates: [],
            evolutionValues: [],
        }
    },


    async mounted(){
        this.loaded = false
        this.date[0] = '1000-01-01'
        this.date[1] = '3000-01-01'
        this.filterTransfers()
        await this.getEvolution()
        for (let i = 0; i < this.evolutionDates.length; i++) { 
            this.evolutionFormatedDates[i] = dayjs(this.evolutionDates[i]).format('DD. MM. YYYY');
        }
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
                    url: "/bank/payment/value", 
                    params: { dateFrom: dateFrom, dateTo: dateTo }, 
                }
            )
            this.currentBalance = response
            console.log(this.currentBalance)
        },

        async getEvolution() {
            this.setupEvolutionDates()
            this.setupEvolutionValues()
            console.log(this.evolutionValues)
        },

        async setupEvolutionDates() {
            this.setupDistance()
            

            
            const currentDay = new Date()
            for (let i = 0; i <= this.scale; i++) { 
                const weekAgo = new Date(currentDay.getTime())
                weekAgo.setDate(currentDay.getDate() - (this.distanceNum * i) )
                //console.log(weekAgo)
                this.evolutionDates[i] = weekAgo
            }
        },

        async setupEvolutionValues() {
            //const distancePast = new Date("1000-01-01")
            for (let i = 0; i < this.evolutionDates.length; i++) { 
                const backDay = new Date(this.evolutionDates[i])
                const distancePast = new Date(this.evolutionDates[i])
                distancePast.setDate(backDay.getDate() - this.distanceNum)
                console.log(distancePast)
                const value = this.getDataWithIndex({ dateFrom:distancePast.toISOString(), dateTo:backDay.toISOString(), index:i })
                this.evolutionValues[i] = value
            }
        },

        async getDataWithIndex({ dateFrom, dateTo, index }) {
            const token = this.$auth.getAccessToken()
            const response = await new apiClient().getMyApi(
                {
                    accessToken:token,
                    url: "/bank/payment/value", 
                    params: { dateFrom: dateFrom, dateTo: dateTo }, 
                }
            )
            this.evolutionValues[index] = response
            console.log(this.evolutionValues[index])
        },

        setupDistance(){
            switch (this.distance) {
                case "weeks":
                    this.distanceNum = 7
                    break;
                case "months":
                    this.distanceNum = 30
                    break;
                case "quarter":
                    this.distanceNum = 91
                    break;
                case "half years":
                    this.distanceNum = 182
                    break;
                case "years":
                    this.distanceNum = 365
                    break;
                default:
                    this.distanceNum = 7
                    break;
            }
        }
    },
}
</script>

<style scoped>

.table {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.hadline {
  width: 100%;
  display: flex;
  justify-content: space-around;
  margin: 20px;
}

.ccsMyChart {
    display: flex;
    justify-content: center;
    margin: 30px;
}

</style>