import { defineStore } from "pinia";
import { loginAction } from "@/api/authService";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    user: null,
    accessToken: localStorage.getItem("accessToken") || null,
    isAuthenticated: !!localStorage.getItem("accessToken"),
  }),

  actions: {
    async login(loginForm) {
      const result = await loginAction(loginForm);

      if (result.success) {
        this.user = result.user;
        this.accessToken = result.jwtToken;
        this.isAuthenticated = true;

        localStorage.setItem(
          "accessToken",
          result.jwtToken
        );
      }

      return result;
    },

    logout() {
      this.user = null;
      this.accessToken = null;
      this.isAuthenticated = false;

      localStorage.removeItem("accessToken");
    },
  },
});

export const useRegisterStore = defineStore('register', () => {
  const isLoading = ref(false);
  const serverError = ref(null);

  async function submitRegistration(formData) {
    isLoading.value = true;
    serverError.value = null;

    const result = await registerAction(formData);
    isLoading.value = false;

    if (!result.success) {
      serverError.value = result.error;
      return false;
    }

    return true;
  }

  return {
    isLoading,
    serverError,
    submitRegistration,
  };
});