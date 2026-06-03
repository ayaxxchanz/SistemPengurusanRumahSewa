<script setup>
import { Icon } from '@iconify/vue'
import { ref, watch } from 'vue'
import { RouterLink, useRouter } from 'vue-router'
import AuthSidebar from '@/components/AuthSidebar.vue'

import { useForm } from 'vee-validate'
import { toTypedSchema } from '@vee-validate/zod'
import { z } from 'zod'
import { useRegisterStore } from "@/stores/authStore"

const registerStore = useRegisterStore()
const router = useRouter()
const showPassword = ref(false)
const showConfirmPassword = ref(false)

const validationSchema = toTypedSchema(
  z.object({
    fullName: z.string().min(1, 'Full name is required').default(''),
    displayName: z.string().min(1, 'Display name is required').default(''),
    role: z.enum(['landlord', 'tenant']).default('landlord'),
    email: z.string().min(1, 'Email is required').email('Invalid email format').default(''),
    mobileNumber: z.string()
      .min(1, 'Mobile number is required')
      .regex(/^(\+?6?01)[0-46-9]-*[0-9]{7,8}$/, 'Invalid Malaysian phone number format'),
    emergencyContact: z.string()
      .min(1, 'Emergency contact is required')
      .regex(/^(\+?6?01)[0-46-9]-*[0-9]{7,8}$/, 'Invalid Malaysian phone number format'),
    street: z.string().min(1, 'Street address is required').default(''),
    postalCode: z.string().min(1, 'Postal code is required').default(''),
    city: z.string().min(1, 'City is required').default(''),
    state: z.string().min(1, 'State is required').default(''),
    country: z.string().min(1, 'Country is required').default(''),
    password: z.string().min(8, 'Password must be at least 8 characters'),
    confirmPassword: z.string().min(1, 'Please confirm your password'),
    agree: z.boolean().refine((val) => val === true, {
      message: 'You must agree to the information verification'
    })
  }).refine((data) => data.password === data.confirmPassword, {
    message: "Password and confirm password do not match",
    path: ["confirmPassword"], 
  })
)

const { errors, defineField, handleSubmit, setFieldError } = useForm({
  validationSchema,
  initialValues: {
    fullName: "",
    displayName: "",
    role: "landlord",
    email: "",
    mobileNumber: "", 
    emergencyContact: "",
    street: "",
    postalCode: "",
    city: "",
    state: "",
    country: "",
    password: "",
    confirmPassword: "",
    agree: false
  }
})

const [fullName] = defineField('fullName')
const [displayName] = defineField('displayName')
const [role] = defineField('role')
const [email] = defineField('email')
const [mobileNumber] = defineField('mobileNumber')
const [emergencyContact] = defineField('emergencyContact')
const [street] = defineField('street')
const [postalCode] = defineField('postalCode')
const [city] = defineField('city')
const [state] = defineField('state')
const [country] = defineField('country')
const [password] = defineField('password')
const [confirmPassword] = defineField('confirmPassword')
const [agree] = defineField('agree')

// Watch for server errors and map to form fields
watch(() => registerStore.fieldErrors, (fieldErrors) => {
  if (fieldErrors) {
    Object.entries(fieldErrors).forEach(([field, message]) => {
      setFieldError(field, message)
    })
  }
})

const onRegisterSubmit = handleSubmit(async (values) => {
  const registrationCompleted = await registerStore.submitRegistration(values)
  
  if (registrationCompleted) {
    // Show success message before redirect
    alert("Pendaftaran berjaya! Sila log masuk.")
    router.push('/login')
  }
})

// Clear errors when component unmounts
import { onUnmounted } from 'vue'
onUnmounted(() => {
  registerStore.resetState()
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
        <h2 id="register-title" class="text-3xl font-bold tracking-tight text-slate-900">Sign Up</h2>
        <p class="mt-2 text-sm text-slate-500">Fields marked with <span class="text-rose-500">*</span> are required.</p>
      </div>

      <!-- Server Error Alert -->
      <div v-if="registerStore.serverError" class="mb-6 rounded-xl bg-red-50 p-4 border border-red-200" role="alert">
        <div class="flex items-start gap-3">
          <div class="shrink-0 mt-0.5">
            <Icon icon="mdi:alert-circle" class="size-5 text-red-600" />
          </div>

          <div class="flex-1">
            <h3 class="text-sm font-semibold text-red-800">
              Registration Failed
            </h3>
            <p class="mt-1 text-sm text-red-700 leading-relaxed">
              {{ registerStore.serverError }}
            </p>

            <div class="mt-3">
              <button type="button" @click="registerStore.clearErrors()"
                class="text-xs font-medium text-red-800 underline hover:text-red-900 transition-colors">
                Dismiss error
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Success Message (optional) -->
      <div v-if="registerStore.isSuccess" class="mb-4 rounded-xl bg-emerald-50 p-4 border border-emerald-200">
        <div class="flex items-center gap-2">
          <Icon icon="mdi:check-circle-outline" class="size-5 text-emerald-600" />
          <p class="text-sm text-emerald-700">Registration successful! Please log in.</p>
        </div>
      </div>

      <form class="space-y-4" @submit.prevent="onRegisterSubmit">
        <!-- Name & Display Name Section -->
        <div class="grid gap-4 sm:grid-cols-2">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Full Name<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <span class="text-slate-400 mr-2 flex items-center justify-center">
                <Icon icon="mdi:account-outline" class="size-5" aria-hidden="true" />
              </span>
              <input 
                v-model="fullName" 
                class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400" 
                type="text" 
                placeholder="Jane Doe" 
                :disabled="registerStore.isLoading"
              />
            </div>
            <span v-if="errors.fullName" class="mt-1 block text-xs font-medium text-rose-500">
              {{ errors.fullName }}
            </span>
          </div>

          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Display Name<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <span class="text-slate-400 mr-2 flex items-center justify-center">
                <Icon icon="mdi:account-card-details-outline" class="size-5" aria-hidden="true" />
              </span>
              <input 
                v-model="displayName" 
                class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400" 
                type="text" 
                placeholder="Ms. Doe" 
                :disabled="registerStore.isLoading"
              />
            </div>
            <span v-if="errors.displayName" class="mt-1 block text-xs font-medium text-rose-500">
              {{ errors.displayName }}
            </span>
          </div>
        </div>

        <!-- Role & Email Section -->
        <div class="grid gap-4 sm:grid-cols-2">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Main Role<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <span class="text-slate-400 mr-2 flex items-center justify-center">
                <Icon icon="mdi:badge-account-outline" class="size-5" aria-hidden="true" />
              </span>
              <select v-model="role" class="w-full text-slate-900 outline-none bg-transparent pr-4" :disabled="registerStore.isLoading">
                <option value="landlord">Landlord / Manager</option>
                <option value="tenant">Tenant</option>
              </select>
            </div>
          </div>

          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Email<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <span class="text-slate-400 mr-2 flex items-center justify-center">
                <Icon icon="mdi:email-outline" class="size-5" aria-hidden="true" />
              </span>
              <input 
                v-model="email" 
                class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400" 
                type="email" 
                placeholder="name@example.com" 
                :disabled="registerStore.isLoading"
              />
            </div>
            <span v-if="errors.email" class="mt-1 block text-xs font-medium text-rose-500">
              {{ errors.email }}
            </span>
          </div>
        </div>

        <!-- Phone Numbers Section -->
        <div class="grid gap-4 sm:grid-cols-2">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Mobile Number<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <span class="text-slate-400 mr-2 flex items-center justify-center">
                <Icon icon="mdi:phone-outline" class="size-5" aria-hidden="true" />
              </span>
              <input 
                v-model="mobileNumber" 
                class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400" 
                type="tel" 
                placeholder="+60 12-345 6789" 
                :disabled="registerStore.isLoading"
              />
            </div>
            <span v-if="errors.mobileNumber" class="mt-1 block text-xs font-medium text-rose-500">
              {{ errors.mobileNumber }}
            </span>
          </div>

          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Emergency Contact<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <span class="text-slate-400 mr-2 flex items-center justify-center">
                <Icon icon="mdi:phone-alert-outline" class="size-5" aria-hidden="true" />
              </span>
              <input 
                v-model="emergencyContact" 
                class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400" 
                type="tel" 
                placeholder="+60 12-987 6543" 
                :disabled="registerStore.isLoading"
              />
            </div>
            <span v-if="errors.emergencyContact" class="mt-1 block text-xs font-medium text-rose-500">
              {{ errors.emergencyContact }}
            </span>
          </div>
        </div>

        <!-- Address Section -->
        <div>
          <label class="mb-1.5 block text-sm font-medium text-slate-700">Street Address<span class="text-rose-500" aria-hidden="true">*</span></label>
          <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
            <span class="text-slate-400 mr-2 flex items-center justify-center">
              <Icon icon="mdi:map-marker-outline" class="size-5" aria-hidden="true" />
            </span>
            <input 
              v-model="street" 
              class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400" 
              type="text" 
              placeholder="123 Main St, Apartment 4B" 
              :disabled="registerStore.isLoading"
            />
          </div>
          <span v-if="errors.street" class="mt-1 block text-xs font-medium text-rose-500">
            {{ errors.street }}
          </span>
        </div>

        <!-- Regional Address Breakdown Section -->
        <div class="grid gap-4 grid-cols-2 sm:grid-cols-4">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Postal Code<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <input 
                v-model="postalCode" 
                class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400" 
                type="text" 
                placeholder="50000" 
                :disabled="registerStore.isLoading"
              />
            </div>
            <span v-if="errors.postalCode" class="mt-1 block text-xs font-medium text-rose-500">
              {{ errors.postalCode }}
            </span>
          </div>

          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">City<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <input 
                v-model="city" 
                class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400" 
                type="text" 
                placeholder="Kuala Lumpur" 
                :disabled="registerStore.isLoading"
              />
            </div>
            <span v-if="errors.city" class="mt-1 block text-xs font-medium text-rose-500">
              {{ errors.city }}
            </span>
          </div>

          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">State<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <input 
                v-model="state" 
                class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400" 
                type="text" 
                placeholder="WPKL" 
                :disabled="registerStore.isLoading"
              />
            </div>
            <span v-if="errors.state" class="mt-1 block text-xs font-medium text-rose-500">
              {{ errors.state }}
            </span>
          </div>

          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Country<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <input 
                v-model="country" 
                class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400" 
                type="text" 
                placeholder="MY" 
                :disabled="registerStore.isLoading"
              />
            </div>
            <span v-if="errors.country" class="mt-1 block text-xs font-medium text-rose-500">
              {{ errors.country }}
            </span>
          </div>
        </div>

        <!-- Password Section -->
        <div class="grid gap-4 sm:grid-cols-2">
          <div>
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Password<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <span class="text-slate-400 mr-2 flex items-center justify-center">
                <Icon icon="mdi:lock-outline" class="size-5" aria-hidden="true" />
              </span>
              <input 
                v-model="password" 
                class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400 pr-2" 
                :type="showPassword ? 'text' : 'password'" 
                placeholder="••••••••" 
                :disabled="registerStore.isLoading"
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
            <label class="mb-1.5 block text-sm font-medium text-slate-700">Confirm Password<span class="text-rose-500" aria-hidden="true">*</span></label>
            <div class="flex items-center w-full rounded-xl border border-slate-200 px-3 py-3 bg-white focus-within:border-emerald-500 focus-within:ring-2 focus-within:ring-emerald-500 transition">
              <span class="text-slate-400 mr-2 flex items-center justify-center">
                <Icon icon="mdi:lock-check-outline" class="size-5" aria-hidden="true" />
              </span>
              <input 
                v-model="confirmPassword" 
                class="w-full text-slate-900 outline-none bg-transparent placeholder:text-slate-400 pr-2" 
                :type="showConfirmPassword ? 'text' : 'password'" 
                placeholder="••••••••" 
                :disabled="registerStore.isLoading"
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

        <!-- Agreement Section -->
        <div class="space-y-1">
          <label class="flex items-start gap-2 text-sm text-slate-600">
            <input v-model="agree" class="mt-0.5 size-4 cursor-pointer rounded-md border-slate-300 accent-emerald-600 focus:ring-emerald-500" type="checkbox" :disabled="registerStore.isLoading" />
            <span>I agree that the information provided is true.<span class="text-rose-500" aria-hidden="true">*</span></span>
          </label>
          <span v-if="errors.agree" class="block text-xs font-medium text-rose-500">
            {{ errors.agree }}
          </span>
        </div>

        <!-- Submit Section -->
        <button 
          :disabled="registerStore.isLoading"
          class="w-full rounded-xl bg-emerald-600 px-4 py-3.5 font-semibold text-white shadow-sm transition hover:bg-emerald-700 active:scale-[0.99] disabled:opacity-50 disabled:cursor-not-allowed" 
          type="submit"
        >
          <span v-if="registerStore.isLoading">Please wait...</span>
          <span v-else>Register Account</span>
        </button>
      </form>

      <p class="mt-7 text-center text-sm text-slate-500">
        Already have an account?
        <RouterLink class="font-medium text-emerald-600 transition hover:text-emerald-700 hover:underline" to="/login">Log in here</RouterLink>
      </p>
    </section>
  </main>
</template>