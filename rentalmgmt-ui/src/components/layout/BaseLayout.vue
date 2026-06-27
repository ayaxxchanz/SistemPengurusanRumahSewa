<script setup>
import { useRoute } from 'vue-router'

import Sidebar from '@/components/layout/Sidebar.vue'
import AppContentWrapper from '@/components/layout/AppContentWrapper.vue'
import TopUtilityBar from '@/components/layout/TopUtilityBar.vue'
import HeroSection from '@/components/layout/HeroSection.vue'
import AppFooter from '@/components/layout/AppFooter.vue'

const route = useRoute()
</script>

<template>
    <main class="flex min-h-screen items-start bg-slate-50 font-primary antialiased">
        <Sidebar />
        <AppContentWrapper>
            <TopUtilityBar class="fixed top-0 left-0 right-0 z-50 pl-18 md:pl-0" />
            <HeroSection>
                <slot name="heroSection"></slot>
            </HeroSection>

            <!-- App Content -->
            <div class="flex-1 px-4 pb-10 pt-8 sm:px-6 lg:px-10">
                <slot name="appContent"></slot>
            </div>

            <AppFooter v-if="route.meta.requiresAuth" />
        </AppContentWrapper>
    </main>
</template>