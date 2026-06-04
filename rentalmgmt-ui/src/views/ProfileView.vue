<script setup>
import Sidebar from '@/components/layout/Sidebar.vue'
import AppContent from '@/components/layout/AppContent.vue'
import AppContentWrapper from '@/components/layout/AppContentWrapper.vue'
import ContentBlock from '@/components/layout/ContentBlock.vue'
import HeroBannerSection from '@/components/layout/HeroBannerSection.vue'
import TopUtilityBar from '@/components/layout/TopUtilityBar.vue'
import { Icon } from '@iconify/vue'
import { ref, onMounted } from 'vue'
import { useAuth } from "@/stores/authStore"
import { useForm } from 'vee-validate'
import { toTypedSchema } from '@vee-validate/zod'
import { profileEditSchema } from '@/schemas/profileSchema.js'

const { isLoading } = useAuth()
const auth = useAuth()

const saveError = ref(null)
const saveSuccess = ref(false)
const showPassword = ref(false)
const showConfirmPassword = ref(false)

// --------------------
// VEEVALIDATE FORM
// --------------------
const {
  errors,
  defineField,
  handleSubmit,
  resetForm,
  meta
} = useForm({
  validationSchema: toTypedSchema(profileEditSchema),
  validateOnModelUpdate: false
})

// --------------------
// FIELDS
// --------------------
const [fullName] = defineField('fullName')
const [displayName] = defineField('displayName')
const [phone] = defineField('phone')
const [emergencyContact] = defineField('emergencyContact')

const [street] = defineField('street')
const [postalCode] = defineField('postalCode')
const [city] = defineField('city')
const [state] = defineField('state')
const [country] = defineField('country')

const [email] = defineField('email')
const [password] = defineField('password')
const [confirmPassword] = defineField('confirmPassword')

// --------------------
// LOAD DATA
// --------------------
async function loadUserData() {
  await auth.fetchProfile()

  const p = auth.profile.value

  resetForm({
    values: {
      fullName: p.fullName || '',
      displayName: p.displayName || '',
      phone: p.phone || '',
      emergencyContact: p.emergencyContact || '',

      street: p.address?.street || '',
      city: p.address?.city || '',
      state: p.address?.state || '',
      postalCode: p.address?.postalCode || '',
      country: p.address?.country || '',

      email: p.email || '',
      password: '',
      confirmPassword: ''
    }
  })
}

// --------------------
// SUBMIT
// --------------------
const saveProfile = handleSubmit(async (formValues) => {
  saveError.value = null
  saveSuccess.value = false

  const result = await auth.updateProfile({
    fullName: formValues.fullName,
    displayName: formValues.displayName,
    phone: formValues.phone,
    emergencyContact: formValues.emergencyContact,

    street: formValues.street,
    postalCode: formValues.postalCode,
    city: formValues.city,
    state: formValues.state,
    country: formValues.country,

    email: formValues.email,
    password: formValues.password || undefined
  })

  if (result.success) {
    saveSuccess.value = true

    resetForm({
      values: {
        ...formValues,
        password: '',
        confirmPassword: ''
      }
    })

    setTimeout(() => {
      saveSuccess.value = false
    }, 3000)
  } else {
    saveError.value = result.error || 'Gagal mengemaskini profil'
  }
})

onMounted(() => {
  loadUserData()
})
</script>

<template>
  <main class="flex min-h-screen items-start bg-slate-50 font-primary antialiased">
    <Sidebar />

    <AppContentWrapper>
      <TopUtilityBar />
      <HeroBannerSection>
        <div>
          <h1 class="text-3xl font-bold text-white sm:text-4xl">Edit Profile</h1>
          <p class="mt-2 max-w-xl text-base text-white">Update your personal information.</p>
        </div>
      </HeroBannerSection>
      
      <AppContent>
        <!-- Profile Picture Section -->
        <div class="relative -mt-28 mb-8">
          <div class="relative inline-block">
            <img 
              src="https://www.w3schools.com/howto/img_avatar.png" 
              alt="Profile Photo"
              class="size-36 rounded-full border-4 border-white object-cover shadow-2xl shadow-slate-950/40 ring-1 ring-white/60"
            />
            <button 
              class="absolute bottom-2 right-2 rounded-full bg-emerald-600 p-2 text-white shadow-lg transition hover:bg-emerald-700"
              type="button"
              @click="console.log('Upload photo feature')"
            >
              <Icon icon="mdi:camera" class="size-4" />
            </button>
          </div>
        </div>

        <ContentBlock class="mb-8">
          <!-- Loading State -->
          <div v-if="isLoading && !profile" class="flex items-center justify-center py-12">
            <Icon icon="mdi:loading" class="size-8 animate-spin text-emerald-600" />
            <span class="ml-2 text-slate-600">Memuatkan profil...</span>
          </div>

          <div v-else>
            <!-- Status Success Message Bar -->
            <div class="mb-5 flex flex-wrap items-center justify-end gap-3">
              <div 
                v-if="saveSuccess" 
                class="flex items-center gap-2 rounded-lg bg-emerald-50 px-4 py-2 text-emerald-700"
              >
                <Icon icon="mdi:check-circle-outline" class="size-5" />
                <span class="text-sm font-medium">Profile updated successfully!</span>
              </div>
            </div>

            <!-- Error Alert dari Backend -->
            <div v-if="saveError" class="mb-6 rounded-xl border border-red-200 bg-red-50 p-4">
              <div class="flex items-start gap-2">
                <Icon icon="mdi:alert-circle-outline" class="size-5 text-red-600 shrink-0 mt-0.5" />
                <div class="flex-1">
                  <p class="text-sm font-medium text-red-800">Failed to update profile</p>
                  <p class="text-sm text-red-700">{{ saveError }}</p>
                </div>
                <button type="button" class="text-red-600 hover:text-red-800" @click="saveError = null">
                  <Icon icon="mdi:close" class="size-4" />
                </button>
              </div>
            </div>

            <!-- Main Form (Satu borang yang sentiasa boleh diedit) -->
            <form @submit.prevent="saveProfile" class="w-full space-y-8">
              
              <!-- Grid Layout Utama: Kiri (Maklumat Peribadi) & Kanan (Kemas Kini Emel/Password) -->
              <div class="grid gap-8 lg:grid-cols-2">
                
                <!-- KOLUM KIRI: Maklumat Peribadi -->
                <div class="space-y-4">
                  <h2 class="text-lg font-bold text-slate-900">Personal Information</h2>
                  
                  <div class="grid gap-4 sm:grid-cols-2">
                    <div>
                      <label class="mb-1.5 block text-sm font-medium text-slate-700">Full Name<span class="text-rose-500" aria-hidden="true">*</span></label>
                      <input v-model="fullName" type="text" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" placeholder="Ahmad Abu bin Ali" required />
                      <p v-if="errors.fullName" class="mt-1 text-xs text-red-600">{{ errors.fullName }}</p>
                    </div>
                    <div>
                      <label class="mb-1.5 block text-sm font-medium text-slate-700">Display Name<span class="text-rose-500" aria-hidden="true">*</span></label>
                      <input v-model="displayName" type="text" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" placeholder="Encik Amad" required/>
                      <p v-if="errors.displayName" class="mt-1 text-xs text-red-600">{{ errors.displayName }}</p>
                    </div>
                  </div>

                  <div class="grid gap-4 sm:grid-cols-2">
                    <div>
                      <label class="mb-1.5 block text-sm font-medium text-slate-700">Phone Number<span class="text-rose-500" aria-hidden="true">*</span></label>
                      <input v-model="phone" type="tel" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" placeholder="60123456789" required />
                      <p v-if="errors.phone" class="mt-1 text-xs text-red-600">{{ errors.phone }}</p>
                    </div>
                    <div>
                      <label class="mb-1.5 block text-sm font-medium text-slate-700">Emergency Contact<span class="text-rose-500" aria-hidden="true">*</span></label>
                      <input v-model="emergencyContact" type="tel" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" placeholder="60123456789" required />
                      <p v-if="errors.emergencyContact" class="mt-1 text-xs text-red-600">{{ errors.emergencyContact }}</p>
                    </div>
                  </div>

                  <div>
                    <label class="mb-1.5 block text-sm font-medium text-slate-700">Address<span class="text-rose-500" aria-hidden="true">*</span></label>
                    <input v-model="street" type="text" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" placeholder="No. 123, Jalan ABC, Taman XYZ" required />
                    <p v-if="errors.street" class="mt-1 text-xs text-red-600">{{ errors.street }}</p>
                  </div>

                  <div class="grid gap-4 sm:grid-cols-2">
                    <div>
                      <label class="mb-1.5 block text-sm font-medium text-slate-700">Postal Code<span class="text-rose-500" aria-hidden="true">*</span></label>
                      <input v-model="postalCode" type="text" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" placeholder="50000" required />
                      <p v-if="errors.postalCode" class="mt-1 text-xs text-red-600">{{ errors.postalCode }}</p>
                    </div>
                    <div>
                      <label class="mb-1.5 block text-sm font-medium text-slate-700">City<span class="text-rose-500" aria-hidden="true">*</span></label>
                      <input v-model="city" type="text" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" placeholder="Kuala Lumpur" required />
                      <p v-if="errors.city" class="mt-1 text-xs text-red-600">{{ errors.city }}</p>
                    </div>
                  </div>

                  <div class="grid gap-4 sm:grid-cols-2">
                    <div>
                      <label class="mb-1.5 block text-sm font-medium text-slate-700">State<span class="text-rose-500" aria-hidden="true">*</span></label>
                      <input v-model="state" type="text" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" placeholder="Selangor" required />
                      <p v-if="errors.state" class="mt-1 text-xs text-red-600">{{ errors.state }}</p>
                    </div>
                    <div>
                      <label class="mb-1.5 block text-sm font-medium text-slate-700">Country<span class="text-rose-500" aria-hidden="true">*</span></label>
                      <input v-model="country" type="text" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" placeholder="Malaysia" required />
                      <p v-if="errors.country" class="mt-1 text-xs text-red-600">{{ errors.country }}</p>
                    </div>
                  </div>
                </div>

                <!-- KOLUM KANAN: Kemas Kini Emel / Kata Laluan -->
                <div class="space-y-4">
                  <h2 class="text-lg font-bold text-slate-900">Update Email / Password</h2>

                  <div>
                    <label class="mb-1.5 block text-sm font-medium text-slate-700">Email<span class="text-rose-500" aria-hidden="true">*</span></label>
                    <input v-model="email" type="email" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" required />
                    <p v-if="errors.email" class="mt-1 text-xs text-red-600">{{ errors.email }}</p>
                  </div>

                  <div>
                    <label class="mb-1.5 block text-sm font-medium text-slate-700">Change Password</label>
                    <input v-model="password" type="password" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" placeholder="Enter new password if you want to change it" />
                    <p v-if="errors.password" class="mt-1 text-xs text-red-600">{{ errors.password }}</p>
                  </div>

                  <div>
                    <label class="mb-1.5 block text-sm font-medium text-slate-700">Confirm New Password</label>
                    <input v-model="confirmPassword" type="password" class="w-full rounded-xl border border-slate-200 px-4 py-3 text-slate-900 outline-none transition focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500" placeholder="Confirm new password" />
                    <p v-if="errors.confirmPassword" class="mt-1 text-xs text-red-600">{{ errors.confirmPassword }}</p>
                  </div>
                </div>

              </div>

              <!-- Bahagian Bawah: Butang Simpan Perubahan -->
              <div class="flex justify-center pt-6 border-t border-slate-100">
                <button
                  class="inline-flex w-full max-w-md items-center justify-center gap-2 rounded-xl bg-emerald-600 px-6 py-3.5 font-semibold text-white shadow-sm transition hover:bg-emerald-700 active:scale-[0.98] disabled:opacity-50 disabled:cursor-not-allowed"
                  type="submit" 
                  :disabled="isLoading || !meta.dirty"
                >
                  <Icon v-if="isLoading" icon="mdi:loading" class="size-5 animate-spin" />
                  <Icon v-else icon="ic:sharp-save" class="size-5" />
                  {{ isLoading ? 'Saving...' : 'Save Changes' }}
                </button>
              </div>

            </form>
          </div>
        </ContentBlock>
      </AppContent>
    </AppContentWrapper>
  </main>
</template>