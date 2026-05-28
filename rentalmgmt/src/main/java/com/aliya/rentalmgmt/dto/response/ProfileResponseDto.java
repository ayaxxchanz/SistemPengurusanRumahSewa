package com.aliya.rentalmgmt.dto.response;

// import com.aliya.rentalmgmt.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
// import lombok.ToString;

@Setter
@Getter
// @ToString // Commented out: Not needed for simple response DTO
public class ProfileResponseDto {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    //private UserRole role;
}
