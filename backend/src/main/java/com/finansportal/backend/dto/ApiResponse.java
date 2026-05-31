package com.finansportal.backend.dto;

// Java 21 Record — Lombok gerekmez
public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {
    // Statik factory metodlar
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "Başarılı", data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}