package com.aliya.rentalmgmt.service;

import com.aliya.rentalmgmt.dto.request.ProfileRequestDto;
import com.aliya.rentalmgmt.dto.response.ProfileResponseDto;

public interface UserService {

//    ProfileResponseDto getProfile(Long id);
//    ProfileResponseDto updateProfile(Long id, ProfileRequestDto profileRequestDto);

    ProfileResponseDto getProfile();
    ProfileResponseDto updateProfile(ProfileRequestDto profileRequestDto);
}
