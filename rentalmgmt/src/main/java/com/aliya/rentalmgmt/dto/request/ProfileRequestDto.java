package com.aliya.rentalmgmt.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProfileRequestDto {
    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 100 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email address must be a valid value")
    private String email;

    @NotBlank(message = "Phone cannot be empty")
    @Pattern(regexp = "^\\d{11,12}$", message = "Phone must be 11 or 12 digits")
    private String phone;
}