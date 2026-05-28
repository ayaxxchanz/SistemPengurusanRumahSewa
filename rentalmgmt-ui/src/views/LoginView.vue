<script setup>
import { Icon } from '@iconify/vue'
import { computed, ref } from 'vue'
import { RouterLink } from 'vue-router'
import SiteTitle from '@/components/SiteTitle.vue'

import { reactive } from "vue";
import { loginAction } from "@/api/authService";

const currentRole = ref('pemilik')
const showPassword = ref(false)

const roleLabel = computed(() => (currentRole.value === 'pemilik' ? 'Pemilik' : 'Penyewa'))
const descLabel = computed(() =>
  currentRole.value === 'pemilik'
    ? 'Lindungi pelaburan hartanah anda dengan rekod rasmi. Kunci bukti pembayaran, simpan log aduan kerosakan, dan urus dokumen kontrak secara sah.'
    : 'Simpan bukti sewaan anda dengan selamat. Lindungi hak anda sebagai penyewa dengan menyimpan resit rasmi bayaran dan log aduan penyelenggaraan yang tidak boleh dipertikaikan.'
)

const form = reactive({
  phone: "",
  password: "",
});

const handleLogin = async () => {
  try {
    const result = await loginAction(form);

    if (result.success) {
      console.log("Login success", result);

      // Save token
      localStorage.setItem("token", result.jwtToken);

      // redirect, store user, etc.
    } else {
      console.log(result.errors.message);
    }
  } catch (error) {
    console.error(error.message);
  }
};
</script>

<template>
  <main class="grid min-h-screen grid-cols-1 bg-white antialiased lg:grid-cols-12">
    <section
      class="relative hidden items-center bg-slate-900 bg-cover bg-center p-16 lg:col-span-7 lg:flex"
      style="background-image: url('https://images.unsplash.com/photo-1598232973707-41b8faa2592c?auto=format&fit=crop&w=1920&q=80')"
      aria-label="Rental property overview"
    >
      <div class="absolute inset-0 bg-slate-950/70 backdrop-blur-sm"></div>

      <div class="relative z-10 max-w-xl text-white">
        <SiteTitle />

        <h1 class="text-4xl font-bold leading-tight tracking-tight lg:text-3xl">
          Rekod sewaan rasmi, hitam putih yang selamat.
        </h1>
        <p class="mt-6 text-lg leading-relaxed text-slate-300">
          {{ descLabel }}
        </p>

        <div class="mt-12 grid grid-cols-3 gap-6 border-t border-slate-800 pt-8">
          <div>
            <p class="text-2xl font-bold text-emerald-400">99.8%</p>
            <p class="mt-1 text-xs text-slate-400">On-time Payments</p>
          </div>
          <div>
            <p class="text-2xl font-bold text-emerald-400">24h</p>
            <p class="mt-1 text-xs text-slate-400">Avg. Repair Resolution</p>
          </div>
          <div>
            <p class="text-2xl font-bold text-emerald-400">10k+</p>
            <p class="mt-1 text-xs text-slate-400">Active Doors Managed</p>
          </div>
        </div>
      </div>
    </section>

    <section class="flex flex-col justify-center bg-white px-6 py-12 sm:px-16 md:px-20 lg:col-span-5 lg:px-12 xl:px-20" aria-labelledby="login-title">
      <div class="mb-10 flex items-center gap-2 lg:hidden">
        <span class="grid size-9 place-items-center rounded-md bg-emerald-600 text-white">
          <Icon icon="mdi:home-city" class="size-6" aria-hidden="true" />
        </span>
        <span class="text-xl font-bold tracking-tight text-slate-900">EazyStore Rent</span>
      </div>

      <div class="mb-8">
        <h2 id="login-title" class="text-3xl font-bold tracking-tight text-slate-900">Selamat Datang</h2>
        <p class="mt-2 text-sm text-slate-500">Segala rekod sewaan di satu tempat. Pilih peranan akses anda untuk bermula.</p>
      </div>

      <div class="mb-8 flex rounded-xl bg-slate-100 p-1">
        <button
          class="flex-1 rounded-lg py-2.5 text-sm font-medium transition"
          :class="currentRole === 'pemilik' ? 'bg-white text-slate-900 shadow-sm' : 'text-slate-600 hover:text-slate-900'"
          type="button"
          @click="currentRole = 'pemilik'"
        >
          Pemilik
        </button>
        <button
          class="flex-1 rounded-lg py-2.5 text-sm font-medium transition"
          :class="currentRole === 'penyewa' ? 'bg-white text-slate-900 shadow-sm' : 'text-slate-600 hover:text-slate-900'"
          type="button"
          @click="currentRole = 'penyewa'"
        >
          Penyewa
        </button>
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
              placeholder="340874791181"
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
              placeholder="Kata Laluan"
            />
            <button class="absolute inset-y-0 right-0 flex items-center pr-3 text-slate-400 hover:text-slate-600" type="button" @click="showPassword = !showPassword">
              <Icon :icon="showPassword ? 'mdi:eye-off-outline' : 'mdi:eye-outline'" class="size-5" aria-hidden="true" />
            </button>
          </div>
        </div>

        <div class="flex items-center">
          <input id="remember" class="size-4 cursor-pointer rounded-md border-slate-300 text-emerald-600 accent-emerald-600 focus:ring-emerald-500" type="checkbox" />
          <label class="ml-2 block cursor-pointer select-none text-sm text-slate-600" for="remember">Ingat peranti ini selama 30 hari</label>
        </div>

        <button class="w-full rounded-xl bg-emerald-600 px-4 py-3.5 font-semibold text-white shadow-sm transition hover:bg-emerald-700 active:scale-[0.99]" type="submit">
          Log masuk sebagai {{ roleLabel }}
        </button>
      </form>

      <p class="mt-8 text-center text-sm text-slate-500">
        Baru di platform ini?
        <RouterLink class="font-medium text-emerald-600 transition hover:text-emerald-700 hover:underline" to="/register">Daftar akaun sekarang</RouterLink>
      </p>
    </section>
  </main>
</template>
