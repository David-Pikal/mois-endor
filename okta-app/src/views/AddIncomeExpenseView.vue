<template>
  <div id="card-iev" v-shadow="4">
    <div class="card-iev-item">
      <h2>Add income/expense</h2>
    </div>
    <div class="card-iev-item">
      <p>Type:</p>
    </div>
    <div class="card-iev-item">
      <ui-select
        id="full-func-js-select"
        :options="options"
        :disabled="controls.disabled"
        @selected="onSelected($event)">
        Choose between income/expense
      </ui-select>
    </div>
    <div class="card-iev-item">
      <p>Date:</p>
    </div>
    <div class="card-iev-item">
      <ui-datepicker label="Pick date"></ui-datepicker>
    </div>
    <div class="card-iev-item">
      <p>Amount:</p>
    </div>
    <div class="card-iev-item">
      <ui-textfield label="Specify amount "></ui-textfield>
    </div>
    <ui-button id="card-iev-button" raised>Add</ui-button>
  </div>
</template>

<script>

const options = [
  {
    label: 'Income',
    value: 'income'
  },
  {
    label: 'Expense',
    value: 'expense'
  }
]

export default {
  data() {
    return {
      options,
      selected: '',
      controls: {
        rtl: false,
        customColor: false,
        disabled: false
      }
    };
  },
  methods: {
    onSelected(selected) {
      this.selected = selected.value;
    }
  }
}
</script>

<style>
#card-iev {
  padding: 20px;
  display: flex;
  flex-direction: column;
}

#card-iev-button {
  display: flex;
  width: 150px;
  height: 50px;
  align-self: flex-end;
}

.card-iev-item {
  padding: 0px 0px 20px 0px;
}
</style>