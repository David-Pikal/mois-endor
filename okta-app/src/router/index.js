import { createRouter, createWebHistory } from "vue-router";
import { LoginCallback, navigationGuard } from "@okta/okta-vue";
import HomeView from "@/views/HomeView";
import LoginView from "@/views/LoginView";
import ProfileView from "@/views/ProfileView";
import SummariesView from "@/views/SummariesView";
import AddIncomeExpenseView from "@/views/AddIncomeExpenseView";
import PlansView from "@/views/PlansView";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      component: HomeView,
      meta: {
        requiresAuth: true,
      },
    },
    {
      path: "/login",
      component: LoginView,
    },
    {
      path: "/login/callback",
      component: LoginCallback,
    },
    {
      path: "/profile",
      component: ProfileView,
      meta: {
        requiresAuth: true,
      },
    },
    {
      path: "/summaries",
      component: SummariesView,
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: "/addincomeexpense",
      component: AddIncomeExpenseView,
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: "/plans",
      component: PlansView,
      meta: {
        requiresAuth: true,
      }
    },
  ],
});

router.beforeEach(navigationGuard);

export default router;
