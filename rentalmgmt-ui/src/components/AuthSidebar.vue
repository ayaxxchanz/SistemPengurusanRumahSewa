<script setup>
import SiteTitle from '@/components/SiteTitle.vue'

</script>
<template>
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
          Urus pelaburan hartanah dan hak kediaman anda dengan rekod rasmi. Kunci bukti pembayaran, simpan log aduan kerosakan, dan urus dokumen kontrak secara sah di satu tempat.
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
</template>