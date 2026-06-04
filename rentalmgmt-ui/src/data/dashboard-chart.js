export const dashboardData = {
    collectionData: {
        Rent: {
            Yearly: [75000, 82000, 90000, 95000, 105000],
            Monthly: [6200, 7100, 6800, 7900, 8450],
            Weekly: [1500, 1800, 2100, 1400, 2650],
            labels: {
                Yearly: ['2022', '2023', '2024', '2025', '2026'],
                Monthly: ['Jan', 'Feb', 'Mar', 'Apr', 'Mei'],
                Weekly: ['Week 1', 'Week 2', 'Week 3', 'Week 4', 'Week 5']
            }
        },
        Utilities: {
            Yearly: [12000, 14000, 13500, 16000, 18500],
            Monthly: [1100, 1300, 950, 1400, 1600],
            Weekly: [300, 450, 200, 350, 300],
            labels: {
                Yearly: ['2022', '2023', '2024', '2025', '2026'],
                Monthly: ['Jan', 'Feb', 'Mar', 'Apr', 'Mei'],
                Weekly: ['Week 1', 'Week 2', 'Week 3', 'Week 4', 'Week 5']
            }
        }
    },
    stats: [
        { label: 'Jumlah Hartanah', value: '12 Unit', color: 'text-sky-600', border: 'border-t-sky-500', icon: 'cil:building' },
        { label: 'Penyewa Aktif', value: '9 Orang', color: 'text-pink-500', border: 'border-t-pink-400', icon: 'ri:group-line' },
        { label: 'Kutipan Bulan Ini', value: 'RM 8,450', color: 'text-teal-600', border: 'border-t-teal-500', icon: 'streamline-ultimate:begging-hand-coin-2' },
        { label: 'Invois Tertunggak', value: '2 Kes', color: 'text-orange-600', border: 'border-t-orange-500', icon: 'hugeicons:clock-alert' }
    ],
    recentActivities: [
        { date: 'Selasa, 25 Mei 2026 09:10 PM', module: 'Pembayaran', info: '25 Mei 2026', amount: 'RM 1,200', status: 'Pemakluman', icon: 'fa7-solid:circle-info', statusClass: 'bg-emerald-100 text-emerald-700' },
        { date: 'Ahad, 05 Mei 2026 11:10 AM', module: 'Permintaan penyelenggaraan', info: '02 Mei 2026', amount: 'RM 850', status: 'Tindakan', icon: 'fa7-solid:triangle-exclamation', statusClass: 'bg-red-50 text-red-700' },
        { date: 'Jumaat, 01 Mei 2026 02:55 PM', module: 'Rumah Teres Seksyen 7', info: '01 Mei 2026', amount: 'RM 1,500', status: 'Peringatan', icon: 'mdi:alarm', statusClass: 'bg-amber-100 text-amber-700' }
    ],
    payments: [
        { tenant: 'Ahmad Ridzuan', unit: 'Kondominium Melati - A-10-3', date: '25 Mei 2026', amount: 'RM 1,200', status: 'Berjaya', icon: 'mdi:check-circle-outline', statusClass: 'bg-emerald-100 text-emerald-700' },
        { tenant: 'Siti Nurhaliza', unit: 'Apartment Mawar - B-05-11', date: '02 Mei 2026', amount: 'RM 850', status: 'Berjaya', icon: 'mdi:check-circle-outline', statusClass: 'bg-emerald-100 text-emerald-700' },
        { tenant: 'Khairul Anuar', unit: 'Rumah Teres Seksyen 7', date: '01 Mei 2026', amount: 'RM 1,500', status: 'Tertunggak', icon: 'mdi:close-circle-outline', statusClass: 'bg-amber-100 text-amber-700' }
    ],
    ranking: [
        ['Kondominium Melati (A-10-3)', 'RM 14,400'],
        ['Rumah Teres Seksyen 7', 'RM 12,000'],
        ['Apartment Mawar (B-05-11)', 'RM 10,200'],
        ['Kondominium Melati (B-12-1)', 'RM 9,600'],
        ['Apartment Mawar (A-02-05)', 'RM 8,400'],
        ['Kondominium Melati (C-03-2)', 'RM 7,200'],
        ['Rumah Teres Seksyen 4', 'RM 6,000']
    ],
    timeframeOptions: [
        { label: 'Mingguan', value: 'Weekly' },
        { label: 'Bulanan', value: 'Monthly' },
        { label: 'Tahunan', value: 'Yearly' }
    ]
}

// Optional: Export individual pieces
export const { collectionData, stats, recentActivities, payments, ranking, timeframeOptions } = dashboardData