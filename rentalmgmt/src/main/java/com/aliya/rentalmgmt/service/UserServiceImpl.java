package com.aliya.rentalmgmt.service;

import com.aliya.rentalmgmt.dto.AddressDto;
import com.aliya.rentalmgmt.dto.request.ProfileRequestDto;
import com.aliya.rentalmgmt.dto.response.ProfileResponseDto;
import com.aliya.rentalmgmt.entity.Address;
import com.aliya.rentalmgmt.entity.User;
import com.aliya.rentalmgmt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public ProfileResponseDto getProfile() {
        User user = getAuthenticatedUser();
        return mapUserToProfileResponseDto(user);
    }

    @Override
    public ProfileResponseDto updateProfile(ProfileRequestDto profileRequestDto) {
        User user = getAuthenticatedUser();
        //boolean isEmailUpdated = !user.getEmail().equals(profileRequestDto.getEmail().trim());
        BeanUtils.copyProperties(profileRequestDto, user);
        Address address = user.getAddress();
        if(address == null) {
            address = new Address();
            address.setUser(user);
        }
        address.setStreet(profileRequestDto.getStreet());
        address.setCity(profileRequestDto.getCity());
        address.setPostalCode(profileRequestDto.getPostalCode());
        address.setState(profileRequestDto.getState());
        address.setCountry(profileRequestDto.getCountry());
        user.setAddress(address);
        user = userRepository.save(user);
        ProfileResponseDto profileResponseDto = mapUserToProfileResponseDto(user);
        //profileResponseDto.setEmailUpdated(isEmailUpdated);
        return profileResponseDto;
    }

    public User getAuthenticatedUser() {
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
        if(user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            BeanUtils.copyProperties(user.getAddress(), addressDto);
            profileResponseDto.setAddress(addressDto);
        }
        // Map Set<Role> or Set<UserRoles> to Set<String> cleanly
        Set<String> stringRoles = user.getRoles().stream()
                .map(role -> role.getName().toString()) // assuming getName() returns the Enum or String
                .collect(Collectors.toSet());

        profileResponseDto.setRoles(stringRoles);
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
