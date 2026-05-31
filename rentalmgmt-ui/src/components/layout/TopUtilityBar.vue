<script setup>
import { onBeforeUnmount, onMounted, ref } from 'vue'
import { Icon } from '@iconify/vue'

const isActionOpen = ref(false)
const isNotificationOpen = ref(false)
const isScrolled = ref(false)
const hasUnreadNotifications = ref(true)

const quickActions = [
  { label: 'Tambah Unit Baru', icon: 'fa7-solid:building' },
  { label: 'Daftar Penyewa', icon: 'fa7-solid:user-plus' },
  { label: 'Kemasukan Rekod', icon: 'fa7-solid:receipt' },
]

const notifications = ref([
  {
    id: 1,
    unread: true,
    icon: 'fa7-solid:wallet',
    iconClass: 'bg-emerald-100 text-emerald-700',
    message: 'Ahmad Ridzuan telah membayar sewa untuk Unit A-10-3.',
    time: '5 minit yang lalu',
  },
  {
    id: 2,
    unread: true,
    icon: 'fa7-solid:file-circle-exclamation',
    iconClass: 'bg-amber-100 text-amber-700',
    message: 'Kontrak sewaan Siti Nurhaliza (Unit B-05-11) bakal tamat dalam 30 hari.',
    time: '2 jam yang lalu',
  },
  {
    id: 3,
    unread: false,
    icon: 'fa7-solid:circle-check',
    iconClass: 'bg-emerald-100 text-emerald-700',
    message: 'Sistem berjaya memperbaharui invois bulanan untuk semua unit aktif.',
    time: 'Semalam',
  },
])

function toggleActionMenu() {
  isActionOpen.value = !isActionOpen.value
  if (isActionOpen.value) isNotificationOpen.value = false
}

function toggleNotifications() {
  isNotificationOpen.value = !isNotificationOpen.value
  if (isNotificationOpen.value) isActionOpen.value = false
}

function handleScroll() {
  isScrolled.value = window.scrollY > 8
}

function markAllRead() {
  hasUnreadNotifications.value = false
  notifications.value = notifications.value.map((item) => ({ ...item, unread: false }))
}

onMounted(() => {
  handleScroll()
  window.addEventListener('scroll', handleScroll, { passive: true })
})

onBeforeUnmount(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>

<template>
  <div
    class="sticky top-0 z-30 flex flex-col gap-4 px-4 py-4 transition-all duration-300 sm:px-6 lg:px-10 xl:flex-row xl:items-center xl:justify-between"
    :class="isScrolled ? 'border-b border-white/10 bg-slate-600/70 shadow-lg shadow-slate-950/10 backdrop-blur-md' : 'bg-[linear-gradient(135deg,#0f172a_0%,#1e1b4b_100%)]'"
  >
    <div class="relative w-full xl:w-[320px]">
      <Icon icon="fa7-solid:magnifying-glass" class="absolute left-4 top-1/2 size-4 -translate-y-1/2" :class="isScrolled ? 'text-slate-700' : 'text-slate-400'" aria-hidden="true" />
      <input
        type="text"
        class="w-full rounded-full border border-white/10 bg-white/15 py-2.5 pl-11 pr-4 text-sm font-medium text-slate-50 outline-none transition focus:border-emerald-400 focus:bg-white/20"
        placeholder="Cari unit, nama penyewa atau invois..."
        :class="isScrolled ? 'placeholder:text-slate-700' : 'placeholder:text-slate-400'"
      >
    </div>

    <div class="flex flex-wrap items-center gap-3">
      <button
        class="inline-flex items-center gap-2 rounded-full border border-emerald-400/30 bg-emerald-500/15 px-4 py-2 text-sm font-semibold text-emerald-300 transition hover:border-emerald-500 hover:bg-emerald-500 hover:text-white"
        type="button"
        title="Tukar sebagai peranan penyewa"
      >
        <span>Tukar Dashboard</span>
        <Icon icon="fa7-solid:rotate" class="size-4" aria-hidden="true" />
      </button>

      <div class="relative">
        <button
          class="grid size-10 place-items-center rounded-full bg-white/10 text-slate-200 transition hover:bg-emerald-500 hover:text-white"
          :class="{ 'bg-emerald-500 text-white': isActionOpen }"
          type="button"
          title="Aksi Pantas"
          @click="toggleActionMenu"
        >
          <Icon icon="mdi:plus" class="size-5" aria-hidden="true" />
        </button>
        <div
          v-if="isActionOpen"
          class="absolute right-0 top-12 z-30 flex w-56 flex-col gap-1 rounded-xl border border-slate-200 bg-white p-1.5 shadow-xl shadow-slate-900/20"
        >
          <a
            v-for="action in quickActions"
            :key="action.label"
            :href="action.href || '#'"
            class="flex items-center gap-3 rounded-lg px-3.5 py-2.5 text-sm font-medium text-slate-700 transition hover:bg-slate-100 hover:text-emerald-600"
          >
            <Icon :icon="action.icon" class="size-4" aria-hidden="true" />
            <span>{{ action.label }}</span>
          </a>
        </div>
      </div>

      <div class="relative">
        <button
          class="relative grid size-10 place-items-center rounded-full text-slate-200 transition hover:bg-white/10 hover:text-white"
          :class="{ 'bg-white/10 text-white': isNotificationOpen }"
          type="button"
          title="Notifikasi"
          @click="toggleNotifications"
        >
          <Icon icon="mdi:bell-outline" class="size-5" aria-hidden="true" />
          <span v-if="hasUnreadNotifications" class="absolute right-2 top-2 size-2 rounded-full bg-red-500"></span>
        </button>
        <div
          v-if="isNotificationOpen"
          class="absolute right-0 top-12 z-30 flex w-[min(340px,calc(100vw-6rem))] flex-col overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-xl shadow-slate-900/20"
        >
          <div class="flex items-center justify-between border-b border-slate-100 p-4">
            <h4 class="text-sm font-bold text-slate-900">Notifikasi</h4>
            <button class="text-xs font-medium text-emerald-600 hover:text-emerald-700" type="button" @click="markAllRead">Tanda semua dibaca</button>
          </div>
          <ul class="max-h-60 overflow-y-auto">
            <li
              v-for="item in notifications"
              :key="item.id"
              class="flex gap-3 border-b border-slate-100 px-4 py-3.5"
              :class="{ 'bg-emerald-50': item.unread }"
            >
              <div class="grid size-9 shrink-0 place-items-center rounded-lg" :class="item.iconClass">
                <Icon :icon="item.icon" class="size-4" aria-hidden="true" />
              </div>
              <div>
                <p class="text-sm text-slate-700" :class="{ 'font-semibold text-slate-900': item.unread }">{{ item.message }}</p>
                <span class="text-xs text-slate-400">{{ item.time }}</span>
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
</template>
