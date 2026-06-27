<script setup>
import { computed } from "vue";
import { useRoute } from "vue-router";
import { createHeroBgStyle, getBgImage } from "@/constants/bgImages";

const route = useRoute();

const heroStyle = computed(() => createHeroBgStyle(getBgImage(route.name)));
</script>

<template>
    <section
        class="bg-cover bg-center px-8 py-42 pt-12 sm:px-6 lg:px-10"
        :style="heroStyle">
            <slot />
    </section>
</template>
