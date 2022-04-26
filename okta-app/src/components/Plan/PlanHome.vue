<template>
    <div class="table" v-shadow="6">
        <div class="hadline">
            <h2>Plan balance</h2>
        </div>
        <ui-list-divider/>
        <div class="ccsMyChart">
            <MyChart :someData="evolutionValues" :someLabels="evolutionFormatedDates"/>
        </div>
    </div>
    
</template>
<script>

import MyChart from "./PlanChart"
import apiClient from "@/api/apiClient"
import dayjs from 'dayjs'
// import moment from 'moment';

export default {
    name: "PlanHome",
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
            fetchedData: [],
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
                console.log(weekAgo)
                this.evolutionDates[i] = weekAgo
            }
        },

        async setupEvolutionValues() {
            
        },

        async getDataWithIndex({ index }) {
            const token = this.$auth.getAccessToken()
            const response = await new apiClient().getMyApi(
                {
                    accessToken:token,
                    url: "/plan/project/all", 
                    params: { userID: 3 }, 
                }
            )
            this.data = response
            this.evolutionValues[index] = 
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
        },

        returnNumberPerMonth(item, dateFrom, dateTo) {
          const startDate = item.startDate
          const end = item.endDate
          const value = item.value
          const isInside = 
          moment()          
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