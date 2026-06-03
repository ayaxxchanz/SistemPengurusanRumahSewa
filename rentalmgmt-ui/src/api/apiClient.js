import axios from "axios";

// Token management utilities
const TOKEN_KEY = "accessToken";

export const tokenManager = {
  getToken: () => localStorage.getItem(TOKEN_KEY),
  setToken: (token) => {
    if (token) {
      localStorage.setItem(TOKEN_KEY, token);
    } else {
      tokenManager.removeToken();
    }
  },
  removeToken: () => localStorage.removeItem(TOKEN_KEY),
  hasToken: () => !!tokenManager.getToken(),
};

// Create axios instance with default config
const createApiClient = () => {
  const client = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
    },
    timeout: 10000,
    withCredentials: true,
  });

  // Request interceptor for auth token
  client.interceptors.request.use(
    (config) => {
      const token = tokenManager.getToken();
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    (error) => Promise.reject(error)
  );

  // Response interceptor for handling auth errors
  client.interceptors.response.use(
    (response) => response,
    (error) => {
      // Handle 401 Unauthorized globally
      if (error.response?.status === 401) {
        tokenManager.removeToken();
      }
      return Promise.reject(error);
    }
  );

  return client;
};

const apiClient = createApiClient();
export default apiClient;