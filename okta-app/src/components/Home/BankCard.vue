<template>
    <div class="table">
        <div class="hadline">
            <h2>Finance balance</h2>
            <h2>{{ currentBalance + currentOutFinances }} Kč</h2>
        </div>
        <ui-list-divider/>
        <div class="hadline">
            <h4>Bank balance</h4>
            <h4>{{ currentBalance }} Kč</h4>
            |
            <h4>My Outs balance</h4>
            <h4>{{ currentOutFinances }} Kč</h4>
        </div>
        <ui-list-divider/>
        <div class="main-div">
            <ui-button class="chartButton" @click="resetup" >Setup</ui-button>
            <div class="div-setting">
                
                <div class="slider-div">
                    <h4>Count of Columns : {{ scale }}</h4>
                    <ui-slider class="just-slider" :min="4" :max="20" v-model="scale"></ui-slider>
                    <ui-select v-model="distance" :options="options"  @selected="onSelected($event)">
                        Period
                    </ui-select>
                </div>
            </div>
        </div>
        <ui-list-divider/>

        <div class="ccsMyChart">
            <MyChart 
            :someData="evolutionValues" 
            :someLabels="evolutionFormatedDates" 
            :key="componentKey"
            :isTransaction=false
            />
        </div>
        <div class="ccsMyChart">
            <MyChart 
            :someData="transactionValues" 
            :someLabels="evolutionFormatedDates" 
            :key="componentKey" 
            :isTransaction=true
            />
        </div>
    </div>
    
</template>
<script>

import MyChart from "./MyChart"
import apiClient from "@/api/apiClient"
import dayjs from 'dayjs'

const options = [
  { label: 'weeks', value: 'weeks' },
  { label: 'months', value: 'months' },
  { label: 'quarter', value: 'quarter' },
  { label: 'half years', value: 'half years' },
  { label: 'years', value: 'years' }
]

export default {
    name: "BankCard",
    components:{ MyChart },
    data(){
        return { 
            currentBalance: 0,
            currentOutFinances: 0,
            currentOutFinancesArray: [],
            date: [],
            distance: "months",
            distanceNum: 7,
            scale: 5,
            evolutionDates: [],
            evolutionFormatedDates: [],
            evolutionValues: [],
            transactionValues: [],
            componentKey: 0,
            options,
        }
    },


    async mounted(){
        this.loaded = false
        this.date[0] = '1000-01-01'
        this.date[1] = '3000-01-01'
        this.filterTransfers()
        this.getOutSum()
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
                    url: "/bank/transaction/value", 
                    params: { dateFrom: dateFrom, dateTo: dateTo }, 
                }
            )
            this.currentBalance = response
            console.log(this.currentBalance)
        },

        async getOutSum() {
            const token = this.$auth.getAccessToken()
            const response = await new apiClient().getMyApi({ accessToken:token })
            this.currentOutFinancesArray = response
            this.sumMyVaules()
        },

        sumMyVaules() {
            for (let i = 0; i < this.currentOutFinancesArray.length; i++) { 
                const item = this.currentOutFinancesArray[i]
                console.log(item)
                if (item.incomeExpenseType == 0) {
                    this.currentOutFinances += item.value
                } else {
                    this.currentOutFinances -= item.value
                }
                console.log(this.currentOutFinances)
            }
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
                this.evolutionDates[i] = weekAgo
            }
        },

        async setupEvolutionValues() {
            for (let i = 0; i < this.evolutionDates.length; i++) { 
                const backDay = new Date(this.evolutionDates[i])
                const distancePast = new Date(this.evolutionDates[i])
                distancePast.setDate(backDay.getDate() - this.distanceNum)
                console.log(distancePast)
                this.getDataWithIndex({ dateFrom:distancePast.toISOString(), dateTo:backDay.toISOString(), index:i })
                this.getTransactionsWithIndex({ dateFrom:distancePast.toISOString(), dateTo:backDay.toISOString(), index:i })
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

        async getTransactionsWithIndex({ dateFrom, dateTo, index }) {
            const token = this.$auth.getAccessToken()
            const response = await new apiClient().getMyApi(
                {
                    accessToken:token,
                    url: "/bank/transaction/value", 
                    params: { dateFrom: dateFrom, dateTo: dateTo }, 
                }
            )
            this.transactionValues[index] = response            
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

        async resetup(){
            await this.getEvolution()
            for (let i = 0; i < this.evolutionDates.length; i++) { 
                this.evolutionFormatedDates[i] = dayjs(this.evolutionDates[i]).format('DD. MM. YYYY');
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

.div-setting {
    display: flex;
    flex-direction: column;
}

.slider-div {
    display: flex;
    justify-content: space-around;
    align-items: center;
}

.just-slider {
    width: 30%;
}

.main-div {
    text-align: center;
    padding: 30px;
}

.chartButton {
  padding: 20px;
  width: 200px;
  margin: 20px;
}

</style>