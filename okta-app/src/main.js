import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import OktaVue from "@okta/okta-vue";
import { oktaAuth } from "./okta";
import BalmUI from 'balm-ui';
import BalmUIPlus from 'balm-ui-plus';
import 'balm-ui-css';

import "@/assets/scss/main.scss"

createApp(App)
  .use(router)
  .use(OktaVue, {
    oktaAuth,
    onAuthRequired: () => {
      router.push("/login");
    },
    onAuthResume: () => {
      router.push("/login");
    },
  })
  .use(BalmUI)
  .use(BalmUIPlus)
  .mount("#app");
