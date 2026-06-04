<script setup>
import { useRoute } from 'vue-router'
import AppFooter from '@/components/layout/AppFooter.vue'

const route = useRoute()
</script>

<template>
  <div class="flex min-h-screen min-w-0 flex-1 flex-col pl-18 font-primary md:pl-0">
    <slot />
    <AppFooter v-if="route.meta.requiresAuth" />
  </div>
</template>
