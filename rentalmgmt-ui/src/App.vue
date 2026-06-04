<script setup>
import { RouterView } from 'vue-router'
import useAutoLogout from '@/composables/useAutoLogout';

useAutoLogout();
</script>

<template>
  <RouterView />
</template>
