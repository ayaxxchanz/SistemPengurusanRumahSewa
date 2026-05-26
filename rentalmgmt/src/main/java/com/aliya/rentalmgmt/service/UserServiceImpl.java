package com.aliya.rentalmgmt.service;

import com.aliya.rentalmgmt.dto.request.ProfileRequestDto;
import com.aliya.rentalmgmt.dto.response.ProfileResponseDto;
import com.aliya.rentalmgmt.entity.User;
import com.aliya.rentalmgmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public ProfileResponseDto getProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return mapUserToProfileResponseDto(user);
    }

    @Override
    public ProfileResponseDto updateProfile(Long id, ProfileRequestDto profileRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        BeanUtils.copyProperties(profileRequestDto, user);

        user = userRepository.save(user);
        return mapUserToProfileResponseDto(user);
    }

    private ProfileResponseDto mapUserToProfileResponseDto(User user) {
        ProfileResponseDto profileResponseDto = new ProfileResponseDto();
        BeanUtils.copyProperties(user, profileResponseDto);
        return profileResponseDto;
    }
}
