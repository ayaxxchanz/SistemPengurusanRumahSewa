<script setup>
import Sidebar from '@/components/layout/Sidebar.vue'
import AppContent from '@/components/layout/AppContent.vue'
import AppContentWrapper from '@/components/layout/AppContentWrapper.vue'
import ContentBlock from '@/components/layout/ContentBlock.vue'
import HeroBannerSection from '@/components/layout/HeroBannerSection.vue'
import TopUtilityBar from '@/components/layout/TopUtilityBar.vue'
import { Icon } from '@iconify/vue'
import { reactive, ref } from 'vue'

const isEditing = ref(false)

const profile = reactive({
  fullName: 'Aliya Hassan',
  email: 'aliya@example.com',
  phone: '+60 12-345 6789',
  role: 'Landlord',
  address: 'No. 18, Jalan Melati, Shah Alam, Selangor',
  emergencyContact: '+60 19-222 3344',
})

const form = reactive({ ...profile })

const details = [
  { key: 'fullName', label: 'Name Penuh', icon: 'mdi:account-outline' },
  { key: 'role', label: 'Peranan', icon: 'mdi:badge-account-outline' },
  { key: 'email', label: 'Emel', icon: 'mdi:email-outline' },
  { key: 'phone', label: 'Nombor Telefon', icon: 'mdi:phone-outline' },
  { key: 'address', label: 'Alamat', icon: 'mdi:map-marker-outline' },
  { key: 'emergencyContact', label: 'Emergency Contact', icon: 'mdi:phone-alert-outline' },
]

function startEdit() {
  Object.assign(form, profile)
  isEditing.value = true
}

function cancelEdit() {
  Object.assign(form, profile)
  isEditing.value = false
}

function saveProfile() {
  Object.assign(profile, form)
  isEditing.value = false
}
</script>

<template>
  <main class="flex min-h-screen items-start bg-slate-50 font-primary antialiased">
    <Sidebar />

    <AppContentWrapper>
      <TopUtilityBar />
      <HeroBannerSection>
        <div>
          <h1 class="text-3xl font-bold text-white sm:text-4xl">Kemas Kini Profil</h1>
          <p class="mt-2 max-w-xl text-base text-white">Kemaskini maklumat peribadi anda yang terkini.</p>
        </div>
      </HeroBannerSection>
      <AppContent>
        <div class="relative -mt-28 mb-8 grid gap-4 sm:grid-cols-2 xl:grid-cols-4">
          <img src="https://www.w3schools.com/howto/img_avatar.png" alt="Profile Photo"
            class="size-36 rounded-full border-2 border-white object-cover shadow-2xl shadow-slate-950/40 ring-1 ring-white/60 sm:size-34 lg:size-38">
        </div>

        <ContentBlock class="mb-8">
          <div class="mb-5 justify-end flex">
            <button v-if="!isEditing"
              class="inline-flex items-center justify-center gap-2 rounded-xl bg-emerald-600 px-4 py-2.5 text-sm font-semibold text-white shadow-sm transition hover:bg-emerald-700"
              type="button" @click="startEdit">
              <Icon icon="mdi:pencil-outline" class="size-5" aria-hidden="true" />
              Sunting
            </button>
          </div>

          <form v-if="isEditing" class="w-full space-y-4" @submit.prevent="saveProfile">
            <div class="grid gap-4 sm:grid-cols-2">
              <div>
                <label class="mb-1.5 block text-sm font-medium text-slate-700">Name Penuh</label>
                <input v-model="form.fullName"
                  class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500"
                  type="text" />
              </div>
              <div>
                <label class="mb-1.5 block text-sm font-medium text-slate-700">Peranan</label>
                <select v-model="form.role"
                  class="w-full rounded-xl border border-slate-200 bg-white px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500">
                  <option>Landlord</option>
                  <option>Tenant</option>
                </select>
              </div>
            </div>

            <div class="grid gap-4 sm:grid-cols-2">
              <div>
                <label class="mb-1.5 block text-sm font-medium text-slate-700">Emel</label>
                <input v-model="form.email"
                  class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500"
                  type="email" />
              </div>
              <div>
                <label class="mb-1.5 block text-sm font-medium text-slate-700">Nombor Telefon</label>
                <input v-model="form.phone"
                  class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500"
                  type="tel" />
              </div>
            </div>

            <div>
              <label class="mb-1.5 block text-sm font-medium text-slate-700">Alamat</label>
              <textarea v-model="form.address"
                class="min-h-24 w-full resize-y rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500"
                rows="3" />
            </div>

            <div>
              <label class="mb-1.5 block text-sm font-medium text-slate-700">Emergency Contact</label>
              <input v-model="form.emergencyContact"
                class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500"
                type="tel" />
            </div>

            <div class="flex flex-col gap-3 pt-2 sm:flex-row">
              <button
                class="flex flex-1 items-center justify-center rounded-xl bg-emerald-600 px-4 py-3 font-semibold text-white shadow-sm transition hover:bg-emerald-700 active:scale-[0.99]"
                type="submit">
                <Icon icon="ic:sharp-save" class="size-4 mr-2" aria-hidden="true" />
                Simpan Perubahan
              </button>
              <button
                class="flex-1 rounded-xl border border-slate-200 px-4 py-3 font-semibold text-slate-700 transition hover:bg-slate-50"
                type="button" @click="cancelEdit">Batal</button>
            </div>
          </form>
          <dl v-else class="grid w-full gap-3 lg:grid-cols-2">
            <div v-for="item in details" :key="item.key" class="rounded-xl border border-slate-200 p-4">
              <dt class="mb-1.5 flex items-center gap-2 text-sm font-medium text-slate-500">
                <Icon :icon="item.icon" class="size-5" aria-hidden="true" />
                {{ item.label }}
              </dt>
              <dd class="wrap-break-word font-semibold text-slate-900">{{ profile[item.key] }}</dd>
            </div>
          </dl>

        </ContentBlock>
      </AppContent>
    </AppContentWrapper>

    <!-- <div class="min-w-0 flex-1 pl-[72px] md:pl-0">
      <section class="relative bg-[linear-gradient(135deg,#0f172a_0%,#1e1b4b_100%)] px-6 pb-16 pt-6 sm:px-10">
        <div class="mb-8 flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">
          <div class="relative w-full lg:w-[300px]">
            <Icon icon="fa7-solid:magnifying-glass" class="absolute left-4 top-1/2 size-4 -translate-y-1/2 text-slate-500" aria-hidden="true" />
            <input
              type="text"
              class="w-full rounded-full border border-transparent bg-white/10 py-2.5 pl-11 pr-4 text-sm text-slate-100 outline-none transition placeholder:text-slate-500 focus:border-emerald-500 focus:bg-white/15"
              placeholder="Cari unit, nama penyewa atau invois..."
            >
          </div>

          <div class="flex flex-wrap items-center gap-3">
            <button
              class="inline-flex items-center gap-2 rounded-full border border-emerald-400/30 bg-emerald-500/15 px-4 py-2 text-sm font-semibold text-emerald-400 transition hover:border-emerald-500 hover:bg-emerald-500 hover:text-white"
              title="Tukar sebagai peranan penyewa"
              type="button"
            >
              <span>Tukar Dashboard</span>
              <Icon icon="fa7-solid:rotate" class="size-4" aria-hidden="true" />
            </button>

            <div class="relative">
              <button
                class="grid size-10 place-items-center rounded-full bg-white/10 text-slate-400 transition hover:bg-emerald-500 hover:text-white"
                :class="{ 'bg-emerald-500 text-white': isActionOpen }"
                title="Aksi Pantas"
                type="button"
                @click="isActionOpen = !isActionOpen"
              >
                <Icon icon="mdi:plus" class="size-5" aria-hidden="true" />
              </button>
              <div
                v-if="isActionOpen"
                class="absolute right-0 top-12 z-30 flex w-56 flex-col gap-1 rounded-xl border border-slate-200 bg-white p-1.5 shadow-xl shadow-slate-900/20"
              >
                <a href="#" class="flex items-center gap-3 rounded-lg px-3.5 py-2.5 text-sm font-medium text-slate-700 transition hover:bg-slate-100 hover:text-emerald-600">
                  <Icon icon="fa7-solid:building" class="size-4" aria-hidden="true" />
                  <span>Tambah Unit Baru</span>
                </a>
                <a href="#" class="flex items-center gap-3 rounded-lg px-3.5 py-2.5 text-sm font-medium text-slate-700 transition hover:bg-slate-100 hover:text-emerald-600">
                  <Icon icon="fa7-solid:user-plus" class="size-4" aria-hidden="true" />
                  <span>Daftar Penyewa</span>
                </a>
                <a href="#" class="flex items-center gap-3 rounded-lg px-3.5 py-2.5 text-sm font-medium text-slate-700 transition hover:bg-slate-100 hover:text-emerald-600">
                  <Icon icon="fa7-solid:receipt" class="size-4" aria-hidden="true" />
                  <span>Kemasukan Rekod</span>
                </a>
              </div>
            </div>

            <div class="relative">
              <button
                class="relative grid size-10 place-items-center rounded-full text-slate-400 transition hover:bg-white/10 hover:text-white"
                :class="{ 'bg-white/10 text-white': isNotificationOpen }"
                type="button"
                @click="isNotificationOpen = !isNotificationOpen"
              >
                <Icon icon="mdi:bell-outline" class="size-5" aria-hidden="true" />
                <span class="absolute right-2 top-2 size-2 rounded-full bg-red-500"></span>
              </button>
              <div
                v-if="isNotificationOpen"
                class="absolute right-0 top-12 z-30 flex w-[min(340px,calc(100vw-3rem))] flex-col overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-xl shadow-slate-900/20"
              >
                <div class="flex items-center justify-between border-b border-slate-100 p-4">
                  <h4 class="text-sm font-bold text-slate-900">Notifikasi</h4>
                  <a href="#" class="text-xs font-medium text-emerald-600 hover:text-emerald-700">Tanda semua dibaca</a>
                </div>

                <ul class="max-h-60 overflow-y-auto">
                  <li class="flex gap-3 border-b border-slate-100 bg-emerald-50 px-4 py-3.5">
                    <div class="grid size-9 shrink-0 place-items-center rounded-lg bg-emerald-100 text-emerald-600">
                      <Icon icon="fa7-solid:wallet" class="size-4" aria-hidden="true" />
                    </div>
                    <div>
                      <p class="text-sm text-slate-700">Ahmad Ridzuan telah membayar sewa untuk Unit A-10-3.</p>
                      <span class="text-xs text-slate-400">5 minit yang lalu</span>
                    </div>
                  </li>
                  <li class="flex gap-3 border-b border-slate-100 bg-emerald-50 px-4 py-3.5">
                    <div class="grid size-9 shrink-0 place-items-center rounded-lg bg-amber-100 text-amber-600">
                      <Icon icon="fa7-solid:file-circle-exclamation" class="size-4" aria-hidden="true" />
                    </div>
                    <div>
                      <p class="text-sm text-slate-700">Kontrak sewaan Siti Nurhaliza (Unit B-05-11) bakal tamat dalam 30 hari.</p>
                      <span class="text-xs text-slate-400">2 jam yang lalu</span>
                    </div>
                  </li>
                  <li class="flex gap-3 border-b border-slate-100 px-4 py-3.5">
                    <div class="grid size-9 shrink-0 place-items-center rounded-lg bg-emerald-100 text-emerald-600">
                      <Icon icon="fa7-solid:circle-check" class="size-4" aria-hidden="true" />
                    </div>
                    <div>
                      <p class="text-sm text-slate-700">Sistem berjaya memperbaharui invois bulanan untuk semua unit aktif.</p>
                      <span class="text-xs text-slate-400">Semalam</span>
                    </div>
                  </li>
                </ul>

                <div class="bg-slate-50 p-3 text-center">
                  <a href="#" class="text-sm font-semibold text-emerald-600 hover:text-emerald-700">Lihat semua aktiviti</a>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div>
          <h1 class="text-3xl font-bold text-white sm:text-4xl">Tetapan Profil</h1>
          <p class="mt-2 max-w-xl text-base text-white">Review and update the account details used across the system.</p>

          <img
            src="https://www.w3schools.com/howto/img_avatar.png"
            alt="Profile Photo"
            class="-mb-32 mx-auto mt-8 size-36 rounded-full border-8 border-white object-cover shadow-2xl shadow-slate-950/40 ring-1 ring-white/60 sm:size-44 lg:size-48"
          >
        </div>
      </section>

      <section class="px-6 pb-10 pt-36 sm:px-10" aria-labelledby="profile-title">
        <div class="w-full rounded-2xl border border-slate-200 bg-white p-5 shadow-sm sm:p-8">
          <div class="flex flex-col gap-4 sm:flex-row sm:items-start sm:justify-between my-7">
            <div>
              <h2 id="profile-title" class="text-3xl font-bold tracking-tight text-slate-900">My profile</h2>
              <p class="mt-2 text-sm text-slate-500">Review and update the account details used across the system.</p>
            </div>

            <button
              v-if="!isEditing"
              class="inline-flex items-center justify-center gap-2 rounded-xl bg-emerald-600 px-4 py-2.5 text-sm font-semibold text-white shadow-sm transition hover:bg-emerald-700"
              type="button"
              @click="startEdit"
            >
              <Icon icon="mdi:pencil-outline" class="size-5" aria-hidden="true" />
              Edit
            </button>
          </div>

          <form v-if="isEditing" class="w-full space-y-4" @submit.prevent="saveProfile">
            <div class="grid gap-4 sm:grid-cols-2">
              <div>
                <label class="mb-1.5 block text-sm font-medium text-slate-700">Name Penuh</label>
                <input v-model="form.fullName" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" type="text" />
              </div>
              <div>
                <label class="mb-1.5 block text-sm font-medium text-slate-700">Peranan</label>
                <select v-model="form.role" class="w-full rounded-xl border border-slate-200 bg-white px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500">
                  <option>Landlord</option>
                  <option>Tenant</option>
                </select>
              </div>
            </div>

            <div class="grid gap-4 sm:grid-cols-2">
              <div>
                <label class="mb-1.5 block text-sm font-medium text-slate-700">Emel</label>
                <input v-model="form.email" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" type="email" />
              </div>
              <div>
                <label class="mb-1.5 block text-sm font-medium text-slate-700">Nombor Telefon</label>
                <input v-model="form.phone" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" type="tel" />
              </div>
            </div>

            <div>
              <label class="mb-1.5 block text-sm font-medium text-slate-700">Alamat</label>
              <textarea v-model="form.address" class="min-h-24 w-full resize-y rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" rows="3" />
            </div>

            <div>
              <label class="mb-1.5 block text-sm font-medium text-slate-700">Emergency Contact</label>
              <input v-model="form.emergencyContact" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" type="tel" />
            </div>

            <div class="flex flex-col gap-3 pt-2 sm:flex-row">
              <button class="flex-1 rounded-xl bg-emerald-600 px-4 py-3 font-semibold text-white shadow-sm transition hover:bg-emerald-700 active:scale-[0.99]" type="submit">Simpan Perubahan</button>
              <button class="flex-1 rounded-xl border border-slate-200 px-4 py-3 font-semibold text-slate-700 transition hover:bg-slate-50" type="button" @click="cancelEdit">Batal</button>
            </div>
          </form>

          <dl v-else class="grid w-full gap-3 lg:grid-cols-2">
            <div v-for="item in details" :key="item.key" class="rounded-xl border border-slate-200 p-4">
              <dt class="mb-1.5 flex items-center gap-2 text-sm font-medium text-slate-500">
                <Icon :icon="item.icon" class="size-5" aria-hidden="true" />
                {{ item.label }}
              </dt>
              <dd class="break-words font-semibold text-slate-900">{{ profile[item.key] }}</dd>
            </div>
          </dl>
        </div>
      </section>
    </div> -->
  </main>
</template>
