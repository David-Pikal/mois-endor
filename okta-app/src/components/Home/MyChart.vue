<template>
    <div class="chartDiv">
    <ui-button class="chartButton" @click="clickMe" >Refresh</ui-button>
        <Bar :chart-data="chartData" :chartOptions="chartOptions"   :height="300"/>
    </div>
</template>

<script>

import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
    name: "MyChart",
    components: { Bar },
    props: {
      isTransaction: {
        type: Boolean,
        required: true
      }, 
        
      someData: {
        type: Object,
        required: true
      },
      someLabels: {
        type: Object,
        required: true
      },
      width: {
          type: Number,
          default: 100
        },
      height: {
        type: Number,
        default: 100
      },
      myColor: {
        type: String,
        default: "#f87979"
      }
    },

    data() {
      return {
        chartData: {
          labels: [],
          datasets: [
            {
              label: '',
              backgroundColor: '#fff',
              data: []
            }
          ]
        },
        chartOptions: {
          responsive: false
        }
      }
    },
    methods: {
      clickMe() {
        this.chartData.labels.splice(0,100)
        for (let i = 0; i < this.someData.length; i++) { 
          this.chartData.datasets[0].data[i] = this.someData[this.someData.length - 1 - i];
        }
        for (let i = this.someLabels.length - 1; i >= 0; i--) {
          this.chartData.labels.push(this.someLabels[i])
        }
        this.chartData.datasets[0].backgroundColor = this.myColor
        this.chartData.datasets[0].label = this.isTransaction ? "Transaction" : "Payments"
        console.log(this.label)
      }
    }
}
</script>

<style>
</style>