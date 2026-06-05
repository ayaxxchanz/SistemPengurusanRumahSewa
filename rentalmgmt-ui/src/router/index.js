import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '@/views/LoginView.vue'
import RegisterView from '@/views/RegisterView.vue'
import ProfileView from '@/views/ProfileView.vue'
import DashboardView from '@/views/DashboardView.vue'
import PropertiesView from '@/views/PropertiesView.vue'
import LeasesView from '@/views/LeasesView.vue'
import { useAuthStore } from "@/stores/authStore";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/login',
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView,
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: DashboardView,
      meta: { requiresAuth: true },
    },
    {
      path: '/properties',
      name: 'properties',
      component: PropertiesView,
      meta: { requiresAuth: true },
    },
    {
      path: '/leases',
      name: 'leases',
      component: LeasesView,
      meta: { requiresAuth: true },
    },
    {
      path: '/profile',
      name: 'profile',
      component: ProfileView,
      meta: { requiresAuth: true },
    },
  ],
  scrollBehavior(to, from, savedPosition) {
    // Always return to the top
    return { top: 0 }
  }
})

router.beforeEach(async (to, from, next) => {
  const authStore = useAuthStore();
  
  // Initialize auth if needed (fetch user profile if token exists but no user data)
  if (authStore.isAuthenticated && !authStore.user) {
    await authStore.initAuth();
  }

  // Check if route requires authentication
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next("/login");
    return;
  }

  // Redirect authenticated users away from login/register pages
  if (to.meta.guestOnly && authStore.isAuthenticated) {
    next("/dashboard");
    return;
  }

  next();
});


export default router
