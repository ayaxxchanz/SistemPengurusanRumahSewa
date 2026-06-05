<script setup>
import AppContent from '@/components/layout/AppContent.vue'
import AppContentWrapper from '@/components/layout/AppContentWrapper.vue'
import ContentBlock from '@/components/layout/ContentBlock.vue'
import HeroBannerSection from '@/components/layout/HeroBannerSection.vue'
import Sidebar from '@/components/layout/Sidebar.vue'
import TopUtilityBar from '@/components/layout/TopUtilityBar.vue'
</script>
<template>
    <main class="flex min-h-screen items-start bg-slate-50 font-primary antialiased">
        <Sidebar />

        <AppContentWrapper>
            <TopUtilityBar />

            <HeroBannerSection>
                <div>
                    <h1 class="text-3xl font-bold text-white sm:text-4xl">Leases</h1>
                    <p class="mt-2 max-w-xl text-base text-white">Manage your rental leases, view details, and perform actions.</p>
                </div>
            </HeroBannerSection>

            <AppContent>
                <ContentBlock class="mb-8">
                    <h2 class="text-2xl font-semibold text-gray-800">Leases</h2>
                    <p class="mt-2 text-gray-600">Manage your rental leases, view details, and perform actions.</p>
                </ContentBlock>
            </AppContent>
        </AppContentWrapper>
    </main>
</template>
