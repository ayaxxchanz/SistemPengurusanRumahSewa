<script setup>
import BaseLayout from '@/components/layout/BaseLayout.vue'
import ContentBlock from '@/components/layout/ContentBlock.vue'
</script>
<template>
    <BaseLayout>
        <template #heroSection>
            <h1 class="text-3xl font-bold text-white sm:text-4xl">Leases</h1>
            <p class="mt-2 max-w-xl text-base text-white">Manage your rental leases, view details, and perform actions.</p>
        </template>

        <template #appContent>
            <ContentBlock class="mb-8">
                <h2 class="text-2xl font-semibold text-gray-800">Leases</h2>
                <p class="mt-2 text-gray-600">Manage your rental leases, view details, and perform actions.</p>
            </ContentBlock>
        </template>
    </BaseLayout>
</template>
