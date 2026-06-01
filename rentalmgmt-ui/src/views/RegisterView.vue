<script setup>
import { Icon } from '@iconify/vue'
import { ref } from 'vue'
import { RouterLink } from 'vue-router'
import AuthSidebar from '@/components/AuthSidebar.vue'

import { useForm } from 'vee-validate'
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import { useRouter } from "vue-router";
import { useRegisterStore } from "@/stores/authStore"

const registerStore = useRegisterStore()
const router = useRouter()
const showPassword = ref(false)
const showConfirmPassword = ref(false)

const validationSchema = toTypedSchema(
  z.object({
    fullName: z.string().min(1, 'Nama penuh wajib diisi').default(''),
    role: z.enum(['landlord', 'tenant']).default('landlord'),
    email: z.string().min(1, 'Emel wajib diisi').email('Format emel tidak sah').default(''),
    mobileNumber: z.string()
      .min(1, 'Nombor telefon wajib diisi')
      .regex(/^(\+?6?01)[0-46-9]-*[0-9]{7,8}$/, 'Format nombor telefon Malaysia tidak sah'),
    password: z.string().min(8, 'Kata laluan mestilah sekurang-kurangnya 8 aksara'),
    confirmPassword: z.string().min(1, 'Sila sahkan kata laluan anda'),
    agree: z.boolean().refine((val) => val === true, {
      message: 'Anda mesti bersetuju dengan pengesahan maklumat'
    })
    }).refine((data) => data.password === data.confirmPassword, {
    message: "Kata laluan dan ulang kata laluan tidak sepadan",
    path: ["confirmPassword"], 
  })
)

const { errors, defineField, handleSubmit } = useForm({
  validationSchema,
  initialValues: {
    fullName: "",
    role: "landlord",
    email: "",
    mobileNumber: "", 
    password: "",
    confirmPassword: "",
    agree: false
  }
})

const [fullName] = defineField('fullName')
const [role] = defineField('role')
const [email] = defineField('email')
const [mobileNumber] = defineField('mobileNumber')
const [password] = defineField('password')
const [confirmPassword] = defineField('confirmPassword')
const [agree] = defineField('agree')

const onRegisterSubmit = handleSubmit(async (values) => {
  const registrationCompleted = await registerStore.submitRegistration(values)
  
  if (registrationCompleted) {
    alert("Pendaftaran berjaya!")
    router.push('/login') // Changes route to your login view upon success
  }
})
</script>

<template>
  <main class="grid min-h-screen grid-cols-1 bg-white antialiased lg:grid-cols-12">
    <AuthSidebar />
    
    <section class="flex flex-col justify-center bg-white px-6 py-10 sm:px-16 md:px-20 lg:col-span-5 lg:px-12 xl:px-20" aria-labelledby="register-title">
      <div class="mb-8 flex items-center gap-2 lg:hidden">
        <span class="grid size-9 place-items-center rounded-md bg-emerald-600 text-white">
          <Icon icon="mdi:home-city" class="size-6" aria-hidden="true" />
        </span>
        <span class="text-xl font-bold tracking-tight text-slate-900">Townhouse SeriKu</span>
      </div>

      <div class="mb-7">
        <h2 id="register-title" class="text-3xl font-bold tracking-tight text-slate-900">Daftar Akaun</h2>
        <p class="mt-2 text-sm text-slate-500">Medan yang ditandakan dengan <span class="text-rose-500">*</span> adalah wajib diisi.</p>
      </div>

      <form class="space-y-4" @submit.prevent="onRegisterSubmit">
        <div class="grid gap-4 sm:grid-cols-2">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Nama Penuh<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <span class="text-slate-400 mr-2 flex items-center justify-center">
                <Icon icon="mdi:account-outline" class="size-5" aria-hidden="true" />
              </span>
              <input 
                v-model="fullName" 
                class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400" 
                type="text" 
                placeholder="Jane Doe" 
              />
            </div>
            <span v-if="errors.fullName" class="mt-1 block text-xs font-medium text-rose-500">
              {{ errors.fullName }}
            </span>
          </div>

          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Peranan Utama<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <span class="text-slate-400 mr-2 flex items-center justify-center">
                <Icon icon="mdi:badge-account-outline" class="size-5" aria-hidden="true" />
              </span>
              <select v-model="role" class="w-full text-slate-900 outline-none bg-transparent pr-4">
                <option value="landlord">Pemilik / Pengurus</option>
                <option value="tenant">Penyewa</option>
              </select>
            </div>
          </div>
        </div>

        <div>
          <label class="mb-1.5 block text-sm font-medium text-slate-700">Emel<span class="text-rose-500" aria-hidden="true">*</span></label>
          <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
            <span class="text-slate-400 mr-2 flex items-center justify-center">
              <Icon icon="mdi:email-outline" class="size-5" aria-hidden="true" />
            </span>
            <input 
              v-model="email" 
              class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400" 
              type="email" 
              placeholder="name@example.com" 
            />
          </div>
          <span v-if="errors.email" class="mt-1 block text-xs font-medium text-rose-500">
            {{ errors.email }}
          </span>
        </div>

        <div>
          <label class="mb-1.5 block text-sm font-medium text-slate-700">Nombor Telefon<span class="text-rose-500" aria-hidden="true">*</span></label>
          <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
            <span class="text-slate-400 mr-2 flex items-center justify-center">
              <Icon icon="mdi:phone-outline" class="size-5" aria-hidden="true" />
            </span>
            <input 
              v-model="mobileNumber" 
              class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400" 
              type="tel" 
              placeholder="+60 12-345 6789" 
            />
          </div>
          <span v-if="errors.mobileNumber" class="mt-1 block text-xs font-medium text-rose-500">
            {{ errors.mobileNumber }}
          </span>
        </div>

        <div class="grid gap-4 sm:grid-cols-2">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Kata Laluan<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <span class="text-slate-400 mr-2 flex items-center justify-center">
                <Icon icon="mdi:lock-outline" class="size-5" aria-hidden="true" />
              </span>
              <input 
                v-model="password" 
                class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400 pr-2" 
                :type="showPassword ? 'text' : 'password'" 
                placeholder="••••••••" 
              />
              <button class="text-slate-400 hover:text-slate-600 flex items-center justify-center ml-auto" type="button" @click="showPassword = !showPassword">
                <Icon :icon="showPassword ? 'mdi:eye-off-outline' : 'mdi:eye-outline'" class="size-5" aria-hidden="true" />
              </button>
            </div>
            <span v-if="errors.password" class="mt-1 block text-xs font-medium text-rose-500">
              {{ errors.password }}
            </span>
          </div>

          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Ulang Kata Laluan<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <span class="text-slate-400 mr-2 flex items-center justify-center">
                <Icon icon="mdi:lock-check-outline" class="size-5" aria-hidden="true" />
              </span>
              <input 
                v-model="confirmPassword" 
                class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400 pr-2" 
                :type="showConfirmPassword ? 'text' : 'password'" 
                placeholder="••••••••" 
              />
              <button class="text-slate-400 hover:text-slate-600 flex items-center justify-center ml-auto" type="button" @click="showConfirmPassword = !showConfirmPassword">
                <Icon :icon="showConfirmPassword ? 'mdi:eye-off-outline' : 'mdi:eye-outline'" class="size-5" aria-hidden="true" />
              </button>
            </div>
            <span v-if="errors.confirmPassword" class="mt-1 block text-xs font-medium text-rose-500">
              {{ errors.confirmPassword }}
            </span>
          </div>
        </div>

        <div class="space-y-1">
          <label class="flex items-start gap-2 text-sm text-slate-600">
            <input v-model="agree" class="mt-0.5 size-4 cursor-pointer rounded-md border-slate-300 accent-emerald-600 focus:ring-emerald-500" type="checkbox" />
            <span>Saya bersetuju maklumat yang diberikan adalah benar.<span class="text-rose-500" aria-hidden="true">*</span></span>
          </label>
          <span v-if="errors.agree" class="block text-xs font-medium text-rose-500">
            {{ errors.agree }}
          </span>
        </div>

        <button 
          :disabled="registerStore.isLoading"
          class="w-full rounded-xl bg-emerald-600 px-4 py-3.5 font-semibold text-white shadow-sm transition hover:bg-emerald-700 active:scale-[0.99] disabled:opacity-50 disabled:cursor-not-allowed" 
          type="submit"
        >
          <span v-if="registerStore.isLoading">Sila tunggu...</span>
          <span v-else>Daftar Akaun</span>
        </button>
      </form>

      <p class="mt-7 text-center text-sm text-slate-500">
        Sudah mempunyai akses?
        <RouterLink class="font-medium text-emerald-600 transition hover:text-emerald-700 hover:underline" to="/login">Log masuk di sini</RouterLink>
      </p>
    </section>
  </main>
</template>4