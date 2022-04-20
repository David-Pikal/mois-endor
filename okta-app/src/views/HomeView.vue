<template>
  <div id="home">
    
    <div v-if="this.$root.authenticated">
      
      <div id="base-card" v-shadow="6">
        <h1 class="home-title">MOIS-ENDOR</h1>
        <div class="content-items">
          <p>Welcome, {{claims.name}}!</p>
          <p>
            {{this.payments}}
          </p>
        </div>
      </div>
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


<style>
#base-card {
  display: flex;
  justify-content: space-evenly;
  flex-direction: column;
  height: 50vh;
  margin: 50px;
  padding: 0px 50px;
}

.home-title {
  display: flex;
  justify-content: space-around;
}

.content-items {
  display: flex;
  justify-content: center;
}

</style>