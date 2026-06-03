package com.aliya.rentalmgmt.dto.response;

import com.aliya.rentalmgmt.dto.AddressDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ProfileResponseDto {
    private Long id;
    private String fullName;
    private String displayName;
    private String email;
    private String phone;
    private String emergencyContact;
    private Set<String> roles;
    private AddressDto address;
    //private boolean emailUpdated;
}
