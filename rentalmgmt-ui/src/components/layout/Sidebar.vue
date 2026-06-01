<script setup>
import { onMounted, ref } from 'vue'
import { Icon } from '@iconify/vue'
import { RouterLink } from 'vue-router'

const isCollapsed = ref(false)
const isSubmenuOpen = ref(false)

const menuLinkClass =
  'group/item relative flex items-center rounded-lg text-sm font-medium text-slate-600 no-underline transition-all duration-200 ease-in-out hover:bg-slate-100 hover:text-emerald-600'

const toggleSubmenu = () => {
  if (isCollapsed.value) return
  isSubmenuOpen.value = !isSubmenuOpen.value
}

onMounted(() => {
  isCollapsed.value = window.matchMedia('(max-width: 767px)').matches
})
</script>

<template>
  <aside
    class="fixed inset-y-0 left-0 z-40 flex h-screen max-h-screen shrink-0 flex-col justify-between overflow-visible border-r border-slate-200 bg-slate-50 font-primary text-slate-600 shadow-xl shadow-slate-900/10 transition-all duration-300 md:sticky md:shadow-none"
    :class="isCollapsed ? 'w-[72px] p-3' : 'w-[260px] p-6'"
  >

    <div class="mb-6 flex items-start" :class="isCollapsed ? 'justify-center' : 'justify-between gap-3'">
  <div class="flex items-start" :class="isCollapsed ? 'justify-center' : 'gap-3'">

    <div v-if="!isCollapsed"
      class="grid size-10 shrink-0 place-items-center rounded-lg bg-emerald-50 text-emerald-600 mt-0.5">
      <Icon icon="fa7-solid:house-chimney-user" class="size-5" aria-hidden="true" />
    </div>

    <span v-if="!isCollapsed" class="font-semibold text-slate-900 leading-tight">Townhouse SeriKu</span>

    <button
      class="group relative grid size-9 shrink-0 place-items-center rounded-lg text-slate-500 transition hover:bg-slate-100 hover:text-emerald-600 mt-0.5"
      type="button" 
      @click="isCollapsed = !isCollapsed">
      <Icon :icon="isCollapsed ? 'mdi:menu-open' : 'mdi:menu'" class="size-5" aria-hidden="true" />

      <span v-if="isCollapsed" class="pointer-events-none absolute left-full top-1/2 z-50 ml-3 -translate-y-1/2 whitespace-nowrap rounded-md bg-slate-900 px-2.5 py-1.5 text-xs font-semibold text-white opacity-0 shadow-lg transition group-hover:opacity-100">
        Buka Sidebar
      </span>
    </button>
  </div>
</div>

    <ul class="m-0 flex list-none flex-col gap-2 p-0">
      
      <li class="group">
        <RouterLink 
          to="/dashboard"
          title="Dashboard"
          class="group relative flex items-center rounded-lg text-sm font-medium no-underline text-slate-600 transition-all duration-200 hover:bg-slate-100 hover:text-emerald-600 aria-[current=page]:bg-white aria-[current=page]:font-semibold aria-[current=page]:text-slate-900 aria-[current=page]:shadow-[0_4px_12px_rgba(15,23,42,0.03),0_1px_2px_rgba(15,23,42,0.02)]"
          :class="isCollapsed ? 'justify-center px-0 py-3' : 'gap-3 px-4 py-2.5'"
        >
          <span class="absolute left-0 top-0 h-full w-1 rounded-r bg-emerald-600 hidden group-aria-[current=page]:block"></span>

          <Icon icon="material-symbols:dashboard-rounded" class="size-5 shrink-0 transition-transform duration-200 group-hover/item:scale-105 group-hover/item:text-emerald-600" aria-hidden="true" />

          <span v-if="!isCollapsed">Dashboard</span>
          <span v-else class="pointer-events-none absolute left-full top-1/2 z-50 ml-3 -translate-y-1/2 whitespace-nowrap rounded-md bg-slate-900 px-2.5 py-1.5 text-xs font-semibold text-white opacity-0 shadow-lg transition group-hover:opacity-100">
            Dashboard
          </span>
        </RouterLink>
      </li>

      <li class="group">
        <a href="#" :class="[menuLinkClass, isCollapsed ? 'justify-center px-0 py-3' : 'gap-3 px-4 py-3']" title="Pemilikan">
          <Icon icon="fa7-solid:building" class="size-5 shrink-0 transition-transform duration-200 group-hover/item:scale-105 group-hover/item:text-emerald-600" aria-hidden="true" />
          <span v-if="!isCollapsed">Pemilikan</span>
          <span v-if="isCollapsed" class="pointer-events-none absolute left-full top-1/2 z-50 ml-3 -translate-y-1/2 whitespace-nowrap rounded-md bg-slate-900 px-2.5 py-1.5 text-xs font-semibold text-white opacity-0 shadow-lg transition group-hover:opacity-100">Pemilikan</span>
        </a>
      </li>

      <li class="group" :class="{ 'open': isSubmenuOpen }">
        <a href="#" @click.prevent="toggleSubmenu" :class="[menuLinkClass, isCollapsed ? 'justify-center px-0 py-3' : 'justify-between gap-3 px-4 py-3']" title="Penyewa">
          <span class="flex items-center" :class="{ 'gap-3': !isCollapsed }">
            <Icon icon="mdi:account-group" class="size-5 shrink-0 transition-transform duration-200 group-hover/item:scale-105 group-hover/item:text-emerald-600" aria-hidden="true" />
            <span v-if="!isCollapsed">Penyewa</span>
          </span>
          <Icon 
            v-if="!isCollapsed"
            icon="mdi:chevron-right" 
            class="size-4 text-slate-400 transition-transform duration-200 ease-in-out" 
            :class="{ 'rotate-90 text-emerald-600': isSubmenuOpen }"
            aria-hidden="true" 
          />
          <span v-if="isCollapsed" class="pointer-events-none absolute left-full top-1/2 z-50 ml-3 -translate-y-1/2 whitespace-nowrap rounded-md bg-slate-900 px-2.5 py-1.5 text-xs font-semibold text-white opacity-0 shadow-lg transition group-hover:opacity-100">Penyewa</span>
        </a>
        
        <ul 
          v-if="!isCollapsed"
          class="flex list-none flex-col gap-1 overflow-hidden pl-8 transition-all duration-300 ease-[cubic-bezier(0.4,0,0.2,1)]"
          :class="isSubmenuOpen ? 'max-h-[150px] mt-1' : 'max-h-0 mt-0'"
        >
          <li class="group/sub">
            <a href="#" class="flex items-center gap-3 rounded-md px-3 py-2 text-[13.5px] font-medium text-slate-500 no-underline transition-all duration-200 hover:bg-slate-100 hover:text-emerald-600">
              <Icon icon="ph:money-bold" class="size-4 transition-transform duration-200 group-hover/sub:scale-105 group-hover/sub:text-emerald-600" aria-hidden="true" /> 
              Kutipan Sewa
            </a>
          </li>
        </ul>
      </li>

      <li class="group">
        <a href="#" :class="[menuLinkClass, isCollapsed ? 'justify-center px-0 py-3' : 'gap-3 px-4 py-3']" title="Kutipan">
          <Icon icon="streamline-ultimate:begging-hand-coin-2-bold" class="size-5 shrink-0 transition-transform duration-200 group-hover/item:scale-105 group-hover/item:text-emerald-600" aria-hidden="true" />
          <span v-if="!isCollapsed">Kutipan</span>
          <span v-if="isCollapsed" class="pointer-events-none absolute left-full top-1/2 z-50 ml-3 -translate-y-1/2 whitespace-nowrap rounded-md bg-slate-900 px-2.5 py-1.5 text-xs font-semibold text-white opacity-0 shadow-lg transition group-hover:opacity-100">Kutipan</span>
        </a>
      </li>

      <li class="group">
        <a href="#" :class="[menuLinkClass, isCollapsed ? 'justify-center px-0 py-3' : 'gap-3 px-4 py-3']" title="Penyelenggaraan">
          <Icon icon="wpf:maintenance" class="size-5 shrink-0 transition-transform duration-200 group-hover/item:scale-105 group-hover/item:text-emerald-600" aria-hidden="true" />
          <span v-if="!isCollapsed">Penyelenggaraan</span>
          <span v-if="isCollapsed" class="pointer-events-none absolute left-full top-1/2 z-50 ml-3 -translate-y-1/2 whitespace-nowrap rounded-md bg-slate-900 px-2.5 py-1.5 text-xs font-semibold text-white opacity-0 shadow-lg transition group-hover:opacity-100">Penyelenggaraan</span>
        </a>
      </li>

      <li class="group">
        <a href="#" :class="[menuLinkClass, isCollapsed ? 'justify-center px-0 py-3' : 'gap-3 px-4 py-3']" title="Kontrak & Perjanjian">
          <Icon icon="fa6-solid:file-signature" class="size-5 shrink-0 transition-transform duration-200 group-hover/item:scale-105 group-hover/item:text-emerald-600" aria-hidden="true" />
          <span v-if="!isCollapsed">Kontrak & Perjanjian</span>
          <span v-if="isCollapsed" class="pointer-events-none absolute left-full top-1/2 z-50 ml-3 -translate-y-1/2 whitespace-nowrap rounded-md bg-slate-900 px-2.5 py-1.5 text-xs font-semibold text-white opacity-0 shadow-lg transition group-hover:opacity-100">Kontrak & Perjanjian</span>
        </a>
      </li>

      <li class="group">
        <a href="#" :class="[menuLinkClass, isCollapsed ? 'justify-center px-0 py-3' : 'gap-3 px-4 py-3']" title="Janji Temu">
          <Icon icon="solar:calendar-bold" class="size-5 shrink-0 transition-transform duration-200 group-hover/item:scale-105 group-hover/item:text-emerald-600" aria-hidden="true" />
          <span v-if="!isCollapsed">Janji Temu</span>
          <span v-if="isCollapsed" class="pointer-events-none absolute left-full top-1/2 z-50 ml-3 -translate-y-1/2 whitespace-nowrap rounded-md bg-slate-900 px-2.5 py-1.5 text-xs font-semibold text-white opacity-0 shadow-lg transition group-hover:opacity-100">Janji Temu</span>
        </a>
      </li>
    </ul>

    <div class="min-h-7 flex-1"></div>

    <div class="flex flex-col gap-1 border-t border-slate-200 pt-4">
      <RouterLink 
        to="/profile" 
        title="Kemas Kini Profil"
        class="group relative flex items-center rounded-lg text-sm font-medium no-underline text-slate-600 transition-all duration-200 hover:bg-slate-100 hover:text-emerald-600 aria-[current=page]:bg-white aria-[current=page]:font-semibold aria-[current=page]:text-slate-900 aria-[current=page]:shadow-[0_4px_12px_rgba(15,23,42,0.03),0_1px_2px_rgba(15,23,42,0.02)]"
        :class="isCollapsed ? 'justify-center px-0 py-3' : 'gap-3 px-4 py-2.5'"
      >
        <span class="absolute left-0 top-0 h-full w-1 rounded-r bg-emerald-600 hidden group-aria-[current=page]:block"></span>

        <Icon
          icon="fa6-solid:user-gear"
          class="size-5 shrink-0 transition-transform duration-200 group-hover:scale-105 group-hover:text-emerald-600 group-aria-[current=page]:text-emerald-600"
          aria-hidden="true"
        /> 

        <span v-if="!isCollapsed">Kemas Kini Profil</span>
        <span v-else class="pointer-events-none absolute left-full top-1/2 z-50 ml-3 -translate-y-1/2 whitespace-nowrap rounded-md bg-slate-900 px-2.5 py-1.5 text-xs font-semibold text-white opacity-0 shadow-lg transition group-hover:opacity-100">
          Kemas Kini Profil
        </span>
      </RouterLink>
      <a href="#" :class="[menuLinkClass, isCollapsed ? 'justify-center px-0 py-3' : 'gap-3 px-4 py-2.5']" title="Panduan Pengguna">
        <Icon icon="fa6-solid:book" class="size-5 shrink-0 transition-transform duration-200 group-hover/item:scale-105 group-hover/item:text-emerald-600" aria-hidden="true" />
        <span v-if="!isCollapsed">Panduan Pengguna</span>
        <span v-if="isCollapsed" class="pointer-events-none absolute left-full top-1/2 z-50 ml-3 -translate-y-1/2 whitespace-nowrap rounded-md bg-slate-900 px-2.5 py-1.5 text-xs font-semibold text-white opacity-0 shadow-lg transition group-hover:opacity-100">Panduan Pengguna</span>
      </a>
      <a href="#" :class="[menuLinkClass, isCollapsed ? 'justify-center px-0 py-3' : 'gap-3 px-4 py-2.5']" title="Meja Bantuan">
        <Icon icon="fa6-solid:headset" class="size-5 shrink-0 transition-transform duration-200 group-hover/item:scale-105 group-hover/item:text-emerald-600" aria-hidden="true" />
        <span v-if="!isCollapsed">Meja Bantuan</span>
        <span v-if="isCollapsed" class="pointer-events-none absolute left-full top-1/2 z-50 ml-3 -translate-y-1/2 whitespace-nowrap rounded-md bg-slate-900 px-2.5 py-1.5 text-xs font-semibold text-white opacity-0 shadow-lg transition group-hover:opacity-100">Meja Bantuan</span>
      </a>
      <a href="#" :class="[menuLinkClass, isCollapsed ? 'justify-center px-0 py-3' : 'gap-3 px-4 py-2.5']" title="Log Keluar">
        <Icon icon="mdi:logout" class="size-5 shrink-0 transition-transform duration-200 group-hover/item:scale-105 group-hover/item:text-emerald-600" aria-hidden="true" />
        <span v-if="!isCollapsed">Log Keluar</span>
        <span v-if="isCollapsed" class="pointer-events-none absolute left-full top-1/2 z-50 ml-3 -translate-y-1/2 whitespace-nowrap rounded-md bg-slate-900 px-2.5 py-1.5 text-xs font-semibold text-white opacity-0 shadow-lg transition group-hover:opacity-100">Log Keluar</span>
      </a>
    </div>
  </aside>
</template>
