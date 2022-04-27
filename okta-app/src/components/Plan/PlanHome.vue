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
import moment from 'moment';

export default {
    name: "PlanHome",
    components:{ MyChart },
    data(){
        return { 
            currentBalance: 0,
            date: [],
            distance: "months",
            distanceNum: 7,
            scale: 15,
            evolutionDates: [],
            evolutionFormatedDates: [],
            evolutionValues: [],
            fetchedData: [],
        }
    },


    async mounted(){
        await this.getEvolution()
        for (let i = 0; i < this.evolutionDates.length; i++) { 
            this.evolutionFormatedDates[i] = dayjs(this.evolutionDates[i]).format('DD. MM. YYYY');
        }
    },

    methods:{
        async getEvolution() {
            this.setupDistance()
            await this.getData()
            this.setupEvolutionDates()
            this.setupEvolutionValues()
        },

        async getData() {
            const user = await this.$auth.getUser()
            const token = this.$auth.getAccessToken()
            const response = await new apiClient().getMyApi(
                {
                    accessToken:token,
                    url: "/plan/project/all", 
                    params: {  userID: user.sub }, 
                }
            )
            this.fetchedData = response
        },

        async setupEvolutionDates() {
            const currentDay = new Date()
            for (let i = 0; i <= this.scale; i++) { 
                const weekAgo = new Date(currentDay.getTime())
                weekAgo.setDate(currentDay.getDate() + (this.distanceNum * i) )
                this.evolutionDates[i] = weekAgo
            }
        },

        async setupEvolutionValues() {
            for (let i = 0; i < this.evolutionDates.length; i++) { 
                this.evolutionValues[i] = 0
                console.log("tady")
                // var projectCount = 0
                const date = this.evolutionDates[i]
                const nextDay = date.setDate(date.getDate() + this.distanceNum)
                for (let j = 0; j < this.fetchedData.length; j++) { 
                    console.log(this.fetchedData)
                    const value = this.returnNumberPerMonth(this.fetchedData[j], date, nextDay)
                    console.log(value)
                    if (value != null) {
                        console.log("Tady")
                        this.evolutionValues[i] = this.evolutionValues[i] + value
                        console.log(this.evolutionValues)
                        // projectCount += 1
                    }
                }
                // console.log("Here")
                // if (projectCount > 0) {
                //     console.log("Here 2")
                //     this.evolutionValues[i] = this.evolutionValues[i] / projectCount
                // } 
            }
            console.log(this.evolutionValues)
        },

        returnNumberPerMonth(item, dateFrom, dateTo) {
          const start = item.startDate
          const end = item.endDate
          const isInside = moment(dateFrom).isBetween(start, end, 'days', []) || moment(dateTo).isBetween(start, end, 'days', [])
          if (isInside) {
              return this.getValuePerMonth(item.value, start, end)
          }
        },

        getValuePerMonth(value, dateStart, dateEnd) {
            const start = new Date(dateStart);
            const end = new Date(dateEnd);
            const years = end.getFullYear() - start.getFullYear()
            const months = (12 * years) + (end.getMonth() - start.getMonth() + 1)
            return value / months
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