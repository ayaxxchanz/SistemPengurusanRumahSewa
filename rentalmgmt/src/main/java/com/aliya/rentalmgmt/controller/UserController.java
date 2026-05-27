package com.aliya.rentalmgmt.controller;

import com.aliya.rentalmgmt.dto.request.ProfileRequestDto;
import com.aliya.rentalmgmt.dto.response.ProfileResponseDto;
import com.aliya.rentalmgmt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(){
        ProfileResponseDto profileResponseDto = userService.getProfile();
        return ResponseEntity.ok(profileResponseDto);
    }

    @PutMapping
    public ResponseEntity<ProfileResponseDto> updateProfile(@Validated @RequestBody ProfileRequestDto profileRequestDto){
        ProfileResponseDto profileResponseDto = userService.updateProfile(profileRequestDto);
        return ResponseEntity.ok(profileResponseDto);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable Long id){
//        ProfileResponseDto profileResponseDto = userService.getProfile(id);
//        return ResponseEntity.ok(profileResponseDto);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ProfileResponseDto> updateProfile(@PathVariable Long id,
//                                                            @Validated @RequestBody ProfileRequestDto profileRequestDto){
//        ProfileResponseDto profileResponseDto = userService.updateProfile(id, profileRequestDto);
//        return ResponseEntity.ok(profileResponseDto);
//    }
}
