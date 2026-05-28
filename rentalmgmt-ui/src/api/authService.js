import apiClient from '@/api/apiClient';

export async function loginAction(loginForm) {
  const loginData = {
    username: loginForm.phone,
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