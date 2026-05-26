package com.aliya.rentalmgmt.dto.response;

import com.aliya.rentalmgmt.dto.UserDto;

public record LoginResponseDto(String message, UserDto user, String jwtToken) {
}