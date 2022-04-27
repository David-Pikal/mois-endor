<template>
  <div class="login">
    <div id="okta-signin-container">
      <div class="container-title">
        <h2>MOIS ENDOR</h2>
      </div>
    </div>
  </div>
</template>

<script>
import '@okta/okta-signin-widget/dist/css/okta-sign-in.min.css'
import {oktaSignIn} from '../okta'

export default {
  
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Login',

  mounted: function () {
    this.$nextTick(function () {
      oktaSignIn.showSignInAndRedirect(
          { el: '#okta-signin-container' }
      )
    })
  },
  unmounted () {
    oktaSignIn.remove()
  }
}
</script>

<style>

#okta-sign-in.auth-container.main-container {
  margin: 0px auto 8px auto;
}

#okta-sign-in.auth-container .okta-sign-in-header {
  display: none;
}

</style>
