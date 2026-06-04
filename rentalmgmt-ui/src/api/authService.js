import apiClient, { tokenManager } from "@/api/apiClient";

// API endpoints
const ENDPOINTS = {
  LOGIN: "/auth/login",
  REGISTER: "/auth/register",
  PROFILE: "/user",
};

// Error messages
const ERROR_MESSAGES = {
  LOGIN_FAILED: "Failed to login. Please try again.",
  REGISTER_FAILED: "Failed to register. Please try again.",
  NETWORK_ERROR: "Network error. Please check your connection.",
};

// Helper to extract error message from response
const extractErrorMessage = (error, defaultMessage) => {
  if (error.response?.data?.message) {
    return error.response.data.message;
  }
  if (error.response?.data && typeof error.response.data === "object") {
    // Handle field-specific errors (e.g., { email: "Email already exists" })
    const firstError = Object.values(error.response.data)[0];
    if (typeof firstError === "string") return firstError;
  }
  return error.message || defaultMessage;
};

// Login service
export async function loginAction(loginForm) {
  const loginData = {
    email: loginForm.email,
    password: loginForm.password,
  };

  try {
    const response = await apiClient.post(ENDPOINTS.LOGIN, loginData);
    const { message, user, jwtToken } = response.data;

    // Store token
    if (jwtToken) {
      tokenManager.setToken(jwtToken);
    }

    return {
      success: true,
      message,
      user,
      jwtToken,
    };
  } catch (error) {
    if (error.response?.status === 401) {
      return {
        success: false,
        errors: {
          message: "Invalid username or password",
        },
      };
    }

    // Handle other errors
    const errorMessage = extractErrorMessage(error, ERROR_MESSAGES.LOGIN_FAILED);
    return {
      success: false,
      errors: {
        message: errorMessage,
      },
    };
  }
}

// Register service
export async function registerAction(registerForm) {
  const registerData = {
    fullName: registerForm.fullName,
    displayName: registerForm.displayName,
    role: registerForm.role.toUpperCase(),
    email: registerForm.email,
    phone: registerForm.phone,
    emergencyContact: registerForm.emergencyContact,
    address: {
      street: registerForm.street,
      postalCode: registerForm.postalCode,
      city: registerForm.city,
      state: registerForm.state,
      country: registerForm.country,
    },
    password: registerForm.password,
  };

  try {
    const response = await apiClient.post(ENDPOINTS.REGISTER, registerData);
    
    return {
      success: true,
      data: response.data,
      message: typeof response.data === "string" ? response.data : "Registration successful",
    };
  } catch (error) {
    const serverMessage = extractErrorMessage(error, ERROR_MESSAGES.REGISTER_FAILED);
    
    return {
      success: false,
      error: serverMessage,
      fieldErrors: error.response?.data && typeof error.response.data === "object" 
        ? error.response.data 
        : null,
    };
  }
}

// Profile service
export async function getProfileAction() {
  try {
    const response = await apiClient.get(ENDPOINTS.PROFILE);
    return {
      success: true,
      data: response.data,
    };
  } catch (error) {
    const errorMessage = extractErrorMessage(error, "Failed to fetch profile");
    return {
      success: false,
      error: errorMessage,
    };
  }
}

export async function updateProfileAction(profileData) {
  try {
    const response = await apiClient.put(ENDPOINTS.PROFILE, profileData);
    return {
      success: true,
      data: response.data,
    };
  } catch (error) {
    const errorMessage = extractErrorMessage(error, "Failed to update profile");
    return {
      success: false,
      error: errorMessage,
      fieldErrors: error.response?.data,
    };
  }
}