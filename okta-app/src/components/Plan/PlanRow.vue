<template>
    <div class="table-wide">
        <table v-if="!edit" class="table-wide">
            <div class="row-div">
                <tr class="row-header" v-shadow="6">
                    <td><h3>Title</h3></td>
                    <td><h3>Start date</h3></td>
                    <td><h3>End date</h3></td>
                    <td><h3>Value</h3></td>
                    <td><h3>Value / Month</h3></td>
                </tr>
            </div>
            <PlanItem v-for="(item, index) in sortMe()" :key="index" :item="item"/>
        </table>
        <table v-else class="table-wide">
            <div class="row-div">
                <tr class="row-header" v-shadow="6">
                    <td><h3>Title</h3></td>
                    <td><h3>Start date</h3></td>
                    <td><h3>End date</h3></td>
                    <td><h3>Value</h3></td>
                    <td><h3>Button</h3></td>
                </tr>
            </div>
            <PlanEdit v-for="(item, index) in sortMe()" :key="index" :item="item"/>
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
                    params: {  userID: user.sub }, 
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



<style>
</style>