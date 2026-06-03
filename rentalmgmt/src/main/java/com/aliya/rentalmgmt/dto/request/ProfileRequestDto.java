package com.aliya.rentalmgmt.dto.request;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProfileRequestDto {
    @NotBlank(message = "Full name is required")
    @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 100 characters")
    private String fullName;

    @NotBlank(message = "Display name is required")
    @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 100 characters")
    private String displayName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email address must be a valid value")
    private String email;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "^\\d{11,12}$", message = "Phone must be 11 or 12 digits")
    private String phone;

    @NotBlank(message = "Emergency contact cannot be empty")
    @Pattern(regexp = "^\\d{11,12}$", message = "Phone must be 11 or 12 digits")
    private String emergencyContact;

    @NotBlank(message = "Street is required")
    @Size(min = 5, max = 50, message = "The length of the street should be between 5 and 50 characters")
    private String street;

    @NotBlank(message = "Postal code is required")
    @Pattern(regexp = "^\\d{5}$", message = "Postal code must be exactly 5 digits")
    private String postalCode;

    @NotBlank(message = "City is required")
    @Size(min = 3, max = 30, message = "The length of the city should be between 3 and 30 characters")
    private String city;

    @NotBlank(message = "State is required")
    @Size(min = 2, max = 30, message = "The length of the state should be between 2 and 30 characters")
    private String state;

    @NotBlank(message = "Country is required")
    @Size(min = 2, max = 2, message = "The length of the country should be exactly 2 characters")
    private String country;
}