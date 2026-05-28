<script setup>
import { Icon } from '@iconify/vue'
import { RouterLink } from 'vue-router'
</script>

<template>
  <main class="grid min-h-screen grid-cols-1 bg-white antialiased lg:grid-cols-12">
    <section
      class="relative hidden items-center bg-slate-900 bg-cover bg-center p-16 lg:col-span-7 lg:flex"
      style="background-image: url('https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?auto=format&fit=crop&w=1920&q=80')"
      aria-label="Rental property overview"
    >
      <div class="absolute inset-0 bg-slate-950/70 backdrop-blur-sm"></div>

      <div class="relative z-10 max-w-xl text-white">
        <div class="mb-8 flex items-center gap-3">
          <span class="grid size-11 place-items-center rounded-lg bg-emerald-600">
            <Icon icon="mdi:home-city" class="size-7" aria-hidden="true" />
          </span>
          <span class="text-2xl font-bold tracking-tight">EazyStore <span class="text-emerald-400">Rent</span></span>
        </div>

        <h1 class="text-4xl font-extrabold leading-tight tracking-tight lg:text-5xl">
          Bring every rental record into one simple workspace.
        </h1>
        <p class="mt-6 text-lg leading-relaxed text-slate-300">
          Create the account foundation for property operations, tenant portals, lease tracking, rent payments, and maintenance requests.
        </p>

        <div class="mt-12 grid grid-cols-3 gap-6 border-t border-slate-800 pt-8">
          <div>
            <p class="text-2xl font-bold text-emerald-400">2</p>
            <p class="mt-1 text-xs text-slate-400">User Portals</p>
          </div>
          <div>
            <p class="text-2xl font-bold text-emerald-400">5+</p>
            <p class="mt-1 text-xs text-slate-400">Core Modules</p>
          </div>
          <div>
            <p class="text-2xl font-bold text-emerald-400">MVP</p>
            <p class="mt-1 text-xs text-slate-400">Ready Flow</p>
          </div>
        </div>
      </div>
    </section>

    <section class="flex flex-col justify-center bg-white px-6 py-10 sm:px-16 md:px-20 lg:col-span-5 lg:px-12 xl:px-20" aria-labelledby="register-title">
      <div class="mb-8 flex items-center gap-2 lg:hidden">
        <span class="grid size-9 place-items-center rounded-md bg-emerald-600 text-white">
          <Icon icon="mdi:home-city" class="size-6" aria-hidden="true" />
        </span>
        <span class="text-xl font-bold tracking-tight text-slate-900">EazyStore Rent</span>
      </div>

      <div class="mb-7">
        <h2 id="register-title" class="text-3xl font-bold tracking-tight text-slate-900">Daftar Akaun</h2>
        <p class="mt-2 text-sm text-slate-500">Lengkapkan profil pemilik atau penyewa.</p>
      </div>

      <form class="space-y-4" @submit.prevent>
        <div class="grid gap-4 sm:grid-cols-2">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Nama Penuh</label>
            <div class="relative">
              <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
                <Icon icon="mdi:account-outline" class="size-5" aria-hidden="true" />
              </span>
              <input class="w-full rounded-xl border border-slate-200 py-3 pl-10 pr-4 text-slate-900 outline-none transition placeholder:text-slate-400 focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" type="text" placeholder="Aliya Hassan" />
            </div>
          </div>

          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Peranan</label>
            <div class="relative">
              <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
                <Icon icon="mdi:badge-account-outline" class="size-5" aria-hidden="true" />
              </span>
              <select class="w-full rounded-xl border border-slate-200 bg-white py-3 pl-10 pr-4 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500">
                <option>Pemilik</option>
                <option>Penyewa</option>
              </select>
            </div>
          </div>
        </div>

        <div>
          <label class="mb-1.5 block text-sm font-medium text-slate-700">Emel</label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
              <Icon icon="mdi:email-outline" class="size-5" aria-hidden="true" />
            </span>
            <input class="w-full rounded-xl border border-slate-200 py-3 pl-10 pr-4 text-slate-900 outline-none transition placeholder:text-slate-400 focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" type="email" placeholder="name@example.com" />
          </div>
        </div>

        <div>
          <label class="mb-1.5 block text-sm font-medium text-slate-700">Nombor Telefon</label>
          <div class="relative">
            <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
              <Icon icon="mdi:phone-outline" class="size-5" aria-hidden="true" />
            </span>
            <input class="w-full rounded-xl border border-slate-200 py-3 pl-10 pr-4 text-slate-900 outline-none transition placeholder:text-slate-400 focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" type="tel" placeholder="+60 12-345 6789" />
          </div>
        </div>

        <div class="grid gap-4 sm:grid-cols-2">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Kata Laluan</label>
            <div class="relative">
              <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
                <Icon icon="mdi:lock-outline" class="size-5" aria-hidden="true" />
              </span>
              <input class="w-full rounded-xl border border-slate-200 py-3 pl-10 pr-4 text-slate-900 outline-none transition placeholder:text-slate-400 focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" type="password" placeholder="Kata Laluan" />
            </div>
          </div>

          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Ulang Kata Laluan</label>
            <div class="relative">
              <span class="absolute inset-y-0 left-0 flex items-center pl-3 text-slate-400">
                <Icon icon="mdi:lock-check-outline" class="size-5" aria-hidden="true" />
              </span>
              <input class="w-full rounded-xl border border-slate-200 py-3 pl-10 pr-4 text-slate-900 outline-none transition placeholder:text-slate-400 focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" type="password" placeholder="Ulang Kata Laluan" />
            </div>
          </div>
        </div>

        <label class="flex items-start gap-2 text-sm text-slate-600">
          <input class="mt-0.5 size-4 cursor-pointer rounded-md border-slate-300 accent-emerald-600 focus:ring-emerald-500" type="checkbox" />
          <span>Saya bersetuju maklumat yang diberikan adalah benar.</span>
        </label>

        <button class="w-full rounded-xl bg-emerald-600 px-4 py-3.5 font-semibold text-white shadow-sm transition hover:bg-emerald-700 active:scale-[0.99]" type="submit">
          Daftar Akaun
        </button>
      </form>

      <p class="mt-7 text-center text-sm text-slate-500">
        Sudah mempunyai akses?
        <RouterLink class="font-medium text-emerald-600 transition hover:text-emerald-700 hover:underline" to="/login">Log masuk di sini</RouterLink>
      </p>
    </section>
  </main>
</template>
