<template>
    
    <div class="container-title">
      <h2>PLANS</h2>
    </div>

    <div class="container" v-shadow="6">
      <ui-navigation-bar content-selector=".container" stacked>
        <ui-tabs
          v-model="active"
          type="textWithIcon"
          stacked
          :items="items"
        ></ui-tabs>
      </ui-navigation-bar>
    </div>

      <div v-show="active == 0" class="container-form" v-shadow="6">
        <ui-form item-margin-bottom="20">

          <ui-form-field class="form-item">
            <ui-textfield v-model="formData.title" :required=true>Title</ui-textfield>
          </ui-form-field>

          <ui-form-field class="form-item">
            <ui-textfield v-model="formData.value" :required=true>Value</ui-textfield>
          </ui-form-field>

          <ui-form-field>
            <ui-datepicker v-model="formData.startDate" :required=true>
              Start Date
            </ui-datepicker>
          </ui-form-field>

          <ui-form-field>
            <ui-datepicker v-model="formData.endDate" :required=true>
              End Date
            </ui-datepicker>
          </ui-form-field>

          <ui-alert v-if="message" state="error">{{ message }}</ui-alert>

          <ui-form-field class="form-item form-actions">
            <ui-button class="add-button" raised @click="onSubmit">Add</ui-button>
          </ui-form-field>
        </ui-form>
    </div>

    <div v-show="active == 1" class="container-form">
      <PlanHome />
    </div>

    <div v-show="active == 2" class="container-form">
      <PlanRow :key="componentKey" :edit=false />
    </div>

    <div v-show="active == 3" class="container-form">
      <PlanRow :key="componentKey" :edit=true />
    </div>
      
</template>

<script>
 
import apiClient from "@/api/apiClient"
import { useValidator } from 'balm-ui';
import PlanRow from "@/components/Plan/PlanRow"
import PlanHome from "@/components/Plan/PlanHome"

const validations = {
  title: { label: 'Title', validator: 'required' },
  value: { label: 'Value', validator: 'required' },
  startDate: { label: 'Start Date', validator: 'required' },
  endDate: { label: 'End Date', validator: 'required' },
};

export default {
  components:{ PlanRow, PlanHome },
  data() {
    return {
      balmUI: useValidator(),
      validations,
      formData: { title: '', value: '', startDate: '', endDate: '' },
      message: '',
      componentKey: 0,
      items: [
        { text: 'ADD PROJECT', icon: 'add_circle'},
        { text: 'PLANS HOME', icon: 'home'},
        { text: 'MY PROJECTS', icon: 'list'},
        { text: 'EDIT PROJECTS', icon: 'edit'},
      ],
      active: 1
    };
  },

  methods: {

    async onSubmit() {
      console.log("tady")
      let result = this.balmUI.validate(this.formData);
      let { valid, message } = result;
      this.message = message;

      if (valid) {
        this.$toast('ok');
        console.log(this.formData.title)
        console.log(this.formData.value)
        console.log(this.formData.startDate)
        console.log(this.formData.endDate)
        const user = await this.$auth.getUser()
        const token = this.$auth.getAccessToken()
        const response = await new apiClient().getMyApi(
          {
            accessToken:token,
            url: "/plan/project/add",
            method: "POST",
            params: {
              "title": this.formData.title,
              "value": this.formData.value,
              "startDate": this.formData.startDate,
              "endDate": this.formData.endDate,
              "userID": user.sub
              }
          })

        this.formData.title = ''
        this.formData.value = ''
        this.formData.startDate = ''
        this.formData.endDate = ''
        this.componentKey += 1
        console.log(response)
      }
    },
  },
}
</script>

<style scoped>
.container-title {
  display: flex;
  justify-content: center;
  padding: 20px;
}

.container-form {
  margin: 30px 0px;
  padding: 30px 0px;
  display: flex;
  justify-content: center;
}

.add-button {
  width: 200px;
}

</style>