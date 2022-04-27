<template>
    
    <div class="container-title">
      <h2>INCOME / EXPENSE</h2>
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
          <ui-form-field>
            <ui-select v-model="formData.incomeExpense" :options="options"  @selected="onSelected($event)">
              Income or Expense
            </ui-select>
          </ui-form-field>

          <ui-form-field>
            <ui-datepicker v-model="formData.date" :required=true>
              Pick date
            </ui-datepicker>
          </ui-form-field>

          <ui-form-field class="form-item">
            <ui-textfield v-model="formData.amount" :required=true>Amount</ui-textfield>
          </ui-form-field>

          <ui-alert v-if="message" state="error">{{ message }}</ui-alert>

          <ui-form-field class="form-item form-actions">
            <ui-button class="add-button" raised @click="onSubmit">Add</ui-button>
          </ui-form-field>
        </ui-form>
    </div>

    <div v-show="active == 1" class="container-form">
      <IncomeExpenseRow :key="componentKey" :edit=false />
    </div>

    <div v-show="active == 2" class="container-form">
      <IncomeExpenseRow :key="componentKey" :edit=true />
    </div>
      
</template>

<script>
 
import apiClient from "@/api/apiClient"
import { useValidator } from 'balm-ui';
import IncomeExpenseRow from "@/components/IncomeExpense/IncomeExpenseRow"

const validations = {
  date: { label: 'Date', validator: 'required' },
  amount: { label: 'Amount', validator: 'required' }
};

const options = [
  { label: 'Income', value: 0 },
  { label: 'Expense', value: 1 }
]

export default {
  components:{ IncomeExpenseRow },
  data() {
    return {
      balmUI: useValidator(),
      validations,
      options,
      formData: { date: '', amount: '', incomeOrExpense: 0 },
      message: '',
      items: [
        { text: 'ADD RECORD', icon: 'add_circle'},
        { text: 'INCOME / EXPENSE', icon: 'list'},
        { text: 'EDIT RECORDS', icon: 'edit'},
      ],
      active: 1
    };
  },

  methods: {
    onSelected(selected) {
      this.formData.incomeOrExpense = selected.value;
    },
    async onSubmit() {
      let result = this.balmUI.validate(this.formData);
      let { valid, message } = result;
      this.message = message;

      if (valid) {
        this.$toast('ok');
        console.log(this.formData.amount)
        console.log(this.formData.date)
        console.log(this.formData.incomeOrExpense)
        const token = this.$auth.getAccessToken()
        const response = await new apiClient().getMyApi(
          {
            accessToken:token,
            url: "IncomeExpenses",
            method: "POST",
            data: {
              "incomeExpenseType": this.formData.incomeOrExpense,
              "date": this.formData.date,
              "value": this.formData.amount
              }
          })

        this.formData.date = ''
        this.formData.amount = ''
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
  padding: 30px;
  display: flex;
  justify-content: center;
}

.add-button {
  width: 200px;
}

</style>