export const bgImages = {
    dashboard:
        'https://images.unsplash.com/vector-1738237558950-a8f9e4fe946e?q=80&w=1074&auto=format&fit=crop',
    properties:
        'https://images.unsplash.com/vector-1759217893623-2ee13c1efbe4?q=80&w=1170&auto=format&fit=crop',
    leases:
        'https://images.unsplash.com/vector-1774785393061-d098e97362d1?q=80&w=2126&auto=format&fit=crop',
    profile:
        'https://images.unsplash.com/vector-1774785392893-f2362ef7b47a?q=80&w=1037&auto=format&fit=crop',
}

export const fallbackBgImage = bgImages.dashboard

export function getBgImage(routeName) {
    return bgImages[routeName] || fallbackBgImage
}

export function createHeroBgStyle(imageUrl) {
    return {
        backgroundImage: `linear-gradient(135deg, rgba(15, 23, 42, 0.88) 0%, rgba(30, 27, 75, 0.72) 100%), url("${imageUrl}")`,
    }
}
