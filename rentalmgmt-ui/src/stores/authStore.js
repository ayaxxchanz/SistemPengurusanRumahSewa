import { defineStore } from "pinia";
import { ref, computed } from "vue";
import { loginAction, registerAction, getProfileAction, updateProfileAction } from "@/api/authService";
import { tokenManager } from "@/api/apiClient";

const isLoading = ref(false);
const serverError = ref(null);
const fieldErrors = ref(null);
const isSuccess = ref(false);

// Composable for registration state
export const useRegisterStore = defineStore("register", () => {
  const resetState = () => {
    isLoading.value = false;
    serverError.value = null;
    fieldErrors.value = null;
    isSuccess.value = false;
  };

  const clearErrors = () => {
    serverError.value = null;
    fieldErrors.value = null;
  };

  async function submitRegistration(formData) {
    resetState();
    isLoading.value = true;

    const result = await registerAction(formData);
    isLoading.value = false;

    if (!result.success) {
      // Check if the error looks like an internal system/URI error or general crash
      const isServerError = result.status === 500 || (result.error && result.error.includes("uri="));
      
      if (isServerError) {
        serverError.value = "We're experiencing technical difficulties on our servers. Please try again shortly.";
      } else {
        // Fallback for expected backend validation/business rule errors
        serverError.value = result.error || "An unexpected error occurred.";
      }

      fieldErrors.value = result.fieldErrors;
      return false;
    }

    isSuccess.value = true;
    return true;
  }

  return {
    isLoading,
    serverError,
    fieldErrors,
    isSuccess,
    submitRegistration,
    clearErrors,
    resetState,
  };
});

// Main auth store
export const useAuthStore = defineStore("auth", {
  state: () => ({
    user: null,
    accessToken: tokenManager.getToken(),
    isAuthenticated: tokenManager.hasToken(),
    isLoading: false,
    authError: null,
  }),

  

  getters: {
    profile: (state) => ({
      id: state.user?.id ?? null,
      fullName: state.user?.fullName ?? "",
      displayName: state.user?.displayName ?? "",
      email: state.user?.email ?? "",
      phone: state.user?.phone ?? "",
      emergencyContact: state.user?.emergencyContact ?? "",
      roles: state.user?.roles ?? [],
      address: {
        street: state.user?.address?.street ?? "",
        city: state.user?.address?.city ?? "",
        state: state.user?.address?.state ?? "",
        postalCode: state.user?.address?.postalCode ?? "",
        country: state.user?.address?.country ?? "",
      },
    }),
    userRole: (state) => state.user?.roles?.join(", ") || "",
    isAuthLoading: (state) => state.isLoading,
    hasAuthError: (state) => !!state.authError,
    roleLabel: (state) => {
      const roleMap = {
        ROLE_LANDLORD: "Landlord",
        ROLE_TENANT: "Tenant",
        ROLE_ADMIN: "Admin"
      };

      const role = state.user?.roles?.[0];
      return roleMap[role] || "User";
    },
    address: (state) => {
      const addressMap = {
        street: state.user?.street || "",
        city: state.user?.city || "",
        state: state.user?.state || "",
        postalCode: state.user?.postalCode || "",
      };
      return addressMap;
    }
  },

  actions: {
    setLoading(loading) {
      this.isLoading = loading;
    },

    setError(error) {
      this.authError = error;
    },

    clearError() {
      this.authError = null;
    },

    async login(loginForm) {
      this.setLoading(true);
      this.clearError();

      const result = await loginAction(loginForm);

      if (result.success) {
        this.user = result.user;
        this.accessToken = result.jwtToken;
        this.isAuthenticated = true;
        await this.fetchProfile();
      } else {
        this.authError = result.errors?.message || "Login failed";
      }

      this.setLoading(false);
      return result;
    },

    async fetchProfile() {
      if (!this.isAuthenticated) return null;
      
      const result = await getProfileAction();
      if (result.success && result.data) {
        this.user = result.data;
      }
      return result;
    },

    async updateProfile(profileData) {
      this.setLoading(true);
      const result = await updateProfileAction(profileData);
      
      if (result.success && result.data) {
        this.user = result.data;
        // If backend doesn't return full user object, refetch
        if (!result.data.role || !result.data.email) {
          await this.fetchProfile();
        }
      }
      
      this.setLoading(false);
      return result;
    },

    logout() {
      this.user = null;
      this.accessToken = null;
      this.isAuthenticated = false;
      this.authError = null;
      tokenManager.removeToken();
    },

    async initAuth() {
      if (this.isAuthenticated && !this.user) {
        await this.fetchProfile();
      }
    },
  },
});

// Composable for using auth store in components
export function useAuth() {
  const authStore = useAuthStore();
  
  return {
    user: computed(() => authStore.user),
    isAuthenticated: computed(() => authStore.isAuthenticated),
    isLoading: computed(() => authStore.isLoading),
    error: computed(() => authStore.authError),
    userName: computed(() => authStore.userName),
    userEmail: computed(() => authStore.userEmail),
    userRole: computed(() => authStore.userRole),
    roleLabel: computed(() => authStore.roleLabel),
    profile: computed(() => authStore.profile),
    login: authStore.login,
    logout: authStore.logout,
    updateProfile: authStore.updateProfile,
    fetchProfile: authStore.fetchProfile,
    clearError: authStore.clearError,
  };
}