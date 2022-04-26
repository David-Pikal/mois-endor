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
                    <td><h2>Title</h2></td>
                    <td><h2>Start date</h2></td>
                    <td><h2>End date</h2></td>
                    <td><h2>Value</h2></td>
                </tr>
            </div>
            <PlanItem v-for="(item, index) in sortMe()" :key="index" :item="item" :dateStart="date[0]" :dateEnd="date[1]" />
        </table>
        <table v-else class="table">
            <PlanEdit v-for="(item, index) in sortMe()" :key="index" :item="item" :dateStart="date[0]" :dateEnd="date[1]" />
        </table>
    </div>
</template>


<script>

import PlanItem from "./PlanItem"
import PlanEdit from "./PlanEdit"
import apiClient from "@/api/apiClient"


export default {
    name: "PlanRow",
    props: { edit:Boolean },
    components:{ PlanEdit, PlanItem },
    data(){
        return{
            myData: [],
            date: []
        }
    },

    async mounted(){
        this.getData()
    },

    methods:{
        async getData(){
            const user = await this.$auth.getUser()
            const token = this.$auth.getAccessToken()
            const response = await new apiClient().getMyApi(
                {
                    accessToken:token,
                    url: "/plan/project/all", 
                    params: { userID: user.sub }, 
                }
            )
            this.myData = response
        },

        sortMe: function() {
            return this.myData.sort((t1,t2) => t1.startDate < t2.startDate);
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