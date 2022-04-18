<template>
  <div id="home">
    <h1>MOIS-ENDOR</h1>

    <div v-if="this.$root.authenticated">
      <p>Welcome, {{claims.name}}!</p>
      <p>
        {{this.payments}}
      </p>
    </div>
  </div>
</template>

<script>

import axios from 'axios';

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'home',
  data: function () {
    return {
      claims: '',
      payments: ''
    }
  },
  created () { this.setup() },
  methods: {
    async setup () {
      if (this.$root.authenticated) {
        //const date = new Date();

        this.claims = await this.$auth.getUser()
        let accessToken = this.$auth.getAccessToken();
        console.log(`Authorization: Bearer ${accessToken}`);
        try {
          let response = await axios.get('http://localhost:8082/bank/payment',
              { headers: {'Authorization': 'Bearer ' + accessToken }, params: {dateFrom: '2012-04-23T18%3A25%3A43.511Z', 
              dateTo: '2020-04-23T18%3A25%3A43.511Z', accountId: 123}} );
            // let response = await axios.get('http://localhost:8082/IncomeExpenses',
            //   { headers: {'Authorization': 'Bearer ' + accessToken } } );
          this.payments = response.data;
        }
        catch (error) {
          this.payments = `${error}`
        }
      }
    }
  }
}
</script>