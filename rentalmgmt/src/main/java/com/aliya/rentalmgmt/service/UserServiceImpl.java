package com.aliya.rentalmgmt.service;

import com.aliya.rentalmgmt.dto.request.ProfileRequestDto;
import com.aliya.rentalmgmt.dto.response.ProfileResponseDto;
import com.aliya.rentalmgmt.entity.User;
import com.aliya.rentalmgmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public ProfileResponseDto getProfile() {
        User customer = getAuthenticatedCustomer();
        return mapUserToProfileResponseDto(customer);
    }

    @Override
    public ProfileResponseDto updateProfile(ProfileRequestDto profileRequestDto) {
        User user = getAuthenticatedCustomer();
        boolean isEmailUpdated = !user.getEmail().equals(profileRequestDto.getEmail().trim());
        BeanUtils.copyProperties(profileRequestDto, user);
        user = userRepository.save(user);
        ProfileResponseDto profileResponseDto = mapUserToProfileResponseDto(user);
        //profileResponseDto.setEmailUpdated(isEmailUpdated);
        return profileResponseDto;
    }

    public User getAuthenticatedCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found")
                );
    }

    private ProfileResponseDto mapUserToProfileResponseDto(User user) {
        ProfileResponseDto profileResponseDto = new ProfileResponseDto();
        BeanUtils.copyProperties(user, profileResponseDto);
        return profileResponseDto;
    }

//    @Override
//    public ProfileResponseDto getProfile(Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
//        return mapUserToProfileResponseDto(user);
//    }
//
//    @Override
//    public ProfileResponseDto updateProfile(Long id, ProfileRequestDto profileRequestDto) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
//        BeanUtils.copyProperties(profileRequestDto, user);
//
//        user = userRepository.save(user);
//        return mapUserToProfileResponseDto(user);
//    }
//
//    private ProfileResponseDto mapUserToProfileResponseDto(User user) {
//        ProfileResponseDto profileResponseDto = new ProfileResponseDto();
//        BeanUtils.copyProperties(user, profileResponseDto);
//        return profileResponseDto;
//    }
}
