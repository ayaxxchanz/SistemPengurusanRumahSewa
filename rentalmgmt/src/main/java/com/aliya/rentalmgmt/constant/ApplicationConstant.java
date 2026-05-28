package com.aliya.rentalmgmt.constant;

public class ApplicationConstant {
    private ApplicationConstant() {
        throw new AssertionError("Utility class cannot be instantiated.");
    }

    public static final String JWT_SECRET_KEY = "JWT_SECRET";
    public static final String JWT_SECRET_DEFAULT_VALUE = "your-super-secret-key";
    public static final String JWT_HEADER = "Authorization"; // same header name as the one defined in frontend inside apiClient.js
}
