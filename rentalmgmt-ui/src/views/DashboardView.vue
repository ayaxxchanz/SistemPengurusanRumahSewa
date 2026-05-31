<script setup>
import { computed, ref } from 'vue'
import { Icon } from '@iconify/vue'
import AppContent from '@/components/layout/AppContent.vue'
import AppContentWrapper from '@/components/layout/AppContentWrapper.vue'
import ContentBlock from '@/components/layout/ContentBlock.vue'
import HeroBannerSection from '@/components/layout/HeroBannerSection.vue'
import Sidebar from '@/components/layout/Sidebar.vue'
import TopUtilityBar from '@/components/layout/TopUtilityBar.vue'

const activeCategory = ref('Rent')
const activeTimeframe = ref('Yearly')

const collectionData = {
    Rent: {
        Yearly: [75000, 82000, 90000, 95000, 105000],
        Monthly: [6200, 7100, 6800, 7900, 8450],
        Weekly: [1500, 1800, 2100, 1400, 2650],
        labels: {
            Yearly: ['2022', '2023', '2024', '2025', '2026'],
            Monthly: ['Jan', 'Feb', 'Mar', 'Apr', 'Mei'],
            Weekly: ['Week 1', 'Week 2', 'Week 3', 'Week 4', 'Week 5'],
        },
    },
    Utilities: {
        Yearly: [12000, 14000, 13500, 16000, 18500],
        Monthly: [1100, 1300, 950, 1400, 1600],
        Weekly: [300, 450, 200, 350, 300],
        labels: {
            Yearly: ['2022', '2023', '2024', '2025', '2026'],
            Monthly: ['Jan', 'Feb', 'Mar', 'Apr', 'Mei'],
            Weekly: ['Week 1', 'Week 2', 'Week 3', 'Week 4', 'Week 5'],
        },
    },
}

const stats = [
    { label: 'Jumlah Hartanah', value: '12 Unit', color: 'text-sky-600', border: 'border-t-sky-500', icon: 'cil:building' },
    { label: 'Penyewa Aktif', value: '9 Orang', color: 'text-pink-500', border: 'border-t-pink-400', icon: 'ri:group-line' },
    { label: 'Kutipan Bulan Ini', value: 'RM 8,450', color: 'text-teal-600', border: 'border-t-teal-500', icon: 'streamline-ultimate:begging-hand-coin-2' },
    { label: 'Invois Tertunggak', value: '2 Kes', color: 'text-orange-600', border: 'border-t-orange-500', icon: 'hugeicons:clock-alert' },
]

const recentActivities = [
    { date: 'Selasa, 25 Mei 2026 09:10 PM', module: 'Pembayaran', info: '25 Mei 2026', amount: 'RM 1,200', status: 'Pemakluman', icon: 'fa7-solid:circle-info', statusClass: 'bg-emerald-100 text-emerald-700' },
    { date: 'Ahad, 05 Mei 2026 11:10 AM', module: 'Permintaan penyelenggaraan', info: '02 Mei 2026', amount: 'RM 850', status: 'Tindakan', icon: 'fa7-solid:triangle-exclamation', statusClass: 'bg-red-50 text-red-700' },
    { date: 'Jumaat, 01 Mei 2026 02:55 PM', module: 'Rumah Teres Seksyen 7', info: '01 Mei 2026', amount: 'RM 1,500', status: 'Peringatan', icon: 'mdi:alarm', statusClass: 'bg-amber-100 text-amber-700' },
]

const payments = [
    { tenant: 'Ahmad Ridzuan', unit: 'Kondominium Melati - A-10-3', date: '25 Mei 2026', amount: 'RM 1,200', status: 'Berjaya', icon: 'mdi:check-circle-outline', statusClass: 'bg-emerald-100 text-emerald-700' },
    { tenant: 'Siti Nurhaliza', unit: 'Apartment Mawar - B-05-11', date: '02 Mei 2026', amount: 'RM 850', status: 'Berjaya', icon: 'mdi:check-circle-outline', statusClass: 'bg-emerald-100 text-emerald-700' },
    { tenant: 'Khairul Anuar', unit: 'Rumah Teres Seksyen 7', date: '01 Mei 2026', amount: 'RM 1,500', status: 'Tertunggak', icon: 'mdi:close-circle-outline', statusClass: 'bg-amber-100 text-amber-700' },
]

const ranking = [
    ['Kondominium Melati (A-10-3)', 'RM 14,400'],
    ['Rumah Teres Seksyen 7', 'RM 12,000'],
    ['Apartment Mawar (B-05-11)', 'RM 10,200'],
    ['Kondominium Melati (B-12-1)', 'RM 9,600'],
    ['Apartment Mawar (A-02-05)', 'RM 8,400'],
    ['Kondominium Melati (C-03-2)', 'RM 7,200'],
    ['Rumah Teres Seksyen 4', 'RM 6,000'],
]

const timeframeOptions = [
    { label: 'Mingguan', value: 'Weekly' },
    { label: 'Bulanan', value: 'Monthly' },
    { label: 'Tahunan', value: 'Yearly' },
]

const chartRows = computed(() => {
    const values = collectionData[activeCategory.value][activeTimeframe.value]
    const labels = collectionData[activeCategory.value].labels[activeTimeframe.value]
    const max = Math.max(...values)

    return labels.map((label, index) => {
        const full = values[index]
        const partial = Math.round(full * 0.4)
        const overdue = Math.round(full * 0.15)

        return {
            label,
            full,
            partial,
            overdue,
            fullHeight: `${Math.max((full / max) * 100, 8)}%`,
            partialHeight: `${Math.max((partial / max) * 100, 8)}%`,
            overdueHeight: `${Math.max((overdue / max) * 100, 8)}%`,
        }
    })
})

</script>

<template>
    <main class="flex min-h-screen items-start bg-slate-50 font-primary antialiased">
        <Sidebar />

        <AppContentWrapper>
            <TopUtilityBar />

            <HeroBannerSection>
                <div>
                    <h1 class="text-3xl font-bold text-white sm:text-4xl">Dashboard Pemilik / Pengurus</h1>
                    <p class="mt-2 max-w-xl text-base text-white">Selamat kembali, Aliya. Berikut ringkasan statistik
                        sewaan anda.</p>
                </div>
            </HeroBannerSection>

            <AppContent>
                <div class="relative -mt-28 mb-8 grid gap-4 sm:grid-cols-2 xl:grid-cols-4">
                    <article v-for="item in stats" :key="item.label"
                        class="flex min-h-36 flex-col justify-between rounded-2xl border border-slate-200 border-t-4 bg-white p-5 shadow-sm"
                        :class="item.border">
                        <div class="flex items-start justify-between gap-4">
                            <div>
                                <p class="text-xs font-black uppercase tracking-wide" :class="item.color">{{ item.label
                                }}</p>
                                <h2 class="mt-2 text-3xl font-bold tracking-tight text-slate-900">{{ item.value }}</h2>
                            </div>
                            <span :class="[item.color, 'opacity-20']">
                                <Icon :icon="item.icon" class="size-28" aria-hidden="true" />
                            </span>
                        </div>
                    </article>
                </div>

                <ContentBlock class="mb-8">
                    <h3 class="mb-5 text-base font-bold text-slate-900">Aktiviti Terkini</h3>
                    <div class="overflow-x-auto">
                        <table class="w-full min-w-[760px] text-left">
                            <thead>
                                <tr class="border-b border-slate-200 text-xs font-semibold uppercase text-slate-500">
                                    <th class="pb-3">Tarikh</th>
                                    <th class="pb-3">Modul</th>
                                    <th class="pb-3">Maklumat</th>
                                    <th class="pb-3">Jumlah</th>
                                    <th class="pb-3">Status</th>
                                </tr>
                            </thead>
                            <tbody class="divide-y divide-slate-100">
                                <tr v-for="row in recentActivities" :key="`${row.date}-${row.module}`"
                                    class="text-sm text-slate-700">
                                    <td class="py-4">{{ row.date }}</td>
                                    <td class="py-4">{{ row.module }}</td>
                                    <td class="py-4">{{ row.info }}</td>
                                    <td class="py-4 font-bold text-slate-900">{{ row.amount }}</td>
                                    <td class="py-4">
                                        <span
                                            class="inline-flex items-center gap-1.5 rounded-md px-2 py-1 text-xs font-semibold"
                                            :class="row.statusClass">
                                            <Icon :icon="row.icon" class="size-3.5" aria-hidden="true" />
                                            {{ row.status }}
                                        </span>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </ContentBlock>

                <div class="mb-8 grid gap-6 xl:grid-cols-[2.3fr_1fr]">
                    <ContentBlock class="shadow-sm">
                        <div class="mb-6 flex flex-wrap items-center gap-4 border-b border-slate-100 pb-4">
                            <h3 class="text-base font-bold text-slate-800">Trend Kutipan Sewa</h3>
                            <div class="flex">
                                <button class="px-4 py-2 text-sm font-semibold transition"
                                    :class="activeCategory === 'Rent' ? 'text-blue-600' : 'text-slate-500 hover:text-blue-600'"
                                    type="button" @click="activeCategory = 'Rent'">Sewa</button>
                                <button class="px-4 py-2 text-sm font-semibold transition"
                                    :class="activeCategory === 'Utilities' ? 'text-blue-600' : 'text-slate-500 hover:text-blue-600'"
                                    type="button" @click="activeCategory = 'Utilities'">Utiliti</button>
                            </div>
                            <div class="flex flex-wrap items-center gap-3 xl:ml-auto">
                                <button v-for="option in timeframeOptions" :key="option.value"
                                    class="text-sm font-medium transition"
                                    :class="activeTimeframe === option.value ? 'text-blue-600' : 'text-slate-500 hover:text-blue-600'"
                                    type="button" @click="activeTimeframe = option.value">
                                    {{ option.label }}
                                </button>
                                <div
                                    class="inline-flex items-center gap-2 rounded-md border border-slate-300 bg-white px-3 py-1.5 text-sm text-slate-600">
                                    <span>2022-01-01 ~ 2026-12-31</span>
                                    <Icon icon="mdi:calendar-month-outline" class="size-4 text-slate-400"
                                        aria-hidden="true" />
                                </div>
                            </div>
                        </div>

                        <div class="h-80">
                            <div
                                class="flex h-full items-end justify-around gap-3 border-b border-l border-slate-100 px-2 pb-8">
                                <div v-for="row in chartRows" :key="row.label"
                                    class="flex h-full flex-1 flex-col justify-end">
                                    <div class="flex h-[86%] items-end justify-center gap-1.5">
                                        <div class="w-3 rounded-t bg-blue-500 sm:w-4"
                                            :style="{ height: row.fullHeight }" :title="`Full Collection: ${row.full}`">
                                        </div>
                                        <div class="w-3 rounded-t bg-blue-300 sm:w-4"
                                            :style="{ height: row.partialHeight }"
                                            :title="`Partial Collection: ${row.partial}`"></div>
                                        <div class="w-3 rounded-t bg-blue-100 sm:w-4"
                                            :style="{ height: row.overdueHeight }" :title="`Overdue: ${row.overdue}`">
                                        </div>
                                    </div>
                                    <span class="mt-3 text-center text-xs font-semibold text-slate-600">{{ row.label
                                    }}</span>
                                </div>
                            </div>
                        </div>

                        <div class="mt-4 flex flex-wrap gap-4 text-xs font-semibold text-slate-500">
                            <span class="inline-flex items-center gap-2"><span
                                    class="size-3 rounded bg-blue-500"></span>Full Collection</span>
                            <span class="inline-flex items-center gap-2"><span
                                    class="size-3 rounded bg-blue-300"></span>Partial Collection</span>
                            <span class="inline-flex items-center gap-2"><span
                                    class="size-3 rounded bg-blue-100"></span>Overdue</span>
                        </div>
                    </ContentBlock>

                    <ContentBlock class="shadow-sm">
                        <h3 class="mb-6 text-base font-bold text-slate-800">Ranking Kutipan Unit</h3>
                        <ul class="flex flex-col gap-3">
                            <li v-for="([unit, amount], index) in ranking" :key="unit"
                                class="flex items-center justify-between gap-3">
                                <div class="flex min-w-0 items-center gap-3">
                                    <span class="grid size-6 shrink-0 place-items-center rounded-full text-xs font-bold"
                                        :class="index < 3 ? 'bg-slate-800 text-white' : 'bg-slate-100 text-slate-500'">{{
                                            index + 1 }}</span>
                                    <span class="truncate text-sm font-medium text-slate-600">{{ unit }}</span>
                                </div>
                                <span class="shrink-0 text-sm font-semibold text-slate-500">{{ amount }}</span>
                            </li>
                        </ul>
                    </ContentBlock>
                </div>

                <ContentBlock>
                    <h3 class="mb-5 text-base font-bold text-slate-900">Aktiviti Pembayaran Terkini</h3>
                    <div class="overflow-x-auto">
                        <table class="w-full min-w-[760px] text-left">
                            <thead>
                                <tr class="border-b border-slate-200 text-xs font-semibold uppercase text-slate-500">
                                    <th class="pb-3">Nama Penyewa</th>
                                    <th class="pb-3">Unit Hartanah</th>
                                    <th class="pb-3">Tarikh</th>
                                    <th class="pb-3">Jumlah</th>
                                    <th class="pb-3">Status</th>
                                </tr>
                            </thead>
                            <tbody class="divide-y divide-slate-100">
                                <tr v-for="row in payments" :key="`${row.tenant}-${row.unit}`"
                                    class="text-sm text-slate-700">
                                    <td class="py-4">{{ row.tenant }}</td>
                                    <td class="py-4">{{ row.unit }}</td>
                                    <td class="py-4">{{ row.date }}</td>
                                    <td class="py-4 font-bold text-slate-900">{{ row.amount }}</td>
                                    <td class="py-4">
                                        <span
                                            class="inline-flex items-center gap-1.5 rounded-md px-2 py-1 text-xs font-semibold"
                                            :class="row.statusClass">
                                            {{ row.status }}
                                            <Icon :icon="row.icon" class="size-3.5" aria-hidden="true" />
                                        </span>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </ContentBlock>
            </AppContent>
        </AppContentWrapper>
    </main>
</template>
