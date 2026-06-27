<script setup>
import { computed, ref } from 'vue'
import { Icon } from '@iconify/vue'
import { useAuth } from "@/stores/authStore"
import ContentBlock from '@/components/layout/ContentBlock.vue'
import { dashboardData } from '@/data/dashboard-chart.js'
import BaseLayout from '@/components/layout/BaseLayout.vue'

const { roleLabel, profile } = useAuth()

// Use imported data
const collectionData = dashboardData.collectionData
const stats = dashboardData.stats
const recentActivities = dashboardData.recentActivities
const payments = dashboardData.payments
const ranking = dashboardData.ranking
const timeframeOptions = dashboardData.timeframeOptions

const activeCategory = ref('Rent')
const activeTimeframe = ref('Yearly')

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
    <BaseLayout>
        <template #heroSection>
            <h1 class="text-3xl font-bold text-white sm:text-4xl">{{ roleLabel || 'User'}} Dashboard</h1>
            <p class="mt-2 max-w-xl text-base text-white">Welcome back, {{ profile?.displayName || profile?.fullName || 'User' }}. Here is a summary of your rental statistics.</p>
        </template>

        <template #appContent>
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
                    <table class="w-full min-w-190 text-left">
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
                    <table class="w-full min-w-190 text-left">
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
        </template>
    </BaseLayout>
</template>