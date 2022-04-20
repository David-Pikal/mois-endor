<template>
  <div id="card-iev" v-shadow="6">
    
    <div class="card-column">

      <div class="card-iev-item">
        <h2>Add income/expense</h2>
      </div>

      <div class="card-row">

        <div class="card-col-item">
          <div class="card-iev-item">
            <p class="card-iev-item-label">TYPE</p>
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
        </div>

        <div class="card-col-item">
          <div class="card-iev-item">
            <p class="card-iev-item-label">DATE</p>
          </div>
          <div class="card-iev-item">
            <ui-datepicker label="Pick date"></ui-datepicker>
          </div>  
        </div>

        <div class="card-col-item">
          <div class="card-iev-item">
            <p class="card-iev-item-label">AMOUNT</p>
          </div>

          <div class="card-iev-item">
            <ui-textfield label="Specify amount "></ui-textfield>
          </div>
        </div>
      </div>

      <ui-button id="card-iev-button" raised>Add</ui-button>

    </div>
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
  display: flex;
  justify-content: center;
  padding: 50px;
  margin: 50px;
}

.card-column {
  display: flex;
  flex-direction: column;
}

.card-row {
  display: flex;
  flex-direction: row;
  padding: 80px;
}

.card-col-item {
  display: flex;
  flex-direction: column;
  padding: 10px;
}

#card-iev-button {
  display: flex;
  justify-content: center;
  align-self: center;
  width: 200px;
  height: 50px;
  font-weight: bold;
  font-size: 20px;
}

.card-iev-item {
  display: flex;
  justify-content: center;
}

.card-iev-item-label {
  font-size: 15px;
  padding: 10px;
}



</style>