<script setup>
import { Icon } from '@iconify/vue'
import { ref, reactive } from 'vue'
import { RouterLink } from 'vue-router'
import { useRouter } from "vue-router";
import AuthSidebar from '@/components/AuthSidebar.vue'
import { useAuthStore } from "@/stores/authStore"

const authStore = useAuthStore()
const showPassword = ref(false)
const router = useRouter()

const form = reactive({
  phone: "",
  password: "",
})

const handleLogin = async () => {
  const result = await authStore.login(form);

  if (result.success) {
    router.push("/profile");
  } else {
    console.log(result.errors.message);
  }
};
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
        <h2 id="login-title" class="text-3xl font-bold tracking-tight text-slate-900">Selamat Datang</h2>
        <p class="mt-2 text-sm text-slate-500">Log masuk untuk mula menggunakan platform.</p>
      </div>

      <form class="space-y-5" @submit.prevent="handleLogin">
        <div>
          <label class="mb-1.5 block text-sm font-medium text-slate-700">Emel</label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
              <Icon icon="mdi:email-outline" class="size-5" aria-hidden="true" />
            </span>
            <input
              v-model="form.phone"
              class="w-full rounded-xl border border-slate-200 py-3 pl-10 pr-4 text-slate-900 outline-none transition placeholder:text-slate-400 focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500"
              type="text"
              required
              placeholder="nama@contoh.com"
            />
          </div>
        </div>

        <div>
          <div class="mb-1.5 flex justify-between">
            <label class="text-sm font-medium text-slate-700">Kata Laluan</label>
            <a class="text-sm font-medium text-emerald-600 transition hover:text-emerald-700 hover:underline" href="#">Lupa kata laluan?</a>
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
            />
            <button class="absolute inset-y-0 right-0 flex items-center pr-3 text-slate-400 hover:text-slate-600" type="button" @click="showPassword = !showPassword">
              <Icon :icon="showPassword ? 'mdi:eye-off-outline' : 'mdi:eye-outline'" class="size-5" aria-hidden="true" />
            </button>
          </div>
        </div>

        <div class="flex items-center">
          <input id="remember" class="size-4 cursor-pointer rounded-md border-slate-300 text-emerald-600 accent-emerald-600 focus:ring-emerald-500" type="checkbox" />
          <label class="ml-2 block cursor-pointer select-none text-sm text-slate-600" for="remember">Simpan maklumat peranti ini selama 30 hari</label>
        </div>

        <button class="w-full rounded-xl bg-emerald-600 px-4 py-3.5 font-semibold text-white shadow-sm transition hover:bg-emerald-700 active:scale-[0.99]" type="submit">
          Log Masuk
        </button>
      </form>

      <p class="mt-8 text-center text-sm text-slate-500">
        Baru di platform ini?
        <RouterLink class="font-medium text-emerald-600 transition hover:text-emerald-700 hover:underline" to="/register">Daftar akaun sekarang</RouterLink>
      </p>
    </section>
  </main>
</template>