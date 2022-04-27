<template>
    <div class="table-to-column">
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
            myColor="#FECEAB"
            />
        </div>
        <div class="ccsMyChart">
            <MyChart 
            :someData="transactionValues" 
            :someLabels="evolutionFormatedDates" 
            :key="componentKey" 
            :isTransaction=true
            myColor="#99B898"
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
                if (item.incomeExpenseType == 0) {
                    this.currentOutFinances += item.value
                } else {
                    this.currentOutFinances -= item.value
                }
            }
        },

        async getEvolution() {
            this.setupEvolutionDates()
            this.setupEvolutionValues()
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
            this.evolutionDates = []
            this.evolutionFormatedDates = []
            this.evolutionValues = []
            this.transactionValues = []
            await this.getEvolution()
            for (let i = 0; i < this.evolutionDates.length; i++) { 
                this.evolutionFormatedDates[i] = dayjs(this.evolutionDates[i]).format('DD. MM. YYYY');
            }
        }
    },
}
</script>

<style scoped>

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

</style>