<script setup>
import { Icon } from '@iconify/vue'
import { reactive, ref } from 'vue'
import { RouterLink } from 'vue-router'

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
  { key: 'fullName', label: 'Full Name', icon: 'mdi:account-outline' },
  { key: 'role', label: 'Role', icon: 'mdi:badge-account-outline' },
  { key: 'email', label: 'Email Address', icon: 'mdi:email-outline' },
  { key: 'phone', label: 'Phone Number', icon: 'mdi:phone-outline' },
  { key: 'address', label: 'Address', icon: 'mdi:map-marker-outline' },
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
  <main class="grid min-h-screen grid-cols-1 bg-white antialiased lg:grid-cols-12">
    <section
      class="relative hidden items-center bg-slate-900 bg-cover bg-center p-16 lg:col-span-2 lg:flex"
      aria-label="Rental property overview">
      
    </section>

    <section class="flex flex-col justify-center bg-white px-6 py-10 sm:px-16 md:px-20 lg:col-span-10 lg:px-12 xl:px-20" aria-labelledby="profile-title">
      <div class="mb-8 flex items-center justify-between gap-4">
        <div class="flex items-center gap-2">
          <span class="grid size-9 place-items-center rounded-md bg-emerald-600 text-white">
            <Icon icon="mdi:home-city" class="size-6" aria-hidden="true" />
          </span>
          <span class="text-xl font-bold tracking-tight text-slate-900">EazyStore Rent</span>
        </div>
        <RouterLink class="text-sm font-medium text-emerald-600 transition hover:text-emerald-700 hover:underline" to="/login">Sign out</RouterLink>
      </div>

      <div class="mb-7 flex items-start justify-between gap-4">
        <div>
          <h2 id="profile-title" class="text-3xl font-bold tracking-tight text-slate-900">My profile</h2>
          <p class="mt-2 text-sm text-slate-500">Review and update the account details used across the system.</p>
        </div>
        <button
          v-if="!isEditing"
          class="inline-flex shrink-0 items-center gap-2 rounded-xl bg-emerald-600 px-4 py-2.5 text-sm font-semibold text-white shadow-sm transition hover:bg-emerald-700"
          type="button"
          @click="startEdit"
        >
          <Icon icon="mdi:pencil-outline" class="size-5" aria-hidden="true" />
          Edit
        </button>
      </div>

      <div class="mb-7 flex items-center gap-4 rounded-2xl bg-slate-50 p-4">
        <div class="grid size-16 shrink-0 place-items-center rounded-xl bg-emerald-100 text-emerald-700">
          <Icon icon="mdi:account-circle-outline" class="size-10" aria-hidden="true" />
        </div>
        <div class="min-w-0">
          <p class="truncate text-lg font-bold text-slate-900">{{ profile.fullName }}</p>
          <p class="mt-1 flex items-center gap-2 text-sm text-slate-500">
            <Icon icon="mdi:badge-account-outline" class="size-5" aria-hidden="true" />
            {{ profile.role }} Portal
          </p>
        </div>
      </div>

      <form v-if="isEditing" class="space-y-4" @submit.prevent="saveProfile">
        <div class="grid gap-4 sm:grid-cols-2">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Full Name</label>
            <input v-model="form.fullName" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" type="text" />
          </div>
          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Role</label>
            <select v-model="form.role" class="w-full rounded-xl border border-slate-200 bg-white px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500">
              <option>Landlord</option>
              <option>Tenant</option>
            </select>
          </div>
        </div>

        <div class="grid gap-4 sm:grid-cols-2">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Email Address</label>
            <input v-model="form.email" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" type="email" />
          </div>
          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Phone Number</label>
            <input v-model="form.phone" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" type="tel" />
          </div>
        </div>

        <div>
          <label class="mb-1.5 block text-sm font-medium text-slate-700">Address</label>
          <textarea v-model="form.address" class="min-h-24 w-full resize-y rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" rows="3" />
        </div>

        <div>
          <label class="mb-1.5 block text-sm font-medium text-slate-700">Emergency Contact</label>
          <input v-model="form.emergencyContact" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" type="tel" />
        </div>

        <div class="flex flex-col gap-3 pt-2 sm:flex-row">
          <button class="flex-1 rounded-xl bg-emerald-600 px-4 py-3 font-semibold text-white shadow-sm transition hover:bg-emerald-700 active:scale-[0.99]" type="submit">Save Changes</button>
          <button class="flex-1 rounded-xl border border-slate-200 px-4 py-3 font-semibold text-slate-700 transition hover:bg-slate-50" type="button" @click="cancelEdit">Cancel</button>
        </div>
      </form>

      <dl v-else class="grid gap-3">
        <div v-for="item in details" :key="item.key" class="rounded-xl border border-slate-200 p-4">
          <dt class="mb-1.5 flex items-center gap-2 text-sm font-medium text-slate-500">
            <Icon :icon="item.icon" class="size-5" aria-hidden="true" />
            {{ item.label }}
          </dt>
          <dd class="wrap-break-word font-semibold text-slate-900">{{ profile[item.key] }}</dd>
        </div>
      </dl>
    </section>
  </main>
</template>
