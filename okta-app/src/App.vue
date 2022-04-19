<template>
  <div>
    <div v-if="authenticated">
      
      <ui-top-app-bar standard title="MOIS Endor" @nav="openDrawer = true"></ui-top-app-bar>
     
      <ui-drawer v-model="openDrawer" type="modal">
        <ui-drawer-header>
          <ui-drawer-title>Navigation</ui-drawer-title>
        </ui-drawer-header>
        <ui-drawer-content>
          <ui-list>
            <router-link to="/">
              <ui-item active>
                <ui-item-first-content>
                  <ui-icon>home</ui-icon>
                </ui-item-first-content>
                <ui-item-text-content>Home</ui-item-text-content>
              </ui-item>
            </router-link>
            <router-link to="/addincomeexpense">
              <ui-item active>
                <ui-item-first-content>
                  <ui-icon>payment</ui-icon>
                </ui-item-first-content>
                <ui-item-text-content>Add income/expense</ui-item-text-content>
              </ui-item>
            </router-link>
            <router-link to="/plans">
              <ui-item active>
                <ui-item-first-content>
                  <ui-icon>savings</ui-icon>
                </ui-item-first-content>
                <ui-item-text-content>Plans</ui-item-text-content>
              </ui-item>
            </router-link>
            <router-link to="/summaries">
              <ui-item active>
                <ui-item-first-content>
                  <ui-icon>assessment</ui-icon>
                </ui-item-first-content>
                <ui-item-text-content>Summaries</ui-item-text-content>
              </ui-item>
            </router-link>
            <ui-list-divider></ui-list-divider>
            <router-link to="/profile">
              <ui-item active>
                <ui-item-first-content>
                  <ui-icon>account_circle</ui-icon>
                </ui-item-first-content>
                <ui-item-text-content>Profile</ui-item-text-content>
              </ui-item>
            </router-link>
            <ui-list-divider></ui-list-divider>
            <a v-if="authenticated" v-on:click="logout()">
              <ui-item active>
                <ui-item-first-content>
                  <ui-icon>logout</ui-icon>
                </ui-item-first-content>
                <ui-item-text-content>Logout</ui-item-text-content>
              </ui-item>
            </a>
          </ui-list>
        </ui-drawer-content>
      </ui-drawer>

    </div>
    <div id="content-view">
      <div id="content">
        <router-view/>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'app',
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

*{
  margin: 0;
  padding: 0;
}

#content-view {
  padding: 90px 25px 25px 25px;
  max-width: 900px;
  margin: 0 auto;
}

a {
  display: block;
  text-decoration: none;
}

</style>
