<template>

    <div v-if="authenticated">
      
      <ui-top-app-bar standard title="MOIS Endor" @nav="openDrawer = true"></ui-top-app-bar>
      <ui-drawer v-model="openDrawer" type="modal">
        
        <ui-drawer-header>
          <ui-drawer-title>Navigation</ui-drawer-title>
        </ui-drawer-header>

        <ui-drawer-content>
          <ui-list>
            <router-link to="/">
                <NavBarRow iconName="home" labelName="Home" />
            </router-link>
            <router-link to="/bank">
                <NavBarRow iconName="account_balance" labelName="Bank" />
            </router-link>

            <router-link to="/addincomeexpense">
                <NavBarRow iconName="payment" labelName="Add income/expense" />
            </router-link>

            <router-link to="/plans">
              <NavBarRow iconName="savings" labelName="Plans" />
            </router-link>

            <ui-list-divider/>

            <router-link to="/profile">
              <NavBarRow iconName="account_circle" labelName="Profile" />
            </router-link>

            <ui-list-divider/>

            <a v-if="authenticated" v-on:click="logout()">
              <NavBarRow iconName="logout" labelName="Logout" />
            </a>
            <ui-list-divider/>
          </ui-list>
        </ui-drawer-content>
      </ui-drawer>
    </div>

    <div id="content-view">
      <div id="content">
        <router-view/>
      </div>
    </div>
</template>

<script>

import apiClient from "@/api/apiClient"
import NavBarRow from "@/components/Navigation/NavBarRow"

export default {
  name: 'app',
  components:{ NavBarRow },
  data () {
    return {
      openDrawer: false,
      authenticated: false,
    }
  },
  async created () {
    await this.isAuthenticated()
    this.$auth.authStateManager.subscribe(this.isAuthenticated)
  },
  watch: {
    // Everytime the route changes, check for auth status
    '$route': 'isAuthenticated'
  },
  mounted(){
    new apiClient().request({})
  },
  methods: {
    async isAuthenticated () {
      this.authenticated = await this.$auth.isAuthenticated()
    },
    async logout () {
      await this.$auth.signOut()
    }
  }
}
</script>


<style>
</style>
