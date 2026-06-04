package com.aliya.rentalmgmt.dto;

import com.aliya.rentalmgmt.enums.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RegisterRequestDto {

    @NotBlank(message = "Full name is required")
    @Size(min = 5, max = 150, message = "Name must be between 5 and 150 characters")
    private String fullName;

    @NotBlank(message = "Display name is required")
    @Size(min = 5, max = 150, message = "Name must be between 5 and 150 characters")
    private String displayName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email address must be a valid value")
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{11,12}$", message = "Phone number must be 11 or 12 digits")
    private String phone;

    @NotBlank(message = "Emergency contact is required")
    @Pattern(regexp = "^\\d{11,12}$", message = "Emergency contact must be 11 or 12 digits")
    private String emergencyContact;

    @Valid // This triggers validation on the nested AddressDto fields
    @NotNull(message = "Address information is required")
    private AddressDto address;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be at least 8 characters")
    private String password;
}