package com.aliya.rentalmgmt.dto;

import com.aliya.rentalmgmt.entity.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@ToString
public class UserDto {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private Set<Role> roles;
    private boolean isActive;
}