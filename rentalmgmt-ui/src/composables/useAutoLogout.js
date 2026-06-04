import { onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';
import apiClient, { tokenManager } from '@/api/apiClient';

export default function useAutoLogout() {
    const router = useRouter();
    let logoutTimer = null;
    let refreshTimer = null;

    const ONE_HOUR = 60 * 60 * 1000;
    const FORTY_FIVE_MINUTES = 45 * 60 * 1000;
    const EVENTS = ['mousemove', 'mousedown', 'keypress', 'scroll', 'touchstart'];

    const logout = () => {
        tokenManager.removeToken();
        router.push('/login');
        clearTimers();
    };

    const clearTimers = () => {
        if (logoutTimer) clearTimeout(logoutTimer);
        if (refreshTimer) clearTimeout(refreshTimer);
    };

    const resetActivityTimer = () => {
        clearTimeout(logoutTimer);
        logoutTimer = setTimeout(logout, ONE_HOUR);
    };

    const sendRefreshRequest = async () => {
        if (!tokenManager.hasToken()) return;

        try {
            const response = await apiClient.post('/auth/refresh');
            tokenManager.setToken(response.data.token);
            scheduleRefresh();
        } catch (error) {
            // Only log out if the token is genuinely invalid/expired (Status 401 or 403)
            if (error.response && (error.response.status === 401 || error.response.status === 403)) {
                logout();
            } else {
                // Network glitch or minor error; try rescheduling instead of killing the session
                scheduleRefresh();
            }
        }
    };

    const scheduleRefresh = () => {
        clearTimeout(refreshTimer);
        refreshTimer = setTimeout(sendRefreshRequest, FORTY_FIVE_MINUTES);
    };

    const setupEventListeners = () => {
        EVENTS.forEach(event => {
            window.addEventListener(event, resetActivityTimer);
        });
    };

    const removeEventListeners = () => {
        EVENTS.forEach(event => {
            window.removeEventListener(event, resetActivityTimer);
        });
        clearTimers();
    };

    onMounted(() => {
        setupEventListeners();
        resetActivityTimer();
        scheduleRefresh();
    });

    onUnmounted(() => {
        removeEventListeners();
    });
}