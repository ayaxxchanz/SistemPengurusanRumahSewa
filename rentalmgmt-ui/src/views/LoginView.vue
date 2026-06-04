<script setup>
import { Icon } from '@iconify/vue'
import { ref, reactive, computed } from 'vue'
import { RouterLink, useRouter } from 'vue-router'
import AuthSidebar from '@/components/AuthSidebar.vue'
import { useAuth } from "@/stores/authStore"

const { login, isLoading, error, clearError } = useAuth()
const showPassword = ref(false)
const router = useRouter()

const form = reactive({
  email: "",
  password: "",
})

const loginError = computed(() => error.value)

const handleLogin = async () => {
  clearError()
  const result = await login(form);

  if (result.success) {
    router.replace("/dashboard");
  }
};

// Optional: Add enter key handler
const handleKeyPress = (e) => {
  if (e.key === 'Enter') {
    handleLogin()
  }
}
</script>

<template>
  <main class="grid min-h-screen grid-cols-1 bg-white antialiased lg:grid-cols-12">
    <AuthSidebar />
    
    <section class="flex flex-col justify-center bg-white px-6 py-12 sm:px-16 md:px-20 lg:col-span-5 lg:px-12 xl:px-20" aria-labelledby="login-title">
      <div class="mb-10 flex items-center gap-2 lg:hidden">
        <span class="grid size-9 place-items-center rounded-md bg-emerald-600 text-white">
          <Icon icon="mdi:home-city" class="size-6" aria-hidden="true" />
        </span>
        <span class="text-xl font-bold tracking-tight text-slate-900">Townhouse SeriKu</span>
      </div>

      <div class="mb-8">
        <h2 id="login-title" class="text-3xl font-bold tracking-tight text-slate-900">Welcome</h2>
        <p class="mt-2 text-sm text-slate-500">Log in to start using the platform.</p>
      </div>

      <!-- Error Alert -->
      <div v-if="loginError" class="mb-4 rounded-xl bg-red-50 p-4 border border-red-200">
        <div class="flex items-center gap-2">
          <Icon icon="mdi:alert-circle-outline" class="size-5 text-red-600" />
          <p class="text-sm text-red-700">{{ loginError }}</p>
        </div>
      </div>

      <form class="space-y-5" @submit.prevent="handleLogin" @keypress="handleKeyPress">
        <div>
          <label class="mb-1.5 block text-sm font-medium text-slate-700">Email</label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
              <Icon icon="mdi:email-outline" class="size-5" aria-hidden="true" />
            </span>
            <input
              v-model="form.email"
              class="w-full rounded-xl border border-slate-200 py-3 pl-10 pr-4 text-slate-900 outline-none transition placeholder:text-slate-400 focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500"
              type="email"
              required
              placeholder="example@email.com"
              :disabled="isLoading"
            />
          </div>
        </div>

        <div>
          <div class="mb-1.5 flex justify-between">
            <label class="text-sm font-medium text-slate-700">Password</label>
            <a class="text-sm font-medium text-emerald-600 transition hover:text-emerald-700 hover:underline" href="#">Forgot password?</a>
          </div>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
              <Icon icon="mdi:lock-outline" class="size-5" aria-hidden="true" />
            </span>
            <input
              v-model="form.password"
              class="w-full rounded-xl border border-slate-200 py-3 pl-10 pr-12 text-slate-900 outline-none transition placeholder:text-slate-400 focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500"
              :type="showPassword ? 'text' : 'password'"
              required
              placeholder="••••••••"
              :disabled="isLoading"
            />
            <button 
              class="absolute inset-y-0 right-0 flex items-center pr-3 text-slate-400 hover:text-slate-600" 
              type="button" 
              @click="showPassword = !showPassword"
              :disabled="isLoading"
            >
              <Icon :icon="showPassword ? 'mdi:eye-off-outline' : 'mdi:eye-outline'" class="size-5" aria-hidden="true" />
            </button>
          </div>
        </div>

        <div class="flex items-center">
          <input id="remember" class="size-4 cursor-pointer rounded-md border-slate-300 text-emerald-600 accent-emerald-600 focus:ring-emerald-500" type="checkbox" />
          <label class="ml-2 block cursor-pointer select-none text-sm text-slate-600" for="remember">Remember this device for 30 days</label>
        </div>

        <button 
          class="w-full rounded-xl bg-emerald-600 px-4 py-3.5 font-semibold text-white shadow-sm transition hover:bg-emerald-700 active:scale-[0.99] disabled:opacity-50 disabled:cursor-not-allowed" 
          type="submit"
          :disabled="isLoading"
        >
          <span v-if="isLoading">Signing in...</span>
          <span v-else>Log In</span>
        </button>
      </form>

      <p class="mt-8 text-center text-sm text-slate-500">
        New to our platform?
        <RouterLink class="font-medium text-emerald-600 transition hover:text-emerald-700 hover:underline" to="/register">Register an account now</RouterLink>
      </p>
    </section>
  </main>
</template>