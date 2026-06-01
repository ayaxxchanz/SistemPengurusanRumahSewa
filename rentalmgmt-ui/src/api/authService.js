import apiClient from '@/api/apiClient';

export async function loginAction(loginForm) {
  const loginData = {
    phone: loginForm.phone,
    password: loginForm.password,
  };

  try {
    const response = await apiClient.post("/auth/login", loginData);

    const { message, user, jwtToken } = response.data;

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

    throw new Error(
      error.response?.data?.message ||
        error.message ||
        "Failed to login. Please try again."
    );
  }
}

export async function registerAction(registerForm) {
  const registerData = {
    fullName: registerForm.fullName,
    role: registerForm.role,
    email: registerForm.email,
    mobileNumber: registerForm.mobileNumber,
    password: registerForm.password,
  };

  try {
    const response = await apiClient.post("/auth/register", registerData);
    
    return {
      success: true,
      data: response.data,
    };
  } catch (error) {
    // Handle specific backend errors (e.g., Email already exists)
    const serverMessage = error.response?.data?.message || "Pendaftaran gagal. Sila cuba lagi.";
    return {
      success: false,
      error: serverMessage,
    };
  }
}