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
    name: "PlanChart",
    components: { Bar },
    props: {
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
    },

    data() {
      return {
        chartData: {
          labels: [],
          datasets: [
            {
              label: 'Data One',
              backgroundColor: '#f87979',
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
          this.chartData.datasets[0].data[i] = this.someData[i];
        }
        for (let i = 0; i < 5; i++) {
          this.chartData.labels.push(this.someLabels[i])
        }
      }
    }
}
</script>

<style scoped>

.chartDiv {
  display: flex;
  flex-direction: column;
}

.chartButton {
  padding: 20px;
  margin: 20px;
}

</style>