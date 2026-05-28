package com.aliya.rentalmgmt.controller;

import com.aliya.rentalmgmt.dto.request.LoginRequestDto;
import com.aliya.rentalmgmt.dto.response.LoginResponseDto;
import com.aliya.rentalmgmt.dto.RegisterRequestDto;
import com.aliya.rentalmgmt.dto.UserDto;
import com.aliya.rentalmgmt.entity.User;
import com.aliya.rentalmgmt.entity.Role;
import com.aliya.rentalmgmt.repository.RoleRepository;
import com.aliya.rentalmgmt.repository.UserRepository;
import com.aliya.rentalmgmt.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CompromisedPasswordChecker compromisedPasswordChecker;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> apiLogin(@RequestBody LoginRequestDto loginRequestDto) {

        // authenticate manually instead of using the spring security filter
        try {
            Authentication authentication = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(loginRequestDto.phone(),
                    loginRequestDto.password()));
            var userDto = new UserDto();
            var loggedInUser = (User) authentication.getPrincipal();
            BeanUtils.copyProperties(loggedInUser, userDto);
            String jwtToken = jwtUtil.generateJwtToken(authentication);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new LoginResponseDto(HttpStatus.OK.getReasonPhrase(),
                            userDto, jwtToken));
        } catch (BadCredentialsException ex) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED,
                    "Invalid username or password");
        } catch (AuthenticationException ex) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED,
                    "Authentication failed");
        } catch (Exception ex) {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        CompromisedPasswordDecision decision = compromisedPasswordChecker.check(registerRequestDto.getPassword());
        if(decision.isCompromised()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("password", "Choose a strong password"));
        }
        Optional<User> existingUser = userRepository.findByEmailOrPhone(
                registerRequestDto.getEmail(),
                registerRequestDto.getMobileNumber()
        );
        if (existingUser.isPresent()) {
            Map <String, String> errors = new HashMap<>();
            User user = existingUser.get();

            if(user.getEmail().equals(registerRequestDto.getEmail())){
                errors.put("email", "Email already exists");
            }
            if(user.getPhone() != null && user.getPhone().equals(registerRequestDto.getMobileNumber())){
                errors.put("mobileNumber", "Mobile number already exists");
            }

            return ResponseEntity.badRequest().body(errors);
        }

        User user = new User();
        BeanUtils.copyProperties(registerRequestDto, user);
        user.setPhone(registerRequestDto.getMobileNumber());
        user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
        Role tenantRole = roleRepository.findByName("ROLE_TENANT")
            .orElseGet(() -> roleRepository.save(new Role("ROLE_TENANT")));
        user.setRoles(Set.of(tenantRole));
        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Registered Successfully");
    }

    private ResponseEntity<LoginResponseDto> buildErrorResponse(HttpStatus status,
                                                                String message) {
        return ResponseEntity
                .status(status)
                .body(new LoginResponseDto(message, null, null));
    }
}