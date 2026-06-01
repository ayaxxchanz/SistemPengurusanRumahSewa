package com.aliya.rentalmgmt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {

    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 150, message = "Name must be between 5 and 150 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email address must be a valid value")
    private String email;

    @NotBlank(message = "Role is required")
    @Pattern(
            regexp = "^(TENANT|LANDLORD)$",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Role must be either TENANT or LANDLORD"
    )
    private String role;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\d{11,12}$", message = "Mobile number must be 11 or 12 digits")
    private String mobileNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 255, message = "Password must be at least 8 characters")
    private String password;
}